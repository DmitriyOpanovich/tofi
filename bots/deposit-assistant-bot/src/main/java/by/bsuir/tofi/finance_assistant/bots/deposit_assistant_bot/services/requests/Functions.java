package by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.services.requests;

import org.springframework.http.HttpMethod;


public enum Functions {
    UPDATE_USER("", HttpMethod.POST),
    GET_CLIENT_TYPES("/client_types", HttpMethod.GET),
    GET_PERCENTAGE_TYPES("/percentage_types", HttpMethod.GET),
    GET_CURRENCIES("/currencies", HttpMethod.GET),
    CHOOSE_DEPOSIT_WITH_FILTER("/filter_deposits", HttpMethod.POST),
    GENERATE_REPORT_FOR_FILTER("", HttpMethod.POST),
    CONNECT_BOT_WITH_SITE_USER("", HttpMethod.POST);


    private final String path;
    private final HttpMethod httpMethod;

    Functions(String path, HttpMethod httpMethod) {
        this.path = path;
        this.httpMethod = httpMethod;
    }

    public String getPath() {
        return path;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }
}
