package by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.model;

public class CreditFilterRequest {

    private int telegramId;
    private CreditFilter filter;
    private String language;

    public CreditFilterRequest(int telegramId, CreditFilter filter) {
        this.telegramId = telegramId;
        this.filter = filter;
    }

    public CreditFilterRequest(int telegramId, CreditFilter filter, String language) {
        this.telegramId = telegramId;
        this.filter = filter;
        this.language = language;
    }

    public int getTelegramId() {
        return telegramId;
    }

    public CreditFilter getFilter() {
        return filter;
    }

    public void setTelegramId(int telegramId) {
        this.telegramId = telegramId;
    }

    public void setFilter(CreditFilter filter) {
        this.filter = filter;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
}

