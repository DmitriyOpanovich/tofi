package by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.model;


public class CreditFilter {

    private Integer amount;
    private Double maxPercentage;
    private Integer termInMounth;
    private Boolean needGurantor; //bool
    private Boolean pledge; //bool
    private Boolean certificates; //bool
    private Boolean gracePerioid; //bool
    private Boolean prePayments; //bool
    private String paymentPosibility;
    private String repaymentMethod;
    private String clientType;
    private String goal;
    private String currency;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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

    public String getPaymentPosibility() {
        return paymentPosibility;
    }

    public void setPaymentPosibility(String paymentPosibility) {
        switch (paymentPosibility){
            case "Не важно":{
                this.paymentPosibility = null;
                break;
            }
            default:{
                this.paymentPosibility=paymentPosibility;
                break;
            }
        }
    }

    public String getRepaymentMethod() {
        return repaymentMethod;
    }

    public void setRepaymentMethod(String repaymentMethod) {
        switch (repaymentMethod){
            case "Не важно":{
                this.repaymentMethod = null;
                break;
            }
            default:{
                this.repaymentMethod=repaymentMethod;
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

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        switch (goal){
            case "Не важно":{
                this.goal = null;
                break;
            }
            default:{
                this.goal=goal;
                break;
            }
        }
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
