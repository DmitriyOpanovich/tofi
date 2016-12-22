package com.tofi.model.enums;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by ulian_000 on 08.12.2016.
 */
@Entity
@Table(name="PercentageTypes")
public class PercentageType extends BaseEnumEntity{
    public PercentageType(String name, String descr){super(name, descr);}
    public PercentageType() {}
}
