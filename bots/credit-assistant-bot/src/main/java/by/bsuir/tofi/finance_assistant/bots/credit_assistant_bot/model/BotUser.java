package by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.model;


public class BotUser {
    private String firstName;
    private String lastName;
    private String userName;
    private Integer userId;

    public BotUser() {
    }

    public BotUser(String firstName, String lastName, String userName, int userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
