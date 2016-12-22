package com.tofi.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ulian_000 on 08.12.2016.
 */
@Entity
@Table(name="BotUsers")
public class BotUser extends BaseEntity {


    @Column(unique = true)
    private String email;

    @Column(name="user_name", unique = true)
    private String userName;

    @Column
    private String password;

    @Column(name="telegarm_id")
    private Long telegramId;

    @Column(name="register_date")
    private Date registerDate;

    @ManyToOne(fetch= FetchType.LAZY, cascade = {CascadeType.REMOVE})
    @JoinColumn(name="history_id")

    private History history;

    public BotUser(){}


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

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegisterDate() {
        return registerDate;
    }
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public History getHistory() {
        return history;
    }
    public void setHistory(History history) {
        this.history = history;
    }

    public Long getTelegramId() {
        return telegramId;
    }
    public void setTelegramId(Long telegramId) {
        this.telegramId = telegramId;
    }
}
