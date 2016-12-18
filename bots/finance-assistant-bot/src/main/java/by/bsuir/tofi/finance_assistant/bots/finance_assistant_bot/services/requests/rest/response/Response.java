package by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.services.requests.rest.response;

/**
 * Created by 1 on 07.12.2016.
 */
public class Response {
    private final boolean status;
    private final String message;

    public Response(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
