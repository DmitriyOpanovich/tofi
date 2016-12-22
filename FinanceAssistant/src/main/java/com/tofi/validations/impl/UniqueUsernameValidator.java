package com.tofi.validations.impl;

import com.tofi.service.UserService;
import com.tofi.validations.UniqueUsername;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by ulian_000 on 14.12.2016.
 */
@Component
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    @Autowired
    UserService userService;

    @Override
    public void initialize(UniqueUsername uniqueUsername) {

    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return ! userService.userWithUserNameExist(username);
    }
}
