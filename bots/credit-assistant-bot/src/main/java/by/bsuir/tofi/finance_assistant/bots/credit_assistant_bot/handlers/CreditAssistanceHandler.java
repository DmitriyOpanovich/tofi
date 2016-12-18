package by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.handlers;

import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.BotConfig;
import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.Commands;
import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.model.Credit;
import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.model.CreditFilter;
import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.model.MyBot;
import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.model.enums.BaseEnumEntity;
import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.model.enums.EnumsCollection;
import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services.CreditAssistantService;
import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services.LocalisationService;
import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.storage.DB;
import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.storage.StorageManager;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.*;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

import java.util.ArrayList;
import java.util.List;


public class CreditAssistanceHandler extends TelegramLongPollingBot {
    private static final String LOGTAG = "CREDITNDLERS";

    private static final int STARTSTATE = 0;
    private static final int MAINMENU = 1;
    private static final int FINDCREDITFORCLIENTTYPE = 2;
    private static final int FINDCREDITFORCREDITGOAL = 3;
    private static final int FINDCREDITFORCURRENCY = 4;
    private static final int FINDCREDITFORSUMM = 5;
    private static final int FINDCREDITFORPERCENTAGE = 6;
    private static final int FINDCREDITFORTERM = 7;
    private static final int FINDCREDITFORGUARANTOR = 8;
    private static final int FINDCREDITFORPLEDGE = 9;
    private static final int FINDCREDITFORCERTIFICATES = 10;
    private static final int FINDCREDITFORGRACEPERIOD = 11;
    private static final int FINDCREDITFORPREPAYMENTS = 12;
    private static final int FINDCREDITFORPAYMENTPOSSIBILITIES = 13;
    private static final int FINDCREDITFORREPAYMENTMETHOD = 14;
    private static final int GENERATEREPORT = 15;
    private static final int SETTINGS = 16;
    private static final int LANGUAGE = 17;
    private static final int LINKTOSITE = 18;
    private static final int BACKTOFINASS = 19;
    private static SendDocument sendDocument;


    public CreditAssistanceHandler() {
        super();
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
            }else if (message.getText().startsWith(Commands.STOPCOMMAND)){
                sendHideKeyboard(message.getFrom().getId(), message.getChatId(), message.getMessageId());
                return;
            }else if (message.getText().startsWith(Commands.SITE)){
                sendMessage(onShowSiteChoosen(message,language));
                return;
            }

        SendMessage sendMessageRequest;
        switch(state) {
            case MAINMENU:
                sendMessageRequest = messageOnMainMenu(message, language);
                break;
            case FINDCREDITFORCLIENTTYPE:
            case FINDCREDITFORCREDITGOAL:
            case FINDCREDITFORCURRENCY:
            case FINDCREDITFORSUMM:
            case FINDCREDITFORPERCENTAGE:
            case FINDCREDITFORTERM:
            case FINDCREDITFORGUARANTOR:
            case FINDCREDITFORPAYMENTPOSSIBILITIES:
            case FINDCREDITFORGRACEPERIOD:
            case FINDCREDITFORPREPAYMENTS:
            case FINDCREDITFORREPAYMENTMETHOD:
            case FINDCREDITFORPLEDGE:
            case FINDCREDITFORCERTIFICATES:
                sendMessageRequest = messageOnCreditFinding(message, language, state);
                break;
            case GENERATEREPORT:
                sendMessageRequest = messageOnGenerateReport(message, language);
                try {
                    sendDocument = CreditAssistantService.getInstance().sendReport(StorageManager.generateReportForThisFilter(message.getFrom().getId(), DB.getUserState(message.getFrom().getId()).getCreditFilter(), language).getPdfView());
                    if (sendDocument != null) {
                        sendDocument.setChatId(message.getChatId());
                        try {
                            sendDocument(sendDocument);
                        } catch (TelegramApiException e) {
                            BotLogger.error(LOGTAG, e);
                        }
                    }
                }catch (Exception e){

                }

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


    private void sendHideKeyboard(Integer userId, Long chatId, Integer messageId) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyToMessageId(messageId);

        ReplyKeyboardRemove  replyKeyboardHide = new ReplyKeyboardRemove();
        replyKeyboardHide.setSelective(true);
        sendMessage.setReplyMarkup(replyKeyboardHide);

        sendMessage(sendMessage);
        DB.getUserState(chatId, userId).setState(STARTSTATE);
    }

    private static boolean isCommandForOther(String text) {
        boolean isSimpleCommand = text.equals("/start") || text.equals("/site") || text.equals("/stop");
        boolean isCommandForMe = text.equals("/start@creditassistantbot") || text.equals("/site@creditassistantbot") || text.equals("/stop@creditassistantbot");
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
            case FINDCREDITFORCLIENTTYPE:{
                sendMessageRequest = onFindingCreditForClientType(message, language);
                break;
            }
            case FINDCREDITFORCREDITGOAL:{
                sendMessageRequest = onFindingCreditForCreditGoal(message, language);
                break;
            }
            case FINDCREDITFORCURRENCY:{
                sendMessageRequest = onFindingCreditForCurrency(message, language);
                break;
            }
            case FINDCREDITFORSUMM:{
                sendMessageRequest = onFindingCreditForSumm(message, language);
                break;
            }
            case FINDCREDITFORPERCENTAGE:{
                sendMessageRequest = onFindingCreditForPercentage(message, language);
                break;
            }
            case FINDCREDITFORTERM:{
                sendMessageRequest = onFindingCreditForTerm(message, language);
                break;
            }
            case FINDCREDITFORGUARANTOR:{
                sendMessageRequest = onFindingCreditForGuarantor(message, language);
                break;
            }
            case FINDCREDITFORPAYMENTPOSSIBILITIES:{
                sendMessageRequest = onFindingCreditForPaymentPossibilities(message, language);
                break;
            }
            case FINDCREDITFORGRACEPERIOD:{
                sendMessageRequest = onFindingCreditForGracePeriod(message, language);
                break;
            }
            case FINDCREDITFORPREPAYMENTS:{
                sendMessageRequest = onFindingCreditForPrepayments(message, language);
                break;
            }
            case FINDCREDITFORREPAYMENTMETHOD:{
                sendMessageRequest = onFindingCreditForRepaymentMethod(message, language);
                break;
            }
            case FINDCREDITFORPLEDGE:{
                sendMessageRequest = onFindingCreditForPledge(message, language);
                break;
            }
            case FINDCREDITFORCERTIFICATES:{
                sendMessageRequest = onFindingCreditForCertificates(message, language);
                break;
            }
        }
        return sendMessageRequest;
    }

    private static SendMessage onFindingCreditForClientType(Message message, String language) {
        return onFindingCreditForClientTypeReceived(message, language);
    }

    private static SendMessage onFindingCreditForClientTypeReceived(Message message, String language){
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            if(StorageManager.getPossibleClientTypes().isOption(message.getText())) {

                EnumsCollection creditGoals = StorageManager.getPossibleCreditGoals();
                ReplyKeyboardMarkup replyKeyboardMarkup = getRecentsKeyboard(message.getFrom().getId(), language, creditGoals.getOptions());
                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                sendMessage.setReplyToMessageId(message.getMessageId());
                sendMessage.setChatId(message.getChatId());
                sendMessage.setText(getCreditGoalsMessage(creditGoals.getOptions(), language));
                DB.getUserState(message.getChatId(), message.getFrom().getId()).getCreditFilter().setClientType(message.getText());
                DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDCREDITFORCREDITGOAL);

            }else {

                EnumsCollection clientTypes = StorageManager.getPossibleClientTypes();
                ReplyKeyboardMarkup replyKeyboardMarkup = getRecentsKeyboard(message.getFrom().getId(), language, clientTypes.getOptions());
                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                sendMessage.setReplyToMessageId(message.getMessageId());
                sendMessage.setChatId(message.getChatId());
                sendMessage.setText(getChooseFailMessage(language));
                DB.getUserState(message.getChatId(), message.getFrom().getId()).setCreditFilter(new CreditFilter());
                DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDCREDITFORCLIENTTYPE);

            }
            return sendMessage;
        }catch (Exception e){
            return sendMessageDefault(message, language, false);
        }
    }

    private static SendMessage onFindingCreditForCreditGoal(Message message, String language) {
        return onFindingCreditForCreditGoalReceived(message, language);
    }

    private static SendMessage onFindingCreditForCreditGoalReceived(Message message, String language){
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            if(StorageManager.getPossibleCreditGoals().isOption(message.getText())) {
                EnumsCollection currencies = StorageManager.getPossibleCurrencies();
                ReplyKeyboardMarkup replyKeyboardMarkup = getRecentsKeyboard(message.getFrom().getId(), language, currencies.getOptions());
                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                sendMessage.setReplyToMessageId(message.getMessageId());
                sendMessage.setChatId(message.getChatId());
                sendMessage.setText(getCurrenciesMessage(currencies.getOptions(), language));
                DB.getUserState(message.getChatId(), message.getFrom().getId()).getCreditFilter().setGoal(message.getText());
                DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDCREDITFORCURRENCY);
            }else {
                EnumsCollection creditGoals = StorageManager.getPossibleCreditGoals();
                ReplyKeyboardMarkup replyKeyboardMarkup = getRecentsKeyboard(message.getFrom().getId(), language, creditGoals.getOptions());
                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                sendMessage.setReplyToMessageId(message.getMessageId());
                sendMessage.setChatId(message.getChatId());
                sendMessage.setText(getChooseFailMessage(language));
                DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDCREDITFORCREDITGOAL);
            }
            return sendMessage;
        }catch (Exception e){
            return sendMessageDefault(message, language, false);
        }
    }

    private static SendMessage onFindingCreditForCurrency(Message message, String language) {
        return onFindingCreditForCurrencyReceived(message, language);
    }

    private static SendMessage onFindingCreditForCurrencyReceived(Message message, String language){
        try {
            SendMessage sendMessage = new SendMessage();
            if(StorageManager.getPossibleCurrencies().isOption(message.getText())) {
                ForceReplyKeyboard forceReplyKeyboard = getForceReply();
                sendMessage.enableMarkdown(true);
                sendMessage.setChatId(message.getChatId());
                sendMessage.setReplyToMessageId(message.getMessageId());
                sendMessage.setReplyMarkup(forceReplyKeyboard);
                sendMessage.setText(getAmountMessage(language));
                DB.getUserState(message.getChatId(), message.getFrom().getId()).getCreditFilter().setCurrency(message.getText());
                DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDCREDITFORSUMM);
            }else {
                sendMessage.enableMarkdown(true);
                EnumsCollection currencies = StorageManager.getPossibleCurrencies();
                ReplyKeyboardMarkup replyKeyboardMarkup = getRecentsKeyboard(message.getFrom().getId(), language, currencies.getOptions());
                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                sendMessage.setReplyToMessageId(message.getMessageId());
                sendMessage.setChatId(message.getChatId());
                sendMessage.setText(getChooseFailMessage(language));
                DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDCREDITFORCURRENCY);
            }
            return sendMessage;
        }catch (Exception e){
            return sendMessageDefault(message, language, false);
        }
    }

    private static SendMessage onFindingCreditForSumm(Message message, String language) {
        if(message.isReply()) {
            return onFindingCreditForSummReceived(message, language);
        }else {
            return sendMessageDefault(message, language);
        }

    }

    private static SendMessage onFindingCreditForSummReceived(Message message, String language){
        ForceReplyKeyboard forceReplyKeyboard = getForceReply();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setReplyMarkup(forceReplyKeyboard);
        try {
            Integer integer = Integer.parseInt(message.getText());
            sendMessage.setText(getPercentageMessage(language));
            DB.getUserState(message.getChatId(), message.getFrom().getId()).getCreditFilter().setAmount(integer);
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDCREDITFORPERCENTAGE);
            return sendMessage;
        }catch (Exception e){
            BotLogger.error(LOGTAG, e);
            sendMessage.setText(getFormatFailMessage(language));
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDCREDITFORSUMM);
            return sendMessage;
        }
    }

    private static SendMessage onFindingCreditForPercentage(Message message, String language) {
        if (message.isReply()) {
            return onFindingCreditForPercentageReceived(message, language);
        } else {
            return sendMessageDefault(message, language);
        }
    }

    private static SendMessage onFindingCreditForPercentageReceived(Message message, String language){
        ForceReplyKeyboard forceReplyKeyboard = getForceReply();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setReplyMarkup(forceReplyKeyboard);
        try {
            Double d = Double.parseDouble(message.getText());
            sendMessage.setText(getTermMessage(language));
            DB.getUserState(message.getChatId(), message.getFrom().getId()).getCreditFilter().setMaxPercentage(d);
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDCREDITFORTERM);
            return sendMessage;
        }catch (Exception e){
            BotLogger.error(LOGTAG, e);
            sendMessage.setText(getFormatFailMessage(language));
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDCREDITFORPERCENTAGE);
            return sendMessage;
        }

    }

    private static SendMessage onFindingCreditForTerm(Message message, String language) {
        if (message.isReply()) {
            return onFindingCreditForTermReceived(message, language);
        } else {
            return sendMessageDefault(message, language);
        }
    }

    private static SendMessage onFindingCreditForTermReceived(Message message, String language){
        ReplyKeyboardMarkup replyKeyboardMarkup = getAlternativeKeyboard(language);
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        try {
            Integer integer = Integer.parseInt(message.getText());
            sendMessage.setText(getGuarantorMessage(language));
            DB.getUserState(message.getChatId(), message.getFrom().getId()).getCreditFilter().setTermInMounth(integer);
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDCREDITFORGUARANTOR);
            return sendMessage;
        }catch (Exception e){
            BotLogger.error(LOGTAG, e);
            sendMessage.setReplyMarkup(getForceReply());
            sendMessage.setText(getFormatFailMessage(language));
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDCREDITFORTERM);
            return sendMessage;
        }

    }

    private static SendMessage onFindingCreditForGuarantor(Message message, String language) {
        return onFindingCreditForGuarantorReceived(message, language);

    }

    private static SendMessage onFindingCreditForGuarantorReceived(Message message, String language){
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            EnumsCollection paymentPossibilities = StorageManager.getPossiblePaymentPossibilities();

            ReplyKeyboardMarkup replyKeyboardMarkup = getRecentsKeyboard(message.getFrom().getId(), language, paymentPossibilities.getOptions());
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            sendMessage.setReplyToMessageId(message.getMessageId());
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText(getPaymentPossibilitiesMessage(paymentPossibilities.getOptions(), language));
            DB.getUserState(message.getChatId(), message.getFrom().getId()).getCreditFilter().setNeedGurantor(message.getText());
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDCREDITFORPAYMENTPOSSIBILITIES);
            return sendMessage;
        }catch (Exception e){
            return sendMessageDefault(message, language, false);
        }

    }

    private static SendMessage onFindingCreditForPaymentPossibilities(Message message, String language) {
        return onFindingCreditForPaymentPossibilitiesReceived(message, language);
    }

    private static SendMessage onFindingCreditForPaymentPossibilitiesReceived(Message message, String language){
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            if (StorageManager.getPossiblePaymentPossibilities().isOption(message.getText())) {
                ReplyKeyboardMarkup replyKeyboardMarkup = getAlternativeKeyboard(language);
                sendMessage.setChatId(message.getChatId());
                sendMessage.setReplyToMessageId(message.getMessageId());
                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                sendMessage.setText(getGracePeriodMessage(language));
                DB.getUserState(message.getChatId(), message.getFrom().getId()).getCreditFilter().setPaymentPosibility(message.getText());
                DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDCREDITFORGRACEPERIOD);
            } else {
                EnumsCollection paymentPossibilities = StorageManager.getPossiblePaymentPossibilities();

                ReplyKeyboardMarkup replyKeyboardMarkup = getRecentsKeyboard(message.getFrom().getId(), language, paymentPossibilities.getOptions());
                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                sendMessage.setReplyToMessageId(message.getMessageId());
                sendMessage.setChatId(message.getChatId());
                sendMessage.setText(getChooseFailMessage(language));
                DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDCREDITFORPAYMENTPOSSIBILITIES);
            }
            return sendMessage;

        }catch (Exception e){
            return sendMessageDefault(message, language, false);
        }

    }

    private static SendMessage onFindingCreditForGracePeriod(Message message, String language) {
        return onFindingCreditForGracePeriodReceived(message, language);

    }

    private static SendMessage onFindingCreditForGracePeriodReceived(Message message, String language){
        ReplyKeyboardMarkup replyKeyboardMarkup = getAlternativeKeyboard(language);
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        try {
            sendMessage.setText(getPrePaymentMessage(language));
            DB.getUserState(message.getChatId(), message.getFrom().getId()).getCreditFilter().setGracePerioid(message.getText());
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDCREDITFORPREPAYMENTS);
            return sendMessage;
        }catch (Exception e){
            BotLogger.error(LOGTAG, e);
            sendMessage.setText(getFormatFailMessage(language));
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDCREDITFORGRACEPERIOD);
            return sendMessage;
        }

    }

    private static SendMessage onFindingCreditForPrepayments(Message message, String language) {
        return onFindingCreditForPrepaymentsReceived(message, language);

    }

    private static SendMessage onFindingCreditForPrepaymentsReceived(Message message, String language){
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            EnumsCollection repaymentMethod = StorageManager.getPossibleRepaymentMethods();
            ReplyKeyboardMarkup replyKeyboardMarkup = getRecentsKeyboard(message.getFrom().getId(), language, repaymentMethod.getOptions());
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            sendMessage.setReplyToMessageId(message.getMessageId());
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText(getRepaymentMethodsMessage(repaymentMethod.getOptions(), language));
            DB.getUserState(message.getChatId(), message.getFrom().getId()).getCreditFilter().setPrePayments(message.getText());
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDCREDITFORREPAYMENTMETHOD);
            return sendMessage;
        }catch (Exception e){
            return sendMessageDefault(message, language, false);
        }

    }

    private static SendMessage onFindingCreditForRepaymentMethod(Message message, String language) {
        return onFindingCreditForRepaymentMethodReceived(message, language);

    }

    private static SendMessage onFindingCreditForRepaymentMethodReceived(Message message, String language){
        try {


            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            if(StorageManager.getPossibleRepaymentMethods().isOption(message.getText())) {
                ReplyKeyboardMarkup replyKeyboardMarkup = getAlternativeKeyboard(language);
                sendMessage.setChatId(message.getChatId());
                sendMessage.setReplyToMessageId(message.getMessageId());
                sendMessage.setReplyMarkup(replyKeyboardMarkup);

                sendMessage.setText(getPledgeMessage(language));
                DB.getUserState(message.getChatId(), message.getFrom().getId()).getCreditFilter().setRepaymentMethod(message.getText());
                DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDCREDITFORPLEDGE);
            }else {
                EnumsCollection repaymentMethod = StorageManager.getPossibleRepaymentMethods();
                ReplyKeyboardMarkup replyKeyboardMarkup = getRecentsKeyboard(message.getFrom().getId(), language, repaymentMethod.getOptions());
                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                sendMessage.setReplyToMessageId(message.getMessageId());
                sendMessage.setChatId(message.getChatId());
                sendMessage.setText(getChooseFailMessage(language));
                DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDCREDITFORREPAYMENTMETHOD);
            }
            return sendMessage;
        }catch (Exception e){
            return sendMessageDefault(message, language, false);
        }

    }

    private static SendMessage onFindingCreditForPledge(Message message, String language) {
        return onFindingCreditForPledgeReceived(message, language);

    }

    private static SendMessage onFindingCreditForPledgeReceived(Message message, String language){
        ReplyKeyboardMarkup replyKeyboardMarkup = getAlternativeKeyboard(language);
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        try {
            sendMessage.setText(getCertificatesMessage(language));
            DB.getUserState(message.getChatId(), message.getFrom().getId()).getCreditFilter().setPledge(message.getText());
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDCREDITFORCERTIFICATES);
            return sendMessage;
        }catch (Exception e){
            BotLogger.error(LOGTAG, e);
            sendMessage.setText(getFormatFailMessage(language));
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDCREDITFORPLEDGE);
            return sendMessage;
        }

    }

    private static SendMessage onFindingCreditForCertificates(Message message, String language) {
        return onFindingCreditForCertificatesReceived(message, language);

    }

    private static SendMessage onFindingCreditForCertificatesReceived(Message message, String language){
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            ReplyKeyboardMarkup replyKeyboardMarkup = getLastKeyboard(language);
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            sendMessage.setReplyToMessageId(message.getMessageId());
            sendMessage.setChatId(message.getChatId());
            DB.getUserState(message.getChatId(), message.getFrom().getId()).getCreditFilter().setCertificates(message.getText());
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(GENERATEREPORT);
            Credit credit = StorageManager.chooseCreditForThisFilter(message.getFrom().getId(), DB.getUserState(message.getFrom().getId()).getCreditFilter());
            sendMessage.setText(CreditAssistantService.getInstance().showBestCredit(credit, language));
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
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setCreditFilter(new CreditFilter());
            DB.getUserState(message.getChatId(), message.getFrom().getId()).setState(FINDCREDITFORCLIENTTYPE);
        }catch (Exception e){
            return sendMessageDefault(message, language, false);
        }

        return sendMessage;
    }

    // endregion Main menu options selected

    // region Get Messages

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
        return CreditAssistantService.getInstance().describeClientTypes(clientTypes, language);
    }

    private static String getCreditGoalsMessage(List<BaseEnumEntity> creditGoals, String language){
        return CreditAssistantService.getInstance().describeCreditGoals(creditGoals, language);
    }

    private static String getCurrenciesMessage(List<BaseEnumEntity> currencies, String language){
        return CreditAssistantService.getInstance().describeCurrencies(currencies, language);
    }

    private static String getAmountMessage(String language) {
        return LocalisationService.getInstance().getString("amount", language);
    }

    private static String getPercentageMessage(String language) {
        return LocalisationService.getInstance().getString("percentage", language);
    }

    private static String getTermMessage(String language) {
        return LocalisationService.getInstance().getString("termInMonth", language);
    }

    private static String getGuarantorMessage(String language) {
        return LocalisationService.getInstance().getString("needGuarantor", language);
    }

    private static String getPaymentPossibilitiesMessage(List<BaseEnumEntity> paymentPosibilities, String language){
        return CreditAssistantService.getInstance().describePaymentPossibilities(paymentPosibilities, language);
    }

    private static String getGracePeriodMessage(String language){
        return LocalisationService.getInstance().getString("gracePeriod", language);
    }

    private static String getPrePaymentMessage(String language) {
        return LocalisationService.getInstance().getString("prePayment", language);
    }

    private static String getRepaymentMethodsMessage(List<BaseEnumEntity> repaymentMethods, String language) {
        return CreditAssistantService.getInstance().describeRepaymentMethods(repaymentMethods, language);
    }

    private static String getPledgeMessage(String language) {
        return LocalisationService.getInstance().getString("pledge", language);
    }

    private static String getCertificatesMessage(String language) {
        return LocalisationService.getInstance().getString("certificates", language);
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
        button.setUrl("http://104.236.114.130:8080/");
        row.add(button);
        rows.add(row);

        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

}
