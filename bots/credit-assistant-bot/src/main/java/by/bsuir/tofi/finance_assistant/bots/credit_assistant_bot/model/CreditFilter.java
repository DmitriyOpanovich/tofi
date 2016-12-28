package by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.model;


import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.model.enums.BaseEnumEntity;

public class CreditFilter {

    private Integer ammount;
    private Double maxPercentage;
    private Integer termInMounth;
    private Boolean needGurantor; //bool
    private Boolean pledge; //bool
    private Boolean certificates; //bool
    private Boolean gracePerioid; //bool
    private Boolean prePayments; //bool
    private BaseEnumEntity paymentPosibility;
    private BaseEnumEntity repaymentMethod;
    private BaseEnumEntity clientType;
    private BaseEnumEntity goal;
    private BaseEnumEntity currency;

    public Integer getAmount() {
        return ammount;
    }

    public void setAmount(Integer ammount) {
        this.ammount = ammount;
    }

    public Double getMaxPercentage() {
        return maxPercentage;
    }

    public void setMaxPercentage(Double maxPercentage) {
        this.maxPercentage = maxPercentage;
    }

    public Integer getTermInMounth() {
        return termInMounth;
    }

    public void setTermInMounth(Integer termInMounth) {
        this.termInMounth = termInMounth;
    }

    public Boolean getNeedGurantor() {
        return needGurantor;
    }

    public void setNeedGurantor(Boolean needGurantor) {
        this.needGurantor = needGurantor;
    }

    public void setPledge(Boolean pledge) {
        this.pledge = pledge;
    }

    public void setCertificates(Boolean certificates) {
        this.certificates = certificates;
    }

    public void setGracePerioid(Boolean gracePerioid) {
        this.gracePerioid = gracePerioid;
    }

    public void setPrePayments(Boolean prePayments) {
        this.prePayments = prePayments;
    }

    public void setNeedGurantor(String needGurantor) {
        switch (needGurantor){
            case "Да":{
                this.needGurantor=true;
                break;
            }
            case "Нет":{
                this.needGurantor=false;
                break;
            }
            default:{
                this.needGurantor=null;
                break;
            }
        }
    }

    public Boolean getPledge() {
        return pledge;
    }

    public void setPledge(String pledge) {
        switch (pledge){
            case "Да":{
                this.pledge=true;
                break;
            }
            case "Нет":{
                this.pledge=false;
                break;
            }
            default:{
                this.pledge=null;
                break;
            }
        }
    }

    public Boolean getCertificates() {
        return certificates;
    }

    public void setCertificates(String certificates) {
        switch (certificates){
            case "Да":{
                this.certificates=true;
                break;
            }
            case "Нет":{
                this.certificates=false;
                break;
            }
            default:{
                this.certificates=null;
                break;
            }
        }
    }

    public Boolean getGracePerioid() {
        return gracePerioid;
    }

    public void setGracePerioid(String gracePerioid) {
        switch (gracePerioid){
            case "Да":{
                this.gracePerioid=true;
                break;
            }
            case "Нет":{
                this.gracePerioid=false;
                break;
            }
            default:{
                this.gracePerioid=null;
                break;
            }
        }
    }

    public Boolean getPrePayments() {
        return prePayments;
    }

    public void setPrePayments(String prePayments) {
        switch (prePayments){
            case "Да":{
                this.prePayments=true;
                break;
            }
            case "Нет":{
                this.prePayments=false;
                break;
            }
            default:{
                this.prePayments=null;
                break;
            }
        }
    }

    public BaseEnumEntity getPaymentPosibility() {
        return paymentPosibility;
    }

    public void setPaymentPosibility(BaseEnumEntity paymentPosibility) {
        this.paymentPosibility = paymentPosibility;
    }

    public BaseEnumEntity getRepaymentMethod() {
        return repaymentMethod;
    }

    public void setRepaymentMethod(BaseEnumEntity repaymentMethod) {
        this.repaymentMethod = repaymentMethod;
    }

    public BaseEnumEntity getClientType() {
        return clientType;
    }

    public void setClientType(BaseEnumEntity clientType) {
        this.clientType = clientType;
    }

    public BaseEnumEntity getGoal() {
        return goal;
    }

    public void setGoal(BaseEnumEntity goal) {
        this.goal = goal;
    }

    public BaseEnumEntity getCurrency() {
        return currency;
    }

    public void setCurrency(BaseEnumEntity currency) {
        this.currency = currency;
    }

    //    public String getPaymentPosibility() {
//        return paymentPosibility.getName();
//    }
//
//    public void setPaymentPosibility(BaseEnumEntity paymentPosibility) {
//        this.paymentPosibility = paymentPosibility;
//    }
//
//    public String getRepaymentMethod() {
//        return repaymentMethod.getName();
//    }
//
//    public void setRepaymentMethod(BaseEnumEntity repaymentMethod) {
//        this.repaymentMethod = repaymentMethod;
//    }
//
//    public String getClientType() {
//        return clientType.getName();
//    }
//
//    public void setClientType(BaseEnumEntity clientType) {
//        this.clientType = clientType;
//    }
//
//    public String getGoal() {
//        return goal.getName();
//    }
//
//    public void setGoal(BaseEnumEntity goal) {
//        this.goal = goal;
//    }
//
//    public String getCurrency() {
//        return currency.getName();
//    }
//
//    public void setCurrency(BaseEnumEntity currency) {
//        this.currency = currency;
//    }
}
