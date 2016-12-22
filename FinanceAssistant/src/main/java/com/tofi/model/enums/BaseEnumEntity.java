package com.tofi.model.enums;

import com.tofi.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by ulian_000 on 05.12.2016.
 */
@MappedSuperclass
public class BaseEnumEntity extends BaseEntity {

    @Column(name="Name", unique = true)
    private String name;

    @Column(name="ru_descr")
    private String ru_descr;

    public BaseEnumEntity(){}

    public BaseEnumEntity(String name, String ru_descr){
        setName(name);
        setRu_descr(ru_descr);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRu_descr() {
        return ru_descr;
    }
    public void setRu_descr(String ru_descr) {
        this.ru_descr = ru_descr;
    }

    @Override
    public String toString() {
        return name == null ? "" : name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEnumEntity)) return false;
        if (!super.equals(o)) return false;

        BaseEnumEntity that = (BaseEnumEntity) o;

        return getName() != null ? getName().equals(that.getName()) : that.getName() == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }
}
