package com.tofi.model.enums;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by ulian_000 on 07.12.2016.
 */
@Entity
@Table(name="Currencies")
public class Currency extends BaseEnumEntity {
    public Currency(String name, String descr){super(name, descr);}
    public Currency(){}
}
