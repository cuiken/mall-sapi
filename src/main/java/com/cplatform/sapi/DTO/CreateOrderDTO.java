package com.cplatform.sapi.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * 客户端下单数据集
 * User: cuikai
 * Date: 13-8-22
 * Time: 上午10:10
 */
public class CreateOrderDTO {

    @JsonProperty("U_ID")
    private Long userId;

    @JsonProperty("GOOD_ID")
    private Long goodsId;

    @JsonProperty("PAY_TYPE")
    private String payType;

    @JsonProperty("COIN")
    private String coin;

    @JsonProperty("CASH")
    private BigDecimal cash;

    @JsonProperty("SCORE")
    private String score;

    @JsonProperty("PRICE")
    private BigDecimal price;

    @JsonProperty("AMOUNT")
    private BigDecimal amount;

    @JsonProperty("FARE")
    private BigDecimal fare;

    @JsonProperty("COUNT")
    private int count;

    @JsonProperty("GOOD_NAME")
    private String goodName;

    @JsonProperty("ADDRESS_ID")
    private Long addressId;

    @JsonProperty("USER_REMARK")
    private String userRemark;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getFare() {
        return fare;
    }

    public void setFare(BigDecimal fare) {
        this.fare = fare;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
