package by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.model.dto;

import java.util.List;


public class CreditHistoryDTO {

    private String pdfView;
    private List<CreditDTO> credits;

    public String getPdfView() {
        return pdfView;
    }

    public void setPdfView(String pdfView) {
        this.pdfView = pdfView;
    }

    public List<CreditDTO> getCredits() {
        return credits;
    }
    public void setCredits(List<CreditDTO> credits) {
        this.credits = credits;
    }
}
