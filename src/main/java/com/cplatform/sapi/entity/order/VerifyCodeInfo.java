package com.cplatform.sapi.entity.order;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * User: ken.cui
 * Date: 13-12-19
 * Time: 上午10:59
 */
@Entity
@Table(name = "t_verify_code_info")
public class VerifyCodeInfo {

    private String orderId;
    private Long itemOrderId;
    private Long actOrderId;
    private String code;
    private String code2d;
    private Long codeStatus;
    private String transDate;
    private String validDate;
    private String expireDate;
    private Long validTimes;
    private Long remainTimes;
    private String terminalId;
    private String itemId;
    private String itemName;
    private Long itemQuantity;
    private Long totalPrice;
    private String platformId;
    private String storeId;
    private String smsContent;

    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "assigned")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getItemOrderId() {
        return itemOrderId;
    }

    public void setItemOrderId(Long itemOrderId) {
        this.itemOrderId = itemOrderId;
    }

    public Long getActOrderId() {
        return actOrderId;
    }

    public void setActOrderId(Long actOrderId) {
        this.actOrderId = actOrderId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name="code_2d")
    public String getCode2d() {
        return code2d;
    }

    public void setCode2d(String code2d) {
        this.code2d = code2d;
    }

    public Long getCodeStatus() {
        return codeStatus;
    }

    public void setCodeStatus(Long codeStatus) {
        this.codeStatus = codeStatus;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public Long getValidTimes() {
        return validTimes;
    }

    public void setValidTimes(Long validTimes) {
        this.validTimes = validTimes;
    }

    public Long getRemainTimes() {
        return remainTimes;
    }

    public void setRemainTimes(Long remainTimes) {
        this.remainTimes = remainTimes;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Long itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
