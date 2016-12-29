package by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.storage;

import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.model.*;
import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.model.dto.DepositDTO;
import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.model.dto.DepositHistoryDTO;
import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.model.enums.*;
import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.services.requests.rest.request.*;
import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.services.requests.rest.response.Response;
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

    public static EnumsCollection getPossiblePercentageTerms(){
        Response response = new GetPercentageTypesRequest().sendRequestAndGetResponse();
        if(response.isStatus()){
            try {
                List<BaseEnumEntity> percentageTypes = new ArrayList<BaseEnumEntity>();
                for(JsonElement jsonElement: jsonParser.parse(response.getMessage()).getAsJsonArray()){
                    PercentageType percentageType = gson.fromJson(jsonElement, PercentageType.class);
                    percentageTypes.add(percentageType);
                }
                percentageTypes.add(new AnyOption());
                return new EnumsCollection(percentageTypes);
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

    public static Deposit chooseDepositForThisFilter(int userId, DepositFilter creditFilter){
        DepostiFilterRequest depostiFilterRequest = new DepostiFilterRequest(userId, creditFilter);
        System.out.println(gson.toJson(depostiFilterRequest));
        Response response = new ChooseDepositWithFilterRequest(depostiFilterRequest).sendRequestAndGetResponse();
        if(response.isStatus()){
            try {
                List<DepositDTO> depositDTOs = new ArrayList<DepositDTO>();
                DepositHistoryDTO depositHistoryDTO = gson.fromJson(jsonParser.parse(response.getMessage()), DepositHistoryDTO.class);
                depositDTOs = depositHistoryDTO.getDeposits();
                DB.getUserState(userId).setPdfView(depositHistoryDTO.getPdfView());

//                for(JsonElement jsonElement: jsonParser.parse(response.getMessage()).getAsJsonArray()){
//                    DepositDTO depositDTO = gson.fromJson(jsonElement, DepositDTO.class);
//                    depositDTOs.add(depositDTO);
//                }
                if(depositDTOs.size()>0) {
                    Deposit deposit = new Deposit(depositDTOs.get(0));
                    return deposit;
                }else {
                    return null;
                }
            }catch (Exception e){
                BotLogger.info(LOGTAG, "Sorry, something went wrong during api response parsing (Get credit)");
                return null;
            }
        }else {
            BotLogger.info(LOGTAG, "Sorry, something went wrong during api call (Get credit)");
            return null;
        }
    }

//    public static PdfReport generateReportForThisFilter(int userId, DepositFilter creditFilter, String language){
//        DepostiFilterRequest depostiFilterRequest = new DepostiFilterRequest(userId, creditFilter, language);
//        System.out.println(gson.toJson(depostiFilterRequest));
//        Response response = new GenerateReportForFilterRequest(depostiFilterRequest).sendRequestAndGetResponse();
//        if(response.isStatus()){
//            try {
//                PdfReport pdfReport = gson.fromJson(response.getMessage(), PdfReport.class);
//                return pdfReport;
//            }catch (Exception e){
//                BotLogger.info(LOGTAG, "Sorry, something went wrong during api response parsing (Get report)");
//                return null;
//            }
//        }else {
//            BotLogger.info(LOGTAG, "Sorry, something went wrong during api call (Get report)");
//            return null;
//        }
//    }

    public static boolean connectWithSiteUser(int userId, String siteUser){
        try {
            ConnectionWithSiteUserRequest connectionWithSiteUserRequest = new ConnectionWithSiteUserRequest(userId, siteUser);
            System.out.println(gson.toJson(connectionWithSiteUserRequest));
            Response response = new ConnectBotWithSiteUserRequest(connectionWithSiteUserRequest).sendRequestAndGetResponse();
            return response.isStatus();
        }catch (Exception e){
            return false;
        }

    }

}
