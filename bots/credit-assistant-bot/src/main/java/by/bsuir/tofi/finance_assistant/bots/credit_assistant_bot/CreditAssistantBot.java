package by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot;

import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.handlers.CreditAssistanceHandler;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;


public class CreditAssistantBot {
    private static final String LOGTAG = "MAIN";

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new CreditAssistanceHandler());
        } catch (TelegramApiException e) {
            BotLogger.error(BotConfig.BOT_USERNAME, e);
        }
    }
}
