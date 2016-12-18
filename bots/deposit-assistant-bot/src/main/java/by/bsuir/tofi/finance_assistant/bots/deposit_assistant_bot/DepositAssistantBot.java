package by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot;

import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.handlers.DepositAssistanceHandler;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

/**
 * Created by 1 on 03.12.2016.
 */
public class DepositAssistantBot {

    private static final String LOGTAG = "MAIN";

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new DepositAssistanceHandler());
        } catch (TelegramApiException e) {
            BotLogger.error(BotConfig.BOT_USERNAME, e);
        }
    }
}
