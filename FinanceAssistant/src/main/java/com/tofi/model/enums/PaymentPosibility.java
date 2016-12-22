package com.tofi.model.enums;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by ulian_000 on 07.12.2016.
 */
@Entity
@Table(name="PaymentPosibilities")
public class PaymentPosibility extends BaseEnumEntity {
    public PaymentPosibility(String name, String descr){super(name, descr);}
    public PaymentPosibility() {}
}
