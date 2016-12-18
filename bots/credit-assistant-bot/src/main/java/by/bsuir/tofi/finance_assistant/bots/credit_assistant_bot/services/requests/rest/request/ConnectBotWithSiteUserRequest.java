package by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services.requests.rest.request;

import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services.requests.Functions;
import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services.requests.rest.response.Response;


public class ConnectBotWithSiteUserRequest extends TypicalRequest {

    public ConnectBotWithSiteUserRequest(Object o) {
        super(o, Functions.CONNECT_BOT_WITH_SITE_USER);
    }

    public Response sendRequestAndGetResponse(){
        return super.sendRequest();
    }

}
