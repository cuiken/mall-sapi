package com.cplatform.sapi.entity.p2;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 
 * Title.商品价格配置 <br>
 * Description.
 * <p>
 * Copyright: Copyright (c) 2013-7-16 下午02:00:22
 * <p>
 * Company: 北京宽连十方数字技术有限公司
 * <p>
 * Author: macl@c-platform.com
 * <p>
 * Version: 1.0
 * <p>
 */
@JsonAutoDetect
public class ItemPriceInfo implements Serializable {
	
	@JsonProperty("saleId")
	private String saleId;
	
	// 价格类型
	@JsonProperty("priceTypeCode")
	private String priceTypeCode;
	
	// 价格类型中文名
	@JsonProperty("priceType")
	private String priceType;
	
	@JsonProperty("price")
	private String price;

	public String getSaleId() {
		return saleId;
	}

	public void setSaleId(String saleId) {
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
