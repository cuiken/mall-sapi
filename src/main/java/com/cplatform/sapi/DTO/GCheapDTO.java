package com.cplatform.sapi.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

/**
 * 标题、简要说明. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2013-8-30 上午8:59:23
 * <p>
 * Company: 北京宽连十方数字技术有限公司
 * <p>
 * 
 * @author yangxm@c-platform.com
 * @version 1.0.0
 */
public class GCheapDTO {

	@JsonProperty("TOTAL_ROW")
	private int totalRow;

	@JsonProperty("DATA")
	private List<Data> data = Lists.newArrayList();

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

	public static class Data {

		@JsonProperty("businessId")
		private Long businessId;

		@JsonProperty("orderId")
		private Long orderId;

		@JsonProperty("storeId")
		private Long storeId;

		@JsonProperty("storeName")
		private String storeName;

		@JsonProperty("goodsId")
		private Long goodsId;

		@JsonProperty("goodsName")
		private String goodsName;

		@JsonProperty("price")
		private String price;

		@JsonProperty("status")
		private Integer status;

		@JsonProperty("imgPath")
		private String imgPath;

		@JsonProperty("buyTime")
		private String buyTime;

		@JsonProperty("createTime")
		private String createTime;

		@JsonProperty("payTime")
		private String payTime;

		public Long getBusinessId() {
			return businessId;
		}

		public void setBusinessId(Long businessId) {
			this.businessId = businessId;
		}

		public Long getOrderId() {
			return orderId;
		}

		public void setOrderId(Long orderId) {
			this.orderId = orderId;
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

		public Long getGoodsId() {
			return goodsId;
		}

		public void setGoodsId(Long goodsId) {
			this.goodsId = goodsId;
		}

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public String getImgPath() {
			return imgPath;
		}

		public void setImgPath(String imgPath) {
			this.imgPath = imgPath;
		}

		public String getBuyTime() {
			return buyTime;
		}

		public void setBuyTime(String buyTime) {
			this.buyTime = buyTime;
		}

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public String getGoodsName() {
			return goodsName;
		}

		public void setGoodsName(String goodsName) {
			this.goodsName = goodsName;
		}

		public String getPayTime() {
			return payTime;
		}

		public void setPayTime(String payTime) {
			this.payTime = payTime;
		}

	}
}
