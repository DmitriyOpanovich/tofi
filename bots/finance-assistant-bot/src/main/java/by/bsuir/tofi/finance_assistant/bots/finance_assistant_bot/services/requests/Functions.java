package by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.services.requests;

import org.springframework.http.HttpMethod;

/**
 * Created by 1 on 06.12.2016.
 */
public enum Functions {
    UPDATE_USER("", HttpMethod.POST),
    LEAVE_FEEDBACK("", HttpMethod.POST),
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
