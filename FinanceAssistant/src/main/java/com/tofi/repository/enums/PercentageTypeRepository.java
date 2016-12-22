package com.tofi.repository.enums;

import com.tofi.model.enums.PercentageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ulian_000 on 18.12.2016.
 */
@Repository
public interface PercentageTypeRepository extends JpaRepository<PercentageType, Long>, EnumRepository<PercentageType> {
}
