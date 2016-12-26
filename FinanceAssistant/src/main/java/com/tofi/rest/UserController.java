package com.tofi.rest;

import com.tofi.dto.UserDTO;
import com.tofi.model.BotUser;
import com.tofi.rest.response.StatusMessage;
import com.tofi.service.UserService;
import com.tofi.validations.groups.LoginValidation;
import com.tofi.validations.groups.SignupValidation;
import com.tofi.validations.groups.TelegramSignupValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ulian_000 on 13.12.2016.
 */
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private ModelMapper modelMapper;


    @RequestMapping(value="/register", method= RequestMethod.POST)
    public StatusMessage register(@RequestBody @Validated(SignupValidation.class) UserDTO userDto){
        BotUser user = modelMapper.map(userDto, BotUser.class);
        BotUser registeredUser = userService.registerUser(user);

        return registeredUser != null ?
                new StatusMessage("User registered", true, null):
                new StatusMessage("Registration failed", false, null);
    }

    @RequestMapping(value="/telegram/register", method= RequestMethod.POST)
    public StatusMessage registerFromTelegram(@RequestBody @Validated(TelegramSignupValidation.class) UserDTO userDto){
        BotUser user = modelMapper.map(userDto, BotUser.class);
        BotUser registeredUser = userService.registerUserFromTelegram(user);

        return registeredUser != null ?
                new StatusMessage("User registered in Telegram", true, null):
                new StatusMessage("Registration failed", false, null);
    }

    @RequestMapping(value="/login", method= RequestMethod.POST)
    public StatusMessage login(@RequestBody @Validated(LoginValidation.class) UserDTO userDto){
        BotUser loggedInUser = userService.loginUser(modelMapper.map(userDto, BotUser.class));

        return loggedInUser != null ?
                new StatusMessage("User logged in", true, null) :
                new StatusMessage("Invalid login or password", false, null);

    }

}
