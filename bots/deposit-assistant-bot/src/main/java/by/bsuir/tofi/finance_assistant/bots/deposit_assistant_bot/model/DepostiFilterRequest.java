package by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.model;

/**
 * Created by 1 on 18.12.2016.
 */
public class DepostiFilterRequest {
    private int userId;
    private DepositFilter depositFilter;
    private String language;

    public DepostiFilterRequest(int userId, DepositFilter depositFilter, String language) {
        this.userId = userId;
        this.depositFilter = depositFilter;
        this.language = language;
    }

    public DepostiFilterRequest(int userId, DepositFilter depositFilter) {
        this.userId = userId;
        this.depositFilter = depositFilter;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public DepositFilter getDepositFilter() {
        return depositFilter;
    }

    public void setDepositFilter(DepositFilter depositFilter) {
        this.depositFilter = depositFilter;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
