package com.tofi.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by ulian_000 on 07.12.2016.
 */
@Entity
@Table(name="CrediResponses")
public class CreditResponse extends BaseEntity{

    @Column
    private Timestamp date;

    @ManyToOne( fetch=FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name="filter_id" )
    private CreditFilter filter;

    @ManyToMany(fetch=FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="creditResponse_credit",
            joinColumns = @JoinColumn(name = "creditResponse_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "credit_id", referencedColumnName = "id"))
    private List<Credit> credits;

    @Lob
    @Column(name="pdf_view")
    private byte[] pdfView;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="history_id")
    private History history;

    public CreditResponse() {}

    public Timestamp getDate() {
        return date;
    }
    public void setDate(Timestamp date) {
        this.date = date;
    }

    public CreditFilter getFilter() {
        return filter;
    }
    public void setFilter(CreditFilter filter) {
        this.filter = filter;
    }

    public List<Credit> getCredits() {
        return credits;
    }
    public void setCredits(List<Credit> credits) {
        this.credits = credits;
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
