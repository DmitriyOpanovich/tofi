package com.tofi.model;

import com.tofi.model.enums.Currency;

import javax.persistence.*;



/**
 * Created by ulian_000 on 07.12.2016.
 */
@Entity
@Table(name="PercentageTerms")
public class PercentageTerm extends BaseEntity{

    @Column(name="min_ammount")
    private Integer minAmmount;

    @Column(name="max_ammount")
    private Integer maxAmmount;

    @Column(name="max_term_mounth")
    private Integer minTermMonth;

    @Column(name="min_term_mounth")
    private Integer maxTermMonth;

    @Column(name="percentage")
    private Double percentage;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="currency_id")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name="credit_id")
    private Credit credit;

    @ManyToOne
    @JoinColumn(name="deposit_id")
    private Deposit deposit;

    public PercentageTerm(){}

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

    public Double getPercentage() {
        return percentage;
    }
    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Currency getCurrency() {
        return currency;
    }
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Credit getCredit() {
        return credit;
    }
    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public Deposit getDeposit() {
        return deposit;
    }
    public void setDeposit(Deposit deposit) {
        this.deposit = deposit;
    }
}
