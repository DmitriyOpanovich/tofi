package by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.model.enums;

public abstract class BaseEnumEntity {

    private String name;
    private String en_descr;
    private String ru_descr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEn_descr() {
        return en_descr;
    }

    public void setEn_descr(String en_descr) {
        this.en_descr = en_descr;
    }

    public String getRu_descr() {
        return ru_descr;
    }

    public void setRu_descr(String ru_descr) {
        this.ru_descr = ru_descr;
    }

    public String getDesc(String language){
        switch (language){
            case "ru":{
                return getRu_descr();
            }
            default: {
                return getEn_descr();
            }
        }
    }

}
