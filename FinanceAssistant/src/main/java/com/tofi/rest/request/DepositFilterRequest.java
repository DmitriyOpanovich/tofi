package com.tofi.rest.request;

import com.tofi.dto.DepositFilterDTO;

/**
 * Created by ulian_000 on 26.12.2016.
 */
public class DepositFilterRequest {

    private Long telegramId;
    private DepositFilterDTO filter;

    public DepositFilterRequest() {}

    public Long getTelegramId() {
        return telegramId;
    }
    public void setTelegramId(Long telegramId) {
        this.telegramId = telegramId;
    }

    public DepositFilterDTO getFilter() {
        return filter;
    }
    public void setFilter(DepositFilterDTO filter) {
        this.filter = filter;
    }
}
