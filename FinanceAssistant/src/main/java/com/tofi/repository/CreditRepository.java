package com.tofi.repository;

import com.tofi.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Created by ulian_000 on 12.12.2016.
 */
@Repository
public interface CreditRepository extends JpaRepository<Credit, Long>, JpaSpecificationExecutor {

    Optional<Credit> findOneByAgregatorName(String id);
    List<Credit> findAllByAgregatorNameIn(List<String> names);


}
