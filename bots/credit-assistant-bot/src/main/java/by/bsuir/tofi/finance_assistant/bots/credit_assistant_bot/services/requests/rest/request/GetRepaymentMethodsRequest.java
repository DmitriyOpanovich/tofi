package by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services.requests.rest.request;

import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services.requests.Functions;
import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services.requests.rest.response.Response;


public class GetRepaymentMethodsRequest extends TypicalRequest {

    public GetRepaymentMethodsRequest() {
        super(Functions.GET_REPAYMENT_METHODS);
    }

    public Response sendRequestAndGetResponse(){
        return super.sendRequest();
    }
}
