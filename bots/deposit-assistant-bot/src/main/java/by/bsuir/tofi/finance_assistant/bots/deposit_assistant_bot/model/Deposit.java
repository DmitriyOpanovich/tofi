package by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.model;

import java.util.Date;
import java.util.List;

/**
 * Created by 1 on 18.12.2016.
 */
public class Deposit {

    private String name="";
    private Boolean capitalization;
    private Boolean refilling;
    private Boolean beforeTermWithdrawal;
    private String clientType;
    private String bank;
    private List<String> terms;
    private String percentageType="";
    private String url="";
    private Date updateDate = new Date();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCapitalization() {
        return capitalization;
    }

    public void setCapitalization(Boolean capitalization) {
        this.capitalization = capitalization;
    }

    public Boolean getRefilling() {
        return refilling;
    }

    public void setRefilling(Boolean refilling) {
        this.refilling = refilling;
    }

    public Boolean getBeforeTermWithdrawal() {
        return beforeTermWithdrawal;
    }

    public void setBeforeTermWithdrawal(Boolean beforeTermWithdrawal) {
        this.beforeTermWithdrawal = beforeTermWithdrawal;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public List<String> getTerms() {
        return terms;
    }

    public void setTerms(List<String> terms) {
        this.terms = terms;
    }

    public String getPercentageType() {
        return percentageType;
    }

    public void setPercentageType(String percentageType) {
        this.percentageType = percentageType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
