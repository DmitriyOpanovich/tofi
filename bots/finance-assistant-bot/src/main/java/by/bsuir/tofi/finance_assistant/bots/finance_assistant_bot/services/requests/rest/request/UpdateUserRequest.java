package by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.services.requests.rest.request;


import by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.services.requests.Functions;
import by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.services.requests.rest.response.Response;

/**
 * Created by 1 on 10.12.2016.
 */
public class UpdateUserRequest extends TypicalRequest {
    public UpdateUserRequest(Object o) {
        super(o, Functions.UPDATE_USER);
    }

    public Response sendRequestAndGetResponse(){
        return super.sendRequest();
    }

}
