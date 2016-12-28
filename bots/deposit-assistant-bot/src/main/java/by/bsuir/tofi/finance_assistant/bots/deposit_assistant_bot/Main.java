package by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot;

import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.model.dto.DepositDTO;
import by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.model.dto.PercentageTermDTO;

/**
 * Created by 1 on 27.12.2016.
 */
public class Main {
    public static void main(String[] args) {

        System.out.println();
        System.out.println();

    }

    private static String mapCreditDTOForReport(DepositDTO depositDTO){
        StringBuilder stringBuilder = new StringBuilder();
        try{
            stringBuilder.append(depositDTO.getName()).append("\n");
        }catch (Exception e){}
        try{
            stringBuilder.append("Банк - ").append(depositDTO.getBankName()).append("\n");
        }catch (Exception e){}
        try{
            stringBuilder.append("Тип клиента - ").append(depositDTO.getClientType().getName()).append("\n");
        }catch (Exception e){}
        try{
            stringBuilder.append("Начисление процентов - ").append(depositDTO.getPercentageType().getName()).append("\n");
        }catch (Exception e){}
        try{
            stringBuilder.append("На следующих условиях: ");
            for(PercentageTermDTO percentageTermDTO: depositDTO.getTerms()){
                stringBuilder.append("\n\t").append(percentageTermDTO.describe());
            }
            stringBuilder.append("\n");
        }catch (Exception e){}
        try{
            stringBuilder.append("Возможность досрочного снятия - ").append(depositDTO.getBeforeTermWithdrawal() == null ? "Нет информации" : depositDTO.getBeforeTermWithdrawal() == true ? "Ecть" : "Нет").append("\n");
        }catch (Exception e){}
        try{
            stringBuilder.append("Возможность пополнения - ").append(depositDTO.getRefilling() == null ? "Нет информации" : depositDTO.getRefilling() == true ? "Есть" : "Нет").append("\n");
        }catch (Exception e){}
        try{
            stringBuilder.append("Возможность капитализации - ").append(depositDTO.getCapitalization() == null ? "Нет информации" : depositDTO.getCapitalization() == true ? "Есть" : "Нет").append("\n");
        }catch (Exception e){}
        try{
            stringBuilder.append("Вы можете ознакомиться с деталями вклада более детально тут - ").append(depositDTO.getUrl()).append("\n");
        }catch (Exception e){}
        try{
            stringBuilder.append("Последний раз этот вклад обновлялся - ").append(depositDTO.getUpdateDate()).append("\n");
        }catch (Exception e){}
        return stringBuilder.toString();
    }
}
