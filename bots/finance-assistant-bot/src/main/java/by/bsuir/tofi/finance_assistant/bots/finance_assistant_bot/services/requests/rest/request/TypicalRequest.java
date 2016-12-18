package by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.services.requests.rest.request;


import by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.BotConfig;
import by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.services.requests.Functions;
import by.bsuir.tofi.finance_assistant.bots.finance_assistant_bot.services.requests.rest.response.Response;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Created by 1 on 07.12.2016.
 */
public abstract class TypicalRequest {
    private final URI uri;
    private final Object requestEntity;
    private final HttpMethod httpMethod;

    public TypicalRequest(Object o, Functions functions) {
        this.requestEntity = o;
        this.uri = URI.create(BotConfig.API_URL + functions.getPath());
        this.httpMethod = functions.getHttpMethod();
    }

    public TypicalRequest(Functions functions) {
        this.requestEntity = null;
        this.uri = URI.create(BotConfig.API_URL + functions.getPath());
        this.httpMethod = functions.getHttpMethod();
    }

    protected Response sendRequest(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = null;

        if(this.httpMethod.equals(HttpMethod.GET)){
            responseEntity = restTemplate.getForEntity(this.uri, String.class);
        }else{
            HttpEntity<Object> requestEntity = new HttpEntity<Object>(this.requestEntity, new HttpHeaders());
            responseEntity = restTemplate.exchange(this.uri, this.httpMethod, requestEntity, String.class);
        }
        JsonParser jsonParser = new JsonParser();
        Gson gson = new Gson();

        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
            return new Response(true, responseEntity.getBody());
        }else {
            return new Response(false, "Sorry, something went wrong on the server side");
        }

//        HttpEntity<Request> requestEntity = new HttpEntity<Request>(this.request, new HttpHeaders());
//        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, requestEntity, String.class);
//        ResponseEntity<String> responseEntity1 = restTemplate.get
//        JsonParser jsonParser = new JsonParser();
//        Gson gson = new Gson();
//        try {
//            ResponseMessage responseMessage = gson.fromJson(jsonParser.parse(responseEntity.getBody()), ResponseMessage.class);
//            return new Response(true, responseMessage.getResult().getMessage());
//        }catch (Exception e){
//            try {
//                ErrorResponseMessage errorResponseMessage = gson.fromJson(jsonParser.parse(responseEntity.getBody()), ErrorResponseMessage.class);
//                return new Response(false, errorResponseMessage.getError().getData());
//            }catch (Exception e1){
//                return new Response(false, "Unknown error");
//            }
//
//        }

    }

}
