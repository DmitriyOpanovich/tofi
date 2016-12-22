package com.tofi.model;

import com.tofi.model.enums.*;

import javax.persistence.*;

/**
 * Created by ulian_000 on 08.12.2016.
 */
@Entity
@Table(name="DepositFilters")
public class DepositFilter extends BaseEntity{

    @Column
    private Integer initFee;

    @Column
    private Integer termInMounth;

    @Column
    private Boolean capitalization;

    @Column
    private Boolean refilling;

    @Column
    private Boolean beforeTermWithdrawal;

    @ManyToOne
    @JoinColumn(name="client_type_id")
    private ClientType clientType;

    @ManyToOne
    @JoinColumn(name="currency_id")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name="percentage_type_id")
    private PercentageType percentageType;

    @Column
    private Double minPercentage;

    public DepositFilter() {}

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

    public ClientType getClientType() {
        return clientType;
    }
    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public Currency getCurrency() {
        return currency;
    }
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public PercentageType getPercentageType() {
        return percentageType;
    }
    public void setPercentageType(PercentageType percentageType) {
        this.percentageType = percentageType;
    }

    public Double getMinPercentage() {
        return minPercentage;
    }
    public void setMinPercentage(Double minPercentage) {
        this.minPercentage = minPercentage;
    }
}
