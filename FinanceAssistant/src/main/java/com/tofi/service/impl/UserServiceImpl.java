package com.tofi.service.impl;

import com.tofi.model.BotUser;
import com.tofi.repository.BotUserRepository;
import com.tofi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by ulian_000 on 14.12.2016.
 */
public class UserServiceImpl implements UserService {
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    BotUserRepository userRepository;

    @Override
    public Boolean userWithEmailExists(String email) {
        Boolean result = userRepository.findByEmail(email).isPresent();
        log.info(email + " exist -> " + result);
        return result;
    }

    @Override
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
                unregisteredUser.getTelegramId() == null ||
                unregisteredUser.getUserName() == null) {
            return null;
        }

       BotUser user = userRepository
                .findByTelegramId(unregisteredUser.getTelegramId())
                .orElseGet(() -> {
                    BotUser newUser = new BotUser();
                    newUser.setTelegramId(unregisteredUser.getTelegramId());
                    return newUser;
                });

       user.setUserName(unregisteredUser.getUserName());
       userRepository.saveAndFlush(user);
       return user;
    }


    @Override
    @Transactional
    public BotUser loginUser(BotUser unloginedUser) {


        return null;
    }


}
