package by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.services.requests.rest.request;


import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.services.requests.Functions;
import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.services.requests.rest.response.Response;

public class GenerateReportForFilterRequest extends TypicalRequest {

    public GenerateReportForFilterRequest(Object o) {
        super(o, Functions.GENERATE_REPORT_FOR_FILTER);
    }

    public Response sendRequestAndGetResponse(){
        return super.sendRequest();
    }

}
