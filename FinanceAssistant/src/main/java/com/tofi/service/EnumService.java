package com.tofi.service;

import com.tofi.model.PercentageTerm;
import com.tofi.model.enums.Currency;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by ulian_000 on 21.12.2016.
 */
@Service
public interface EnumService {
     Map<String, Currency> persistCurrencies(List<PercentageTerm> termsRecords);
}
