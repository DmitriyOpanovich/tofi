package com.tofi.rest.request;

import com.tofi.dto.CreditFilterDTO;

/**
 * Created by ulian_000 on 25.12.2016.
 */
public class CreditFilterRequest {

    private CreditFilterDTO filter;
    private Long telegramId;

    public CreditFilterRequest() {

    }

    public CreditFilterDTO getFilter() {
        return filter;
    }

    public void setFilter(CreditFilterDTO filter) {
        this.filter = filter;
    }

    public Long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(Long telegramId) {
        this.telegramId = telegramId;
    }
}
