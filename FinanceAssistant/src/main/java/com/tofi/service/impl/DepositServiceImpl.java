package com.tofi.service.impl;

import com.tofi.model.Deposit;
import com.tofi.model.enums.Currency;
import com.tofi.repository.BankRepository;
import com.tofi.repository.DepositRepository;
import com.tofi.repository.PercentageTermRepository;
import com.tofi.repository.enums.ClientTypeRepository;
import com.tofi.repository.enums.PercentageTypeRepository;
import com.tofi.service.DepositService;
import com.tofi.service.EnumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by ulian_000 on 20.12.2016.
 */
@Transactional
@Async
public class DepositServiceImpl implements DepositService{
    Logger log = LoggerFactory.getLogger(DepositServiceImpl.class);
    @Autowired
    DepositRepository depositRepository;
    @Autowired
    PercentageTermRepository percentageTermRepository;
    @Autowired
    ClientTypeRepository clientTypeRepository;
    @Autowired
    BankRepository bankRepository;
    @Autowired
    PercentageTypeRepository percentageTypeRepository;
    @Autowired
    EnumService enumService;

    @Override
    public void updateDeposits(List<Deposit> depositRecords) {
        deleteOldTermsForDeposits(depositRecords);
        Map<String, Currency> existingCurrencies = enumService.persistCurrencies(
                depositRecords.stream().map(deposit -> deposit.getTerms()).flatMap(List::stream).collect(Collectors.toList()));

        List<Deposit> mergedDeposits = new ArrayList<>();

        for (Deposit deposit : depositRecords) {
            try {
                Deposit persistentDeposit = depositRepository
                        .findOneByAgregatorName(deposit.getAgregatorName())
                        .orElse(deposit);

                mergeFields(persistentDeposit, deposit, existingCurrencies);
                mergedDeposits.add(persistentDeposit);
            }
            catch (Exception ex) {
                log.error("Unexpected error on deposit record merge: " + ex.getMessage());
            }
        }

        depositRepository.save(mergedDeposits);
        depositRepository.flush();

        log.info("Deposits were updated");
    }


    @Override
    public List<Deposit> list() {
        return depositRepository.findAll();
    }

    private void deleteOldTermsForDeposits(List<Deposit> depositRecords) {
        percentageTermRepository.deleteAllByDepositIn(depositRepository.findAllByAgregatorNameIn(
                depositRecords
                        .parallelStream()
                        .map(deposit -> deposit.getAgregatorName())
                        .collect(Collectors.toList())
        ));
        percentageTermRepository.flush();
    }

    private void mergeFields(Deposit target, Deposit source, Map<String, Currency> existingCurrencies) {
        target.setClientType(
                source.getClientType() == null ?
                        null :
                        clientTypeRepository
                                .findOneByName(source.getClientType().getName())
                                .orElse(source.getClientType()));
        target.setBank(
                source.getBank() == null ?
                        null :
                        bankRepository
                                .findOneByName(source.getBank().getName())
                                .orElse(source.getBank()));

        target.setPercentageType(
                source.getPercentageType() == null ?
                        null :
                        percentageTypeRepository
                                .findOneByName(source.getPercentageType().getName())
                                .orElse(source.getPercentageType()));
        source.getTerms()
                .forEach(term -> {
                    term.setDeposit(target);
                    term.setCurrency(
                            term.getCurrency() == null ?
                                    null :
                                    existingCurrencies.get(term.getCurrency().getName()));

                });

        target.setName(source.getName());
        target.setCapitalization(source.getCapitalization());
        target.setBeforeTermWithdrawal(source.getBeforeTermWithdrawal());
        target.setUrl(source.getUrl());
        target.setUpdateDate(source.getUpdateDate());
        target.setDescription(source.getDescription());
        target.setTerms(source.getTerms());

        persistNewEnums(target);
    }

    private void persistNewEnums(Deposit persistentDeposit) {
        if (persistentDeposit.getId() == null) {
            if (persistentDeposit.getClientType() != null && persistentDeposit.getClientType().getId() == null) {
                clientTypeRepository.save(persistentDeposit.getClientType());
            }
            if (persistentDeposit.getPercentageType() != null && persistentDeposit.getPercentageType().getId() == null) {
                percentageTypeRepository.save(persistentDeposit.getPercentageType());
            }
            if (persistentDeposit.getBank() != null && persistentDeposit.getBank().getId() == null) {
                bankRepository.save(persistentDeposit.getBank());
            }
        }
    }

}
