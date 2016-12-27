package com.tofi.service;

import com.tofi.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ulian_000 on 14.12.2016.
 */
@Service
public interface UserService  {
    Boolean userWithEmailExists(String email);
    Boolean userWithUserNameExist(String username);
    Boolean userHasAlreadyRegistered(String username, String email);

    BotUser registerUserFromTelegram(BotUser unregisteredUser);
    BotUser registerUser(BotUser unregisteredUser);
    BotUser loginUser(BotUser unloginedUser);

    void logCreditSearchHistory(CreditFilter filter, List<Credit> filteredCredits, BotUser user);
    void logDepositSearchHistory(DepositFilter filter, List<Deposit> filteredDeposits, BotUser user);
}
