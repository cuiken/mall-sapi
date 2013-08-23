package com.cplatform.sapi.DTO.Product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dozer.Mapping;

import java.math.BigDecimal;
import java.util.List;

/**
 * 客户端商品详细DTO
 * User: cuikai
 * Date: 13-8-23
 * Time: 上午11:00
 */
public class ProductDTO {
    @JsonProperty("ID")
    private Long id;

    @JsonProperty("NAME")
    private String name;

    @JsonProperty("MARKET_PRICE")
    private BigDecimal marketPrice;

    @JsonProperty("MALL_PRICE")
    private BigDecimal mallPrice;

    @JsonProperty("SCORE_PRICE")
    private BigDecimal scorePrice;

    @JsonProperty("MEMBER_PRICE")
    private BigDecimal memberPrice;

    @JsonProperty("MEMBER_SCORE_PRICE")
    private BigDecimal memberScorePrice;

    @JsonProperty("FARE")
    private BigDecimal fare;

    @JsonProperty("SOLD_COUNT")
    private int soldCount;

    @JsonProperty("ALLOW_CASH")
    private String allowCash;

    @JsonProperty("ALLOW_COIN")
    private String allowCoin;

    @JsonProperty("ALLOW_SCORE")
    private String allowScore;

    @JsonProperty("COIN_POINT")
    private String coinPoint;

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

    @Mapping("shopPrice")
    public BigDecimal getMallPrice() {
        return mallPrice;
    }

    public void setMallPrice(BigDecimal mallPrice) {
        this.mallPrice = mallPrice;
    }

    public BigDecimal getScorePrice() {
        return scorePrice;
    }

    public void setScorePrice(BigDecimal scorePrice) {
        this.scorePrice = scorePrice;
    }

    public BigDecimal getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(BigDecimal memberPrice) {
        this.memberPrice = memberPrice;
    }

    public BigDecimal getMemberScorePrice() {
        return memberScorePrice;
    }

    public void setMemberScorePrice(BigDecimal memberScorePrice) {
        this.memberScorePrice = memberScorePrice;
    }

    public BigDecimal getFare() {
        return fare;
    }

    public void setFare(BigDecimal fare) {
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

    public String getCoinPoint() {
        return coinPoint;
    }

    public void setCoinPoint(String coinPoint) {
        this.coinPoint = coinPoint;
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
