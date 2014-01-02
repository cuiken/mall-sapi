package com.cplatform.sapi.DTO;

import com.cplatform.sapi.entity.order.VerifyCodeInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.util.List;

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
    private BigDecimal fare;

    @JsonProperty("STATUS")
    private int status;

    @JsonProperty("CREATE_TIME")
    private String orderTime;
    @JsonProperty("CLOSE_TIME")
    private String closeTime;

    @JsonProperty("AMOUNT")
    private BigDecimal amount;

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
    private String expressId;

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
    private List<VerifyCodeInfo> verifyCodeInfos = Lists.newArrayList();

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


    public BigDecimal getFare() {
        return fare;
    }

    public void setFare(BigDecimal fare) {
        this.fare = fare;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
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

    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
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

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public List<VerifyCodeInfo> getVerifyCodeInfos() {
        return verifyCodeInfos;
    }

    public void setVerifyCodeInfos(List<VerifyCodeInfo> verifyCodeInfos) {
        this.verifyCodeInfos = verifyCodeInfos;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
