package by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot;

import by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.handlers.FinanceAssistanceHandler;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;


public class FinanceAssistantBot {
    private static final String LOGTAG = "MAIN";

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new FinanceAssistanceHandler());
        } catch (TelegramApiException e) {
            BotLogger.error(BotConfig.BOT_USERNAME, e);
        }
    }
}
