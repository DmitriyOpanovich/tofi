package by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.model.dto;

import java.util.List;


public class DepositHistoryDTO {

    private String pdfView;
    private List<DepositDTO> deposits;

    public String getPdfView() {
        return pdfView;
    }

    public void setPdfView(String pdfView) {
        this.pdfView = pdfView;
    }

    public List<DepositDTO> getDeposits() {
        return deposits;
    }

    public void setDeposits(List<DepositDTO> deposits) {
        this.deposits = deposits;
    }
}
