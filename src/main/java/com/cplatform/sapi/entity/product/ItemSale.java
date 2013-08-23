package com.cplatform.sapi.entity.product;

import com.cplatform.sapi.entity.order.TActOrderGoods;
import com.cplatform.sapi.entity.profile.MemberFavorite;
import com.cplatform.sapi.entity.profile.TItemComment;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基于商品基本属性所发布销售的商品表 Title. <br>
 * Description.
 * <p/>
 * Copyright: Copyright (c) 2013-6-1 下午5:42:53
 * <p/>
 * Company: 北京宽连十方数字技术有限公司
 * <p/>
 * Author: baibo@c-platform.com
 * <p/>
 * Version: 1.0
 * <p/>
 */
@Table(name = "T_ITEM_SALE")
@Entity
public class ItemSale {

    /**
     * 不限库存，库存初始值 *
     */
    public static final Long INIT_STOCK_NUM = -1000l;

    /**
     * 草稿
     */
    public static Long STATUS_BASE_SAVED = 0l;

    /**
     * 待审核
     */
    public static Long STATUS_BASE_NO_AUDIT = 1l;

    /**
     * 审核中
     */
    public static Long STATUS_BASE_AUDITING = 2l;

    /**
     * 基本资料审核通过
     */
    public static Long STATUS_BASE_AUDIT_PASS = 3l;

    /**
     * 基本资料审核驳回
     */
    public static Long STATUS_BASE_AUDIT_RETJECT = 4l;

    private static Map<Long, String> verifyCodeTypeMap = null;

    static {
        verifyCodeTypeMap = new HashMap<Long, String>();
        verifyCodeTypeMap.put(1l, "一维码");
        verifyCodeTypeMap.put(2l, "二维码");
    }

    private static Map<Long, String> sendCodeModeMap = null;

    static {
        sendCodeModeMap = new HashMap<Long, String>();
        sendCodeModeMap.put(0l, "不发码");
        sendCodeModeMap.put(1l, "按照订单发码");
        sendCodeModeMap.put(2l, "按照商品个数发码");
    }

    public static Map<Long, String> statusMap = null;

    static {
        statusMap = new HashMap<Long, String>();
        // statusMap.put(-1l, "删除");
        statusMap.put(0l, "草稿");
        statusMap.put(1l, "待审核");
        statusMap.put(2l, "审核中");
        statusMap.put(3l, "审核通过");
        statusMap.put(4l, "审核驳回");
        // statusMap.put(5l, "发布草稿");
        // statusMap.put(6l, "发布审核中");
        // statusMap.put(7l, "发布审核中");
        // statusMap.put(8l, "发布审核通过");
        // statusMap.put(9l, "发布审核驳回");
    }

    private static Map<Long, String> syncGyFlagMap = null;

    static {
        syncGyFlagMap = new HashMap<Long, String>();
        syncGyFlagMap.put(0L, "未同步");
        syncGyFlagMap.put(1L, "同步成功");
        syncGyFlagMap.put(2L, "同步失败");
    }

    public static Map<Long, String> itemModeMap = null;

    static {
        itemModeMap = new HashMap<Long, String>();
        itemModeMap.put(0l, "实物");
        itemModeMap.put(1l, "虚拟物");
    }

    public static Map<Long, String> groupFlagMap = null;

    static {
        groupFlagMap = new HashMap<Long, String>();
        groupFlagMap.put(0l, "普通商品");
        groupFlagMap.put(1l, "优惠套餐");
    }

    public static Map<Long, String> virtualTypeMap = null;

    static {
        virtualTypeMap = new HashMap<Long, String>();
        virtualTypeMap.put(1l, "卡密");
        virtualTypeMap.put(2l, "直充");
    }

    public static Map<Long, String> isValidMap = null;

    static {
        isValidMap = new HashMap<Long, String>();
        isValidMap.put(0l, "下架 ");
        isValidMap.put(1l, "上架");
    }

    public static Map<Long, String> iseckillMap = null;

    static {
        iseckillMap = new HashMap<Long, String>();
        iseckillMap.put(1L, "秒杀");
        iseckillMap.put(0L, "不秒杀");
    }

    @Transient
    public String getIseckillName() {

        return iseckillMap.get(this.getIseckill());

    }

    private String shelfTitles;

    @Transient
    public String getShelfTitles() {
        return shelfTitles;
    }

    public void setShelfTitles(String shelfTitles) {
        this.shelfTitles = shelfTitles;
    }

    @Transient
    public String getIsValidName() {
        return isValidMap.get(this.isValid);
    }

    @Transient
    public String getVirtualTypeName() {
        return virtualTypeMap.get(this.virtualType);
    }

    @Transient
    public String getGroupFlagName() {
        return groupFlagMap.get(this.groupFlag);
    }

    @Transient
    public String getItemModeName() {
        return itemModeMap.get(this.itemMode);
    }

    @Transient
    public String getSyncGyFlagName() {
        return syncGyFlagMap.get(this.getSyncGyFlag());
    }

    @Transient
    public String getVerifyCodeTypeName() {
        return verifyCodeTypeMap.get(this.verifyCodeType);
    }

    @Transient
    public String getSendCodeModeName() {
        return sendCodeModeMap.get(this.sendCodeMode);
    }

    @Transient
    public String getStatusName() {
        return statusMap.get(this.status);
    }

    private Long id;

    /**
     * 商品ID和t_item_org匹配 *
     */
    private Long orgId;

    /**
     * 支付体系价格ID *
     */
    private Long salePriceId;

    /**
     * 码类型1--维码 2--二维码 *
     */
    private Long verifyCodeType;

    /**
     * 发码方式0--不发码 1--按照订单发码 2--按照商品个数发码 *
     */
    private Long sendCodeMode;

    /**
     * 制码方0--平台自己 1--方正码平台 2--第三方应用 *
     */
    private Long sendCodeChannel;

    /**
     * 制码渠道如果制码方选择第三方应用，则该字段有用 10--85度C 11--鲜芋仙 ... *
     */
    private Long sendCodeSrc;

    /**
     * 是否需要物流配送 0-不需要物流配送 1-需要物流配送 *
     */
    private Long postFlag;

    /**
     * 销售有效开始时间 *
     */
    private String saleStartTime;

    /**
     * 销售有效结束时间 *
     */
    private String saleStopTime;

    /**
     * 验证有效开始时间 *
     */
    private String verifyStartTime;

    /**
     * 验证有效结束时间 *
     */
    private String verifyStopTime;

    /**
     * 商品库存数量 *
     */
    private Long stockNum;

    /**
     * 单个用户购买数量 0不限制 *
     */
    private Long userPerBuyNum;

    /**
     * 商品状态0--草稿 1--待审核 2--审核中 3--审核通过 4--审核驳回 *
     */
    private Long status;

    /**
     * 商品是否有效 0--无效 1--有效 *
     */
    private Long isValid;

    /**
     * 是否同步高阳0--未成功同步 1--成功同步 *
     */
    private Long syncGyFlag;

    /**
     * "1--业务门店2--商户3--渠道商" *
     */
    private Long shopClass;

    /**
     * 结算商户ID匹配表t_store *
     */
    private Long storeId;

    /**
     * 市场价 *
     */
    private Float marketPrice;

    /**
     * 商品类型0--实物 1--虚拟物 *
     */
    private Long itemMode;

    /**
     * 商品分类 id *
     */
    private Long typeId;

    /**
     * 0-普通商品1-优惠套餐" *
     */
    private Long groupFlag;

    /**
     * 是否虚拟商品 0-否 1-是
     */
    private Long virtualFlag;

    /**
     * 虚拟商品类型1-卡密 2-直充
     */
    private Long virtualType;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品简称
     */
    private String shortName;

    /**
     * 温馨提示
     */
    private String warmPrompt;

    /**
     * 商品介绍
     */
    private String remark;

    /**
     * 商户用户id
     */
    private Long shopUserId;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 重量
     */
    private Double weight;

    /**
     * 更新时间
     */
    private String createTime;

    /**
     * 创建人
     */
    private Long createUserId;

    /**
     * 更新人
     */
    private Long updateUserId;

    /**
     * 营销语
     */
    private String marketContent;

    /**
     * 商城价
     */
    private Double shopPrice;

    /**
     * 封面图路径
     */
    private String imgPath;

    /**
     * 结算价
     */
    private Double settlePrice;

    /**
     * 验证天数 *
     */
    private Long verifyDay;

    /**
     * 商品是否显示 0，不现实；1显示 *
     */
    private Long isView;

    private Long allowCash;
    private Long allowCoin;
    private Long allowScore;

    /**
     * 是否秒杀*
     */
    private Long iseckill;

    private BigDecimal iseckillPrice;

    private List<MemberFavorite> memberFavorites = Lists.newArrayList();

    private List<TItemComment> comments = Lists.newArrayList();

    private List<SysFileImg> sysFileImgs = Lists.newArrayList();

    private List<TActOrderGoods> orderGoodses = Lists.newArrayList();

    @Column(name = "IS_VIEW", precision = 1, scale = 0)
    public Long getIsView() {
        return isView;
    }

    public void setIsView(Long isView) {
        this.isView = isView;
    }

    @Column(name = "MARKET_PRICE", precision = 9, scale = 2)
    public Float getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Float marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
     * 获取 id
     *
     * @return id
     */
    @SequenceGenerator(name = "seq_item", sequenceName = "SEQ_SYS_ITEM_ID")
    @Id
    @GeneratedValue(generator = "seq_item")
    @JsonProperty
    public Long getId() {
        return id;
    }

    /**
     * 设置 id
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ORG_ID", precision = 8, scale = 0)
    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    @Column(name = "SALE_PRICE_ID", precision = 8, scale = 0)
    public Long getSalePriceId() {
        return salePriceId;
    }

    public void setSalePriceId(Long salePriceId) {
        this.salePriceId = salePriceId;
    }

    @Column(name = "VERIFY_CODE_TYPE", precision = 1, scale = 0)
    public Long getVerifyCodeType() {
        return verifyCodeType;
    }

    public void setVerifyCodeType(Long verifyCodeType) {
        this.verifyCodeType = verifyCodeType;
    }

    @Column(name = "SEND_CODE_MODE", precision = 1, scale = 0)
    public Long getSendCodeMode() {
        return sendCodeMode;
    }

    public void setSendCodeMode(Long sendCodeMode) {
        this.sendCodeMode = sendCodeMode;
    }

    @Column(name = "SEND_CODE_CHANNEL", precision = 1, scale = 0)
    public Long getSendCodeChannel() {
        return sendCodeChannel;
    }

    public void setSendCodeChannel(Long sendCodeChannel) {
        this.sendCodeChannel = sendCodeChannel;
    }

    @Column(name = "SEND_CODE_SRC", precision = 1, scale = 0)
    public Long getSendCodeSrc() {
        return sendCodeSrc;
    }

    public void setSendCodeSrc(Long sendCodeSrc) {
        this.sendCodeSrc = sendCodeSrc;
    }

    @Column(name = "POST_FLAG", precision = 1, scale = 0)
    public Long getPostFlag() {
        return postFlag;
    }

    public void setPostFlag(Long postFlag) {
        this.postFlag = postFlag;
    }

    @Column(name = "SALE_START_TIME", length = 14)
    public String getSaleStartTime() {
        return saleStartTime;
    }

    public void setSaleStartTime(String saleStartTime) {
        this.saleStartTime = saleStartTime;
    }

    @Column(name = "SALE_STOP_TIME", length = 14)
    public String getSaleStopTime() {
        return saleStopTime;
    }

    public void setSaleStopTime(String saleStopTime) {
        this.saleStopTime = saleStopTime;
    }

    @Column(name = "VERIFY_START_TIME", length = 14)
    public String getVerifyStartTime() {
        return verifyStartTime;
    }

    public void setVerifyStartTime(String verifyStartTime) {
        this.verifyStartTime = verifyStartTime;
    }

    @Column(name = "VERIFY_STOP_TIME", length = 14)
    public String getVerifyStopTime() {
        return verifyStopTime;
    }

    public void setVerifyStopTime(String verifyStopTime) {
        this.verifyStopTime = verifyStopTime;
    }

    @Column(name = "STOCK_NUM", precision = 8, scale = 0)
    public Long getStockNum() {
        return stockNum;
    }

    public void setStockNum(Long stockNum) {
        this.stockNum = stockNum;
    }

    @Column(name = "USER_PER_BUY_NUM", precision = 8, scale = 0)
    public Long getUserPerBuyNum() {
        return userPerBuyNum;
    }

    public void setUserPerBuyNum(Long userPerBuyNum) {
        this.userPerBuyNum = userPerBuyNum;
    }

    @Column(name = "STATUS", precision = 1, scale = 0)
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Column(name = "IS_VALID", precision = 1, scale = 0)
    public Long getIsValid() {
        return isValid;
    }

    public void setIsValid(Long isValid) {
        this.isValid = isValid;
    }

    @Column(name = "SYNC_GY_FLAG", precision = 1, scale = 0)
    public Long getSyncGyFlag() {
        return syncGyFlag;
    }

    public void setSyncGyFlag(Long syncGyFlag) {
        this.syncGyFlag = syncGyFlag;
    }

    @Column(name = "SHOP_CLASS", precision = 1, scale = 0)
    public Long getShopClass() {
        return shopClass;
    }

    public void setShopClass(Long shopClass) {
        this.shopClass = shopClass;
    }

    @Column(name = "STORE_ID", precision = 1, scale = 0)
    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    @Column(name = "ITEM_MODE", precision = 1, scale = 0)
    public Long getItemMode() {
        return itemMode;
    }

    public void setItemMode(Long itemMode) {
        this.itemMode = itemMode;
    }

    @Column(name = "TYPE_ID", precision = 8, scale = 0)
    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    @Column(name = "GROUP_FLAG", precision = 1, scale = 0)
    public Long getGroupFlag() {
        return groupFlag;
    }

    public void setGroupFlag(Long groupFlag) {
        this.groupFlag = groupFlag;
    }

    @Column(name = "VIRTUAL_FLAG", precision = 1, scale = 0)
    public Long getVirtualFlag() {
        return virtualFlag;
    }

    public void setVirtualFlag(Long virtualFlag) {
        this.virtualFlag = virtualFlag;
    }

    @Column(name = "VIRTUAL_TYPE", precision = 1, scale = 0)
    public Long getVirtualType() {
        return virtualType;
    }

    public void setVirtualType(Long virtualType) {
        this.virtualType = virtualType;
    }

    @Column(name = "NAME", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "SHORT_NAME", length = 100)
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Column(name = "WARM_PROMPT", length = 200)
    public String getWarmPrompt() {
        return warmPrompt;
    }

    public void setWarmPrompt(String warmPrompt) {
        this.warmPrompt = warmPrompt;
    }

    @Lob
    @Column(name = "REMARK", nullable = true)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "SHOP_USER_ID", precision = 8, scale = 0)
    public Long getShopUserId() {
        return shopUserId;
    }

    public void setShopUserId(Long shopUserId) {
        this.shopUserId = shopUserId;
    }

    @Column(name = "UPDATE_TIME", length = 14)
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "BRAND", length = 20)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "WEIGHT", precision = 8, scale = 2)
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Column(name = "CREATE_TIME", length = 14)
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Column(name = "CREATE_USER_ID", precision = 9, scale = 0)
    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public BigDecimal getIseckillPrice() {
        return iseckillPrice;
    }

    public void setIseckillPrice(BigDecimal iseckillPrice) {
        this.iseckillPrice = iseckillPrice;
    }

    @Column(name = "UPDATE_USER_ID", precision = 9, scale = 0)
    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    @Column(name = "MARKET_CONTENT", length = 200)
    public String getMarketContent() {
        return marketContent;
    }

    public void setMarketContent(String marketContent) {
        this.marketContent = marketContent;
    }

    @Column(name = "SHOP_PRICE", precision = 9, scale = 2)
    public Double getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(Double shopPrice) {
        this.shopPrice = shopPrice;
    }

    @Column(name = "IMG_PATH", length = 200)
    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Column(name = "SETTLE_PRICE", precision = 9, scale = 2)
    public Double getSettlePrice() {
        return settlePrice;
    }

    public void setSettlePrice(Double settlePrice) {
        this.settlePrice = settlePrice;
    }

    @Column(name = "VERIFY_DAY", precision = 5, scale = 0)
    public Long getVerifyDay() {
        return verifyDay;
    }

    public void setVerifyDay(Long verifyDay) {
        this.verifyDay = verifyDay;
    }

    /**
     * 配送区域
     */
    private String postArea;

    @Column(name = "ISECKILL")
    public Long getIseckill() {
        return iseckill;
    }

    public void setIseckill(Long iseckill) {
        this.iseckill = iseckill;
    }

    @OneToMany(mappedBy = "itemSale", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    public List<MemberFavorite> getMemberFavorites() {
        return memberFavorites;
    }

    public void setMemberFavorites(List<MemberFavorite> memberFavorites) {
        this.memberFavorites = memberFavorites;
    }

    @OneToMany(mappedBy = "itemSale", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    public List<TItemComment> getComments() {
        return comments;
    }

    public void setComments(List<TItemComment> comments) {
        this.comments = comments;
    }

    @OneToMany(mappedBy = "itemSale", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    public List<SysFileImg> getSysFileImgs() {
        return sysFileImgs;
    }

    public void setSysFileImgs(List<SysFileImg> sysFileImgs) {
        this.sysFileImgs = sysFileImgs;
    }

    @OneToMany(mappedBy = "itemSale", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    public List<TActOrderGoods> getOrderGoodses() {
        return orderGoodses;
    }

    public void setOrderGoodses(List<TActOrderGoods> orderGoodses) {
        this.orderGoodses = orderGoodses;
    }

    @Column(name = "CASH_IDGOODS")
    public Long getAllowCash() {
        return allowCash;
    }

    public void setAllowCash(Long allowCash) {
        this.allowCash = allowCash;
    }

    @Column(name = "COIN_IDGOODS")
    public Long getAllowCoin() {
        return allowCoin;
    }

    public void setAllowCoin(Long allowCoin) {
        this.allowCoin = allowCoin;
    }

    @Column(name = "SCORE_IDGOODS")
    public Long getAllowScore() {
        return allowScore;
    }

    public void setAllowScore(Long allowScore) {
        this.allowScore = allowScore;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
