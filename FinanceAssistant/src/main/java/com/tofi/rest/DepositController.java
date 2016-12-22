package com.tofi.rest;

import com.tofi.dto.DepositDTO;
import com.tofi.model.Deposit;
import com.tofi.service.DepositService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ulian_000 on 20.12.2016.
 */
@RestController
@RequestMapping(value="/api/v1/deposits")
public class DepositController {
    @Autowired
    private DepositService depositService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping( method= RequestMethod.POST)
    @Transactional
    public void  create(@RequestBody List<DepositDTO> depositDTOs){
        List<Deposit> deposits = depositDTOs.stream()
                .map(depositDTO -> modelMapper.map(depositDTO, Deposit.class))
                .collect(Collectors.toList());

        depositService.updateDeposits(deposits);
    }
}
