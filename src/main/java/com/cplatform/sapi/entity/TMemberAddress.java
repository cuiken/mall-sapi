package com.cplatform.sapi.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;


@Entity
@Table(name = "T_MEMBER_ADDRESS")
public class TMemberAddress extends IdEntity {

    private Long mid;

    private String remark;

    private String region;

    private String address;

    private String zipcode;

    private String name;

    private String mobile;

    private String phone;

    private String updateTime;

    private String createTime;

    private String lastUseTime;

    private String defaultShipping;

    private String defaultPayType;


    @Column(name = "MID")
    public Long getMid() {
        return this.mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    @Column(name = "REMARK", length = 10)
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "REGION")
    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Column(name = "ADDRESS")
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "ZIPCODE", length = 10)
    public String getZipcode() {
        return this.zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Column(name = "NAME")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "MOBILE", length = 20)
    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "PHONE", length = 20)
    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "UPDATE_TIME")
    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "CREATE_TIME")
    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Column(name = "LAST_USE_TIME")
    public String getLastUseTime() {
        return this.lastUseTime;
    }

    public void setLastUseTime(String lastUseTime) {
        this.lastUseTime = lastUseTime;
    }

    @Column(name = "DEFAULT_SHIPPING")
    public String getDefaultShipping() {
        return this.defaultShipping;
    }

    public void setDefaultShipping(String defaultShipping) {
        this.defaultShipping = defaultShipping;
    }

    @Column(name = "DEFAULT_PAY_TYPE")
    public String getDefaultPayType() {
        return this.defaultPayType;
    }

    public void setDefaultPayType(String defaultPayType) {
        this.defaultPayType = defaultPayType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}