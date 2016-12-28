package by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.model;

/**
 * Created by 1 on 17.12.2016.
 */
public class FeedbackRequest {

    private String userName;
    private String message;
    private final String typeName = "feedback";

    public FeedbackRequest(Integer telegramId, String message) {
        this.userName = String.valueOf(telegramId);
        this.message = message;
    }

    public String getTelegramId() {
        return userName;
    }

    public void telegramId(Integer telegramId) {
        this.userName = String.valueOf(telegramId);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTypeName() {
        return typeName;
    }
}
