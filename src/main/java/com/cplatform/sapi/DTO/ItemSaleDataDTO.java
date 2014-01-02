package com.cplatform.sapi.DTO;

import com.cplatform.sapi.entity.product.MarketGoods;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.util.List;

/**
 * User: cuikai
 * Date: 13-8-19
 * Time: 下午2:01
 */

public class ItemSaleDataDTO {

    private Item item;
    private ItemProperties properties;
    private List<ItemPrice> itemPrice = Lists.newArrayList();
    private MarketGoods marketGoodsProperty;

    private int count;
    private int discount;

    public static class Item {
        private Long id;
        private Long itemId;
        private String name;
        private String shortName;
        private Long storeId;
        private String storeName;
        private String storeShortName;
        private Long typeId;
        private String typeName;
        private String brand;
        private String createTime;
        private String updateTime;
        private String marketContent;
        private String property;
        private int clicknum;
        private String tags;
        private String webPath;
        private BigDecimal marketPrice;  //市场价
        private BigDecimal shopPrice;    //商城价
        private BigDecimal otherPrice;
        private int salenum;
        private int commentnum;
        private int usernum;
        private int collectnum;
        private int stocknum;
        private BigDecimal logisticsFee;
        private int logisticsFeeType;
        private int postFlag;
        private int itemMode;
        private String startTime;
        private String stopTime;
        private String rank;
        private String warmPrompt;
        private String groupFlag;
        @JsonProperty("isValid")
        private String isvalid;
        private String status;
        private String iseckill;
        private String minPrice;
        private String userLevels;
        private int cashIdgoods;
        private int coinIdgoods;
        private int scoreIdgoods;
        private int deliveryPay;
        private String regionCodes;
        private BigDecimal iseckillPrice;

        private int payType;
        private int billPay;
        private String source;

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public int getBillPay() {
            return billPay;
        }

        public void setBillPay(int billPay) {
            this.billPay = billPay;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getItemId() {
            return itemId;
        }

        public void setItemId(Long itemId) {
            this.itemId = itemId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
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

        public int getClicknum() {
            return clicknum;
        }

        public void setClicknum(int clicknum) {
            this.clicknum = clicknum;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

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

        public BigDecimal getOtherPrice() {
            return otherPrice;
        }

        public void setOtherPrice(BigDecimal otherPrice) {
            this.otherPrice = otherPrice;
        }

        public int getSalenum() {
            return salenum;
        }

        public void setSalenum(int salenum) {
            this.salenum = salenum;
        }

        public int getCommentnum() {
            return commentnum;
        }

        public void setCommentnum(int commentnum) {
            this.commentnum = commentnum;
        }

        public int getUsernum() {
            return usernum;
        }

        public void setUsernum(int usernum) {
            this.usernum = usernum;
        }

        public int getCollectnum() {
            return collectnum;
        }

        public void setCollectnum(int collectnum) {
            this.collectnum = collectnum;
        }

        public int getStocknum() {
            return stocknum;
        }

        public void setStocknum(int stocknum) {
            this.stocknum = stocknum;
        }

        public BigDecimal getLogisticsFee() {
            return logisticsFee;
        }

        public void setLogisticsFee(BigDecimal logisticsFee) {
            this.logisticsFee = logisticsFee;
        }

        public int getLogisticsFeeType() {
            return logisticsFeeType;
        }

        public void setLogisticsFeeType(int logisticsFeeType) {
            this.logisticsFeeType = logisticsFeeType;
        }

        public int getPostFlag() {
            return postFlag;
        }

        public void setPostFlag(int postFlag) {
            this.postFlag = postFlag;
        }

        public int getItemMode() {
            return itemMode;
        }

        public void setItemMode(int itemMode) {
            this.itemMode = itemMode;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getStopTime() {
            return stopTime;
        }

        public void setStopTime(String stopTime) {
            this.stopTime = stopTime;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getWarmPrompt() {
            return warmPrompt;
        }

        public void setWarmPrompt(String warmPrompt) {
            this.warmPrompt = warmPrompt;
        }

        public String getGroupFlag() {
            return groupFlag;
        }

        public void setGroupFlag(String groupFlag) {
            this.groupFlag = groupFlag;
        }

        public String getIsvalid() {
            return isvalid;
        }

        public void setIsvalid(String isvalid) {
            this.isvalid = isvalid;
        }

        public String getIseckill() {
            return iseckill;
        }

        public void setIseckill(String iseckill) {
            this.iseckill = iseckill;
        }

        public String getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(String minPrice) {
            this.minPrice = minPrice;
        }

        public String getUserLevels() {
            return userLevels;
        }

        public void setUserLevels(String userLevels) {
            this.userLevels = userLevels;
        }

        public int getCashIdgoods() {
            return cashIdgoods;
        }

        public void setCashIdgoods(int cashIdgoods) {
            this.cashIdgoods = cashIdgoods;
        }

        public int getCoinIdgoods() {
            return coinIdgoods;
        }

        public void setCoinIdgoods(int coinIdgoods) {
            this.coinIdgoods = coinIdgoods;
        }

        public int getScoreIdgoods() {
            return scoreIdgoods;
        }

        public void setScoreIdgoods(int scoreIdgoods) {
            this.scoreIdgoods = scoreIdgoods;
        }

        public String getRegionCodes() {
            return regionCodes;
        }

        public void setRegionCodes(String regionCodes) {
            this.regionCodes = regionCodes;
        }

        public BigDecimal getIseckillPrice() {
            return iseckillPrice;
        }

        public void setIseckillPrice(BigDecimal iseckillPrice) {
            this.iseckillPrice = iseckillPrice;
        }

        public int getDeliveryPay() {
            return deliveryPay;
        }

        public void setDeliveryPay(int deliveryPay) {
            this.deliveryPay = deliveryPay;
        }
    }


    @JsonProperty("properties")
    public ItemProperties getProperties() {
        return properties;
    }

    public void setProperties(ItemProperties properties) {
        this.properties = properties;
    }

    @JsonProperty("itemPrice")
    public List<ItemPrice> getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(List<ItemPrice> itemPrice) {
        this.itemPrice = itemPrice;
    }

    @JsonProperty("item")
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @JsonIgnore
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @JsonIgnore
    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public MarketGoods getMarketGoodsProperty() {
        return marketGoodsProperty;
    }

    public void setMarketGoodsProperty(MarketGoods marketGoodsProperty) {
        this.marketGoodsProperty = marketGoodsProperty;
    }

    public static class ItemProperties {

    }

    public static class ItemPrice {
        private Long saleId;
        private String priceTypeCode;
        private String priceType;
        private BigDecimal price;

        public Long getSaleId() {
            return saleId;
        }

        public void setSaleId(Long saleId) {
            this.saleId = saleId;
        }

        public String getPriceTypeCode() {
            return priceTypeCode;
        }

        public void setPriceTypeCode(String priceTypeCode) {
            this.priceTypeCode = priceTypeCode;
        }

        public String getPriceType() {
            return priceType;
        }

        public void setPriceType(String priceType) {
            this.priceType = priceType;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
