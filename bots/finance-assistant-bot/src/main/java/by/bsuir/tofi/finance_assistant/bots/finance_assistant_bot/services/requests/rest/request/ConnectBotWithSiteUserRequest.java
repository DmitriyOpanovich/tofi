package by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.services.requests.rest.request;


import by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.services.requests.Functions;
import by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.services.requests.rest.response.Response;

/**
 * Created by 1 on 10.12.2016.
 */
public class ConnectBotWithSiteUserRequest extends TypicalRequest {

    public ConnectBotWithSiteUserRequest(Object o) {
        super(o, Functions.CONNECT_BOT_WITH_SITE_USER);
    }

    public Response sendRequestAndGetResponse(){
        return super.sendRequest();
    }

}
