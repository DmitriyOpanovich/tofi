package com.tofi.repository;

import com.tofi.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by ulian_000 on 18.12.2016.
 */
@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
    Optional<Bank> findOneByName(String name);
}
