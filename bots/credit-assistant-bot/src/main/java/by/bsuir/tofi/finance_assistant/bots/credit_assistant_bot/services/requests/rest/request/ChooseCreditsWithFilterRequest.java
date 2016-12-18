package by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services.requests.rest.request;

import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services.requests.Functions;
import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services.requests.rest.response.Response;


public class ChooseCreditsWithFilterRequest extends TypicalRequest {
    public ChooseCreditsWithFilterRequest(Object o) {
        super(o, Functions.CHOOSE_CREDITS_WITH_FILTER);
    }

    public Response sendRequestAndGetResponse(){
        return super.sendRequest();
    }

}
