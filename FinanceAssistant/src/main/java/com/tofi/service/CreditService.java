package com.tofi.service;

import com.tofi.model.Credit;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ulian_000 on 12.12.2016.
 */
@Service
public interface CreditService {

    void updateCredits(List<Credit> creditRecords);
    List<Credit> list();

}
