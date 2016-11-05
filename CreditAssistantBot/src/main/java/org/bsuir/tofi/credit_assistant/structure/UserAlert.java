package org.bsuir.tofi.credit_assistant.structure;

import java.util.ArrayList;
import java.util.List;


public class UserAlert {
    private int userId;
    private String userName;
    private String userSecondName;
    private long chatId;
    List<Fullname> fullname = new ArrayList<>();
    private String name = "";
    private String secondName = "";
    private int state = 1;

    public UserAlert(int userId, long chatId) {
        this.userId = userId;
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void addFullName(){
        fullname.add(new Fullname(this.name, this.secondName));
        this.name =null;
        this.secondName =null;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public List<Fullname> getFullname() {
        return fullname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSecondName() {
        return userSecondName;
    }

    public void setUserSecondName(String userSecondName) {
        this.userSecondName = userSecondName;
    }
}
