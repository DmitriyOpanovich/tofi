package com.tofi.service;

import com.tofi.model.*;
import com.tofi.model.enums.*;

import java.util.List;

/**
 * Created by ulian_000 on 12.12.2016.
 */
public interface FilterService {
    List<ClientType> getAvailableClientTypes();
    List<Currency> getAvailableCurrencies();
    List<CreditGoal> getAvailableCreditGoals();
    List<PaymentPosibility> getAvailablePaymentPosibilities();
    List<PercentageType> getAvailablePercentageTypes();
    List<RepaymentMethod> getAvailableRepaymentMethods();

    List<Credit> filterCredits(CreditFilter filter, BotUser user);
    List<Deposit> filterDeposits(DepositFilter filter, BotUser user);
}
