package by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.storage;

import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.model.CreditFilter;

public class UserState {

    private int userId;
    private long chatId;
    private CreditFilter creditFilter = new CreditFilter();
    private int state;
    private String language = "ru";


    public UserState(int userId, long chatId) {
        this.userId = userId;
        this.chatId = chatId;
        this.state = 0;
    }

    public UserState(int userId, long chatId, int state) {
        this.userId = userId;
        this.chatId = chatId;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public CreditFilter getCreditFilter() {
        return creditFilter;
    }

    public void setCreditFilter(CreditFilter creditFilter) {
        this.creditFilter = creditFilter;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
