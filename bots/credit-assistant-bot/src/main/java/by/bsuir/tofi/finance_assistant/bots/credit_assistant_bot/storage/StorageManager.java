package by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.storage;

import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.model.*;
import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.model.enums.*;
import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services.requests.rest.request.*;
import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services.requests.rest.response.Response;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.logging.BotLogger;

import java.util.ArrayList;
import java.util.List;


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
            BotLogger.info(LOGTAG, "User ifo was updated successfully");
        }
    }

    public static EnumsCollection getPossibleClientTypes(){
        Response response = new GetClientTypesRequest().sendRequestAndGetResponse();
        if(response.isStatus()){
            try {
                List<BaseEnumEntity> clientTypes = new ArrayList<BaseEnumEntity>();
                for(JsonElement jsonElement: jsonParser.parse(response.getMessage()).getAsJsonArray()){
                    ClientType clientType = gson.fromJson(jsonElement, ClientType.class);
                    clientTypes.add(clientType);
                }
                return new EnumsCollection(clientTypes);
            }catch (Exception e){
                BotLogger.info(LOGTAG, "Sorry, something went wrong during api response parsing (Client types)");
                return new EnumsCollection();
            }
        }else {
            BotLogger.info(LOGTAG, "Sorry, something went wrong during api call (Client types)");
            return new EnumsCollection();
        }
    }

    public static EnumsCollection getPossibleCreditGoals(){
        Response response = new GetCreditGoalsRequest().sendRequestAndGetResponse();
        if(response.isStatus()){
            try {
                List<BaseEnumEntity> creditGoals = new ArrayList<BaseEnumEntity>();
                for(JsonElement jsonElement: jsonParser.parse(response.getMessage()).getAsJsonArray()){
                    CreditGoal creditGoal = gson.fromJson(jsonElement, CreditGoal.class);
                    creditGoals.add(creditGoal);
                }
                creditGoals.add(new AnyOption());
                return new EnumsCollection(creditGoals);
            }catch (Exception e){
                BotLogger.info(LOGTAG, "Sorry, something went wrong during api response parsing (Credit goals)");
                return new EnumsCollection();
            }
        }else {
            BotLogger.info(LOGTAG, "Sorry, something went wrong during api call (Credit goals)");
            return new EnumsCollection();
        }
    }

    public static EnumsCollection getPossibleCurrencies(){
        Response response = new GetCurrenciesRequest().sendRequestAndGetResponse();
        if(response.isStatus()){
            try {
                List<BaseEnumEntity> currencies = new ArrayList<BaseEnumEntity>();
                for(JsonElement jsonElement: jsonParser.parse(response.getMessage()).getAsJsonArray()){
                    Currency currency = gson.fromJson(jsonElement, Currency.class);
                    currencies.add(currency);
                }
                return new EnumsCollection(currencies);
            }catch (Exception e){
                BotLogger.info(LOGTAG, "Sorry, something went wrong during api response parsing (Currencies)");
                return new EnumsCollection();
            }
        }else {
            BotLogger.info(LOGTAG, "Sorry, something went wrong during api call (Currencies)");
            return new EnumsCollection();
        }
    }

    public static EnumsCollection getPossiblePaymentPossibilities(){
        Response response = new GetPaymentPossibilitiesRequest().sendRequestAndGetResponse();
        if(response.isStatus()){
            try {
                List<BaseEnumEntity> paymentPosibilities = new ArrayList<BaseEnumEntity>();
                for(JsonElement jsonElement: jsonParser.parse(response.getMessage()).getAsJsonArray()){
                    PaymentPosibility paymentPosibility = gson.fromJson(jsonElement, PaymentPosibility.class);
                    paymentPosibilities.add(paymentPosibility);
                }
                paymentPosibilities.add(new AnyOption());
                return new EnumsCollection(paymentPosibilities);
            }catch (Exception e){
                BotLogger.info(LOGTAG, "Sorry, something went wrong during api response parsing (Payment possibilities)");
                return new EnumsCollection();
            }
        }else {
            BotLogger.info(LOGTAG, "Sorry, something went wrong during api call (Payment possibilities)");
            return new EnumsCollection();
        }
    }

    public static EnumsCollection getPossibleRepaymentMethods(){
        Response response = new GetRepaymentMethodsRequest().sendRequestAndGetResponse();
        if(response.isStatus()){
            try {
                List<BaseEnumEntity> repaymentMethods = new ArrayList<BaseEnumEntity>();
                for(JsonElement jsonElement: jsonParser.parse(response.getMessage()).getAsJsonArray()){
                    RepaymentMethod repaymentMethod = gson.fromJson(jsonElement, RepaymentMethod.class);
                    repaymentMethods.add(repaymentMethod);
                }
                repaymentMethods.add(new AnyOption());
                return new EnumsCollection(repaymentMethods);
            }catch (Exception e){
                BotLogger.info(LOGTAG, "Sorry, something went wrong during api response parsing (Repayment methods)");
                return new EnumsCollection();
            }
        }else {
            BotLogger.info(LOGTAG, "Sorry, something went wrong during api call (Repayment methods)");
            return new EnumsCollection();
        }
    }

    public static Credit chooseCreditForThisFilter(int userId, CreditFilter creditFilter){
        CreditFilterRequest creditFilterRequest = new CreditFilterRequest(userId, creditFilter);
        Response response = new ChooseCreditsWithFilterRequest(creditFilterRequest).sendRequestAndGetResponse();
        if(response.isStatus()){
            try {
                Credit credit = gson.fromJson(response.getMessage(), Credit.class);
                return credit;
            }catch (Exception e){
                BotLogger.info(LOGTAG, "Sorry, something went wrong during api response parsing (Get credit)");
                return null;
            }
        }else {
            BotLogger.info(LOGTAG, "Sorry, something went wrong during api call (Get credit)");
            return null;
        }
    }

    public static PdfReport generateReportForThisFilter(int userId, CreditFilter creditFilter, String language){
        CreditFilterRequest creditFilterRequest = new CreditFilterRequest(userId, creditFilter, language);
        Response response = new GenerateReportForFilterRequest(creditFilterRequest).sendRequestAndGetResponse();
        if(response.isStatus()){
            try {
                PdfReport pdfReport = gson.fromJson(response.getMessage(), PdfReport.class);
                return pdfReport;
            }catch (Exception e){
                BotLogger.info(LOGTAG, "Sorry, something went wrong during api response parsing (Get report)");
                return null;
            }
        }else {
            BotLogger.info(LOGTAG, "Sorry, something went wrong during api call (Get report)");
            return null;
        }
    }

    public static boolean connectWithSiteUser(int userId, String siteUser){
        ConnectionWithSiteUserRequest connectionWithSiteUserRequest = new ConnectionWithSiteUserRequest(userId, siteUser);
        Response response = new ConnectBotWithSiteUserRequest(connectionWithSiteUserRequest).sendRequestAndGetResponse();
        return response.isStatus();
    }

}
