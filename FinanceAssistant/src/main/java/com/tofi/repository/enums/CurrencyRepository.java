package com.tofi.repository.enums;

import com.tofi.model.enums.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ulian_000 on 18.12.2016.
 */
@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long>, EnumRepository<Currency> {
}
