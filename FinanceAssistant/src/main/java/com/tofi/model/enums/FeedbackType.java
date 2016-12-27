package com.tofi.model.enums;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by ulian_000 on 26.12.2016.
 */
@Entity
@Table(name="FeedbackTypes")
public class FeedbackType extends BaseEnumEntity {
    public FeedbackType(String name, String descr){super(name, descr);}
    public FeedbackType(){}
}
