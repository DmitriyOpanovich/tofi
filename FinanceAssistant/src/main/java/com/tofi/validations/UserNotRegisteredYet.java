package com.tofi.validations;

import com.tofi.validations.impl.UserNotRegisteredYetValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * Created by ulian_000 on 25.12.2016.
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy = UserNotRegisteredYetValidator.class)
public @interface UserNotRegisteredYet {
    String message() default "error.alreadyRegistered.user";
    Class[] groups() default {};

    Class[] payload() default {};
}

