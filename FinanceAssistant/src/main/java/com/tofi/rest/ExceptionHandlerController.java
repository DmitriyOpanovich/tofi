package com.tofi.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
//import org.springframework.validation.FieldError;

/**
 * Created by ulian_000 on 13.12.2016.
 */
@RestControllerAdvice
@RequestMapping(produces = "application/json")
public class ExceptionHandlerController  {

    private Logger log = LoggerFactory.getLogger(ExceptionHandlerController.class);
//    @Autowired
//    private AbstractMessageSource messageSource;

    @ExceptionHandler(value={ConstraintViolationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public StatusMessage handleConstraintViolationException(ConstraintViolationException ex) {

        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        List<String> messages = constraintViolations.stream()
                .map(constraintViolation -> String.format("%s value '%s' %s", constraintViolation.getPropertyPath(),
                        constraintViolation.getInvalidValue(), constraintViolation.getMessage()))
                .collect(Collectors.toList());

        return new StatusMessage(null, false, messages);
    }

    @ExceptionHandler(value={MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public StatusMessage handleArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage().replaceAll("[{}]",""))
                .collect(Collectors.toList());

        return new StatusMessage(null, false, errors);
    }

}
