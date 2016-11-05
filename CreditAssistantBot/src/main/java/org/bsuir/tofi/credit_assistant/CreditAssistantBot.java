package org.bsuir.tofi.credit_assistant;

import org.bsuir.tofi.credit_assistant.structure.DB;
import org.bsuir.tofi.credit_assistant.structure.Fullname;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardHide;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 18.10.2016.
 */
public class CreditAssistantBot extends TelegramLongPollingBot {
    private static final String LOGTAG = "WEATHERHANDLERS";

    private static final int STARTSTATE = 0;
    private static final int MAINMENU = 1;
    private static final int GREETINGS = 2;
    private static final int GREETINGSFIRSTNAME = 3;
    private static final int GREETINGSSECONDNAME = 4;
    private static final int HATINGS = 5;
    private static final int NEWHATINGS = 6;
    private static final int HATINGSFIRSTNAME = 7;
    private static final int HATINGSSECONDNAME = 8;


    public CreditAssistantBot() {
        super();
    }

    @Override
    public String getBotToken() {
        return BotConfig.BOT_TOKEN;
    }
/*
    public void onUpdateReceived(Update update) {
        //check if the update has a message
        if(update.hasMessage()){
            Message message = update.getMessage();

            //check if the message has text. it could also contain for example a location ( message.hasLocation() )
            if(message.hasText()){
                //create an object that contains the information to send back the message
                SendMessage sendMessageRequest = new SendMessage();
                sendMessageRequest.setChatId(message.getChatId().toString()); //who should get from the message the sender that sent it.
                sendMessageRequest.setText("you said: " + message.getText());
                try {
                    sendMessage(sendMessageRequest); //at the end, so some magic and send the message ;)
                } catch (TelegramApiException e) {
                    //do some error handling
                }
            }
        }
    }
*/
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
        final int state = DB.getUser(message.getChatId(),message.getFrom().getId()).getState();
        DB.getUser(message.getChatId(),message.getFrom().getId()).setUserName(message.getFrom().getFirstName());
        DB.getUser(message.getChatId(),message.getFrom().getId()).setUserSecondName(message.getFrom().getLastName());
        if (!message.isUserMessage() && message.hasText()) {
            if (isCommandForOther(message.getText())) {
                return;
            } else if (message.getText().startsWith(Commands.STOPCOMMAND)){
                sendHideKeyboard(message.getFrom().getId(), message.getChatId(), message.getMessageId());
                return;
            }
        }
        SendMessage sendMessageRequest;
        switch(state) {
            case MAINMENU:
                sendMessageRequest = messageOnMainMenu(message);
                break;
            case GREETINGS:
            case GREETINGSFIRSTNAME:
            case GREETINGSSECONDNAME:
                sendMessageRequest = messageOnGreetings(message,state);
                break;
            case HATINGS:
            case NEWHATINGS:
            case HATINGSFIRSTNAME:
            case HATINGSSECONDNAME:
                sendMessageRequest = messageOnHatings(message,state);
                break;
            default:
                sendMessageRequest = sendMessageDefault(message);
                break;
        }

        sendMessage(sendMessageRequest);
    }

    private static boolean isCommandForOther(String text) {
        boolean isSimpleCommand = text.equals("/start") || text.equals("/help") || text.equals("/stop");
        boolean isCommandForMe = text.equals("/start@creditassistant") || text.equals("/help@creditassistant") || text.equals("/stop@weatherbot");
        return text.startsWith("/") && !isSimpleCommand && !isCommandForMe;
    }

    private void sendHideKeyboard(Integer userId, Long chatId, Integer messageId) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyToMessageId(messageId);
        sendMessage.setText(Emoji.WAVING_HAND_SIGN.toString());
        ReplyKeyboardHide replyKeyboardHide = new ReplyKeyboardHide();
        replyKeyboardHide.setSelective(true);
        sendMessage.setReplyMarkup(replyKeyboardHide);

        sendMessage(sendMessage);
    }

    // endregion Current Weather Menu Option selected

    // region Main menu options selected

    private static SendMessage messageOnMainMenu(Message message) {
        SendMessage sendMessageRequest;
        if (message.hasText()) {
            if (message.getText().equals(getGreetingsCommand())) {
                sendMessageRequest = onGreetingsChosen(message);
            } else if (message.getText().equals(getHatingsCommand())) {
                sendMessageRequest = onHatingsChosen(message);
            } else if (message.getText().equals(getHelpCommand())) {
                sendMessageRequest = onHelpChoosen(message);
            } else if (message.getText().equals(getRateCommand())) {
                sendMessageRequest = sendRateMessage(message.getChatId().toString(), message.getMessageId(), null);
            } else {
                sendMessageRequest = sendChooseOptionMessage(message.getChatId(), message.getMessageId(),
                        getMainMenuKeyboard());
            }
        } else {
            sendMessageRequest = sendChooseOptionMessage(message.getChatId(), message.getMessageId(),
                    getMainMenuKeyboard());
        }

        return sendMessageRequest;
    }

    private static SendMessage messageOnHatings(Message message, int state) {
        SendMessage sendMessageRequest = null;
        switch(state) {
            case HATINGS:
                sendMessageRequest = onHatings(message);
                break;
            case NEWHATINGS:
                sendMessageRequest = onNewHatings(message);
                break;
            case HATINGSFIRSTNAME:
                sendMessageRequest = onHatingsFirstName(message);
                break;
            case HATINGSSECONDNAME:
                sendMessageRequest = onHatingsSecondName(message);
                break;
        }
        return sendMessageRequest;
    }

    private static SendMessage onHatingsFirstName(Message message) {
        if (message.isReply()) {
            return sendMessageFirstName(message);
        } else {
            return sendMessageDefault(message);
        }
    }


    private static SendMessage onHatingsSecondName(Message message) {
        if (message.isReply()) {
            return sendMessageSecondName(message);
        } else {
            return sendMessageDefault(message);
        }
    }

    private static SendMessage onHatings(Message message) {
        SendMessage sendMessageRequest = null;
        if (message.hasText()) {
            if (message.getText().startsWith(getNewCommand())) {
                sendMessageRequest = onNewHatingsChosen(message);
            } else if (message.getText().startsWith(getCancelCommand())) {
                sendMessageRequest = onCancelCommand(message.getChatId(), message.getFrom().getId(), message.getMessageId(),
                        getMainMenuKeyboard());
            } else {
                sendMessageRequest = onChosenPersonHatingsReceived(message.getChatId(), message.getFrom().getId(), message.getMessageId(),
                        message.getText());
            }
        }
        return sendMessageRequest;
    }

    private static SendMessage onChosenPersonHatingsReceived(Long chatId, Integer userId, Integer messageId, String text) {
        String message = "I hate " + text;
        SendMessage sendMessageRequest = new SendMessage();
        sendMessageRequest.enableMarkdown(true);
        sendMessageRequest.setReplyMarkup(getMainMenuKeyboard());
        sendMessageRequest.setReplyToMessageId(messageId);
        sendMessageRequest.setText(message);
        sendMessageRequest.setChatId(chatId.toString());
        DB.getUser(chatId,userId).setState(MAINMENU);
        return sendMessageRequest;
    }

    private static SendMessage onNewHatings(Message message){
        SendMessage sendMessageRequest = null;
        if (message.hasText()) {
            if (message.getText().startsWith(getHateCommand())) {
                sendMessageRequest = onHateCommand(message.getChatId(), message.getFrom().getId(), message.getMessageId());
            } else if (message.getText().startsWith(getFirstNameCommand())) {
                sendMessageRequest = onHateFirstNameCommand(message);
            } else if (message.getText().startsWith(getSecondNameCommand())) {
                sendMessageRequest = onHateSecondNameCommand(message);
            } else if (message.getText().startsWith(getCancelCommand())) {
                sendMessageRequest = onCancelCommand(message.getChatId(), message.getFrom().getId(), message.getMessageId(),
                        getMainMenuKeyboard());
            } else {
                sendMessageRequest = sendMessageDefault(message);
            }
        }
        return sendMessageRequest;
    }

    private static SendMessage onHateCommand(Long chatId, Integer userId, Integer messageId) {
        String firstName = DB.getUser(chatId,userId).getName();
        String secondName = DB.getUser(chatId,userId).getSecondName();
        SendMessage sendMessageRequest = new SendMessage();
        sendMessageRequest.enableMarkdown(true);
        sendMessageRequest.setReplyMarkup(getMainMenuKeyboard());
        sendMessageRequest.setReplyToMessageId(messageId);
        if(firstName==null||firstName.isEmpty()||secondName==null||secondName.isEmpty()){
            sendMessageRequest.setText("You have not set both FirstName and SecondName");
        }else {
            DB.getUser(chatId,userId).addFullName();
            sendMessageRequest.setText("I, " + DB.getUser(chatId, userId).getUserName() + " " + DB.getUser(chatId, userId).getUserSecondName() + ", hate " + firstName + " " + secondName + "!");
        }
        sendMessageRequest.setChatId(chatId.toString());
        DB.getUser(chatId,userId).setState(MAINMENU);
        return sendMessageRequest;
    }

    private static SendMessage onHateFirstNameCommand(Message message) {
        ForceReplyKeyboard forceReplyKeyboard = getForceReply();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setReplyMarkup(forceReplyKeyboard);
        sendMessage.setText("Please send me name of the person you want to hate in format *FirstName*");
        DB.getUser(message.getChatId(),message.getFrom().getId()).setState(HATINGSFIRSTNAME);
        return sendMessage;
    }

    private static SendMessage onHateSecondNameCommand(Message message) {
        ForceReplyKeyboard forceReplyKeyboard = getForceReply();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setReplyMarkup(forceReplyKeyboard);
        sendMessage.setText("Please send me SecondName of the person you want to hate in format *SecondName*");
        DB.getUser(message.getChatId(),message.getFrom().getId()).setState(HATINGSSECONDNAME);
        return sendMessage;
    }



    private static SendMessage messageOnGreetings(Message message, int state) {
        SendMessage sendMessageRequest = null;
        switch(state) {
            case GREETINGS:
                sendMessageRequest = onGreetings(message);
                break;
            case GREETINGSFIRSTNAME:
                sendMessageRequest = onGreetingsFirstName(message);
                break;
            case GREETINGSSECONDNAME:
                sendMessageRequest = onGreetingsSecondName(message);
        }
        return sendMessageRequest;
    }

    private static SendMessage onGreetingsFirstName(Message message) {
        if (message.isReply()) {
            return onCurrentWeatherReceived(message.getChatId(), message.getFrom().getId(), message.getMessageId(), message.getText());
        } else {
            return sendMessageDefault(message);
        }
    }
    private static SendMessage onGreetingsSecondName(Message message) {
        if (message.isReply()) {
            return onGreetingSecondNameStep(message.getChatId(), message.getFrom().getId(), message.getMessageId(), message.getText());
        } else {
            return sendMessageDefault(message);
        }
    }

    private static SendMessage onCurrentWeatherReceived(Long chatId, Integer userId, Integer messageId, String text) {
        DB.getUser(chatId,userId).setName(text);
        ForceReplyKeyboard forceReplyKeyboard = getForceReply();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId.toString());
        sendMessage.setReplyToMessageId(messageId);
        sendMessage.setReplyMarkup(forceReplyKeyboard);
        sendMessage.setText("Please send me SecondName of the person you want to greet in format *SecondName*");
        DB.getUser(chatId,userId).setState(GREETINGSSECONDNAME);
        return sendMessage;
    }
    private static SendMessage onGreetingSecondNameStep(Long chatId, Integer userId, Integer messageId, String text) {
        DB.getUser(chatId,userId).setSecondName(text);
        String firstName = DB.getUser(chatId,userId).getName();
        DB.getUser(chatId,userId).addFullName();
        SendMessage sendMessageRequest = new SendMessage();
        sendMessageRequest.enableMarkdown(true);
        sendMessageRequest.setReplyMarkup(getMainMenuKeyboard());
        sendMessageRequest.setReplyToMessageId(messageId);
        sendMessageRequest.setText("I, "+ DB.getUser(chatId,userId).getUserName() + " " + DB.getUser(chatId,userId).getUserSecondName() + ", greet " + firstName + " " + text + "!");
        sendMessageRequest.setChatId(chatId.toString());
        DB.getUser(chatId,userId).setState(MAINMENU);
        return sendMessageRequest;
    }

    private static SendMessage onGreetings(Message message) {
        SendMessage sendMessageRequest = null;
        if (message.hasText()) {
            if (message.getText().startsWith(getNewCommand())) {
                sendMessageRequest = onNewGreetingsCommand(message.getChatId(), message.getFrom().getId(), message.getMessageId());
            } else if (message.getText().startsWith(getCancelCommand())) {
                sendMessageRequest = onCancelCommand(message.getChatId(), message.getFrom().getId(), message.getMessageId(),
                        getMainMenuKeyboard());
            } else {
                sendMessageRequest = onChosenPersonGreetingsReceived(message.getChatId(), message.getFrom().getId(), message.getMessageId(),
                        message.getText());
            }
        }
        return sendMessageRequest;
    }

    private static SendMessage onChosenPersonGreetingsReceived(Long chatId, Integer userId, Integer messageId, String text) {
        String message = "I greet " + text;
        SendMessage sendMessageRequest = new SendMessage();
        sendMessageRequest.enableMarkdown(true);
        sendMessageRequest.setReplyMarkup(getMainMenuKeyboard());
        sendMessageRequest.setReplyToMessageId(messageId);
        sendMessageRequest.setText(message);
        sendMessageRequest.setChatId(chatId.toString());
        DB.getUser(chatId,userId).setState(MAINMENU);
        return sendMessageRequest;
    }

    private static SendMessage onCancelCommand(Long chatId, Integer userId, Integer messageId, ReplyKeyboard replyKeyboard) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyToMessageId(messageId);
        sendMessage.setReplyMarkup(replyKeyboard);
        sendMessage.setText("Process cancelled, back to main menu.");
        DB.getUser(chatId,userId).setState(MAINMENU);
        return sendMessage;
    }


    private static SendMessage onNewGreetingsCommand(Long chatId, Integer userId, Integer messageId) {
        ForceReplyKeyboard forceReplyKeyboard = getForceReply();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId.toString());
        sendMessage.setReplyToMessageId(messageId);
        sendMessage.setReplyMarkup(forceReplyKeyboard);
        sendMessage.setText("Please send me name of the person you want to greet in format *FirstName*");
        DB.getUser(chatId,userId).setState(GREETINGSFIRSTNAME);
        return sendMessage;
    }

    private static SendMessage onGreetingsChosen(Message message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        ReplyKeyboardMarkup replyKeyboardMarkup = getRecentsKeyboard(message.getFrom().getId());
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setChatId(message.getChatId().toString());
        if (replyKeyboardMarkup.getKeyboard().size() > 2) {
            sendMessage.setText("Select an option from your _recent requests_, *new* to send a new person to greet him.");
        } else {
            sendMessage.setText("Select *new* to send a new person to greet him.");
        }
        DB.getUser(message.getChatId(), message.getFrom().getId()).setState(GREETINGS);
        return sendMessage;
    }

    private static SendMessage onHatingsChosen(Message message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        ReplyKeyboardMarkup replyKeyboardMarkup = getRecentsKeyboard(message.getFrom().getId());
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setChatId(message.getChatId().toString());
        if (replyKeyboardMarkup.getKeyboard().size() > 2) {
            sendMessage.setText("Select an option from your _recent requests_, *new* to send a new person to hate him.");
        } else {
            sendMessage.setText("Select *new* to send a new person to hate him.");
        }
        DB.getUser(message.getChatId(), message.getFrom().getId()).setState(HATINGS);
        return sendMessage;
    }

    private static SendMessage onNewHatingsChosen(Message message){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        ReplyKeyboardMarkup replyKeyboardMarkup = getHatingMenuKeyboard();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("Set FirstName and SecondName of a person, you want to hate.");

        DB.getUser(message.getChatId(), message.getFrom().getId()).setState(NEWHATINGS);
        return sendMessage;
    }

    private static SendMessage onHelpChoosen(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("You chose help");
        return sendMessage;
    }


    // endregion Main menu options selected

    // region Get Messages


    private static String getHelpMessage() {
        String baseString = "helpMessage";
        return String.format(baseString, Emoji.BLACK_RIGHT_POINTING_TRIANGLE.toString(),
                Emoji.BLACK_RIGHT_POINTING_DOUBLE_TRIANGLE.toString(), Emoji.ALARM_CLOCK.toString(),
                Emoji.EARTH_GLOBE_EUROPE_AFRICA.toString(), Emoji.STRAIGHT_RULER.toString());
    }

    // region ReplyKeyboards

    private static ReplyKeyboardMarkup getRecentsKeyboard(Integer userId) {
        return getRecentsKeyboard(userId, true);
    }

    private static ReplyKeyboardMarkup getRecentsKeyboard(Integer userId, boolean allowNew) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        for(Fullname fullname : DB.getUser(userId).getFullname()){
            KeyboardRow row = new KeyboardRow();
            row.add(fullname.getName() + " " + fullname.getSecondName());
            keyboard.add(row);
        }
        KeyboardRow row = new KeyboardRow();
        if (allowNew) {
            row = new KeyboardRow();
            row.add(getNewCommand());
            keyboard.add(row);
            row = new KeyboardRow();
        }
        row.add(getCancelCommand());
        keyboard.add(row);

        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    private static String getNewCommand() {
        return String.format("%sNew",
                Emoji.HEAVY_PLUS_SIGN.toString());
    }

    private static ReplyKeyboardMarkup getMainMenuKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(false);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(getGreetingsCommand());
        keyboardFirstRow.add(getHatingsCommand());
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(getHelpCommand());
        keyboardSecondRow.add(getRateCommand());
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

    private static ReplyKeyboardMarkup getHatingMenuKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboad(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(getHateCommand());
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(getFirstNameCommand());
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardFirstRow.add(getSecondNameCommand());
        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardSecondRow.add(getCancelCommand());
        keyboard.add(keyboardRow);
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
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

    private static String getRateCommand() {
        return String.format("rateMe",
                Emoji.HUNDRED_POINTS_SYMBOL.toString());
    }

    private static String getGreetingsCommand() {
        return String.format("Greetings",
                Emoji.BLACK_RIGHT_POINTING_TRIANGLE.toString());
    }

    private static String getHatingsCommand() {
        return String.format("Hatings",
                Emoji.BLACK_RIGHT_POINTING_DOUBLE_TRIANGLE.toString());
    }

    private static String getFirstNameCommand() {
        return String.format("First Name",
                Emoji.BLACK_RIGHT_POINTING_DOUBLE_TRIANGLE.toString());
    }

    private static String getSecondNameCommand() {
        return String.format("Second Name",
                Emoji.BLACK_RIGHT_POINTING_DOUBLE_TRIANGLE.toString());
    }

    private static String getHelpCommand() {
        return String.format("Help",
                Emoji.BLACK_RIGHT_POINTING_DOUBLE_TRIANGLE.toString());
    }

    private static String getCancelCommand() {
        return String.format("cancel",
                Emoji.CROSS_MARK.toString());
    }

    private static String getHateCommand() {
        return String.format("Hate",
                Emoji.CROSS_MARK.toString());
    }


    private static String getLocationCommand() {
        return String.format("%sLocation",
                Emoji.ROUND_PUSHPIN.toString());
    }
    // endregion getCommnads



    // region Send common messages

    private static SendMessage sendMessageDefault(Message message) {
        ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard();
        DB.getUser(message.getChatId(),message.getFrom().getId()).setState(MAINMENU);
        return sendHelpMessage(message.getChatId().toString(), message.getMessageId(), replyKeyboardMarkup);
    }

    private static SendMessage sendMessageFirstName(Message message) {
        ReplyKeyboardMarkup replyKeyboardMarkup = getHatingMenuKeyboard();
        DB.getUser(message.getChatId(),message.getFrom().getId()).setState(NEWHATINGS);
        DB.getUser(message.getChatId(),message.getFrom().getId()).setName(message.getText());
        return sendHelpMessage(message.getChatId().toString(), message.getMessageId(), replyKeyboardMarkup);
    }

    private static SendMessage sendMessageSecondName(Message message) {
        ReplyKeyboardMarkup replyKeyboardMarkup = getHatingMenuKeyboard();
        DB.getUser(message.getChatId(),message.getFrom().getId()).setState(NEWHATINGS);
        DB.getUser(message.getChatId(),message.getFrom().getId()).setSecondName(message.getText());
        return sendHelpMessage(message.getChatId().toString(), message.getMessageId(), replyKeyboardMarkup);
    }

    private static SendMessage sendChooseOptionMessage(Long chatId, Integer messageId,
                                                       ReplyKeyboard replyKeyboard) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId.toString());
        sendMessage.setReplyToMessageId(messageId);
        sendMessage.setReplyMarkup(replyKeyboard);
        sendMessage.setText("chooseOption");

        return sendMessage;
    }

    private static SendMessage sendHelpMessage(String chatId, Integer messageId, ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setReplyToMessageId(messageId);
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        sendMessage.setText(getHelpMessage());
        return sendMessage;
    }

    private static SendMessage sendRateMessage(String chatId, Integer messageId, ReplyKeyboardMarkup replyKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setReplyToMessageId(messageId);
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        sendMessage.setText("rateMeMessage");

        return sendMessage;
    }
}
