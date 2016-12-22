package com.tofi.service.impl;

import com.tofi.model.PercentageTerm;
import com.tofi.model.enums.Currency;
import com.tofi.repository.enums.CurrencyRepository;
import com.tofi.service.EnumService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by ulian_000 on 21.12.2016.
 */
public class EnumServiceImpl implements EnumService{
    @Autowired
    CurrencyRepository currencyRepository;

//    public Map<String, ? extends BaseEnumEntity> persistEnums(List<? extends BaseEnumEntity> enumRecords, Class entityClass) {
//        Map<String, ? extends BaseEnumEntity> enumsForMerge = enumRecords
//                .stream()
//                .map(term -> term.getName())
//                .distinct()
//                .collect(Collectors.toMap(BaseEnumEntity::getName, Function.identity()));
//
//        Map<String, ? extends BaseEnumEntity> existedEnums = enumRepositoryFactory.getRepository(entityClass).findAll()
//                .parallelStream()
//                .collect(Collectors.toMap(BaseEnumEntity::getName, Function.identity()));
//
//        List<? extends BaseEnumEntity> newEnums = enumsForMerge.keySet()
//                .stream()
//                .filter(enumName -> !existedEnums.containsKey(enumName))
//                .map(enumsForMerge::get)
//                .collect(Collectors.toList());
//
//        enumRepositoryFactory.getRepository(entityClass).save(newEnums);
//        newEnums.forEach(curr -> existedEnums.put(curr.getName(), curr));
//
//        return existedEnums;
//    }


    @Override
    public Map<String, Currency> persistCurrencies(List<PercentageTerm> termsRecords) {
        Map<String, Currency> currenciesForMerge = termsRecords
                .parallelStream()
                .map(term -> term.getCurrency())
                .distinct()
                .collect(Collectors.toMap(Currency::getName, Function.identity()));

        Map<String, Currency> existedCurrencies = currencyRepository.findAll()
                .parallelStream()
                .collect(Collectors.toMap(Currency::getName, Function.identity()));

        List<Currency> newCurrencies = currenciesForMerge.keySet()
                .stream()
                .filter(currencyName -> !existedCurrencies.containsKey(currencyName))
                .map(currenciesForMerge::get)
                .collect(Collectors.toList());

        currencyRepository.save(newCurrencies);
        newCurrencies.forEach(curr -> existedCurrencies.put(curr.getName(), curr));

        return existedCurrencies;
    }

}
