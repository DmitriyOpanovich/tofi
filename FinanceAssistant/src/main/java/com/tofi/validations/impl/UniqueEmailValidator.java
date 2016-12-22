package com.tofi.validations.impl;

import com.tofi.service.UserService;
import com.tofi.validations.UniqueEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by ulian_000 on 14.12.2016.
 */
@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    UserService userService;

    @Override
    public void initialize(UniqueEmail uniqueEmail) {

    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return ! userService.userWithEmailExists(email);
    }
}
