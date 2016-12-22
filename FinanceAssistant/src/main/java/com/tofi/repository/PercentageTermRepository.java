package com.tofi.repository;

import com.tofi.model.Credit;
import com.tofi.model.Deposit;
import com.tofi.model.PercentageTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ulian_000 on 19.12.2016.
 */
@Repository
public interface PercentageTermRepository extends JpaRepository<PercentageTerm, Long> {

    void deleteAllByCreditIn(List<Credit> credits);
    void deleteAllByDepositIn(List<Deposit> deposits);

}
