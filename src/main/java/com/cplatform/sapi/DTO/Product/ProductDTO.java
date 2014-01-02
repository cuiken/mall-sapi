package com.cplatform.sapi.DTO.Product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dozer.Mapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 客户端商品详细DTO
 * User: cuikai
 * Date: 13-8-23
 * Time: 上午11:00
 */
public class ProductDTO {

    @JsonProperty("FLAG")
    private String ret;

    @JsonProperty("MSG")
    private String msg;

    @JsonProperty("ID")
    private Long id;

    @JsonProperty("NAME")
    private String name;

    @JsonProperty("MARKET_PRICE")
    private BigDecimal marketPrice;

    @JsonProperty("STORE_ID")
    private Long storeId;

    @JsonProperty("MALL_PRICE")
    private BigDecimal mallPrice;

    @JsonProperty("MEMBER_PRICE")
    private Map<String, Object> memberPrice = Maps.newHashMap();

    @JsonProperty("FARE")
    private Double fare;

    @JsonProperty("FARE_TYPE")
    private Long logisticsFeeType;

    @JsonProperty("SOLD_COUNT")
    private int soldCount;

    @JsonProperty("ALLOW_CASH")
    private String allowCash;

    @JsonProperty("ALLOW_COIN")
    private String allowCoin;

    @JsonProperty("ALLOW_SCORE")
    private String allowScore;

    @JsonProperty("STORE")
    private int store;

    @JsonProperty("IMAGES")
    private List<String> images = Lists.newArrayList();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Map<String, Object> getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(Map<String, Object> memberPrice) {
        this.memberPrice = memberPrice;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getLogisticsFeeType() {
        return logisticsFeeType;
    }

    public void setLogisticsFeeType(Long logisticsFeeType) {
        this.logisticsFeeType = logisticsFeeType;
    }

    @Mapping("shopPrice")
    public BigDecimal getMallPrice() {
        return mallPrice;
    }

    public void setMallPrice(BigDecimal mallPrice) {
        this.mallPrice = mallPrice;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }

    public int getSoldCount() {
        return soldCount;
    }

    public void setSoldCount(int soldCount) {
        this.soldCount = soldCount;
    }

    public String getAllowCash() {
        return allowCash;
    }

    public void setAllowCash(String allowCash) {
        this.allowCash = allowCash;
    }

    public String getAllowCoin() {
        return allowCoin;
    }

    public void setAllowCoin(String allowCoin) {
        this.allowCoin = allowCoin;
    }

    public String getAllowScore() {
        return allowScore;
    }

    public void setAllowScore(String allowScore) {
        this.allowScore = allowScore;
    }

    @Mapping("stockNum")
    public int getStore() {
        return store;
    }

    public void setStore(int store) {
        this.store = store;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getRet() {
        return "0";
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return "操作成功";
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
