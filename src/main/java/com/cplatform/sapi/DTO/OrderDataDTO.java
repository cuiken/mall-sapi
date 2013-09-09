package com.cplatform.sapi.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User: cuikai Date: 13-8-15 Time: 上午11:24
 */
public class OrderDataDTO {

	@JsonProperty("FLAG")
	private String flag;
	
	@JsonProperty("MSG")
	private String msg;
	
	@JsonProperty("ORDER_ID")
	private Long orderId;

	@JsonProperty("FARE")
	private int fare;

	@JsonProperty("STATUS")
	private int status;

	@JsonProperty("CREATE_TIME")
	private String orderTime;
	
	@JsonProperty("AMOUNT")
	private double amount;
	
	@JsonProperty("PAY_STATUS")
	private int payStatus;
	
	@JsonProperty("PAY_DESCRIPTION")
	private String payDescription;
	
	@JsonProperty("GOODS_INFOS")
	private List<GoodsInfoDTO> goodsInfoDTOs;
	
	@JsonProperty("PAYMENTS")
	private List<PaymentDTO> paymentDTOs;
	
	@JsonProperty("PAY_TIME")
	private String payTime;

	@JsonProperty("EXPRESS_NAME")
	private String expressName;
	
	@JsonProperty("EXPRESS_ID")
	private long expressId;
	
	@JsonProperty("EXPRESS_CODE")
	private String expressCode;
	
	@JsonProperty("DELIVERY_NAME")
	private String deliveryName;
	
	@JsonProperty("DELIVERY_PHONE")
	private String deliveryPhone;
	
	@JsonProperty("ADDRESS")
	private String address;
	
	@JsonProperty("SUBJECT")
	private String subject;
	
	public String getFlag() {
		return flag == null ? "0" : flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg == null ? "操作成功" : msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public int getFare() {
		return fare;
	}

	public void setFare(int fare) {
		this.fare = fare;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public long getExpressId() {
		return expressId;
	}

	public void setExpressId(long expressId) {
		this.expressId = expressId;
	}

	public String getExpressCode() {
		return expressCode;
	}

	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}
	
	public int getPayStatus() {
		return payStatus;
	}
	
	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}
	
	public String getPayDescription() {
		return payDescription;
	}
	
	public void setPayDescription(String payDescription) {
		this.payDescription = payDescription;
	}
	
	public List<GoodsInfoDTO> getGoodsInfoDTOs() {
		return goodsInfoDTOs;
	}
	
	public void setGoodsInfoDTOs(List<GoodsInfoDTO> goodsInfoDTOs) {
		this.goodsInfoDTOs = goodsInfoDTOs;
	}
	
	public List<PaymentDTO> getPaymentDTOs() {
		return paymentDTOs;
	}
	
	public void setPaymentDTOs(List<PaymentDTO> paymentDTOs) {
		this.paymentDTOs = paymentDTOs;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getDeliveryName() {
		return deliveryName;
	}
	
	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}
	
	public String getDeliveryPhone() {
		return deliveryPhone;
	}
	
	public void setDeliveryPhone(String deliveryPhone) {
		this.deliveryPhone = deliveryPhone;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
}
