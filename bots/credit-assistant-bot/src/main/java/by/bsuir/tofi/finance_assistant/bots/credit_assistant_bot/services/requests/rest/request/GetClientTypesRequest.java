package by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services.requests.rest.request;

import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services.requests.Functions;
import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services.requests.rest.response.Response;


public class GetClientTypesRequest extends TypicalRequest {

    public GetClientTypesRequest() {
        super(Functions.GET_CLIENT_TYPES);
    }

    public Response sendRequestAndGetResponse(){
        return super.sendRequest();
    }

}
