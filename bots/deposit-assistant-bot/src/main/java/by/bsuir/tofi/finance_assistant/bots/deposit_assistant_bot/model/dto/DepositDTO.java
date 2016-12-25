package by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.model.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by ulian_000 on 18.12.2016.
 */
public class DepositDTO {

    private String agregatorName;
    private String name;
    private Boolean capitalization;
    private Boolean refilling;
    private Boolean beforeTermWithdrawal;
    private EnumDTO clientType;
    private String bankName;
    private List<PercentageTermDTO> terms;
    private EnumDTO percentageType;
    private String url;
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

    public EnumDTO getPercentageType() {
        return percentageType;
    }
    public void setPercentageType(EnumDTO percentageType) {
        this.percentageType = percentageType;
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

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DepositDTO)) return false;

        DepositDTO that = (DepositDTO) o;

        return getAgregatorName() != null ? getAgregatorName().equals(that.getAgregatorName()) : that.getAgregatorName() == null;

    }

    @Override
    public int hashCode() {
        return getAgregatorName() != null ? getAgregatorName().hashCode() : 0;
    }
}
