package com.tofi.repository;

import com.tofi.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by ulian_000 on 20.12.2016.
 */
@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long>, JpaSpecificationExecutor {

    Optional<Deposit> findOneByAgregatorName(String id);
    List<Deposit> findAllByAgregatorNameIn(List<String> names);
}
