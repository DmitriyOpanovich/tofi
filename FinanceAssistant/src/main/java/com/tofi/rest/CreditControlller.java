package com.tofi.rest;

import com.tofi.dto.CreditDTO;
import com.tofi.model.Credit;
import com.tofi.service.CreditService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ulian_000 on 12.12.2016.
 */
@RestController
@RequestMapping(value="/api/v1/credits")
public class CreditControlller {
    @Autowired
    private CreditService creditService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping( method= RequestMethod.GET)
    public List<CreditDTO> list(){
        List<Credit> credits = creditService.list();

        return credits.parallelStream()
                .map(credit -> modelMapper.map(credit, CreditDTO.class))
                .collect(Collectors.toList());
    }


    @RequestMapping( method= RequestMethod.POST)
    public void  create(@RequestBody List<CreditDTO> creditDTOs){
        List<Credit> credits = creditDTOs.stream()
                .map(creditDTO -> modelMapper.map(creditDTO, Credit.class))
                .collect(Collectors.toList());

        creditService.updateCredits(credits);
    }
}
