package by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services.requests.rest.request;

import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services.requests.Functions;
import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services.requests.rest.response.Response;


public class GetPaymentPossibilitiesRequest extends TypicalRequest {

    public GetPaymentPossibilitiesRequest() {
        super(Functions.GET_PAYMENT_POSSIBILITIES);
    }

    public Response sendRequestAndGetResponse(){
        return super.sendRequest();
    }

}
