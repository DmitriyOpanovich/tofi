package by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.services;

import by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.model.enums.MyBot;

import java.time.format.DateTimeFormatter;

public class FinanceAssistantService {
    private static final String LOGTAG = "FINASSSERVICE";
    private static final int BYTES1024 = 1024;

    private static final DateTimeFormatter dateFormaterFromDate = DateTimeFormatter.ofPattern("dd/MM/yyyy"); ///< Date to text formater
    private static volatile FinanceAssistantService instance; ///< Instance of this class

    public static FinanceAssistantService getInstance() {
        FinanceAssistantService currentInstance;
        if (instance == null) {
            synchronized (FinanceAssistantService.class) {
                if (instance == null) {
                    instance = new FinanceAssistantService();
                }
                currentInstance = instance;
            }
        } else {
            currentInstance = instance;
        }
        return currentInstance;
    }

    public String describeMyBots(String language){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(LocalisationService.getInstance().getString("onChooseServicesCommand", language)).append('\n');
        for(MyBot bot: MyBot.values()){
            stringBuilder.append(bot.describe(language)).append('\n');
        }
        return stringBuilder.toString();
    }




}
