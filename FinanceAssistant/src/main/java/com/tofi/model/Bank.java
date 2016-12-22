package com.tofi.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ulian_000 on 05.12.2016.
 */
@Entity
@Table(name="Banks")
public class Bank extends BaseEntity{

    @Column(name="Name")
    private String name;

    @OneToMany(mappedBy="bank", fetch=FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private List<Credit> credits;

    @OneToMany(mappedBy="bank", fetch=FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private List<Deposit> deposits;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Credit> getCredits() {
        return credits;
    }
    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }

    public List<Deposit> getDeposits() {
        return deposits;
    }
    public void setDeposits(List<Deposit> deposits) {
        this.deposits = deposits;
    }

    public Bank() {}

    public Bank(String name) {setName(name);}

}
