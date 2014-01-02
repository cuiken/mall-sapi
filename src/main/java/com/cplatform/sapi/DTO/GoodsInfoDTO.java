package com.cplatform.sapi.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class GoodsInfoDTO {

    @JsonProperty("GOODS_ID")
    private Long goodsId;

    @JsonProperty("GOODS_NAME")
    private String goodsName;

    @JsonProperty("GOODS_IMAGE")
    private String goodsImage;

    @JsonProperty("PRICE")
    private BigDecimal price;

    @JsonProperty("COUNT")
    private int count;

    @JsonProperty("TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
