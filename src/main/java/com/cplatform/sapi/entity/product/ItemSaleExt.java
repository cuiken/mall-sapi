package com.cplatform.sapi.entity.product;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * 基于发布商品表的附加其它属性表 Title. <br>
 * Description.
 * <p>
 * Copyright: Copyright (c) 2013-6-20 下午4:22:36
 * <p>
 * Company: 北京宽连十方数字技术有限公司
 * <p>
 * Author: baibo@c-platform.com
 * <p>
 * Version: 1.0
 * <p>
 */

@Entity
@Table(name = "T_ITEM_SALE_EXT")
public class ItemSaleExt {

	private Long id;

	/** 和t_item_sale匹配 **/
//	private Long saleId;
    private ItemSale itemSale;
	/** 销售数量 **/
	private Long saleNum;

	/** 人气数 **/
	private Long clickNum;

	/** 评论量 **/
	private Long commentNum;

	/** 购买人数 **/
	private Long userNum;

	/** 收藏数量 **/
	private Long collectNum;

	/** 商品评分 **/
	private Double rank;

	/** 物流运费 **/
	private Double logisticsFee;

	/** 物流计算方式 0-不累计1-按数量 **/
	private Long logisticsFeeType;

	public ItemSaleExt() {

	}

	/**
	 * 获取 id
	 * 
	 * @return id
	 */
	@SequenceGenerator(name = "seq_item_param", sequenceName = "SEQ_SYS_ITEM_PARAM_ID")
	@Id
	@GeneratedValue(generator = "seq_item_param")
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
//
//	@Column(name = "SALE_ID", precision = 8, scale = 0)
//	public Long getSaleId() {
//		return saleId;
//	}
//
//	public void setSaleId(Long saleId) {
//		this.saleId = saleId;
//	}

    @OneToOne
    @JoinColumn(name="SALE_ID")
    public ItemSale getItemSale() {
        return itemSale;
    }

    public void setItemSale(ItemSale itemSale) {
        this.itemSale = itemSale;
    }

    @Column(name = "SALE_NUM", precision = 8, scale = 0)
	public Long getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Long saleNum) {
		this.saleNum = saleNum;
	}

	@Column(name = "CLICK_NUM", precision = 8, scale = 0)
	public Long getClickNum() {
		return clickNum;
	}

	public void setClickNum(Long clickNum) {
		this.clickNum = clickNum;
	}

	@Column(name = "COMMENT_NUM", precision = 8, scale = 0)
	public Long getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Long commentNum) {
		this.commentNum = commentNum;
	}

	@Column(name = "USER_NUM", precision = 8, scale = 0)
	public Long getUserNum() {
		return userNum;
	}

	public void setUserNum(Long userNum) {
		this.userNum = userNum;
	}

	@Column(name = "COLLECT_NUM", precision = 8, scale = 0)
	public Long getCollectNum() {
		return collectNum;
	}

	public void setCollectNum(Long collectNum) {
		this.collectNum = collectNum;
	}

	@Column(name = "RANK", precision = 9, scale = 2)
	public Double getRank() {
		return rank;
	}

	public void setRank(Double rank) {
		this.rank = rank;
	}

	@Column(name = "LOGISTICS_FEE", precision = 9, scale = 2)
	public Double getLogisticsFee() {
		return logisticsFee;
	}

	public void setLogisticsFee(Double logisticsFee) {
		this.logisticsFee = logisticsFee;
	}

	@Column(name = "LOGISTICS_FEE_TYPE", precision = 1, scale = 0)
	public Long getLogisticsFeeType() {
		return logisticsFeeType;
	}

	public void setLogisticsFeeType(Long logisticsFeeType) {
		this.logisticsFeeType = logisticsFeeType;
	}

}
