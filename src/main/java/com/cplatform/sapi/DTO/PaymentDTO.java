package com.cplatform.sapi.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentDTO {

	@JsonProperty("AMOUNT")
	private String amount;

	@JsonProperty("CURRENCY")
	private String currency;

	/**
	 * 获取 amount
	 *
	 * @return amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * 设置 amount
	 *
	 * @param amount
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * 获取 currency
	 *
	 * @return currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * 设置 currency
	 *
	 * @param currency
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	

}
