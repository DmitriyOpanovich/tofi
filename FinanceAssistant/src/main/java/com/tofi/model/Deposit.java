package com.tofi.model;

import com.tofi.model.enums.ClientType;
import com.tofi.model.enums.PercentageType;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Created by ulian_000 on 08.12.2016.
 */
@Entity
@Table(name="Deposits")
public class Deposit extends BaseEntity{

    @Column(name="AgregatorName", unique = true)
    private String agregatorName;

    @Column(name="Name")
    private String name;

    @Column
    private Boolean capitalization;

    @Column
    private Boolean refilling;

    @Column(name="before_term_withdrawal")
    private Boolean beforeTermWithdrawal;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="ClientType_id")
    private ClientType clientType;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="Bank_id")
    private Bank bank;

    @OneToMany(mappedBy="deposit", fetch=FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<PercentageTerm> terms;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="percentage_type_id")
    private PercentageType percentageType;

    @Column(name="url")
    private String url;

    @Column(name="update_date")
    private Date updateDate;

    @Column(columnDefinition="TEXT")
    private String description;

    public Deposit() {}


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

    public ClientType getClientType() {
        return clientType;
    }
    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public Bank getBank() {
        return bank;
    }
    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public List<PercentageTerm> getTerms() {
        return terms;
    }
    public void setTerms(List<PercentageTerm> terms) {
        this.terms = terms;
    }

    public PercentageType getPercentageType() {
        return percentageType;
    }
    public void setPercentageType(PercentageType percentageType) {
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

}
