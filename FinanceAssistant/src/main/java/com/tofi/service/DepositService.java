package com.tofi.service;

import com.tofi.model.Deposit;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ulian_000 on 20.12.2016.
 */
@Service
public interface DepositService {
    void updateDeposits(List<Deposit> creditRecords);
    List<Deposit> list();
}
