package com.tofi.rest;

import com.tofi.dto.CreditDTO;
import com.tofi.dto.DepositDTO;
import com.tofi.dto.EnumDTO;
import com.tofi.model.CreditFilter;
import com.tofi.model.DepositFilter;
import com.tofi.service.FilterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ulian_000 on 21.12.2016.
 */
@RestController
public class FilterController {
    @Autowired
    FilterService filterService;
    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value="/api/v1/client_types", method= RequestMethod.GET)
    public List<EnumDTO> getClientTypes(){
        return filterService.getAvailableClientTypes().stream()
                .map(clientType -> modelMapper.map(clientType,EnumDTO.class))
                .collect(Collectors.toList());
    }

    @RequestMapping(value="/api/v1/repayment_methods", method= RequestMethod.GET)
    public List<EnumDTO> getRepaymentMethods(){
        return filterService.getAvailableRepaymentMethods().stream()
                .map(clientType -> modelMapper.map(clientType,EnumDTO.class))
                .collect(Collectors.toList());
    }

    @RequestMapping(value="/api/v1/payment_posibilities", method= RequestMethod.GET)
    public List<EnumDTO> getPaymentPosibilities(){
        return filterService.getAvailablePaymentPosibilities().stream()
                .map(clientType -> modelMapper.map(clientType,EnumDTO.class))
                .collect(Collectors.toList());
    }

    @RequestMapping(value="/api/v1/credit_goals", method= RequestMethod.GET)
    public List<EnumDTO> getCreditGoals(){
        return filterService.getAvailableCreditGoals().stream()
                .map(clientType -> modelMapper.map(clientType,EnumDTO.class))
                .collect(Collectors.toList());
    }

    @RequestMapping(value="/api/v1/percentage_types", method= RequestMethod.GET)
    public List<EnumDTO> getPercentageTypes(){
        return filterService.getAvailablePercentageTypes().stream()
                .map(clientType -> modelMapper.map(clientType,EnumDTO.class))
                .collect(Collectors.toList());
    }

    @RequestMapping(value="/api/v1/currencies", method= RequestMethod.GET)
    public List<EnumDTO> getCurrencies(){
        return filterService.getAvailableCurrencies().stream()
                .map(clientType -> modelMapper.map(clientType,EnumDTO.class))
                .collect(Collectors.toList());
    }

    @RequestMapping(value="/api/v1/filter_credits", method= RequestMethod.POST)
    public List<CreditDTO> filterCredits(@RequestBody CreditFilter filter){
        if(filter == null) return null;

        return filterService.filterCredits(filter, null).stream()
                .map(credit -> modelMapper.map(credit, CreditDTO.class))
                .distinct()
                .collect(Collectors.toList());
    }

    @RequestMapping(value="/api/v1/filter_deposits", method= RequestMethod.POST)
    public List<DepositDTO> filterDeposits(@RequestBody DepositFilter filter){
        if(filter == null) return null;

        return filterService.filterDeposits(filter, null).stream()
                .map(deposit -> modelMapper.map(deposit, DepositDTO.class))
                .distinct()
                .collect(Collectors.toList());
    }
}
