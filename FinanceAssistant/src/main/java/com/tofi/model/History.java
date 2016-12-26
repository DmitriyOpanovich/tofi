package com.tofi.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ulian_000 on 08.12.2016.
 */
@Entity
@Table(name="Histories")
public class History extends BaseEntity{

    @OneToMany(mappedBy = "history", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<CreditResponse> creditResponses;

    @OneToMany(mappedBy = "history", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<DepositResponse> depositResponses;


    public History(){}

    public List<CreditResponse> getCreditResponses() {
        return creditResponses;
    }
    public void setCreditResponses(List<CreditResponse> creditResponses) {
        this.creditResponses = creditResponses;
    }

    public List<DepositResponse> getDepositResponses() {
        return depositResponses;
    }
    public void setDepositResponses(List<DepositResponse> depositResponses) {
        this.depositResponses = depositResponses;
    }
}
