package com.tofi.dto;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by ulian_000 on 26.12.2016.
 */
public class CreditHistoryDTO {

    private Timestamp date;
    private byte[] pdfView;
    private CreditFilterDTO filter;
    private List<CreditDTO> credits;

    public Timestamp getDate() {
        return date;
    }
    public void setDate(Timestamp date) {
        this.date = date;
    }

    public byte[] getPdfView() {
        return pdfView;
    }
    public void setPdfView(byte[] pdfView) {
        this.pdfView = pdfView;
    }

    public CreditFilterDTO getFilter() {
        return filter;
    }
    public void setFilter(CreditFilterDTO filter) {
        this.filter = filter;
    }

    public List<CreditDTO> getCredits() {
        return credits;
    }
    public void setCredits(List<CreditDTO> credits) {
        this.credits = credits;
    }
}
