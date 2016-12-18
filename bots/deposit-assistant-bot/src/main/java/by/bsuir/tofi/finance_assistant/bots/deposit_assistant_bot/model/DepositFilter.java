package by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.model;

/**
 * Created by 1 on 18.12.2016.
 */
public class DepositFilter {

    private Integer initFee;
    private Integer termInMounth;
    private Boolean capitalization;
    private Boolean refilling;
    private Boolean beforeTermWithdrawal;
    private String clientType;
    private String currency;

    private String percentageType;
    private Double minPercentage;



    public Integer getInitFee() {
        return initFee;
    }

    public void setInitFee(Integer initFee) {
        this.initFee = initFee;
    }

    public Integer getTermInMounth() {
        return termInMounth;
    }

    public void setTermInMounth(Integer termInMounth) {
        this.termInMounth = termInMounth;
    }

    public Boolean getCapitalization() {
        return capitalization;
    }

    public void setCapitalization(String capitalization) {
        switch (capitalization){
            case "Да":{
                this.capitalization=true;
                break;
            }
            case "Нет":{
                this.capitalization=false;
                break;
            }
            default:{
                this.capitalization=null;
                break;
            }
        }
    }

    public Boolean getRefilling() {
        return refilling;
    }

    public void setRefilling(String refilling) {
        switch (refilling){
            case "Да":{
                this.refilling=true;
                break;
            }
            case "Нет":{
                this.refilling=false;
                break;
            }
            default:{
                this.refilling=null;
                break;
            }
        }
    }

    public Boolean getBeforeTermWithdrawal() {
        return beforeTermWithdrawal;
    }

    public void setBeforeTermWithdrawal(String beforeTermWithdrawal) {
        switch (beforeTermWithdrawal){
            case "Да":{
                this.beforeTermWithdrawal=true;
                break;
            }
            case "Нет":{
                this.beforeTermWithdrawal=false;
                break;
            }
            default:{
                this.beforeTermWithdrawal=null;
                break;
            }
        }
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPercentageType() {
        return percentageType;
    }

    public void setPercentageType(String percentageType) {
        switch (percentageType){
            case "Не важно":{
                this.percentageType = null;
                break;
            }
            default:{
                this.percentageType=percentageType;
                break;
            }
        }
    }

    public Double getMinPercentage() {
        return minPercentage;
    }

    public void setMinPercentage(Double minPercentage) {
        this.minPercentage = minPercentage;
    }
}
