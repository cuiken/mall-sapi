package com.cplatform.sapi.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dozer.Mapping;

import java.math.BigDecimal;
import java.util.List;

/**
 * 搜索引擎返回数据DTO
 * User: cuikai
 * Date: 13-8-27
 * Time: 上午9:26
 */
public class ProductSearchDTO {

    @JsonProperty("RET")
    private String ret;

    @JsonProperty("MSG")
    private String msg;

    @JsonProperty("TOTAL")
    private int total;

    @JsonProperty("DATA")
    private List<Data> data= Lists.newArrayList();

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        @JsonProperty("G_ID")
        private Long id;

        @JsonProperty("G_ORG_ID")
        private Long orgId;

        @JsonProperty("G_NAME")
        private String name;

        @JsonProperty("G_SHORT_NAME")
        private String shortName;

        @JsonProperty("G_STORE_ID")
        private Long storeId;

        @JsonProperty("G_STORE_NAME")
        private String storeName;

        @JsonProperty("G_STORE_SHORT_NAME")
        private String storeShortName;

        @JsonProperty("G_TYPE_ID")
        private Long typeId;

        @JsonProperty("G_TYPE_NAME")
        private String typeName;

        @JsonProperty("G_BRAND")
        private String brand;

        @JsonProperty("G_MARKET_CONTENT")
        private String marketContent;

        @JsonProperty("G_PROPERTY")
        private String property;

        @JsonProperty("G_CLICK_NUM")
        private int clickNum;

        @JsonProperty("G_TAGS")
        private String tags;

        @JsonProperty("G_WEB_PATH")
        private String webPath;

        @JsonProperty("G_MARKET_PRICE")
        private BigDecimal marketPrice;

        @JsonProperty("G_SHOP_PRICE")
        private BigDecimal shopPrice;

        @JsonProperty("G_RED_PRICE")
        private BigDecimal redPrice;

        @JsonProperty("G_OTHER_PRICE")
        private BigDecimal otherPrice;

        @JsonProperty("G_REGION_CODE")
        private String regionCode;

        @Mapping("ID")
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getOrgId() {
            return orgId;
        }

        public void setOrgId(Long orgId) {
            this.orgId = orgId;
        }

        @Mapping("NAME")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public Long getStoreId() {
            return storeId;
        }

        public void setStoreId(Long storeId) {
            this.storeId = storeId;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public String getStoreShortName() {
            return storeShortName;
        }

        public void setStoreShortName(String storeShortName) {
            this.storeShortName = storeShortName;
        }

        public Long getTypeId() {
            return typeId;
        }

        public void setTypeId(Long typeId) {
            this.typeId = typeId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getMarketContent() {
            return marketContent;
        }

        public void setMarketContent(String marketContent) {
            this.marketContent = marketContent;
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public int getClickNum() {
            return clickNum;
        }

        public void setClickNum(int clickNum) {
            this.clickNum = clickNum;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        @Mapping("IMAGES")
        public String getWebPath() {
            return webPath;
        }

        public void setWebPath(String webPath) {
            this.webPath = webPath;
        }

        public BigDecimal getMarketPrice() {
            return marketPrice;
        }

        public void setMarketPrice(BigDecimal marketPrice) {
            this.marketPrice = marketPrice;
        }

        public BigDecimal getShopPrice() {
            return shopPrice;
        }

        public void setShopPrice(BigDecimal shopPrice) {
            this.shopPrice = shopPrice;
        }

        public BigDecimal getRedPrice() {
            return redPrice;
        }

        public void setRedPrice(BigDecimal redPrice) {
            this.redPrice = redPrice;
        }

        public BigDecimal getOtherPrice() {
            return otherPrice;
        }

        public void setOtherPrice(BigDecimal otherPrice) {
            this.otherPrice = otherPrice;
        }

        public String getRegionCode() {
            return regionCode;
        }

        public void setRegionCode(String regionCode) {
            this.regionCode = regionCode;
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
