package com.tofi.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by ulian_000 on 22.12.2016.
 */
public class CreditFilterDTO {

    @NotNull(message = "error.empty.ammount")
    @Min(value = 0, message = "error.negative.ammount")
    private Integer ammount;

    @NotNull(message = "error.empty.percentage")
    @Max(value = 100, message = "error.invalid.percent")
    private Double maxPercentage;

    @NotNull(message = "error.empty.term")
    @Min(value = 0,  message = "error.negative.term")
    private Integer termInMounth;

    private Boolean needGurantor;
    private Boolean pledge;
    private Boolean certificates;
    private Boolean gracePerioid;
    private Boolean prePayments;
    private EnumDTO paymentPosibility;
    private EnumDTO repaymentMethod;

    @NotNull(message = "error.empty.clientType")
    private EnumDTO clientType;

    @NotNull(message = "error.empty.goal")
    private EnumDTO goal;

    @NotNull(message = "error.empty.currency")
    private EnumDTO currency;

    @NotNull(message = "error.empty.telegramId")
    private Long telegramUserId;

    public CreditFilterDTO(){}

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

    public EnumDTO getPaymentPosibility() {
        return paymentPosibility;
    }
    public void setPaymentPosibility(EnumDTO paymentPosibility) {
        this.paymentPosibility = paymentPosibility;
    }

    public EnumDTO getRepaymentMethod() {
        return repaymentMethod;
    }
    public void setRepaymentMethod(EnumDTO repaymentMethod) {
        this.repaymentMethod = repaymentMethod;
    }

    public EnumDTO getClientType() {
        return clientType;
    }
    public void setClientType(EnumDTO clientType) {
        this.clientType = clientType;
    }

    public EnumDTO getGoal() {
        return goal;
    }
    public void setGoal(EnumDTO goal) {
        this.goal = goal;
    }

    public EnumDTO getCurrency() {
        return currency;
    }
    public void setCurrency(EnumDTO currency) {
        this.currency = currency;
    }

    public Long getTelegramUserId() {
        return telegramUserId;
    }
    public void setTelegramUserId(Long telegramUserId) {
        this.telegramUserId = telegramUserId;
    }
}
