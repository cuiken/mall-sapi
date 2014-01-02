package com.cplatform.sapi.entity.p2;


import com.cplatform.sapi.entity.product.MarketGoods;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@JsonAutoDetect
public class ItemInfo implements Serializable {

    @JsonProperty("id")
    private String gId;

    @JsonProperty("itemId")
    private String gOrgId;

    @JsonProperty("name")
    private String gName;

    @JsonProperty("shortName")
    private String gShortName;

    @JsonProperty("storeId")
    private String gStoreId;

    @JsonProperty("typeId")
    private String gTypeId;

    @JsonProperty("typeName")
    private String gTypeName;

    @JsonProperty("brand")
    private String gBrand;

    @JsonProperty("createTime")
    private String gCreateTime;

    @JsonProperty("updateTime")
    private String gUpdateTime;

    @JsonProperty("marketContent")
    private String gMarketContent;

    @JsonProperty("property")
    private String gProperty;

    @JsonProperty("userLevels")
    private String gUserLevels;

    @JsonProperty("tags")
    private String gTags;

    @JsonProperty("webPath")
    private String gWebPath;

    private List<String> images = Lists.newArrayList();

    @JsonProperty("marketPrice")
    private String gMarketPrice;

    @JsonProperty("shopPrice")
    private String gShopPrice;

    @JsonProperty("otherPrice")
    private String gOtherPrice;

    @JsonProperty("logisticsFee")
    private String gLogisticsFee;

    @JsonProperty("logisticsFeeType")
    private String gLogisticsFeeType;

    @JsonProperty("postFlag")
    private String gPostFlag;

    @JsonProperty("itemMode")
    private String gItemMode;

    @JsonProperty("startTime")
    private String gStartTime;

    @JsonProperty("stopTime")
    private String gStopTime;

    private String status;

    @JsonProperty("warmPrompt")
    private String gWarmPrompt;

    @JsonProperty("groupFlag")
    private String gGroupFlag;

    @JsonProperty("paymentCash")
    private String gPaymentCash;

    @JsonProperty("paymentCoin")
    private String gPaymentCoin;

    @JsonProperty("paymentScore")
    private String gPaymentScore;

    @JsonProperty("isValid")
    private String gIsValid;

    @JsonProperty("iseckill")
    private String gIseckill;

    @JsonProperty("iseckillPrice")
    private String gIseckillPrice;

    @JsonProperty("regionCodes")
    private String gRegionCodes;

    //所有会员价中的最低价
    @JsonProperty("minPrice")
    private String gMinPrice;

    @JsonProperty("source")
    private String gSource;

    private ItemPayment itemPayment;

    private ItemStoreInfo storeInfo;

    @JsonProperty("ext")
    private ItemExtNumInfo itemExtNumInfo;
    private MarketGoods marketGoodsProperty;

    private List<SysType> sysTypePath;

    private List<ItemPriceInfo> itemPrices = Lists.newArrayList();

    private Map<String, ItemProperty> itemProperties = Maps.newHashMap();

    public ItemExtNumInfo getItemExtNumInfo() {
        return itemExtNumInfo;
    }

    public void setItemExtNumInfo(ItemExtNumInfo itemExtNumInfo) {
        this.itemExtNumInfo = itemExtNumInfo;
    }

    public List<SysType> getSysTypePath() {
        return sysTypePath;
    }

    public void setSysTypePath(List<SysType> sysTypePath) {
        this.sysTypePath = sysTypePath;
    }

    public ItemStoreInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(ItemStoreInfo storeInfo) {
        this.storeInfo = storeInfo;
    }

    public Map<String, ItemProperty> getItemProperties() {
        return itemProperties;
    }

    public void setItemProperties(Map<String, ItemProperty> itemProperties) {
        this.itemProperties = itemProperties;
    }

    public List<ItemPriceInfo> getItemPrices() {
        return itemPrices;
    }

    public void setItemPrices(List<ItemPriceInfo> itemPrices) {
        this.itemPrices = itemPrices;
    }

    public String getgMinPrice() {
        return gMinPrice;
    }

    public void setgMinPrice(String gMinPrice) {
        this.gMinPrice = gMinPrice;
    }

    public String getgId() {
        return gId;
    }

    public String getgPaymentCash() {
        return gPaymentCash;
    }

    public void setgPaymentCash(String gPaymentCash) {
        this.gPaymentCash = gPaymentCash;
    }

    public String getgPaymentCoin() {
        return gPaymentCoin;
    }

    public void setgPaymentCoin(String gPaymentCoin) {
        this.gPaymentCoin = gPaymentCoin;
    }

    public String getgPaymentScore() {
        return gPaymentScore;
    }

    public void setgPaymentScore(String gPaymentScore) {
        this.gPaymentScore = gPaymentScore;
    }

    public void setgId(String gId) {
        this.gId = gId;
    }

    public String getgOrgId() {
        return gOrgId;
    }

    public void setgOrgId(String gOrgId) {
        this.gOrgId = gOrgId;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public String getgShortName() {
        return gShortName;
    }

    public void setgShortName(String gShortName) {
        this.gShortName = gShortName;
    }

    public String getgStoreId() {
        return gStoreId;
    }

    public void setgStoreId(String gStoreId) {
        this.gStoreId = gStoreId;
    }

    public String getgTypeId() {
        return gTypeId;
    }

    public void setgTypeId(String gTypeId) {
        this.gTypeId = gTypeId;
    }

    public String getgTypeName() {
        return gTypeName;
    }

    public void setgTypeName(String gTypeName) {
        this.gTypeName = gTypeName;
    }

    public String getgBrand() {
        return gBrand;
    }

    public void setgBrand(String gBrand) {
        this.gBrand = gBrand;
    }

    public String getgCreateTime() {
        return gCreateTime;
    }

    public void setgCreateTime(String gCreateTime) {
        this.gCreateTime = gCreateTime;
    }

    public String getgUpdateTime() {
        return gUpdateTime;
    }

    public void setgUpdateTime(String gUpdateTime) {
        this.gUpdateTime = gUpdateTime;
    }

    public String getgMarketContent() {
        return gMarketContent;
    }

    public void setgMarketContent(String gMarketContent) {
        this.gMarketContent = gMarketContent;
    }

    public String getgProperty() {
        return gProperty;
    }

    public void setgProperty(String gProperty) {
        this.gProperty = gProperty;
    }

    public String getgTags() {
        return gTags;
    }

    public void setgTags(String gTags) {
        this.gTags = gTags;
    }

    public String getgWebPath() {
        return gWebPath;
    }

    public void setgWebPath(String gWebPath) {
        this.gWebPath = gWebPath;
    }

    public String getgMarketPrice() {
        return gMarketPrice;
    }

    public void setgMarketPrice(String gMarketPrice) {
        this.gMarketPrice = gMarketPrice;
    }

    public String getgShopPrice() {
        return gShopPrice;
    }

    public void setgShopPrice(String gShopPrice) {
        this.gShopPrice = gShopPrice;
    }

//	public String getgRedPrice() {
//		return gRedPrice;
//	}
//
//	public void setgRedPrice(String gRedPrice) {
//		this.gRedPrice = gRedPrice;
//	}

    public String getgOtherPrice() {
        return gOtherPrice;
    }

    public void setgOtherPrice(String gOtherPrice) {
        this.gOtherPrice = gOtherPrice;
    }

    public String getgLogisticsFee() {
        return gLogisticsFee;
    }

    public void setgLogisticsFee(String gLogisticsFee) {
        this.gLogisticsFee = gLogisticsFee;
    }

    public String getgLogisticsFeeType() {
        return gLogisticsFeeType;
    }

    public void setgLogisticsFeeType(String gLogisticsFeeType) {
        this.gLogisticsFeeType = gLogisticsFeeType;
    }

    public String getgPostFlag() {
        return gPostFlag;
    }

    public void setgPostFlag(String gPostFlag) {
        this.gPostFlag = gPostFlag;
    }

    public String getgItemMode() {
        return gItemMode;
    }

    public void setgItemMode(String gItemMode) {
        this.gItemMode = gItemMode;
    }

    public String getgStartTime() {
        return gStartTime;
    }

    public void setgStartTime(String gStartTime) {
        this.gStartTime = gStartTime;
    }

    public String getgStopTime() {
        return gStopTime;
    }

    public void setgStopTime(String gStopTime) {
        this.gStopTime = gStopTime;
    }

    public String getgWarmPrompt() {
        return gWarmPrompt;
    }

    public void setgWarmPrompt(String gWarmPrompt) {
        this.gWarmPrompt = gWarmPrompt;
    }

    public String getgGroupFlag() {
        return gGroupFlag;
    }

    public void setgGroupFlag(String gGroupFlag) {
        this.gGroupFlag = gGroupFlag;
    }

    public String getgIsValid() {
        return gIsValid;
    }

    public void setgIsValid(String gIsValid) {
        this.gIsValid = gIsValid;
    }

    public String getgIseckill() {
        return gIseckill;
    }

    public void setgIseckill(String gIseckill) {
        this.gIseckill = gIseckill;
    }

    public String getgIseckillPrice() {
        return gIseckillPrice;
    }

    public void setgIseckillPrice(String gIseckillPrice) {
        this.gIseckillPrice = gIseckillPrice;
    }

    public String getgRegionCodes() {
        return gRegionCodes;
    }

    public void setgRegionCodes(String gRegionCodes) {
        this.gRegionCodes = gRegionCodes;
    }

    public String getgUserLevels() {
        return gUserLevels;
    }

    public void setgUserLevels(String gUserLevels) {
        this.gUserLevels = gUserLevels;
    }

    public ItemPayment getItemPayment() {
        return itemPayment;
    }

    public void setItemPayment(ItemPayment itemPayment) {
        this.itemPayment = itemPayment;
    }

    public String getgSource() {
        return gSource;
    }

    public void setgSource(String gSource) {
        this.gSource = gSource;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MarketGoods getMarketGoodsProperty() {
        return marketGoodsProperty;
    }

    public void setMarketGoodsProperty(MarketGoods marketGoodsProperty) {
        this.marketGoodsProperty = marketGoodsProperty;
    }
}
