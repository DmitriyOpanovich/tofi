package by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.services;


import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.model.Deposit;
import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.model.enums.BaseEnumEntity;
import org.apache.commons.io.IOUtils;
import org.telegram.telegrambots.api.methods.send.SendDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DepositAssistantService {
    private static final String LOGTAG = "DEPASSSERVICE";
    private static final int BYTES1024 = 1024;

    private static final DateTimeFormatter dateFormaterFromDate = DateTimeFormatter.ofPattern("dd/MM/yyyy"); ///< Date to text formater
    private static volatile DepositAssistantService instance; ///< Instance of this class

    public static DepositAssistantService getInstance() {
        DepositAssistantService currentInstance;
        if (instance == null) {
            synchronized (DepositAssistantService.class) {
                if (instance == null) {
                    instance = new DepositAssistantService();
                }
                currentInstance = instance;
            }
        } else {
            currentInstance = instance;
        }
        return currentInstance;
    }



    public String describeClientTypes(List<BaseEnumEntity> clientTypes, String language){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(LocalisationService.getInstance().getString("clientTypes", language));
        for (BaseEnumEntity clientType: clientTypes){
            stringBuilder.append("\n").append('*').append(clientType.getName()).append('*').append(" - ").append(clientType.getDesc(language));
        }
        return stringBuilder.toString();
    }

    public String describePercentageTypes(List<BaseEnumEntity> percentageTypes, String language){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(LocalisationService.getInstance().getString("percentageTypes", language));
        for (BaseEnumEntity percentageType: percentageTypes){
            stringBuilder.append("\n").append('*').append(percentageType.getName()).append('*').append(" - ").append(percentageType.getDesc(language));
        }
        return stringBuilder.toString();
    }

    public String describeCurrencies(List<BaseEnumEntity> currencies, String language){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(LocalisationService.getInstance().getString("currencies", language));
        for (BaseEnumEntity currency: currencies){
            stringBuilder.append("\n").append('*').append(currency.getName()).append('*').append(" - ").append(currency.getDesc(language));
        }
        return stringBuilder.toString();
    }

    public String showBestDeposit(Deposit deposit, String language) {
        if(deposit == null){
            return LocalisationService.getInstance().getString("depositNotFound", language);
        }
        String template = LocalisationService.getInstance().getString("bestDeposit", language);
        StringBuilder stringBuilder = new StringBuilder();
        for (String s: deposit.getTerms()){
            stringBuilder.append("\n\t").append(s);
        }
        String terms = stringBuilder.toString();


        String beforeTermWithdrawal = LocalisationService.getInstance().getString("needsBeforeTermWithdrawalNo", language);
        if(deposit.getBeforeTermWithdrawal()==null){
            beforeTermWithdrawal = LocalisationService.getInstance().getString("needsBeforeTermWithdrawalnotDefined", language);
        }else if(deposit.getBeforeTermWithdrawal()){
            beforeTermWithdrawal = LocalisationService.getInstance().getString("needsBeforeTermWithdrawalYes", language);
        }
        String refilling = LocalisationService.getInstance().getString("needsRefillingNo", language);
        if(deposit.getRefilling()==null){
            refilling = LocalisationService.getInstance().getString("needsRefillingnotDefined", language);
        }else if(deposit.getRefilling()){
            refilling = LocalisationService.getInstance().getString("needsRefillingYes", language);
        }
        String capitalization = LocalisationService.getInstance().getString("needsCapitalizationNo", language);
        if(deposit.getCapitalization()==null){
            capitalization = LocalisationService.getInstance().getString("needsCapitalizationnotDefined", language);
        }else if(deposit.getCapitalization()){
            capitalization = LocalisationService.getInstance().getString("needsCapitalizationYes", language);
        }


        return String.format(template, deposit.getName(), deposit.getBank(), deposit.getClientType(), deposit.getPercentageType(), stringBuilder.toString(),
                beforeTermWithdrawal, refilling, capitalization, deposit.getUrl(), deposit.getUpdateDate().toString());
    }

    public SendDocument sendReport(byte[] pdf){
        SendDocument sendDocument = null;
        try {
            byte[] file = pdf;
            if (file != null && file.length / BYTES1024 >= 10) {
                try {
                    String fileName = "report.pdf";
                    File fileToUpload = new File(fileName);
                    FileOutputStream output = new FileOutputStream(fileToUpload);
                    IOUtils.write(file, output);
                    output.close();
                    if (fileToUpload.exists()) {
                        sendDocument = new SendDocument();
                        sendDocument.setNewDocument(fileToUpload.getAbsoluteFile());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendDocument;
    }


}
