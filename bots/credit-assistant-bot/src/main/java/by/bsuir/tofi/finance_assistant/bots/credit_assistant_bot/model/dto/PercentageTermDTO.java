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
        try {
            if (minAmmount != null) {
                stringBuilder.append("от ").append(minAmmount + " ").append(currency.getName() + " ");
            }
        }catch (Exception e){}
        try {
            if (maxAmmount != null) {
                stringBuilder.append("до ").append(maxAmmount + " ").append(currency.getName() + " ");
            }
        }catch (Exception e){}
        stringBuilder.append("; На срок ");
        try {
            if (minTermMonth != null) {
                stringBuilder.append("от ").append(minTermMonth + " месяцев ");
            }
        }catch (Exception e){}
        try {
            if(maxTermMonth!=null){
                stringBuilder.append("до ").append(maxTermMonth + " месяцев ");
            }
        }catch (Exception e){}
        try {
            if(getPercentage()!=null) {
                stringBuilder.append("; Под ").append(getPercentage()).append(" процентов.");
            }else {
                stringBuilder.append("; Процентная ставка определяется по согласованию.");
            }
        }catch (Exception e){}
        return stringBuilder.toString();
    }
}
