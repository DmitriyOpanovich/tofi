package by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.storage;

import by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.model.BotUser;
import by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.model.ConnectionWithSiteUserRequest;
import by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.model.FeedbackRequest;
import by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.services.requests.rest.request.ConnectBotWithSiteUserRequest;
import by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.services.requests.rest.request.LeaveFeedbackRequest;
import by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.services.requests.rest.request.UpdateUserRequest;
import by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.services.requests.rest.response.Response;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.logging.BotLogger;

/**
 * Created by 1 on 03.12.2016.
 */
public class StorageManager {
    private static final String LOGTAG = "API";
    private static Gson gson = new Gson();
    private static JsonParser jsonParser = new JsonParser();


    public static void updateUser(User user){
        BotUser botUser = new BotUser(user.getFirstName(), user.getFirstName(), user.getUserName(), user.getId());
        Response response = new UpdateUserRequest(botUser).sendRequestAndGetResponse();

        if(!response.isStatus()){
            BotLogger.info(LOGTAG, "Sorry, something went wrong during api call");
        }else {
            BotLogger.info(LOGTAG, "User info was updated successfully");
        }
    }

    public static boolean connectWithSiteUser(int userId, String siteUser){
        ConnectionWithSiteUserRequest connectionWithSiteUserRequest = new ConnectionWithSiteUserRequest(userId, siteUser);
        Response response = new ConnectBotWithSiteUserRequest(connectionWithSiteUserRequest).sendRequestAndGetResponse();
        return response.isStatus();
    }

    public static boolean leaveFeedback(int userId, String message){
        FeedbackRequest feedbackRequest = new FeedbackRequest(userId, message);
        Response response = new LeaveFeedbackRequest(feedbackRequest).sendRequestAndGetResponse();
        return response.isStatus();
    }


}
