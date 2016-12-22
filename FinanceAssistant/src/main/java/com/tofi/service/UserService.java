package com.tofi.service;

import com.tofi.model.BotUser;
import org.springframework.stereotype.Service;

/**
 * Created by ulian_000 on 14.12.2016.
 */
@Service
public interface UserService  {
    Boolean userWithEmailExists(String email);
    Boolean userWithUserNameExist(String username);

    BotUser registerUserFromTelegram(BotUser unregisteredUser);
    BotUser registerUser(BotUser unregisteredUser);
    BotUser loginUser(BotUser unloginedUser);
}
