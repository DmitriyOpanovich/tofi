package com.tofi.validations.impl;

import com.tofi.dto.UserDTO;
import com.tofi.service.UserService;
import com.tofi.validations.UserNotRegisteredYet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by ulian_000 on 25.12.2016.
 */
@Component
public class UserNotRegisteredYetValidator implements ConstraintValidator<UserNotRegisteredYet, UserDTO> {

    @Autowired
    UserService userService;


    @Override
    public void initialize(UserNotRegisteredYet userNotRegisteredYet) {

    }

    @Override
    public boolean isValid(UserDTO botUser, ConstraintValidatorContext constraintValidatorContext) {

        return ! userService.userHasAlreadyRegistered(botUser.getUserName(), botUser.getEmail());
    }
}
