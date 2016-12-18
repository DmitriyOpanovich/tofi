package by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot;

import by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.handlers.FinanceAssistanceHandler;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

/**
 * Created by 1 on 03.12.2016.
 */
public class FinanceAssistantBot {

    private static final String LOGTAG = "MAIN";

//    public static void main(String[] args) {
//        BotLogger.setLevel(Level.ALL);
//        BotLogger.registerLogger(new ConsoleHandler());
//        try {
//            BotLogger.registerLogger(new BotsFileHandler());
//        } catch (IOException e) {
//            BotLogger.severe(LOGTAG, e);
//        }
//
//        try {
//            TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
//            try {
//                telegramBotsApi.registerBot(new CreditAssistanceHandler());
//            } catch (TelegramApiException e) {
//                BotLogger.error(BotConfig.BOT_USERNAME, e);
//            }
//        } catch (Exception e) {
//            BotLogger.error(LOGTAG, e);
//        }
//    }

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
