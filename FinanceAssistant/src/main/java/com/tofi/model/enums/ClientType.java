package com.tofi.model.enums;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by ulian_000 on 05.12.2016.
 */
@Entity
@Table(name="ClientTypes")
public class ClientType extends BaseEnumEntity {
    public ClientType(String name, String descr){super(name, descr);}
    public ClientType(){}
}
