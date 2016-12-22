package com.tofi.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by ulian_000 on 08.12.2016.
 */
@Entity
@Table(name="DepositResponses")
public class DepositResponse extends BaseEntity {

    @Column
    private Timestamp date;

    @ManyToOne( fetch= FetchType.LAZY, cascade = {CascadeType.REMOVE})
    @JoinColumn(name="filter_id" )
    private DepositFilter filter;

    @ManyToMany
    @JoinTable(name="depositResponse_deposit",
            joinColumns = @JoinColumn(name = "depositResponse_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "deposit_id", referencedColumnName = "id"))
    private List<Deposit> deposits;

    @Lob
    @Column(name="pdf_view")
    private byte[] pdfView;

    @ManyToOne
    @JoinColumn(name="history_id")
    private History history;

    public DepositResponse() {}

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public DepositFilter getFilter() {
        return filter;
    }

    public void setFilter(DepositFilter filter) {
        this.filter = filter;
    }

    public List<Deposit> getDeposits() {
        return deposits;
    }
    public void setDeposits(List<Deposit> deposits) {
        this.deposits = deposits;
    }

    public byte[] getPdfView() {
        return pdfView;
    }

    public void setPdfView(byte[] pdfView) {
        this.pdfView = pdfView;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }
}
