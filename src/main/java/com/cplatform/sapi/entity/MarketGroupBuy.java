package com.cplatform.sapi.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 团购
 * User: cuikai
 * Date: 13-11-25
 * Time: 上午9:42
 */
@Entity
@Table(name = "t_market_groupbuy")
public class MarketGroupBuy extends IdEntity{

//    private Long id;
    private String name;
    private String image;
    private String description;
    private Long sort;
    private String status;
    private Long modifyValue;
    private Long productId;
    private String type;
    private String createTime;
    private String issale;
    private String goodsDetail;
    private String goodsArea;
    private String goodsAreaCode;
    private String goodsType;
    private String checkStatus;
    private String checkContent;
    private String checkMan;
    private String checkTime;
    private String createFrom;

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getModifyValue() {
        return modifyValue;
    }

    public void setModifyValue(Long modifyValue) {
        this.modifyValue = modifyValue;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIssale() {
        return issale;
    }

    public void setIssale(String issale) {
        this.issale = issale;
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public String getGoodsArea() {
        return goodsArea;
    }

    public void setGoodsArea(String goodsArea) {
        this.goodsArea = goodsArea;
    }

    public String getGoodsAreaCode() {
        return goodsAreaCode;
    }

    public void setGoodsAreaCode(String goodsAreaCode) {
        this.goodsAreaCode = goodsAreaCode;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getCheckContent() {
        return checkContent;
    }

    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent;
    }

    public String getCheckMan() {
        return checkMan;
    }

    public void setCheckMan(String checkMan) {
        this.checkMan = checkMan;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getCreateFrom() {
        return createFrom;
    }

    public void setCreateFrom(String createFrom) {
        this.createFrom = createFrom;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
