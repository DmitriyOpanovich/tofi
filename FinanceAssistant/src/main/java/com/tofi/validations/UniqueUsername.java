package com.tofi.validations;

import com.tofi.validations.impl.UniqueUsernameValidator;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * Created by ulian_000 on 14.12.2016.
 */
@NotBlank(message = "error.blank.username")
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy = UniqueUsernameValidator.class)
public @interface UniqueUsername {
    String message() default "error.duplicate.username";
    Class[] groups() default {};

    Class[] payload() default {};
}
