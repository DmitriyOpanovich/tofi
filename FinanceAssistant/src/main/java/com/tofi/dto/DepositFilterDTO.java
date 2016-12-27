package com.tofi.dto;

/**
 * Created by ulian_000 on 26.12.2016.
 */
public class DepositFilterDTO {

    private Integer initFee;
    private Integer termInMounth;
    private Boolean capitalization;
    private Boolean refilling;
    private Boolean beforeTermWithdrawal;
    private EnumDTO clientType;
    private EnumDTO currency;
    private EnumDTO percentageType;
    private Double minPercentage;

    public DepositFilterDTO(){}

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

    public EnumDTO getClientType() {
        return clientType;
    }
    public void setClientType(EnumDTO clientType) {
        this.clientType = clientType;
    }

    public EnumDTO getCurrency() {
        return currency;
    }
    public void setCurrency(EnumDTO currency) {
        this.currency = currency;
    }

    public EnumDTO getPercentageType() {
        return percentageType;
    }
    public void setPercentageType(EnumDTO percentageType) {
        this.percentageType = percentageType;
    }

    public Double getMinPercentage() {
        return minPercentage;
    }
    public void setMinPercentage(Double minPercentage) {
        this.minPercentage = minPercentage;
    }
}
