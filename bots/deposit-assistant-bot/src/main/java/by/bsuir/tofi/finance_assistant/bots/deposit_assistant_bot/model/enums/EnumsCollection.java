package by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.model.enums;

import java.util.ArrayList;
import java.util.List;


public class EnumsCollection {
    private List<BaseEnumEntity> options = new ArrayList<>();

    public EnumsCollection() {
    }

    public EnumsCollection(List<BaseEnumEntity> options) {
        this.options = options;
    }

    public List<BaseEnumEntity> getOptions() {
        return options;
    }

    public void setOptions(List<BaseEnumEntity> options) {
        this.options.addAll(options);
    }

    public void addOption(BaseEnumEntity option){
        this.options.add(option);
    }

    public BaseEnumEntity getOption(String option){
        for (BaseEnumEntity entity: this.options){
            if(entity.getName().equals(option)&& !option.equals("Не важно")){
                return entity;
            }
        }
        return null;
    }

    public boolean isOption(String option){
        for (BaseEnumEntity entity: this.options){
            if(entity.getName().equals(option)){
                return true;
            }
        }
        return false;
    }


}
