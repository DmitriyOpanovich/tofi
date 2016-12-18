package by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.services.requests.rest.request;


import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.services.requests.Functions;
import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.services.requests.rest.response.Response;

public class ChooseDepositWithFilterRequest extends TypicalRequest {
    public ChooseDepositWithFilterRequest(Object o) {
        super(o, Functions.CHOOSE_DEPOSIT_WITH_FILTER);
    }

    public Response sendRequestAndGetResponse(){
        return super.sendRequest();
    }

}
