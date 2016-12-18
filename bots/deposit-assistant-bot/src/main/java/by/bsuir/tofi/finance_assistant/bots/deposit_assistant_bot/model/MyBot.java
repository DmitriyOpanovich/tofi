package by.bsuir.tofi.finance_assistant.bots.deposit_assistant_bot.model;


public enum MyBot {

    FINANCE_ASSISTANT("Finance Assistant", "Финансовый помошник", "This service will help you to deal up with all your financial problems", "Этот сервис поможет вам решить все ваши финансовые проблемы", "telegram.me/ByFinanceAssistantBot");

    private String en_name;
    private String ru_name;
    private String en_desc;
    private String ru_desc;
    private String url;

    MyBot(String en_name, String ru_name, String en_desc, String ru_desc, String url) {
        this.en_name = en_name;
        this.ru_name = ru_name;
        this.en_desc = en_desc;
        this.ru_desc = ru_desc;
        this.url = url;
    }


    public String describe(String language){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('*').append(getName(language)).append('*').append(" - ").append(getDesc(language));
        return stringBuilder.toString();
    }

    public String getDesc(String language){
        StringBuilder stringBuilder = new StringBuilder();
        switch (language){
            case "ru":{
                stringBuilder.append(this.ru_desc);
                break;
            }
            case "en":{
                stringBuilder.append(this.en_desc);
                break;
            }
            default:{
                stringBuilder.append(this.en_desc);
                break;
            }
        }
        return stringBuilder.toString();
    }

    public String getName(String language){
        StringBuilder stringBuilder = new StringBuilder();
        switch (language){
            case "ru":{
                stringBuilder.append(this.ru_name);
                break;
            }
            case "en":{
                stringBuilder.append(this.en_name);
                break;
            }
            default:{
                stringBuilder.append(this.en_name);
                break;
            }
        }
        return stringBuilder.toString();
    }

    public String getUrl() {
        return url;
    }
}
