package by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.model;

import java.util.Date;
import java.util.List;


public class Credit {

    private String name="";
    private Boolean needsGurantor;
    private Boolean gracePeriod;
    private Boolean needCertificates;
    private Boolean pledge;
    private Boolean prePayments;
    private String clientType;
    private String bank;
    private List<String> terms;
    private String goal="";
    private String paymentPosibility="";
    private String repaymentMethod="";
    private String url="";
    private Date updateDate= new Date();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getNeedsGurantor() {
        return needsGurantor;
    }

    public void setNeedsGurantor(Boolean needsGurantor) {
        this.needsGurantor = needsGurantor;
    }

    public Boolean getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(Boolean gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public Boolean getNeedCertificates() {
        return needCertificates;
    }

    public void setNeedCertificates(Boolean needCertificates) {
        this.needCertificates = needCertificates;
    }

    public Boolean getPledge() {
        return pledge;
    }

    public void setPledge(Boolean pledge) {
        this.pledge = pledge;
    }

    public Boolean getPrePayments() {
        return prePayments;
    }

    public void setPrePayments(Boolean prePayments) {
        this.prePayments = prePayments;
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

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getPaymentPosibility() {
        return paymentPosibility;
    }

    public void setPaymentPosibility(String paymentPosibility) {
        this.paymentPosibility = paymentPosibility;
    }

    public String getRepaymentMethod() {
        return repaymentMethod;
    }

    public void setRepaymentMethod(String repaymentMethod) {
        this.repaymentMethod = repaymentMethod;
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
