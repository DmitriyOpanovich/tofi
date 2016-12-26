package com.tofi.service.impl;

import com.tofi.model.*;
import com.tofi.model.enums.*;
import com.tofi.repository.CreditRepository;
import com.tofi.repository.DepositRepository;
import com.tofi.repository.enums.*;
import com.tofi.repository.specifications.CreditSpecifications;
import com.tofi.repository.specifications.DepositSpecification;
import com.tofi.service.FilterService;
import com.tofi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ulian_000 on 12.12.2016.
 */
@Transactional
public class FilterServiceImpl implements FilterService{
    private final static Integer MAX_CREDITS_FETCH_SIZE = 10;
    private final static Integer MAX_DEPOSITS_FETCH_SIZE = 10;

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
    @Autowired
    UserService userService;

    @Override
    @Transactional(readOnly = true)
    public List<ClientType> getAvailableClientTypes() {
        List<ClientType> types = clientTypeRepository.findAll();
//        types.forEach(
//                clientType -> {
//                    try {
//                        clientType.setRu_descr(new String(Base64.getDecoder().decode(clientType.getRu_descr())));
//                    } catch(IllegalArgumentException ex) {
//                        log.error("Illegal base64 string", clientType.getRu_descr());
//                    }
//                }
//        );

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
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
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
    @Transactional
    public List<Credit> filterCredits(CreditFilter filter, BotUser user) {
        BotUser registeredUser = userService.registerUserFromTelegram(user);
        mergeCreditFilterFields(filter, filter);
        List<Credit> credits = creditRepository.findAll(CreditSpecifications.matchFilter(filter));
        credits = credits.stream().distinct().limit(MAX_CREDITS_FETCH_SIZE).collect(Collectors.toList());
        userService.logCreditSearchHistory(filter, credits, registeredUser);
        return credits;
    }

    @Override
    @Transactional
    public List<Deposit> filterDeposits(DepositFilter filter, BotUser user) {
        BotUser registeredUser = userService.registerUserFromTelegram(user);
        mergeDepositFilterFields(filter,filter);
        List<Deposit> deposits = depositRepository.findAll(DepositSpecification.matchFilter(filter));
        deposits = deposits.stream().distinct().limit(MAX_CREDITS_FETCH_SIZE).collect(Collectors.toList());
        userService.logDepositSearchHistory(filter, deposits, registeredUser);
        return deposits;
    }

    private void mergeCreditFilterFields(CreditFilter target, CreditFilter source) {
        target.setCurrency(currencyRepository.findOneByName(source.getCurrency().getName()).orElse(null));
        target.setClientType(clientTypeRepository.findOneByName(source.getClientType().getName()).orElse(null));
        target.setGoal(creditGoalRepository.findOneByName(source.getGoal().getName()).orElse(null));

        if (source.getPaymentPosibility() != null) {
            target.setPaymentPosibility(paymentPosibilityRepository.findOneByName(source.getPaymentPosibility().getName())
                    .orElse(null));
        }

        if (source.getRepaymentMethod() != null) {
            target.setRepaymentMethod(repaymentMethodRepository.findOneByName(source.getRepaymentMethod().getName())
                    .orElse(null));
        }
    }

    private void mergeDepositFilterFields(DepositFilter target, DepositFilter source) {
        target.setCurrency(currencyRepository.findOneByName(source.getCurrency().getName()).orElse(null));
        target.setClientType(clientTypeRepository.findOneByName(source.getClientType().getName()).orElse(null));

        if (source.getPercentageType() != null) {
            target.setPercentageType(percentageTypeRepository.findOneByName(source.getPercentageType().getName())
                    .orElse(null));
        }
    }


}
