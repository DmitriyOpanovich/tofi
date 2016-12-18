package by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.model;


public class CreditFilterRequest {

    private int userId;
    private CreditFilter creditFilter;
    private String language;

    public CreditFilterRequest(int userId, CreditFilter creditFilter) {
        this.userId = userId;
        this.creditFilter = creditFilter;
    }

    public CreditFilterRequest(int userId, CreditFilter creditFilter, String language) {
        this.userId = userId;
        this.creditFilter = creditFilter;
        this.language = language;
    }

    public int getUserId() {
        return userId;
    }

    public CreditFilter getCreditFilter() {
        return creditFilter;
    }

    public String getLanguage() {
        return language;
    }
}

