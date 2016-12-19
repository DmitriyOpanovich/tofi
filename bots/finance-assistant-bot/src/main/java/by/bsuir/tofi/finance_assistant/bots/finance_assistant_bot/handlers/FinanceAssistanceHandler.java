package by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.handlers;


import by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.BotConfig;
import by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.Commands;
import by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.model.enums.MyBot;
import by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.services.FinanceAssistantService;
import by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.services.LocalisationService;
import by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.storage.DB;
import by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.storage.StorageManager;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.*;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.logging.BotLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 03.12.2016.
 */
public class FinanceAssistanceHandler extends TelegramLongPollingBot {
    private static final String LOGTAG = "FINANCEHANDLERS";

    private static final int STARTSTATE = 0;
    private static final int MAINMENU = 1;
    private static final int ONCHOOSESERVICE = 2;
    private static final int SETTINGS = 16;
    private static final int LANGUAGE = 17;
    private static final int LINKTOSITE = 18;
    private static final int LEAVEFEEDBACK = 19;


    public FinanceAssistanceHandler() {
        super();
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {
        super.clearWebhook();
    }

    @Override
    public String getBotToken() {
        return BotConfig.BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage()) {
                Message message = update.getMessage();
                if (message.hasText() || message.hasLocation()) {
                    handleIncomingMessage(message);
                }
            }
        } catch (Exception e) {
            BotLogger.error(LOGTAG, e);
        }
    }

    public String getBotUsername() {
        return BotConfig.BOT_USERNAME;
    }

    private void handleIncomingMessage(Message message) throws TelegramApiException {
        try {
            //StorageManager.updateUser(message.getFrom());
        }catch (Exception e){
            BotLogger.error(LOGTAG, e);
        }
        final int state = DB.getUserState(message.getChatId(),message.getFrom().getId()).getState();
        final String language = DB.getUserState(message.getChatId(),message.getFrom().getId()).getLanguage();

        if (isCommandForOther(message.getText())) {
            return;
        } else if(message.getText().startsWith(Commands.startCommand)){
            sendMessage(sendMessageDefault(message, language));
            return;
        }else if (message.getText().startsWith(Commands.SITE)){
            sendMessage(onShowSiteChoosen(message,language));
            return;
        } else if (message.getText().startsWith(Commands.STOPCOMMAND)){
            sendHideKeyboard(message.getFrom().getId(), message.getChatId(), message.getMessageId(), language);
            return;
        }

        SendMessage sendMessageRequest;
        switch(state) {
            case MAINMENU:
                sendMessageRequest = messageOnMainMenu(message, language);
                break;
            case SETTINGS:
                sendMessageRequest = messageOnSetting(message, language);
                break;
            case LANGUAGE:
                sendMessageRequest = messageOnLanguage(message, language);
                break;
            case LINKTOSITE:
                sendMessageRequest = onLinkToSite(message, language);
                break;
            case LEAVEFEEDBACK:
                sendMessageRequest = onLeaveFeedback(message, language);
                break;
            default:
                sendMessageRequest = sendMessageDefault(message, language);
                break;
        }

        sendMessage(sendMessageRequest);
    }


    private void sendHideKeyboard(Integer userId, Long chatId, Integer messageId, String language) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyToMessageId(messageId);
        sendMessage.setText(getStoppedMessage(language));
        ReplyKeyboardRemove  replyKeyboardHide = new ReplyKeyboardRemove();
        replyKeyboardHide.setSelective(true);
        sendMessage.setReplyMarkup(replyKeyboardHide);

        sendMessage(sendMessage);
        DB.getUserState(chatId, userId).setState(STARTSTATE);
    }

    private static boolean isCommandForOther(String text) {
        boolean isSimpleCommand = text.equals("/start")  || text.equals("/stop") || text.equals("/site");
        boolean isCommandForMe = text.equals("/start@financeassistantbot") ||  text.equals("/stop@financeassistantbot") ||  text.equals("/site@financeassistantbot");
        return text.startsWith("/") && !isSimpleCommand && !isCommandForMe;
    }

    // endregion Incoming messages handlers

    // region Settings Menu Option selected

    private static SendMessage messageOnSetting(Message message, String language) {
        SendMessage sendMessageRequest = null;
        if (message.hasText()) {
            if (message.getText().startsWith(getLanguagesCommand(language))) {
                sendMessageRequest = onLanguageCommand(message, language);
            }  else if (message.getText().startsWith(getBackCommand(language))) {
                sendMessageRequest = sendMessageDefault(message, language);
            } else {
                sendMessageRequest = sendChooseOption(message.getChatId(), message.getMessageId(),
                        getSettingsKeyboard(language), getSettingsMessage(language));
            }
        }
        return sendMessageRequest;
    }



    private static SendMessage onLanguageCommand(Message message, String language) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyMarkup(getLanguagesKeyboard(language));
        sendMessage.setText(getLanguageMessage(language));
        DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(LANGUAGE);
        return sendMessage;
    }

    // endregion Settings Menu Option selected


    // region Language Menu Option selected

    private static SendMessage messageOnLanguage(Message message, String language) {
        SendMessage sendMessageRequest = null;
        if (message.hasText()) {
            if (message.getText().trim().equals(getBackCommand(language))) {
                sendMessageRequest = onBackLanguageCommand(message, language);
            } else if (LocalisationService.getInstance().getSupportedLanguages().values().contains(message.getText().trim())) {
                sendMessageRequest = onLanguageChosen(message.getFrom().getId(), message.getChatId(),
                        message.getMessageId(), message.getText().trim());
            } else {
                sendMessageRequest = onLanguageError(message.getChatId(), message.getMessageId(), language);
            }
        }
        return sendMessageRequest;
    }

    private static SendMessage onBackLanguageCommand(Message message, String language) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        ReplyKeyboardMarkup replyKeyboardMarkup = getSettingsKeyboard(language);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(getSettingsMessage(language));
        DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(SETTINGS);
        return sendMessage;
    }

    private static SendMessage onLanguageError(Long chatId, Integer messageId, String language) {
        SendMessage sendMessageRequest = new SendMessage();
        sendMessageRequest.enableMarkdown(true);
        sendMessageRequest.setChatId(chatId.toString());
        sendMessageRequest.setReplyMarkup(getLanguagesKeyboard(language));
        sendMessageRequest.setText(LocalisationService.getInstance().getString("errorLanguageNotFound", language));
        sendMessageRequest.setReplyToMessageId(messageId);

        return sendMessageRequest;
    }

    private static SendMessage onLanguageChosen(Integer userId, Long chatId, Integer messageId, String language) {
        String languageCode = LocalisationService.getInstance().getLanguageCodeByName(language);
        DB.getUserState(chatId, userId).setLanguage(languageCode);

        SendMessage sendMessageRequest = new SendMessage();
        sendMessageRequest.enableMarkdown(true);
        sendMessageRequest.setChatId(chatId.toString());
        sendMessageRequest.setText(LocalisationService.getInstance().getString("languageUpdated", languageCode));
        sendMessageRequest.setReplyToMessageId(messageId);
        sendMessageRequest.setReplyMarkup(getMainMenuKeyboard(languageCode));

        DB.getUserState(chatId, userId).setState(MAINMENU);
        return sendMessageRequest;
    }

    // region Main menu options selected


    private static SendMessage messageOnMainMenu(Message message, String language) {
        SendMessage sendMessageRequest;
        if (message.hasText()) {
            if (message.getText().equals(getChooseServiceCommand(language))) {
                sendMessageRequest = sendChooseServicesOption(message, language);
            } else if (message.getText().equals(getLinkCommand(language))) {
                sendMessageRequest = onLinkChoosen(message, language);
            } else if (message.getText().equals(getSettingsCommand(language))) {
                sendMessageRequest = onSettingsChoosen(message, language);
            } else if (message.getText().equals(getFeedbackCommand(language))) {
                sendMessageRequest = onFeedbackChoosen(message, language);
            } else {
                sendMessageRequest = sendMessageDefault(message, language);
            }
        } else {
            sendMessageRequest = sendMessageDefault(message, language);
        }

        return sendMessageRequest;
    }

    private static SendMessage onSettingsChoosen(Message message, String language) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        ReplyKeyboardMarkup replyKeyboardMarkup = getSettingsKeyboard(language);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(getSettingsMessage(language));
        DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(SETTINGS);
        return sendMessage;
    }

    private static SendMessage onLinkChoosen(Message message, String language) {
        ForceReplyKeyboard forceReplyKeyboard = getForceReply();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setReplyMarkup(forceReplyKeyboard);
        sendMessage.setText(getLinkMessage(language));
        DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(LINKTOSITE);
        return sendMessage;
    }

    private static SendMessage onFeedbackChoosen(Message message, String language) {
        ForceReplyKeyboard forceReplyKeyboard = getForceReply();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setReplyMarkup(forceReplyKeyboard);
        sendMessage.setText(getFeedbackMessage(language));
        DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(LINKTOSITE);
        return sendMessage;
    }

    private static SendMessage onLinkToSite(Message message, String language) {
        if (message.isReply()) {
            return onLinkReceived(message, language);
        } else {
            return sendMessageDefault(message, language);
        }
    }

    private static SendMessage onLinkReceived(Message message, String language) {
        boolean status = false;
        try {
            status = StorageManager.connectWithSiteUser(message.getFrom().getId(), message.getText());
        }catch (Exception e){

        }
        ForceReplyKeyboard forceReplyKeyboard = getForceReply();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setReplyMarkup(forceReplyKeyboard);
        if (status) {
            sendMessage.setText(LocalisationService.getInstance().getString("onLinkSuccess", language));
        } else {
            sendMessage.setText(LocalisationService.getInstance().getString("onLinkFail", language));
        }
        DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(MAINMENU);

        return justSendMessage(sendMessage, language);

    }

    private static SendMessage onLeaveFeedback(Message message, String language) {
        if (message.isReply()) {
            return onFeedbackReceived(message, language);
        } else {
            return sendMessageDefault(message, language);
        }
    }

    private static SendMessage onFeedbackReceived(Message message, String language) {
        boolean status = false;
        try {
            status = StorageManager.leaveFeedback(message.getFrom().getId(), message.getText());
        }catch (Exception e){

        }
        ForceReplyKeyboard forceReplyKeyboard = getForceReply();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setReplyMarkup(forceReplyKeyboard);
        if (status) {
            sendMessage.setText(LocalisationService.getInstance().getString("onFeedbackSuccess", language));
        } else {
            sendMessage.setText(LocalisationService.getInstance().getString("onFeedbackFail", language));
        }
        DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(MAINMENU);

        return justSendMessage(sendMessage, language);
    }














    // endregion Main menu options selected

    // region Get Messages

    private static String getStoppedMessage(String language){
        String baseString = LocalisationService.getInstance().getString("onStop", language);
        return baseString;
    }

    private static String getInternalErrorMessage(String language){
        String baseString = LocalisationService.getInstance().getString("onInternalFail", language);
        return baseString;
    }

    private static String getSettingsMessage(String language) {
        String baseString = LocalisationService.getInstance().getString("onSettingsCommand", language);
        return baseString;
    }

    private static String onChooseServiceMessage(String language) {
        return FinanceAssistantService.getInstance().describeMyBots(language);
    }

    private static String getHelpMessage(String language) {
        String baseString = LocalisationService.getInstance().getString("greetings", language);
        return baseString;
    }

    private static String getLanguageMessage(String language) {
        String baseString = LocalisationService.getInstance().getString("selectLanguage", language);
        return String.format(baseString, language);
    }

    private static String getLinkMessage(String language) {
        String baseString = LocalisationService.getInstance().getString("onLinkCommand", language);
        return String.format(baseString, language);
    }

    private static String getFeedbackMessage(String language){
        String baseString = LocalisationService.getInstance().getString("onFeedbackCommand", language);
        return String.format(baseString, language);
    }

    private static String getSiteMessage(String language){
        return LocalisationService.getInstance().getString("onSite", language);
    }

    private static String getVisitSiteMessage(String language){
        return LocalisationService.getInstance().getString("visitSite", language);
    }




    private static ReplyKeyboardMarkup getMainMenuKeyboard(String language) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(false);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(getChooseServiceCommand(language));
        keyboardFirstRow.add(getSettingsCommand(language));
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(getFeedbackCommand(language));
        keyboardSecondRow.add(getLinkCommand(language));
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);

        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    private static ReplyKeyboardMarkup getLanguagesKeyboard(String language) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        for (String languageName : LocalisationService.getInstance().getSupportedLanguages().values()) {
            KeyboardRow row = new KeyboardRow();
            row.add(languageName);
            keyboard.add(row);
        }
        KeyboardRow row = new KeyboardRow();
        row.add(getBackCommand(language));
        keyboard.add(row);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }


    private static ReplyKeyboardMarkup getSettingsKeyboard(String language) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(getLanguagesCommand(language));
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(getBackCommand(language));
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    private static InlineKeyboardMarkup getServicesKeyboard(String language){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for(MyBot bot: MyBot.values()) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(bot.getName(language));
            button.setUrl(bot.getUrl());
            row.add(button);
            rows.add(row);
        }
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    private static ForceReplyKeyboard getForceReply() {
        ForceReplyKeyboard forceReplyKeyboard = new ForceReplyKeyboard();
        forceReplyKeyboard.setSelective(true);
        return forceReplyKeyboard;
    }

    // endregion ReplyKeyboards

    // region getCommnads

    private static String getLanguagesCommand(String language) {
        return LocalisationService.getInstance().getString("languages", language);
    }



    private static String getBackCommand(String language) {
        return LocalisationService.getInstance().getString("back", language);
    }

    private static String getChooseServiceCommand(String language){
        return LocalisationService.getInstance().getString("chooseService", language);
    }

    private static String getSettingsCommand(String language) {
        return LocalisationService.getInstance().getString("settings", language);
    }


    private static String getLinkCommand(String language){
        return LocalisationService.getInstance().getString("link", language);
    }

    private static String getFeedbackCommand(String language){
        return LocalisationService.getInstance().getString("feedback", language);
    }

    // endregion getCommnads

    // region Send common messages

    private static SendMessage sendMessageDefault(Message message, String language) {
        ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard(language);
        DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(MAINMENU);
        return sendHelpMessage(message.getChatId(), message.getMessageId(), replyKeyboardMarkup, language);
    }


    private static SendMessage justSendMessage(SendMessage message, String language){
        ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard(language);
        SendMessage sendMessage = message;
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    private static SendMessage sendChooseOption(Long chatId, Integer messageId,
                                                ReplyKeyboard replyKeyboard, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId.toString());
        sendMessage.setReplyToMessageId(messageId);
        sendMessage.setReplyMarkup(replyKeyboard);
        sendMessage.setText(message);

        return sendMessage;
    }

    private static SendMessage sendChooseServicesOption(Message message, String language){
        SendMessage answer = new SendMessage();
        answer.enableMarkdown(true);
        answer.setChatId(message.getChatId());
        answer.setReplyToMessageId(message.getMessageId());
        answer.setText(onChooseServiceMessage(language));
        answer.setReplyMarkup(getServicesKeyboard(language));
        return answer;
    }


    private static SendMessage sendHelpMessage(Long chatId, Integer messageId, ReplyKeyboardMarkup replyKeyboardMarkup, String language) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setReplyToMessageId(messageId);
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        sendMessage.setText(getHelpMessage(language));
        return sendMessage;
    }


    private static SendMessage sendErrorMessage(Long chatId, Integer messageId, ReplyKeyboardMarkup replyKeyboardMarkup, String language) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setReplyToMessageId(messageId);
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        sendMessage.setText(getInternalErrorMessage(language));
        return sendMessage;
    }


    private static SendMessage onShowSiteChoosen(Message message, String language){
        SendMessage answer = new SendMessage();
        answer.enableMarkdown(true);
        answer.setChatId(message.getChatId());
        answer.setReplyToMessageId(message.getMessageId());
        answer.setText(getSiteMessage(language));

        answer.setReplyMarkup(getSiteKeyboard(language));
        return answer;
    }

    private static InlineKeyboardMarkup getSiteKeyboard(String language){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(getVisitSiteMessage(language));
        button.setUrl("http://104.236.114.130:8080/");
        row.add(button);
        rows.add(row);

        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

}
