package by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services.requests.rest.request;

import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services.requests.Functions;
import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services.requests.rest.response.Response;


public class GetCurrenciesRequest extends TypicalRequest {

    public GetCurrenciesRequest() {
        super(Functions.GET_CURRENCIES);
    }

    public Response sendRequestAndGetResponse(){
        return super.sendRequest();
    }

}
