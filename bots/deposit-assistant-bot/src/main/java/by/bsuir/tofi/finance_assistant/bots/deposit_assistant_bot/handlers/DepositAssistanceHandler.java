package by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.handlers;

import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.BotConfig;
import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.Commands;
import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.model.Deposit;
import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.model.DepositFilter;
import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.model.MyBot;
import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.model.enums.BaseEnumEntity;
import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.model.enums.EnumsCollection;
import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.services.DepositAssistantService;
import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.services.LocalisationService;
import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.storage.DB;
import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.storage.StorageManager;
import org.telegram.telegrambots.api.methods.send.SendDocument;
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
public class DepositAssistanceHandler extends TelegramLongPollingBot {
    private static final String LOGTAG = "DEPOSITNDLERS";

    private static final int STARTSTATE = 0;
    private static final int MAINMENU = 1;
    
    private static final int FINDDEPOSITFORCLIENTTYPE = 2;
    private static final int FINDDEPOSITFORCURRENCY = 3;
    private static final int FINDDEPOSITFORPERCENTAGE = 4;
    private static final int FINDDEPOSITFORPERCENTAGETYPE = 5;
    private static final int FINDDEPOSITFORINITFEE = 6;
    private static final int FINDDEPOSITFORTERM = 7;
    private static final int FINDDEPOSITFORBEFORETERM = 8;
    private static final int FINDDEPOSITFORREFILLING = 9;
    private static final int FINDDEPOSITFORCAPIT = 10;

    private static final int GENERATEREPORT = 11;
    private static final int SETTINGS = 12;
    private static final int LANGUAGE = 13;
    private static final int LINKTOSITE = 14;
    private static final int BACKTOFINASS = 15;
    private static SendDocument sendDocument;


    public DepositAssistanceHandler() {
        super();
    }


    @Override
    public String getBotToken() {
        return BotConfig.BOT_TOKEN;
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {
        super.clearWebhook();
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
            case FINDDEPOSITFORCLIENTTYPE:
            case FINDDEPOSITFORCURRENCY:
            case FINDDEPOSITFORPERCENTAGE:
            case FINDDEPOSITFORPERCENTAGETYPE:
            case FINDDEPOSITFORINITFEE:
            case FINDDEPOSITFORTERM:
            case FINDDEPOSITFORBEFORETERM:
            case FINDDEPOSITFORREFILLING:
            case FINDDEPOSITFORCAPIT:
                sendMessageRequest = messageOnCreditFinding(message, language, state);
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
        boolean isSimpleCommand = text.equals("/start") || text.equals("/site") || text.equals("/stop");
        boolean isCommandForMe = text.equals("/start@depositassistantbot") || text.equals("/site@depositassistantbot") || text.equals("/stop@depositassistantbot");
        return text.startsWith("/") && !isSimpleCommand && !isCommandForMe;
    }

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

    private static SendMessage messageOnCreditFinding(Message message, String language, int state) {
        SendMessage sendMessageRequest = null;
        switch (state) {
            case FINDDEPOSITFORCLIENTTYPE:{
                sendMessageRequest = onFindingDepositForClientType(message, language);
                break;
            }
            case FINDDEPOSITFORCURRENCY:{
                sendMessageRequest = onFindingDepositForCurrency(message, language);
                break;
            }
            case FINDDEPOSITFORPERCENTAGE:{
                sendMessageRequest = onFindingDepositForPercentage(message, language);
                break;
            }
            case FINDDEPOSITFORPERCENTAGETYPE:{
                sendMessageRequest = onFindingDepositForPercentageType(message, language);
                break;
            }
            case FINDDEPOSITFORINITFEE:{
                sendMessageRequest = onFindingDepositForInitFee(message, language);
                break;
            }
            case FINDDEPOSITFORTERM:{
                sendMessageRequest = onFindingDepositForTerm(message, language);
                break;
            }
            case FINDDEPOSITFORBEFORETERM:{
                sendMessageRequest = onFindingDepositForBeforeTerm(message, language);
                break;
            }
            case FINDDEPOSITFORREFILLING:{
                sendMessageRequest = onFindingDepositForRefilling(message, language);
                break;
            }
            case FINDDEPOSITFORCAPIT:{
                sendMessageRequest = onFindingDepositForCapit(message, language);
                break;
            }
        }
        return sendMessageRequest;
    }

    private static SendMessage onFindingDepositForClientType(Message message, String language) {
        return onFindingDepositForClientTypeReceived(message, language);
    }

    private static SendMessage onFindingDepositForClientTypeReceived(Message message, String language){
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            EnumsCollection clientTypes = StorageManager.getPossibleClientTypes();

            if(clientTypes.isOption(message.getText())) {

                EnumsCollection currencies = StorageManager.getPossibleCurrencies();
                ReplyKeyboardMarkup replyKeyboardMarkup = getRecentsKeyboard(message.getFrom().getId(), language, currencies.getOptions());
                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                sendMessage.setReplyToMessageId(message.getMessageId());
                sendMessage.setChatId(message.getChatId());
                sendMessage.setText(getCurrenciesMessage(currencies.getOptions(), language));
                DB.getUserState(message.getChatId(), message.getFrom().getId()).getDepositFilter().setClientType(clientTypes.getOption(message.getText()));
                DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDDEPOSITFORCURRENCY);

            }else {

                ReplyKeyboardMarkup replyKeyboardMarkup = getRecentsKeyboard(message.getFrom().getId(), language, clientTypes.getOptions());
                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                sendMessage.setReplyToMessageId(message.getMessageId());
                sendMessage.setChatId(message.getChatId());
                sendMessage.setText(getChooseFailMessage(language));
                DB.getUserState(message.getChatId(), message.getFrom().getId()).setDepositFilter(new DepositFilter());
                DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDDEPOSITFORCLIENTTYPE);

            }
            return sendMessage;
        }catch (Exception e){
            return sendMessageDefault(message, language, false);
        }
    }

    private static SendMessage onFindingDepositForCurrency(Message message, String language) {
        return onFindingDepositForCurrencyReceived(message, language);
    }

    private static SendMessage onFindingDepositForCurrencyReceived(Message message, String language){
        try {
            SendMessage sendMessage = new SendMessage();
            EnumsCollection currencies = StorageManager.getPossibleCurrencies();

            if(currencies.isOption(message.getText())) {
                ForceReplyKeyboard forceReplyKeyboard = getForceReply();
                sendMessage.enableMarkdown(true);
                sendMessage.setChatId(message.getChatId());
                sendMessage.setReplyToMessageId(message.getMessageId());
                sendMessage.setReplyMarkup(forceReplyKeyboard);
                sendMessage.setText(getPercentageMessage(language));
                DB.getUserState(message.getChatId(), message.getFrom().getId()).getDepositFilter().setCurrency(currencies.getOption(message.getText()));
                DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDDEPOSITFORPERCENTAGE);
            }else {
                sendMessage.enableMarkdown(true);
                ReplyKeyboardMarkup replyKeyboardMarkup = getRecentsKeyboard(message.getFrom().getId(), language, currencies.getOptions());
                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                sendMessage.setReplyToMessageId(message.getMessageId());
                sendMessage.setChatId(message.getChatId());
                sendMessage.setText(getChooseFailMessage(language));
                DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDDEPOSITFORCURRENCY);
            }
            return sendMessage;
        }catch (Exception e){
            return sendMessageDefault(message, language, false);
        }
    }

    private static SendMessage onFindingDepositForInitFee(Message message, String language) {
        if(message.isReply()) {
            return onFindingDepositForSummReceived(message, language);
        }else {
            return sendMessageDefault(message, language);
        }

    }

    private static SendMessage onFindingDepositForSummReceived(Message message, String language){
        ForceReplyKeyboard forceReplyKeyboard = getForceReply();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setReplyMarkup(forceReplyKeyboard);
        try {
            Integer integer = Integer.parseInt(message.getText());
            if(integer<0)
                throw new NumberFormatException();
            sendMessage.setText(getTermMessage(language));
            DB.getUserState(message.getChatId(), message.getFrom().getId()).getDepositFilter().setInitFee(integer);
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDDEPOSITFORTERM);
            return sendMessage;

        }catch (Exception e){
            BotLogger.error(LOGTAG, e);
            sendMessage.setText(getFormatFailMessage(language));
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDDEPOSITFORINITFEE);
            return sendMessage;
        }
    }

    private static SendMessage onFindingDepositForPercentage(Message message, String language) {
        if (message.isReply()) {
            return onFindingDepositForPercentageReceived(message, language);
        } else {
            return sendMessageDefault(message, language);
        }
    }

    private static SendMessage onFindingDepositForPercentageReceived(Message message, String language){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyToMessageId(message.getMessageId());
        try {

            EnumsCollection percentageTypes = StorageManager.getPossiblePercentageTerms();
            Double d = Double.parseDouble(message.getText());
            if( d<0 || d>100 )
                throw new NumberFormatException();
            sendMessage.setText(getPercentageTypeMessage(percentageTypes.getOptions(), language));
            sendMessage.setReplyMarkup(getRecentsKeyboard(message.getFrom().getId(), language, percentageTypes.getOptions()));
            DB.getUserState(message.getChatId(), message.getFrom().getId()).getDepositFilter().setMinPercentage(d);
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDDEPOSITFORPERCENTAGETYPE);
            return sendMessage;
        }catch (Exception e){
            ForceReplyKeyboard forceReplyKeyboard = getForceReply();
            BotLogger.error(LOGTAG, e);
            sendMessage.setReplyMarkup(forceReplyKeyboard);
            sendMessage.setText(getFormatFailMessage(language));
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDDEPOSITFORPERCENTAGE);
            return sendMessage;
        }

    }

    private static SendMessage onFindingDepositForPercentageType(Message message, String language){
        return onFindingDepositForPercentageTypeReceived(message, language);
    }
    private static SendMessage onFindingDepositForPercentageTypeReceived(Message message, String language) {
        try {
            SendMessage sendMessage = new SendMessage();
            EnumsCollection percentageTypes = StorageManager.getPossiblePercentageTerms();

            if(percentageTypes.isOption(message.getText())) {
                ForceReplyKeyboard forceReplyKeyboard = getForceReply();
                sendMessage.enableMarkdown(true);
                sendMessage.setChatId(message.getChatId());
                sendMessage.setReplyToMessageId(message.getMessageId());
                sendMessage.setReplyMarkup(forceReplyKeyboard);
                sendMessage.setText(getInitFeeMessage(language));
                DB.getUserState(message.getChatId(), message.getFrom().getId()).getDepositFilter().setPercentageType(percentageTypes.getOption(message.getText()));
                DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDDEPOSITFORINITFEE);
            }else {
                sendMessage.enableMarkdown(true);
                ReplyKeyboardMarkup replyKeyboardMarkup = getRecentsKeyboard(message.getFrom().getId(), language, percentageTypes.getOptions());
                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                sendMessage.setReplyToMessageId(message.getMessageId());
                sendMessage.setChatId(message.getChatId());
                sendMessage.setText(getChooseFailMessage(language));
                DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDDEPOSITFORPERCENTAGETYPE);
            }
            return sendMessage;
        }catch (Exception e){
            return sendMessageDefault(message, language, false);
        }
    }


    private static SendMessage onFindingDepositForTerm(Message message, String language) {
        if (message.isReply()) {
            return onFindingDepositForTermReceived(message, language);
        } else {
            return sendMessageDefault(message, language);
        }
    }

    private static SendMessage onFindingDepositForTermReceived(Message message, String language){
        ReplyKeyboardMarkup replyKeyboardMarkup = getAlternativeKeyboard(language);
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        try {
            Integer integer = Integer.parseInt(message.getText());
            if(integer < 0)
                throw new NumberFormatException();
            sendMessage.setText(getBeforeTermMessage(language));
            DB.getUserState(message.getChatId(), message.getFrom().getId()).getDepositFilter().setTermInMounth(integer);
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDDEPOSITFORBEFORETERM);
            return sendMessage;
        }catch (Exception e){
            BotLogger.error(LOGTAG, e);
            sendMessage.setReplyMarkup(getForceReply());
            sendMessage.setText(getFormatFailMessage(language));
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDDEPOSITFORTERM);
            return sendMessage;
        }

    }


    private static SendMessage onFindingDepositForBeforeTerm(Message message, String language) {
        return onFindingDepositForBeforeTermReceived(message, language);

    }

    private static SendMessage onFindingDepositForBeforeTermReceived(Message message, String language){
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            ReplyKeyboardMarkup replyKeyboardMarkup = getAlternativeKeyboard(language);
            sendMessage.setChatId(message.getChatId());
            sendMessage.setReplyToMessageId(message.getMessageId());
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            sendMessage.setText(getRefillingMessage(language));
            DB.getUserState(message.getChatId(), message.getFrom().getId()).getDepositFilter().setBeforeTermWithdrawal(message.getText());
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDDEPOSITFORREFILLING);
            return sendMessage;
        }catch (Exception e){
            return sendMessageDefault(message, language, false);
        }

    }

    private static SendMessage onFindingDepositForRefilling(Message message, String language) {
        return onFindingDepositForRefillingReceived(message, language);

    }

    private static SendMessage onFindingDepositForRefillingReceived(Message message, String language){
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            ReplyKeyboardMarkup replyKeyboardMarkup = getAlternativeKeyboard(language);
            sendMessage.setChatId(message.getChatId());
            sendMessage.setReplyToMessageId(message.getMessageId());
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            sendMessage.setText(getCapitMessage(language));
            DB.getUserState(message.getChatId(), message.getFrom().getId()).getDepositFilter().setRefilling(message.getText());
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDDEPOSITFORCAPIT);
            return sendMessage;
        }catch (Exception e){
            return sendMessageDefault(message, language, false);
        }

    }

    private static SendMessage onFindingDepositForCapit(Message message, String language) {
        return onFindingDepositForCapitReceived(message, language);

    }

    private static SendMessage onFindingDepositForCapitReceived(Message message, String language){
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            sendMessage.setReplyMarkup(getReportKeyboard(message.getFrom().getId(), language));
            sendMessage.setReplyToMessageId(message.getMessageId());
            sendMessage.setChatId(message.getChatId());
            DB.getUserState(message.getChatId(), message.getFrom().getId()).getDepositFilter().setCapitalization(message.getText());
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(MAINMENU);
            Deposit deposit = StorageManager.chooseDepositForThisFilter(message.getFrom().getId(), DB.getUserState(message.getChatId(), message.getFrom().getId()).getDepositFilter());
            sendMessage.setText(DepositAssistantService.getInstance().showBestDeposit(deposit, language));
            return sendMessage;
        }catch (Exception e){
            return sendMessageDefault(message, language, false);
        }

    }

    // region Main menu options selected

    private static SendMessage messageOnGenerateReport(Message message, String language) {
        SendMessage sendMessageRequest;
        sendMessageRequest = sendMessageDefault(message, language);
        return sendMessageRequest;
    }


    private static SendMessage messageOnMainMenu(Message message, String language) {
        SendMessage sendMessageRequest;
        if (message.hasText()) {
            if (message.getText().equals(getStartCommand(language))) {
                sendMessageRequest = onStartChoosen(message, language);
            } else if (message.getText().equals(getLinkCommand(language))) {
                sendMessageRequest = onLinkChoosen(message, language);
            } else if (message.getText().equals(getSettingsCommand(language))) {
                sendMessageRequest = onSettingsChoosen(message, language);
            } else if (message.getText().equals(getBackToFinanceAssistantCommand(language))) {
                sendMessageRequest = sendBackToFinanceAssistantOption(message, language);
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

    private static SendMessage onLinkToSite(Message message, String language) {
        if (message.isReply()) {
            return onLinkReceived(message, language);
        } else {
            return sendMessageDefault(message, language);
        }
    }

    private static SendMessage onLinkReceived(Message message, String language) {
        try {
            boolean status = StorageManager.connectWithSiteUser(message.getFrom().getId(), message.getText());
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            sendMessage.setChatId(message.getChatId());
            sendMessage.setReplyToMessageId(message.getMessageId());
            sendMessage.setReplyMarkup(getMainMenuKeyboard(language));
            if (status) {
                sendMessage.setText(LocalisationService.getInstance().getString("onLinkSuccess", language));
            } else {
                sendMessage.setText(LocalisationService.getInstance().getString("onLinkFail", language));
            }
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(MAINMENU);
            return sendMessage;
        }catch (Exception e){
            return sendMessageDefault(message, language);
        }
    }

    private static SendMessage onStartChoosen(Message message, String language) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        try {
            EnumsCollection clientTypes = StorageManager.getPossibleClientTypes();
            ReplyKeyboardMarkup replyKeyboardMarkup = getRecentsKeyboard(message.getFrom().getId(), language, clientTypes.getOptions());
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            sendMessage.setReplyToMessageId(message.getMessageId());
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText(getClientTypeMessage(clientTypes.getOptions(), language));
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setDepositFilter(new DepositFilter());
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDDEPOSITFORCLIENTTYPE);
        }catch (Exception e){
            return sendMessageDefault(message, language, false);
        }

        return sendMessage;
    }

    // endregion Main menu options selected

    // region Get Messages

    private static String getStoppedMessage(String language){
        String baseString = LocalisationService.getInstance().getString("onStop", language);
        return baseString;
    }

    private static String getBackMessage(String language){
        String baseString = LocalisationService.getInstance().getString("back", language);
        return baseString;
    }

    private static String getBackToFinanceAssistantMessage(String language){
        String baseString = LocalisationService.getInstance().getString("backToFinanceAssistantMessage", language);
        return baseString;
    }

    private static String getFormatFailMessage(String language){
        String baseString = LocalisationService.getInstance().getString("onFormatFail", language);
        return baseString;
    }

    private static String getChooseFailMessage(String language){
        String baseString = LocalisationService.getInstance().getString("onChooseFail", language);
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

    private static String getClientTypeMessage(List<BaseEnumEntity> clientTypes, String language) {
        return DepositAssistantService.getInstance().describeClientTypes(clientTypes, language);
    }

    private static String getPercentageTypeMessage(List<BaseEnumEntity> percentageTypes, String language){
        return DepositAssistantService.getInstance().describePercentageTypes(percentageTypes, language);
    }

    private static String getCurrenciesMessage(List<BaseEnumEntity> currencies, String language){
        return DepositAssistantService.getInstance().describeCurrencies(currencies, language);
    }

    private static String getInitFeeMessage(String language) {
        return LocalisationService.getInstance().getString("initFee", language);
    }

    private static String getPercentageMessage(String language) {
        return LocalisationService.getInstance().getString("percentage", language);
    }

    private static String getTermMessage(String language) {
        return LocalisationService.getInstance().getString("termInMonth", language);
    }

    private static String getBeforeTermMessage(String language) {
        return LocalisationService.getInstance().getString("beforeTerm", language);
    }

    private static String getCapitMessage(String language) {
        return LocalisationService.getInstance().getString("capitalization", language);
    }

    private static String getRefillingMessage(String language) {
        return LocalisationService.getInstance().getString("refilling", language);
    }

    private static String getYesMessage(String language){
        return LocalisationService.getInstance().getString("yesAnswer", language);
    }

    private static String getNoMessage(String language){
        return LocalisationService.getInstance().getString("noAnswer", language);
    }

    private static String getNvmMessage(String language){
        return LocalisationService.getInstance().getString("nvmAnswer", language);
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
        keyboardFirstRow.add(getStartCommand(language));
        keyboardFirstRow.add(getBackToFinanceAssistantCommand(language));
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(getSettingsCommand(language));
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

    private static ReplyKeyboardMarkup getAlternativeKeyboard(String language){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(getYesMessage(language));
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(getNoMessage(language));
        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add(getNvmMessage(language));
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    private static ReplyKeyboardMarkup getLastKeyboard(String language){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(getBackMessage(language));
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(getReportCommand(language));
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    private static ReplyKeyboardMarkup getRecentsKeyboard(Integer userId, String language, List<BaseEnumEntity> baseEnumEntities) {
        return getRecentsKeyboard(userId, language, true, baseEnumEntities);
    }

    private static ReplyKeyboardMarkup getRecentsKeyboard(Integer userId, String language, boolean allowNew, List<BaseEnumEntity> baseEnumEntities) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(true);

        List<KeyboardRow> keyboard = new ArrayList<>();
        for (BaseEnumEntity baseEnumEntity: baseEnumEntities) {
            KeyboardRow row = new KeyboardRow();
            row.add(baseEnumEntity.getName());
            keyboard.add(row);
        }

        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
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

    private static String getBackToFinanceAssistantCommand(String language) {
        return LocalisationService.getInstance().getString("backToFinanceAssistant", language);
    }

    private static String getSettingsCommand(String language) {
        return LocalisationService.getInstance().getString("settings", language);
    }

    private static String getStartCommand(String language){
        return LocalisationService.getInstance().getString("start", language);
    }

    private static String getLinkCommand(String language){
        return LocalisationService.getInstance().getString("link", language);
    }


    private static String getReportCommand(String language){
        String baseString = LocalisationService.getInstance().getString("fullReport", language);
        return baseString;
    }
    // endregion getCommnads

    // region Send common messages

    private static SendMessage sendMessageDefault(Message message, String language) {
        ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard(language);
        DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(MAINMENU);
        return sendHelpMessage(message.getChatId(), message.getMessageId(), replyKeyboardMarkup, language);
    }


    private static SendMessage sendMessageDefault(Message message, String language, boolean s) {
        ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard(language);
        DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(MAINMENU);
        return sendErrorMessage(message.getChatId(), message.getMessageId(), replyKeyboardMarkup, language);
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

    private static SendMessage sendBackToFinanceAssistantOption(Message message, String language){
        SendMessage answer = new SendMessage();
        answer.enableMarkdown(true);
        answer.setChatId(message.getChatId());
        answer.setReplyToMessageId(message.getMessageId());
        answer.setText(getBackToFinanceAssistantMessage(language));
        answer.setReplyMarkup(getServicesKeyboard(language));
        return answer;
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

    private static InlineKeyboardMarkup getServicesKeyboard(String language){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(MyBot.FINANCE_ASSISTANT.getName(language));
        button.setUrl(MyBot.FINANCE_ASSISTANT.getUrl());
        row.add(button);
        rows.add(row);

        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    private static InlineKeyboardMarkup getSiteKeyboard(String language){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(getVisitSiteMessage(language));
        button.setUrl("http://finance-assistant.club/");
        row.add(button);
        rows.add(row);

        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }
    private static InlineKeyboardMarkup getReportKeyboard(int userId, String language){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(getReportCommand(language));
        button.setUrl(BotConfig.GETPDF_URL+DB.getUserState(userId).getPdfView());
        row.add(button);
        rows.add(row);

        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }
}