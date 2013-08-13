package com.cplatform.sapi.entity.product;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User: cuikai
 * Date: 13-8-13
 * Time: 下午12:05
 */
@Entity
@Table(name="T_SYS_TYPE")
public class TSysType {

    private Long id;
    private Long PId;
    private String name;
    private Long type;

    @Id
    @Column(name = "ID", unique = true, nullable = false, precision = 8, scale = 0)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "P_ID", precision = 8, scale = 0)
    public Long getPId() {
        return PId;
    }

    public void setPId(Long PId) {
        this.PId = PId;
    }

    @Column(name = "NAME", length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "TYPE", precision = 1, scale = 0)
    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
