package com.tofi.dto;


import com.tofi.validations.ExistUsername;
import com.tofi.validations.UniqueEmail;
import com.tofi.validations.UniqueUsername;
import com.tofi.validations.UserNotRegisteredYet;
import com.tofi.validations.groups.LoginValidation;
import com.tofi.validations.groups.SignupValidation;
import com.tofi.validations.groups.TelegramSignupValidation;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by ulian_000 on 14.12.2016.
 */
@UserNotRegisteredYet(groups = {SignupValidation.class})
public class UserDTO {

    private String firstName;

    private String lastName;

    @UniqueEmail(groups = {SignupValidation.class})
    private String email;

    @ExistUsername(groups = {SignupValidation.class})
    @UniqueUsername(groups = {TelegramSignupValidation.class})
    @NotNull(groups={LoginValidation.class}, message="error.empty.login")
    private String userName;

    @NotNull(message="error.blank.password", groups = {SignupValidation.class, LoginValidation.class})
    private String password;

    @NotNull(groups={TelegramSignupValidation.class}, message="error.empty.telegramId")
    private Long telegramId;

    private Date registerDate;

    private String description;

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Long getTelegramId() {
        return telegramId;
    }
    public void setTelegramId(Long telegramId) {
        this.telegramId = telegramId;
    }

    public Date getRegisterDate() {
        return registerDate;
    }
    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
