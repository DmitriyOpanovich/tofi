package by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.model.enums;


public class AnyOption extends BaseEnumEntity {
    @Override
    public String getName() {
        return "Не важно";
    }

    @Override
    public String getEn_descr() {
        return "Any option would be acceptable";
    }

    @Override
    public String getRu_descr() {
        return "Подойдет любой вариант";
    }

    @Override
    public String getDesc(String language) {
        return super.getDesc(language);
    }
}
