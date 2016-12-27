package com.tofi.model;

import com.tofi.model.enums.FeedbackType;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by ulian_000 on 26.12.2016.
 */
@Entity
@Table(name="Feedbacks")
public class Feedback extends BaseEntity {

    @Column(columnDefinition="TEXT", name="Message")
    private String message;

    @JoinColumn(name="FeedbackType_id")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY)
    private FeedbackType type;

    @Column(name="Email")
    private String email;

    @Column(name="UserName")
    private String userName;

    @Column(name="Date")
    private Timestamp date;

    @Column(name="Answered")
    private Boolean isAnswered;

    public Feedback(){}

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public FeedbackType getType() {
        return type;
    }
    public void setType(FeedbackType type) {
        this.type = type;
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
}
