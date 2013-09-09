package com.cplatform.sapi.entity.order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "T_MARKET_ORDER")
public class MarketOrder {

	/**
	 * 
	 */
	private long id;

	private long goodsNo;

	private int auctionQuantity;

	private long userId;

	private BigDecimal auctionPrice;

	private Timestamp auctionTime;

	private String productId;

	private String goodsName;

	private BigDecimal productPrice;

	private String auctionTimeString;

	private int status;

	private Date createTime;

	private String updateTime;

	private int delFlag;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "GOODS_NO")
	@Basic
	public long getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(long goodsNo) {
		this.goodsNo = goodsNo;
	}

	@Column(name = "OPERATOR_ID")
	@Basic
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Column(name = "AUCTION_PRICE")
	@Basic
	public BigDecimal getAuctionPrice() {
		return auctionPrice;
	}

	public void setAuctionPrice(BigDecimal auctionPrice) {
		this.auctionPrice = auctionPrice;
	}

	@Column(name = "AUCTION_TIME")
	@Basic
	public Timestamp getAuctionTime() {
		return auctionTime;
	}

	public void setAuctionTime(Timestamp auctionTime) {
		this.auctionTime = auctionTime;
	}

	@Transient
	public String getAuctionTimeString() {
		return auctionTimeString;
	}

	public void setAuctionTimeString(String auctionTimeString) {
		this.auctionTimeString = auctionTimeString;
	}

	@Column(name = "AUCTION_QUANTITY")
	@Basic
	public int getAuctionQuantity() {
		return auctionQuantity;
	}

	public void setAuctionQuantity(int auctionQuantity) {
		this.auctionQuantity = auctionQuantity;
	}

	@Column(name = "PRODUCT_ID")
	@Basic
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Column(name = "PRODUCT_Name")
	@Basic
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@Column(name = "PRODUCT_PRICE")
	@Basic
	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	@Column(name = "STATUS")
	@Basic
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "CREATE_TIME")
	@Basic
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_TIME")
	@Basic
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "DEL_FLAG")
	@Basic
	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

}
