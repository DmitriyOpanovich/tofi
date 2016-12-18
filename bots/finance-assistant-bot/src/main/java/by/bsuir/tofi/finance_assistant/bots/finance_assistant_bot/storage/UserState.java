package by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.storage;


/**
 * Created by 1 on 04.12.2016.
 */
public class UserState {

    private int userId;
    private long chatId;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
