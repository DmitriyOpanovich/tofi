package by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.model.dto;


public class PercentageTermDTO {

    private Integer minAmmount;
    private Integer maxAmmount;
    private Integer minTermMonth;
    private Integer maxTermMonth;
    private Double percentage;
    private EnumDTO currency;

    public PercentageTermDTO(){}

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


    public String describe() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Предлагаемая сумма: ");
        if(minAmmount!=null){
            stringBuilder.append("от ").append(minAmmount + " ").append(currency.getName() + " ");
        }
        if(maxAmmount!=null){
            stringBuilder.append("до ").append(maxAmmount + " ").append(currency.getName() + " ");
        }
        stringBuilder.append("; На срок ");
        if(minTermMonth!=null){
            stringBuilder.append("от ").append(minTermMonth + " месяцев ");
        }
        if(maxTermMonth!=null){
            stringBuilder.append("до ").append(maxTermMonth + " месяцев ");
        }
        stringBuilder.append("; Под ").append(getPercentage()).append(" процентов.");
        return stringBuilder.toString();
    }
}
