package by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.model;

/**
 * Created by 1 on 18.12.2016.
 */
public class DepostiFilterRequest {
    private int telegramId;
    private DepositFilter filter;
    private String language;

    public DepostiFilterRequest(int telegramId, DepositFilter filter, String language) {
        this.telegramId = telegramId;
        this.filter = filter;
        this.language = language;
    }

    public DepostiFilterRequest(int telegramId, DepositFilter filter) {
        this.telegramId = telegramId;
        this.filter = filter;
    }

    public int getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(int telegramId) {
        this.telegramId = telegramId;
    }

    public DepositFilter getFilter() {
        return filter;
    }

    public void setFilter(DepositFilter filter) {
        this.filter = filter;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
