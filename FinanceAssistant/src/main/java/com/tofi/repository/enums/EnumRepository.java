package com.tofi.repository.enums;

import com.tofi.model.enums.BaseEnumEntity;

import java.util.List;
import java.util.Optional;

/**
 * Created by ulian_000 on 22.12.2016.
 */
public interface EnumRepository<T extends BaseEnumEntity>  {
    Optional<T> findOneByName(String name);
    List<T> findAllByNameIn (List<String> names);
}
