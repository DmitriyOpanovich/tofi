package com.tofi.repository.enums;

import com.tofi.model.enums.CreditGoal;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ulian_000 on 18.12.2016.
 */
public interface CreditGoalRepository extends JpaRepository<CreditGoal, Long>, EnumRepository<CreditGoal> {
}
