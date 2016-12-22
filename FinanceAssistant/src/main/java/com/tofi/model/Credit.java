package com.tofi.model;

import com.tofi.model.enums.ClientType;
import com.tofi.model.enums.CreditGoal;
import com.tofi.model.enums.PaymentPosibility;
import com.tofi.model.enums.RepaymentMethod;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Created by ulian_000 on 05.12.2016.
 */
@Entity
@Table(name="Credits")
public class Credit extends BaseEntity{

    @Column(name="AgregatorId", unique = true)
    private String agregatorName;

    @Column(name="Name")
    private String name;

    @Column(name="Gurantor")
    private Boolean needsGurantor;

    @Column(name="GracePeriod")
    private Boolean gracePeriod;

    @Column(name="Certificates")
    private Boolean needCertificates;

    @Column(name="Pledge")
    private Boolean pledge;

    @Column(name="PrePayments")
    private Boolean prePayments;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="ClientType_id")
    private ClientType clientType;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="Bank_id")
    private Bank bank;

    @OneToMany(mappedBy="credit", fetch=FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<PercentageTerm> terms;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="CreditGoal_id")
    private CreditGoal goal;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "credit_paymentPosibility",
            joinColumns = @JoinColumn(name = "credit_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "PaymentPosibility_id", referencedColumnName = "id"))
    private List<PaymentPosibility> paymentPosibilities;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="RepaymentMethod_id")
    private RepaymentMethod repaymentMethod;

    @Column(name="url")
    private String url;

    @Column(name="update_date")
    private Date updateDate;

    @Column(columnDefinition="TEXT")
    private String description;

    public Credit(){}

//    public Credit clone(Credit other){
//        this.setName(other.getName());
//        this.setPaymentPosibilities(other.getPaymentPosibilities());
//        this.setRepaymentMethod(other.getRepaymentMethod());
//        this.setBank(other.getBank());
//        this.setGoal(other.getGoal());
//        this.setAgregatorName(other.getAgregatorName());
//        this.setClientType(other.getClientType());
//        this.setDescription(other.getDescription());
//        this.setGracePeriod(other.getGracePeriod());
//        this.setNeedCertificates(other.getNeedCertificates());
//        this.setNeedsGurantor(other.getNeedsGurantor());
//        this.setPledge(other.getPledge());
//        this.setPrePayments(other.getPrePayments());
//        this.setTerms(other.getTerms());
//        this.setUpdateDate(other.getUpdateDate());
//        this.setUrl(other.getUrl());
//        return  this;
//    }

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

    public Bank getBank() {
        return bank;
    }
    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Boolean getNeedCertificates() {
        return needCertificates;
    }
    public void setNeedCertificates(Boolean needCertificates) {
        this.needCertificates = needCertificates;
    }

    public void setTerms(List<PercentageTerm> terms) {
        this.terms = terms;
    }
    public List<PercentageTerm> getTerms() {
//        if (terms == null) {
//            terms = new ArrayList<>();
//        }
        return  terms;
    }

    public RepaymentMethod getRepaymentMethod() {
        return repaymentMethod;
    }
    public void setRepaymentMethod(RepaymentMethod repaymentMethod) {
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

    public ClientType getClientType() {
        return clientType;
    }
    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public CreditGoal getGoal() {
        return goal;
    }
    public void setGoal(CreditGoal goal) {
        this.goal = goal;
    }

    public List<PaymentPosibility> getPaymentPosibilities() {
//        if (paymentPosibilities == null) {
//            paymentPosibilities = new ArrayList<>();
//        }

        return paymentPosibilities;
    }
    public void setPaymentPosibilities(List<PaymentPosibility> paymentPosibilities) {
        this.paymentPosibilities = paymentPosibilities;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getAgregatorName() {
        return agregatorName;
    }

    public void setAgregatorName(String agregatorName) {
        this.agregatorName = agregatorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Credit)) return false;
        if (!super.equals(o)) return false;

        Credit credit = (Credit) o;

        return getAgregatorName() != null ? getAgregatorName().equals(credit.getAgregatorName()) : credit.getAgregatorName() == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getAgregatorName() != null ? getAgregatorName().hashCode() : 0);
        return result;
    }
}
