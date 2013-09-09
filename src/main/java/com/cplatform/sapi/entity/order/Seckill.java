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

@Entity
@Table(name = "T_MARKET_SECKILL")
public class Seckill {

	/** serialVersionUID */
	private static final long serialVersionUID = 8211551213374105178L;

	/**
	 * 
	 */

	private long id;

	private long goodsNo;

	private String goodsName;

	private BigDecimal goodsPrice;

	private BigDecimal seckillPrice;

	private String goodsPic1;

	private String goodsPic2;

	private String goodsPic3;

	private String goodsPic4;

	private Timestamp startTime;

	private Timestamp endTime;

	private Timestamp updateTime;

	private String status;

	private String imagePath;

	private long userId;

	private String orderId;

	private Date createTime;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "GOODS_NAME")
	@Basic
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	@Column(name = "GOODS_PRICE")
	@Basic
	public BigDecimal getGoodsPrice() {
		if (goodsPrice == null) {
			return new BigDecimal(0.00);
		}
		return goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	@Column(name = "SECKILL_PRICE")
	@Basic
	public BigDecimal getSeckillPrice() {
		if (seckillPrice == null) {
			return new BigDecimal(0.00);
		}
		return seckillPrice;
	}

	public void setSeckillPrice(BigDecimal seckillPrice) {
		this.seckillPrice = seckillPrice;
	}

	@Column(name = "GOODS_PIC1")
	@Basic
	public String getGoodsPic1() {
		return goodsPic1;
	}

	public void setGoodsPic1(String goodsPic1) {
		this.goodsPic1 = goodsPic1;
	}

	@Column(name = "GOODS_PIC2")
	@Basic
	public String getGoodsPic2() {
		return goodsPic2;
	}

	public void setGoodsPic2(String goodsPic2) {
		this.goodsPic2 = goodsPic2;
	}

	@Column(name = "GOODS_PIC3")
	@Basic
	public String getGoodsPic3() {
		return goodsPic3;
	}

	public void setGoodsPic3(String goodsPic3) {
		this.goodsPic3 = goodsPic3;
	}

	@Column(name = "GOODS_PIC4")
	@Basic
	public String getGoodsPic4() {
		return goodsPic4;
	}

	public void setGoodsPic4(String goodsPic4) {
		this.goodsPic4 = goodsPic4;
	}

	@Column(name = "START_TIME")
	@Basic
	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Column(name = "END_TIME")
	@Basic
	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Column(name = "UPDATE_TIME")
	@Basic
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "STATUS")
	@Basic
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "USER_ID")
	@Basic
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Column(name = "ORDER_ID")
	@Basic
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "CREATE_TIME")
	@Basic
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "IMAGEPATH")
	@Basic
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Column(name = "GOODS_NO")
	@Basic
	public long getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(long goodsNo) {
		this.goodsNo = goodsNo;
	}
}
