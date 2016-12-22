package com.tofi.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by ulian_000 on 13.12.2016.
 */
public class CreditDTO {

    private String agregatorName;
    private String name;
    private Boolean needsGurantor;
    private Boolean gracePeriod;
    private Boolean needCertificates;
    private Boolean pledge;
    private Boolean prePayments;
    private String url;
    private EnumDTO clientType;
    private EnumDTO goal;
    private List<EnumDTO> paymentPosibilities;
    private EnumDTO repaymentMethod;
    private String bankName;
    private List<PercentageTermDTO> terms;
    private Date updateDate;
    private String description;

    public String getAgregatorName() {
        return agregatorName;
    }
    public void setAgregatorName(String agregatorName) {
        this.agregatorName = agregatorName;
    }

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

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
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

    public List<EnumDTO> getPaymentPosibilities() {
        return paymentPosibilities;
    }
    public void setPaymentPosibilities(List<EnumDTO> paymentPosibilities) {
        this.paymentPosibilities = paymentPosibilities;
    }

    public EnumDTO getRepaymentMethod() {
        return repaymentMethod;
    }
    public void setRepaymentMethod(EnumDTO repaymentMethod) {
        this.repaymentMethod = repaymentMethod;
    }

    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public List<PercentageTermDTO> getTerms() {
        return terms;
    }
    public void setTerms(List<PercentageTermDTO> terms) {
        this.terms = terms;
    }

    public Date getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditDTO)) return false;

        CreditDTO creditDTO = (CreditDTO) o;

        return getAgregatorName() != null ? getAgregatorName().equals(creditDTO.getAgregatorName()) : creditDTO.getAgregatorName() == null;

    }

    @Override
    public int hashCode() {
        return getAgregatorName() != null ? getAgregatorName().hashCode() : 0;
    }
}
