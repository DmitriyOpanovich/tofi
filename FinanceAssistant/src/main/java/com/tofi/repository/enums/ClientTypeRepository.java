package com.tofi.repository.enums;

import com.tofi.model.enums.ClientType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ulian_000 on 17.12.2016.
 */
@Repository
public interface ClientTypeRepository extends EnumRepository<ClientType>, JpaRepository<ClientType, Long> {

}
