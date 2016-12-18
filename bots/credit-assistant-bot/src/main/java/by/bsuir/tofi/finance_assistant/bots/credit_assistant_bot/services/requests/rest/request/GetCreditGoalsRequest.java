package by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services.requests.rest.request;

import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services.requests.Functions;
import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services.requests.rest.response.Response;


public class GetCreditGoalsRequest extends TypicalRequest {

    public GetCreditGoalsRequest() {
        super(Functions.GET_CREDIT_GOALS);
    }

    public Response sendRequestAndGetResponse(){
        return super.sendRequest();
    }
}
