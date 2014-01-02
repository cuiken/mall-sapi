package com.cplatform.sapi.DTO;

import com.cplatform.sapi.entity.p2.ItemPriceInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品信息V2_V1结构转换
 * User: cuikai
 * Date: 13-9-26
 * Time: 上午10:31
 */
public class ItemInfo_v2Tov1DTO {

    private Long id;
    private String itemId;
    private String name;
    private String shortName;
    private Long storeId;
    private Long typeId;
    private String typeName;
    private String brand;
    private String createTime;
    private String updateTime;
    private String marketContent;
    private String property;
    private String tags;
    private String webPath;
    private BigDecimal marketPrice;  //市场价
    private BigDecimal shopPrice;    //商城价
    private BigDecimal otherPrice;
    private BigDecimal minPrice;
    private BigDecimal logisticsFee;
    private int logisticsFeeType;
    private int postFlag;
    private int itemMode;
    private String startTime;
    private String stopTime;
    private String warmPrompt;
    private String groupFlag;
    @JsonProperty("paymentCash")
    private int cashIdgoods;
    @JsonProperty("paymentCoin")
    private int coinIdgoods;
    @JsonProperty("paymentScore")
    private int scoreIdgoods;
    @JsonProperty("isValid")
    private String isvalid;
    private String status;
    private String iseckill;
    private BigDecimal iseckillPrice;
    private String regionCodes;
    private String userLevels;
    private String source;
    @JsonProperty("itemPrices")
    private List<ItemPriceInfo> itemPrice = Lists.newArrayList();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
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

    public BigDecimal getIseckillPrice() {
        return iseckillPrice;
    }

    public void setIseckillPrice(BigDecimal iseckillPrice) {
        this.iseckillPrice = iseckillPrice;
    }

    public String getRegionCodes() {
        return regionCodes;
    }

    public void setRegionCodes(String regionCodes) {
        this.regionCodes = regionCodes;
    }

    public List<ItemPriceInfo> getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(List<ItemPriceInfo> itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getUserLevels() {
        return userLevels;
    }

    public void setUserLevels(String userLevels) {
        this.userLevels = userLevels;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
