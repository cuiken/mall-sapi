package com.cplatform.sapi.entity.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * 存放商品的各类价格信息 Title. <br>
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
@Table(name = "T_ITEM_PRICE")
@Entity
public class ItemPrice {

    private Long id;

    /**
     * 省份 *
     */
    private String province;

    /**
     * 地市 *
     */
    private String areaCode;

    /**
     * 商户ID *
     */
    private Long storeId;

    /**
     * 商品ID *
     */
    private Long itemId;

    /**
     * 来源:T_ITEM_PRICE_TYPE *
     */
	private String priceTypeCode;

//    private ItemPriceType itemPriceType;

    /**
     * 商品价格 *
     */
    private Float price;

    /**
     * 备注 *
     */
    private String remark;

    /**
     * 发布商品ID *
     */
//	private Long saleId;
    private ItemSale itemSale;

    private String priceType;

    private String regionName;

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

    @Column(name = "PROVINCE", length = 20)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Column(name = "AREA_CODE", length = 20)
    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    @Column(name = "STORE_ID", precision = 9, scale = 0)
    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    @Column(name = "ITEM_ID", precision = 9, scale = 0)
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

	@Column(name = "PRICE_TYPE_CODE", length = 20)
	public String getPriceTypeCode() {
		return priceTypeCode;
	}

	public void setPriceTypeCode(String priceTypeCode) {
		this.priceTypeCode = priceTypeCode;
	}

//    @OneToOne(mappedBy = "itemPrice", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
//    public ItemPriceType getItemPriceType() {
//        return itemPriceType;
//    }
//
//    public void setItemPriceType(ItemPriceType itemPriceType) {
//        this.itemPriceType = itemPriceType;
//    }

    @Column(name = "PRICE", precision = 11, scale = 2)
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Column(name = "REMARK", length = 500)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

//	@Column(name = "SALE_ID", precision = 9, scale = 0)
//    public Long getSaleId() {
//    	return saleId;
//    }
//
//
//    public void setSaleId(Long saleId) {
//    	this.saleId = saleId;
//    }

    @ManyToOne
    @JoinColumn(name = "SALE_ID")
    public ItemSale getItemSale() {
        return itemSale;
    }

    public void setItemSale(ItemSale itemSale) {
        this.itemSale = itemSale;
    }

    @Transient
    public String getPriceType() {
        return priceType;
    }

    @Transient
    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    @Transient
    public String getRegionName() {
        return regionName;
    }

    @Transient
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }


}
