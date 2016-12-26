package com.tofi.service.impl;

import com.tofi.model.Credit;
import com.tofi.model.enums.Currency;
import com.tofi.repository.BankRepository;
import com.tofi.repository.CreditRepository;
import com.tofi.repository.PercentageTermRepository;
import com.tofi.repository.enums.*;
import com.tofi.service.CreditService;
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
 * Created by ulian_000 on 12.12.2016.
 */
@Transactional
public class CreditServiceImpl implements CreditService {
    private final Logger log = LoggerFactory.getLogger(CreditServiceImpl.class);

    @Autowired
    CreditRepository creditRepository;
    @Autowired
    CreditGoalRepository goalRepository;
    @Autowired
    ClientTypeRepository clientTypeRepository;
    @Autowired
    RepaymentMethodRepository repaymentMethodRepository;
    @Autowired
    BankRepository bankRepository;
    @Autowired
    PercentageTermRepository percentageTermRepository;
    @Autowired
    PaymentPosibilityRepository paymentPosibilityRepository;
    @Autowired
    EnumService enumService;

    @Override
    @Transactional
    @Async
    public void updateCredits(List<Credit> creditRecords) {
        deleteOldTermsForCredits(creditRecords);
        Map<String, Currency> existingCurrencies = enumService.persistCurrencies(
                creditRecords.stream().map(Credit::getTerms).flatMap(List::stream).collect(Collectors.toList()));
        List<Credit> persistCredits = new ArrayList<>();

        for (Credit credit : creditRecords) {
            try {
                Credit persistentCredit = creditRepository
                        .findOneByAgregatorName(credit.getAgregatorName())
                        .orElse(credit);

                mergeFields(persistentCredit, credit, existingCurrencies);
                persistCredits.add(persistentCredit);
            } catch (Exception ex) {
                log.error("Unexpected error on credit record merge: " + ex.getMessage());
            }
        }

        creditRepository.save(creditRecords);
        creditRepository.flush();

        log.info("Credits were updated");
    }

    @Override
    @Transactional
    public List<Credit> list() {
        return creditRepository.findAll();
    }


    private void deleteOldTermsForCredits(List<Credit> creditRecords) {
        percentageTermRepository.deleteAllByCreditIn(creditRepository.findAllByAgregatorNameIn(
                creditRecords
                        .parallelStream()
                        .map(Credit::getAgregatorName)
                        .collect(Collectors.toList())
        ));
        percentageTermRepository.flush();
    }

    private void mergeFields(Credit target, Credit source, Map<String, Currency> existingCurrencies) {
        log.info("clientType Id1: " + target.getClientType().getId());
        log.info("clientType Name1: " + target.getClientType().getName());

        target.setClientType(
                source.getClientType() == null ?
                        null :
                        clientTypeRepository
                                .findOneByName(source.getClientType().getName())
                                .orElse(source.getClientType()));

        log.info("clientType Id2: " + target.getClientType().getId());
        log.info("clientType Name2: " + target.getClientType().getName());

        target.setGoal(
                source.getGoal() == null ?
                        null :
                        goalRepository
                                .findOneByName(source.getGoal().getName())
                                .orElse(source.getGoal()));

        target.setRepaymentMethod(
                source.getRepaymentMethod() == null ?
                        null :
                        repaymentMethodRepository
                                .findOneByName(source.getRepaymentMethod().getName())
                                .orElse(source.getRepaymentMethod()));
        target.setBank(
                source.getBank() == null ?
                        null :
                        bankRepository
                                .findOneByName(source.getBank().getName())
                                .orElse(source.getBank()));
        target.setPaymentPosibilities(source.getPaymentPosibilities()
                .stream()
                .map(posibility -> paymentPosibilityRepository.findOneByName(posibility.getName()).orElse(posibility))
                .collect(Collectors.toList()));

        source.getTerms()
                .forEach(term -> {
                    term.setCredit(target);
                    term.setCurrency(
                            term.getCurrency() == null ?
                                    null :
                                    existingCurrencies.get(term.getCurrency().getName()));
                });

        target.setTerms(source.getTerms());

        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setGracePeriod(source.getGracePeriod());
        target.setNeedCertificates(source.getNeedCertificates());
        target.setNeedsGurantor(source.getNeedsGurantor());
        target.setPledge(source.getPledge());
        target.setPrePayments(source.getPrePayments());
        target.setUpdateDate(source.getUpdateDate());
        target.setUrl(source.getUrl());

        persistNewEnums(target);
    }

    private void persistNewEnums(Credit persistentCredit) {
        if (persistentCredit.getId() == null) {
            if (persistentCredit.getClientType() != null && persistentCredit.getClientType().getId() == null) {
                clientTypeRepository.save(persistentCredit.getClientType());
                log.info("qqqq persistentCredit error " + persistentCredit.getClientType().getId());
            }
            if (persistentCredit.getGoal() != null && persistentCredit.getGoal().getId() == null) {
                goalRepository.save(persistentCredit.getGoal());
            }
            if (persistentCredit.getRepaymentMethod() != null && persistentCredit.getRepaymentMethod().getId() == null) {
                repaymentMethodRepository.save(persistentCredit.getRepaymentMethod());
            }
            if (persistentCredit.getBank() != null && persistentCredit.getBank().getId() == null) {
                bankRepository.save(persistentCredit.getBank());
            }
            if (persistentCredit.getPaymentPosibilities() != null) {
                persistentCredit.getPaymentPosibilities().forEach(posibility -> {
                    if (posibility.getId() == null) {
                        paymentPosibilityRepository.save(posibility);
                    }
                });
            }
        }
    }

}
