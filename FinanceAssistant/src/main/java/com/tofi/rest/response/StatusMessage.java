package com.tofi.rest.response;

import java.util.List;

/**
 * Created by ulian_000 on 22.12.2016.
 */
public class StatusMessage {
    private String message;
    private Boolean isSuccess;
    private List<String> errors;

    public StatusMessage(String message, Boolean isSuccess, List<String> errors) {
        setErrors(errors);
        setMessage(message);
        setSuccess(isSuccess);
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }
    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public List<String> getErrors() {
        return errors;
    }
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
