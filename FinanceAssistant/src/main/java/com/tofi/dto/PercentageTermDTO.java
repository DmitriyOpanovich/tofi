package com.tofi.dto;

import com.tofi.model.PercentageTerm;

/**
 * Created by ulian_000 on 13.12.2016.
 */
public class PercentageTermDTO {

    private Integer minAmmount;
    private Integer maxAmmount;
    private Integer minTermMonth;
    private Integer maxTermMonth;
    private Double percentage;
    private EnumDTO currency;

    public PercentageTermDTO(){}
    public PercentageTermDTO(PercentageTerm termEntity) {}

    public Integer getMinAmmount() {
        return minAmmount;
    }
    public void setMinAmmount(Integer minAmmount) {
        this.minAmmount = minAmmount;
    }

    public Integer getMaxAmmount() {
        return maxAmmount;
    }
    public void setMaxAmmount(Integer maxAmmount) {
        this.maxAmmount = maxAmmount;
    }

    public Double getPercentage() {
        return percentage;
    }
    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Integer getMinTermMonth() {
        return minTermMonth;
    }
    public void setMinTermMonth(Integer minTermMonth) {
        this.minTermMonth = minTermMonth;
    }

    public Integer getMaxTermMonth() {
        return maxTermMonth;
    }
    public void setMaxTermMonth(Integer maxTermMonth) {
        this.maxTermMonth = maxTermMonth;
    }

    public EnumDTO getCurrency() {
        return currency;
    }
    public void setCurrency(EnumDTO currency) {
        this.currency = currency;
    }

}
