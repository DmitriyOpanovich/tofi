package by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.model;

import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.model.enums.BaseEnumEntity;

/**
 * Created by 1 on 18.12.2016.
 */
public class DepositFilter {

    private Integer initFee;
    private Integer termInMounth;
    private Boolean capitalization;
    private Boolean refilling;
    private Boolean beforeTermWithdrawal;
    private BaseEnumEntity clientType;
    private BaseEnumEntity currency;

    private BaseEnumEntity percentageType;
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

    public void setCapitalization(Boolean capitalization) {
        this.capitalization = capitalization;
    }

    public void setRefilling(Boolean refilling) {
        this.refilling = refilling;
    }

    public void setBeforeTermWithdrawal(Boolean beforeTermWithdrawal) {
        this.beforeTermWithdrawal = beforeTermWithdrawal;
    }

    public BaseEnumEntity getClientType() {
        return clientType;
    }

    public void setClientType(BaseEnumEntity clientType) {
        this.clientType = clientType;
    }

    public BaseEnumEntity getCurrency() {
        return currency;
    }

    public void setCurrency(BaseEnumEntity currency) {
        this.currency = currency;
    }

    public BaseEnumEntity getPercentageType() {
        return percentageType;
    }

    public void setPercentageType(BaseEnumEntity percentageType) {
        this.percentageType = percentageType;
    }

    //    public String getClientType() {
//        return clientType.getName();
//    }
//
//    public void setClientType(BaseEnumEntity clientType) {
//        this.clientType = clientType;
//    }
//
//    public String getCurrency() {
//        return currency.getName();
//    }
//
//    public void setCurrency(BaseEnumEntity currency) {
//        this.currency = currency;
//    }
//
//    public String getPercentageType() {
//        return percentageType.getName();
//    }
//
//    public void setPercentageType(BaseEnumEntity percentageType) {
//        this.percentageType = percentageType;
//    }

    public Double getMinPercentage() {
        return minPercentage;
    }

    public void setMinPercentage(Double minPercentage) {
        this.minPercentage = minPercentage;
    }
}
