package com.tofi.repository;

import com.tofi.model.CreditResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ulian_000 on 22.12.2016.
 */
@Repository
public interface CreditResponseRepository extends JpaRepository<CreditResponse, Long>{
}
