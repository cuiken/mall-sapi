package com.cplatform.sapi.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: cuikai
 * Date: 13-9-22
 * Time: 下午4:26
 */
@Entity
@Table(name = "T_SYS_LOGISTICS")
public class SysLogistic extends IdEntity {

    private String name;
    private Long isValid;
    private String face;
    private String ename;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "is_valid")
    public Long getValid() {
        return isValid;
    }

    public void setValid(Long valid) {
        isValid = valid;
    }

    @Column(name = "interface")
    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
