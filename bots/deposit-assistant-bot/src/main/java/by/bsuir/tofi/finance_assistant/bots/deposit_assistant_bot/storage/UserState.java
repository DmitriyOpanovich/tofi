package by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.storage;

import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.model.DepositFilter;

public class UserState {

    private int userId;
    private long chatId;
    private DepositFilter depositFilter = new DepositFilter();
    private int state;
    private String language = "ru";
    private String pdfView;


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

    public DepositFilter getDepositFilter() {
        return depositFilter;
    }

    public void setDepositFilter(DepositFilter depositFilter) {
        this.depositFilter = depositFilter;
        this.pdfView = null;
    }

    public String getPdfView() {
        return pdfView;
    }

    public void setPdfView(String pdfView) {
        this.pdfView = pdfView;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
