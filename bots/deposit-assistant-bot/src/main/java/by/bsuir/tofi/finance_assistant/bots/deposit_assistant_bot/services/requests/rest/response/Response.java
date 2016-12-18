package by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.services.requests.rest.response;


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
