package com.tofi.service.impl;

import com.tofi.model.*;
import com.tofi.repository.BotUserRepository;
import com.tofi.repository.CreditResponseRepository;
import com.tofi.repository.DepositResponseRepository;
import com.tofi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * Created by ulian_000 on 14.12.2016.
 */
public class UserServiceImpl implements UserService {
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    BotUserRepository userRepository;
    @Autowired
    CreditResponseRepository creditResponseRepository;
    @Autowired
    DepositResponseRepository depositResponseRepository;
//    @Autowired
//    HistoryRepository historyRepository;

    @Override
    @Transactional(readOnly = true)
    public Boolean userWithEmailExists(String email) {
        Boolean result = userRepository.findByEmail(email).isPresent();
        log.info(email + " exist -> " + result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean userWithUserNameExist(String username) {
        Boolean result = userRepository.findByUserName(username).isPresent();
        log.info(username + " exist -> " + result);
        return result;
    }

    @Override
    @Transactional
    public BotUser registerUser(BotUser unregisteredUser) {
        if (
                unregisteredUser == null ||
                unregisteredUser.getUserName() == null) {
            return null;
        }

        Optional<BotUser> user = userRepository.findByUserName(unregisteredUser.getUserName());

        if (user.isPresent()) {
            user.get().setEmail(unregisteredUser.getEmail());
            user.get().setPassword(unregisteredUser.getPassword());
            userRepository.saveAndFlush(user.get());
        }

        return  user.orElse(null);
    }
    @Override
    @Transactional
    public BotUser registerUserFromTelegram(BotUser unregisteredUser) {
        if (
                unregisteredUser == null ||
                unregisteredUser.getTelegramId() == null) {
            return null;
        }

       BotUser user = userRepository
                .findByTelegramId(unregisteredUser.getTelegramId())
                .orElseGet(() -> {
                    BotUser newUser = new BotUser();
                    newUser.setTelegramId(unregisteredUser.getTelegramId());
                    newUser.setHistory(new History());
                    return newUser;
                });

       if (unregisteredUser.getUserName() != null && !unregisteredUser.getUserName().isEmpty()) {
           user.setUserName(unregisteredUser.getUserName());
       }

       userRepository.saveAndFlush(user);
       return user;
    }


    @Override
    @Transactional
    public BotUser loginUser(BotUser unloginedUser) {
        if (unloginedUser == null || unloginedUser.getUserName() == null || unloginedUser.getPassword() == null){
            return null;
        }

        Optional<BotUser> persistUser = userRepository.findByUserName(unloginedUser.getUserName());

        if (persistUser.isPresent() && persistUser.get().getPassword().compareTo(unloginedUser.getPassword()) == 0) {
            return persistUser.get();
        }

        return null;
    }

    @Override
    public BotUser changePassword(String prevPass, String newPass, String username) {
        return null;
    }

    @Override
    public BotUser forgotPassword(String newPass, String username) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean userHasAlreadyRegistered(String username, String email) {
        if (username == null || email == null)
            return false;

        Boolean result = false;
        Optional<BotUser> persistUser = userRepository.findByUserName(username);

        if (persistUser.isPresent()) {
            result = persistUser.get().getEmail() != null;
        }

        log.info("userHasAlreadyRegistered " + result);

        return result;
    }

    @Override
    @Transactional
    public void logCreditSearchHistory(CreditFilter filter, List<Credit> filteredCredits, BotUser user) {
        CreditResponse creditResponse = new CreditResponse();
        creditResponse.setFilter(filter);
        creditResponse.setDate(new Timestamp(System.currentTimeMillis()));
        creditResponse.setCredits(filteredCredits);
        creditResponse.setHistory(user.getHistory());
        creditResponseRepository.saveAndFlush(creditResponse);

    }

    @Override
    @Transactional
    public void logDepositSearchHistory(DepositFilter filter, List<Deposit> filteredDeposits, BotUser user){
        DepositResponse depositResponse = new DepositResponse();
        depositResponse.setFilter(filter);
        depositResponse.setDate(new Timestamp(System.currentTimeMillis()));
        depositResponse.setDeposits(filteredDeposits);
        depositResponse.setHistory(user.getHistory());
        depositResponseRepository.saveAndFlush(depositResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public History getCreditSearchHistory(BotUser user) {
        Optional<BotUser> registeredUser = userRepository.findByTelegramId(user.getTelegramId());

        return registeredUser.isPresent() ? registeredUser.get().getHistory() : null;
    }
}
