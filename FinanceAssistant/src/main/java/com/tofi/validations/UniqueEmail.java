package com.tofi.validations;

import com.tofi.validations.impl.UniqueEmailValidator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * Created by ulian_000 on 14.12.2016.
 */
@NotBlank(message = "error.blank.email")
@Email(message = "error.invalid.email")
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {
    String message() default "error.duplicate.email";
    Class[] groups() default {};

    Class[] payload() default {};
}
