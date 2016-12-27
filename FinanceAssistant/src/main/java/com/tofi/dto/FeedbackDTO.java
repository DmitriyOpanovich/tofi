package com.tofi.dto;

import java.sql.Timestamp;

/**
 * Created by ulian_000 on 26.12.2016.
 */
public class FeedbackDTO {

    private String message;
    private String typeName;
    private String email;
    private String userName;
    private Timestamp date;
    private Boolean isAnswered;
    private Long id;


    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Timestamp getDate() {
        return date;
    }
    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Boolean getAnswered() {
        return isAnswered;
    }
    public void setAnswered(Boolean answered) {
        isAnswered = answered;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
