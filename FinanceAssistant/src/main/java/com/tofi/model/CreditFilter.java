package com.tofi.model;

import com.tofi.model.enums.*;

import javax.persistence.*;

/**
 * Created by ulian_000 on 07.12.2016.
 */
@Entity
@Table(name="CreditFilters")
public class CreditFilter extends BaseEntity{

    @Column
    private Integer ammount;

    @Column(name="max_ercentage")
    private Double maxPercentage;

    @Column(name="term_mounth")
    private Integer termInMounth;

    @Column(name="gurantor")
    private Boolean needGurantor;

    @Column
    private Boolean pledge;

    @Column
    private Boolean certificates;

    @Column(name="grace_perioid")
    private Boolean gracePerioid;

    @Column(name="pre_payments")
    private Boolean prePayments;

    @ManyToOne
    @JoinColumn(name="payment_posibility_id")
    private PaymentPosibility paymentPosibility;

    @ManyToOne
    @JoinColumn(name="repayment_method_id")
    private RepaymentMethod repaymentMethod;

    @ManyToOne
    @JoinColumn(name="client_type_id")
    private ClientType clientType;

    @ManyToOne
    @JoinColumn(name="credit_goal_id")
    private CreditGoal goal;

    @ManyToOne
    @JoinColumn(name="currency_id")
    private Currency currency;

    public CreditFilter(){}


    public Integer getAmmount() {
        return ammount;
    }

    public void setAmmount(Integer ammount) {
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

    public Boolean getPledge() {
        return pledge;
    }

    public void setPledge(Boolean pledge) {
        this.pledge = pledge;
    }

    public Boolean getCertificates() {
        return certificates;
    }

    public void setCertificates(Boolean certificates) {
        this.certificates = certificates;
    }

    public Boolean getGracePerioid() {
        return gracePerioid;
    }

    public void setGracePerioid(Boolean gracePerioid) {
        this.gracePerioid = gracePerioid;
    }

    public Boolean getPrePayments() {
        return prePayments;
    }

    public void setPrePayments(Boolean prePayments) {
        this.prePayments = prePayments;
    }

    public PaymentPosibility getPaymentPosibility() {
        return paymentPosibility;
    }

    public void setPaymentPosibility(PaymentPosibility paymentPosibility) {
        this.paymentPosibility = paymentPosibility;
    }

    public RepaymentMethod getRepaymentMethod() {
        return repaymentMethod;
    }

    public void setRepaymentMethod(RepaymentMethod repaymentMethod) {
        this.repaymentMethod = repaymentMethod;
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
