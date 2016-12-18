package by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.model;

/**
 * Created by Vladislav Bulikov on 12.12.2016.
 */
public class ConnectionWithSiteUserRequest {

    private Integer userId;
    private String siteUserName;

    public ConnectionWithSiteUserRequest(Integer userId, String siteUserName) {
        this.userId = userId;
        this.siteUserName = siteUserName;
    }

    public ConnectionWithSiteUserRequest() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSiteUserName() {
        return siteUserName;
    }

    public void setSiteUserName(String siteUserName) {
        this.siteUserName = siteUserName;
    }
}
