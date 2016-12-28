package by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services;

import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.model.Credit;
import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.model.enums.BaseEnumEntity;
import org.apache.commons.io.IOUtils;
import org.telegram.telegrambots.api.methods.send.SendDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CreditAssistantService {
    private static final String LOGTAG = "CREDASSSERVICE";
    private static final int BYTES1024 = 1024;

    private static final DateTimeFormatter dateFormaterFromDate = DateTimeFormatter.ofPattern("dd/MM/yyyy"); ///< Date to text formater
    private static volatile CreditAssistantService instance; ///< Instance of this class

    public static CreditAssistantService getInstance() {
        CreditAssistantService currentInstance;
        if (instance == null) {
            synchronized (CreditAssistantService.class) {
                if (instance == null) {
                    instance = new CreditAssistantService();
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

    public String describeCreditGoals(List<BaseEnumEntity> creditGoals, String language){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(LocalisationService.getInstance().getString("creditGoals", language));
        for (BaseEnumEntity creditGoal: creditGoals){
            stringBuilder.append("\n").append('*').append(creditGoal.getName()).append('*').append(" - ").append(creditGoal.getDesc(language));
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

    public String describePaymentPossibilities(List<BaseEnumEntity> paymentPossibilities, String language){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(LocalisationService.getInstance().getString("paymentPossibilities", language));
        for (BaseEnumEntity paymentPosibility: paymentPossibilities){
            stringBuilder.append("\n").append('*').append(paymentPosibility.getName()).append('*').append(" - ").append(paymentPosibility.getDesc(language));
        }
        return stringBuilder.toString();
    }

    public String describeRepaymentMethods(List<BaseEnumEntity> repaymentMethods, String language) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(LocalisationService.getInstance().getString("repaymentMethods", language));
        for (BaseEnumEntity repaymentMethod: repaymentMethods){
            stringBuilder.append("\n").append('*').append(repaymentMethod.getName()).append('*').append(" - ").append(repaymentMethod.getDesc(language));
        }
        return stringBuilder.toString();
    }

    public String showBestCredit(Credit credit, String language) {
        if(credit == null){
            return LocalisationService.getInstance().getString("creditNotFound", language);
        }
        String template = LocalisationService.getInstance().getString("bestCredit", language);
        StringBuilder stringBuilder = new StringBuilder();
        for (String s: credit.getTerms()){
            stringBuilder.append("\n\t").append(s);
        }
        String terms = stringBuilder.toString();
        String needsGurantor = LocalisationService.getInstance().getString("needsGurantorNo", language);
        if(credit.getNeedsGurantor()==null){
            needsGurantor = LocalisationService.getInstance().getString("needsGurantornotDefined", language);
        }else if(credit.getNeedsGurantor()){
            needsGurantor = LocalisationService.getInstance().getString("needsGurantorYes", language);
        }
        String gracePeriod = LocalisationService.getInstance().getString("gracePeriodNo", language);
        if(credit.getGracePeriod()==null){
            gracePeriod = LocalisationService.getInstance().getString("gracePeriodnotDefined", language);
        }else if(credit.getGracePeriod()){
            gracePeriod = LocalisationService.getInstance().getString("gracePeriodYes", language);
        }
        String needCertificates = LocalisationService.getInstance().getString("needCertificatesNo", language);
        if(credit.getNeedCertificates()==null){
            needCertificates = LocalisationService.getInstance().getString("needCertificatesnotDefined", language);
        }else if(credit.getNeedCertificates()){
            needCertificates = LocalisationService.getInstance().getString("needCertificatesYes", language);
        }
        String pledge = LocalisationService.getInstance().getString("pledgeNo", language);
        if(credit.getPledge()==null){
            pledge = LocalisationService.getInstance().getString("pledgenotDefined", language);
        }else if(credit.getPledge()){
            pledge = LocalisationService.getInstance().getString("pledgeYes", language);
        }
        String prePayments = LocalisationService.getInstance().getString("prePaymentsNo", language);
        if(credit.getPrePayments()==null){
            prePayments = LocalisationService.getInstance().getString("prePaymentsnotDefined", language);
        }else if(credit.getPrePayments()){
            prePayments = LocalisationService.getInstance().getString("prePaymentsYes", language);
        }

        return String.format(template, credit.getName(), credit.getBank(), credit.getClientType(), credit.getGoal(), stringBuilder.toString(),
                needsGurantor, gracePeriod, needCertificates, pledge, prePayments, credit.getPaymentPosibility(), credit.getRepaymentMethod(),
                credit.getUrl(), credit.getUpdateDate().toString());
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
