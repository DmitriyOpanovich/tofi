package by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.services.requests.rest.request;

import by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.services.requests.Functions;
import by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.services.requests.rest.response.Response;

/**
 * Created by 1 on 17.12.2016.
 */
public class LeaveFeedbackRequest extends TypicalRequest {

    public LeaveFeedbackRequest(Object o) {
        super(o, Functions.LEAVE_FEEDBACK);
    }

    public Response sendRequestAndGetResponse(){
        return super.sendRequest();
    }

}
