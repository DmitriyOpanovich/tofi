package com.tofi.service.impl;

import com.tofi.model.*;
import com.tofi.model.enums.*;
import com.tofi.repository.CreditRepository;
import com.tofi.repository.DepositRepository;
import com.tofi.repository.enums.*;
import com.tofi.repository.specifications.CreditSpecifications;
import com.tofi.repository.specifications.DepositSpecification;
import com.tofi.service.FilterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;

/**
 * Created by ulian_000 on 12.12.2016.
 */
@Transactional
public class FilterServiceImpl implements FilterService{
    Logger log = LoggerFactory.getLogger(CreditServiceImpl.class);

    @Autowired
    ClientTypeRepository clientTypeRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    CreditGoalRepository creditGoalRepository;
    @Autowired
    PaymentPosibilityRepository paymentPosibilityRepository;
    @Autowired
    PercentageTypeRepository percentageTypeRepository;
    @Autowired
    RepaymentMethodRepository repaymentMethodRepository;
    @Autowired
    CreditRepository creditRepository;
    @Autowired
    DepositRepository depositRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ClientType> getAvailableClientTypes() {
        List<ClientType> types = clientTypeRepository.findAll();
        types.forEach(
                clientType -> {
                    try {
                        clientType.setRu_descr(new String(Base64.getDecoder().decode(clientType.getRu_descr())));
                    } catch(IllegalArgumentException ex) {
                        log.error("Illegal base64 string", clientType.getRu_descr());
                    }
                }
        );

        return types;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Currency> getAvailableCurrencies() {
        List<Currency> currencies = currencyRepository.findAll();
        currencies.forEach(
                curr -> {
                    try {
                        curr.setRu_descr(new String(Base64.getDecoder().decode(curr.getRu_descr())));
                    } catch(IllegalArgumentException ex) {
                        log.error("Illegal base64 string", curr.getRu_descr());
                    }
                }
        );

        return currencies;
    }

    @Override
    public List<CreditGoal> getAvailableCreditGoals() {
        List<CreditGoal> goals = creditGoalRepository.findAll();
        goals.forEach(
                goal -> {
                    try {
                        goal.setRu_descr(new String(Base64.getDecoder().decode(goal.getRu_descr())));
                    } catch(IllegalArgumentException ex) {
                        log.error("Illegal base64 string", goal.getRu_descr());
                    }
                }
        );

        return goals;
    }

    @Override
    public List<PaymentPosibility> getAvailablePaymentPosibilities() {
        List<PaymentPosibility> posibilities = paymentPosibilityRepository.findAll();
        posibilities.forEach(
                posibility -> {
                    try {
                        posibility.setRu_descr(new String(Base64.getDecoder().decode(posibility.getRu_descr())));
                    } catch(IllegalArgumentException ex) {
                        log.error("Illegal base64 string", posibility.getRu_descr());
                    }
                }
        );

        return posibilities;
    }

    @Override
    public List<PercentageType> getAvailablePercentageTypes() {
        List<PercentageType> types = percentageTypeRepository.findAll();
        types.forEach(
                type -> {
                    try {
                        type.setRu_descr(new String(Base64.getDecoder().decode(type.getRu_descr())));
                    } catch(IllegalArgumentException ex) {
                        log.error("Illegal base64 string", type.getRu_descr());
                    }
                }
        );

        return types;
    }

    @Override
    public List<RepaymentMethod> getAvailableRepaymentMethods() {
        List<RepaymentMethod> methods = repaymentMethodRepository.findAll();
        methods.forEach(
                method -> {
                    try {
                        method.setRu_descr(new String(Base64.getDecoder().decode(method.getRu_descr())));
                    } catch(IllegalArgumentException ex) {
                        log.error("Illegal base64 string", method.getRu_descr());
                    }
                }
        );

        return methods;
    }

    @Override
    public List<Credit> filterCredits(CreditFilter filter, BotUser user) {
        return creditRepository.findAll(CreditSpecifications.matchFilter(filter));
    }

    @Override
    public List<Deposit> filterDeposits(DepositFilter filter, BotUser user) {
        return depositRepository.findAll(DepositSpecification.matchFilter(filter));
    }


}
