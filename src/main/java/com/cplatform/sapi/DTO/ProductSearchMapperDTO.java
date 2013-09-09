package com.cplatform.sapi.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import org.dozer.Mapping;

import java.math.BigDecimal;
import java.util.List;

/**
 * User: cuikai
 * Date: 13-8-27
 * Time: 上午10:42
 */
public class ProductSearchMapperDTO {

    @JsonProperty("TOTAL_ROW")
    private Long totalRow;

    @JsonProperty("FLAG")
    private String ret;

    @JsonProperty("MSG")
    private String msg;

    @JsonProperty("DATA")
    private List<Data> data = Lists.newArrayList();

    @Mapping("total")
    public Long getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Long totalRow) {
        this.totalRow = totalRow;
    }

    public String getRet() {
        return "0";
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return "success";
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        @JsonProperty("ID")
        private Long id;

        @JsonProperty("NAME")
        private String name;

        @JsonProperty("IMAGE")
        private String images;

        @JsonProperty("MARKET_PRICE")
        private BigDecimal marketPrice;

        @JsonProperty("MALL_PRICE")
        private BigDecimal mallPrice;

        @JsonProperty("MEMBER_PRICE")
        private BigDecimal memberPrice;

        @JsonProperty("SOLD_COUNT")
        private int soldCount;

        @JsonProperty("ALLOW_CASH")
        private String allowCash;

        @JsonProperty("ALLOW_COIN")
        private String allowCoin;

        @JsonProperty("ALLOW_SCORE")
        private String allowScore;

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

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public BigDecimal getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(BigDecimal marketPrice) {
            this.marketPrice = marketPrice;
        }

        public BigDecimal getMallPrice() {
            return mallPrice;
        }

        public void setMallPrice(BigDecimal mallPrice) {
            this.mallPrice = mallPrice;
        }

        public BigDecimal getMemberPrice() {
            return memberPrice;
        }

        public void setMemberPrice(BigDecimal memberPrice) {
            this.memberPrice = memberPrice;
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
    }

}
