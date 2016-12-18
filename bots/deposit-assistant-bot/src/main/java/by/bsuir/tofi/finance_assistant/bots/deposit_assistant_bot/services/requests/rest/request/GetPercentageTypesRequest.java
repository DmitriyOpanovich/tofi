package by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.services.requests.rest.request;

import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.services.requests.Functions;
import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.services.requests.rest.response.Response;

/**
 * Created by 1 on 18.12.2016.
 */
public class GetPercentageTypesRequest extends TypicalRequest {

    public GetPercentageTypesRequest() {
        super(Functions.GET_PERCENTAGE_TYPES);
    }

    public Response sendRequestAndGetResponse(){
        return super.sendRequest();
    }
}
