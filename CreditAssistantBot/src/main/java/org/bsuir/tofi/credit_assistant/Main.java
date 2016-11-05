package org.bsuir.tofi.credit_assistant;

import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

/**
 * Created by 1 on 18.10.2016.
 */
public class Main {
    public static void main(String[] args) {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new CreditAssistantBot());
        } catch (TelegramApiException e) {
            BotLogger.error(BotConfig.BOT_USERNAME, e);
        }
    }
}
