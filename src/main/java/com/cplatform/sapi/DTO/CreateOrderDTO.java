package com.cplatform.sapi.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * 客户端下单数据集
 * User: cuikai
 * Date: 13-8-22
 * Time: 上午10:10
 */
public class CreateOrderDTO {

    @JsonProperty("U_ID")
    private Long userId;

    @JsonProperty("GOOD_ID")
    private Long goodsId;

    @JsonProperty("PAY_TYPE")
    private String payType;

    @JsonProperty("COUNT")
    private int count;

    @JsonProperty("ADDRESS_ID")
    private Long addressId;

    @JsonProperty("USER_REMARK")
    private String userRemark;

    @JsonProperty("INVOICE_TYPE")
    private int invoiceType;

    @JsonProperty("INVOICE_SUBJECT")
    private String invoiceSubject;

    @JsonProperty("INVOICE_CONTENT")
    private String invoiceContent;


    public int getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(int invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceSubject() {
        return invoiceSubject;
    }

    public void setInvoiceSubject(String invoiceSubject) {
        this.invoiceSubject = invoiceSubject;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getUserRemark() {
        return userRemark;
    }

    public void setUserRemark(String userRemark) {
        this.userRemark = userRemark;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
