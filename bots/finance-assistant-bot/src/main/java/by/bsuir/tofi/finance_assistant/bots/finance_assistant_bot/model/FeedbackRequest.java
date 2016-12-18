package by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.model;

/**
 * Created by 1 on 17.12.2016.
 */
public class FeedbackRequest {

    private Integer userId;
    private String message;

    public FeedbackRequest(Integer userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
