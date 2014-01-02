package com.cplatform.sapi.entity.p2;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Title. <br>
 * Description.
 * <p/>
 * Copyright: Copyright (c) 13-10-30 上午9:45
 * <p/>
 * Company: 北京宽连十方数字技术有限公司
 * <p/>
 * Author: nicky
 * <p/>
 * Version: 1.0
 * <p/>
 */
@JsonAutoDetect
public class ItemPayment implements Serializable {

    @JsonProperty("payType")
    private int payType;

    // 价格类型
    @JsonProperty("cashPayRatio")
    private int cashPayRatio;

    // 价格类型中文名
    @JsonProperty("otherPayRatio")
    private int otherPayRatio;

    @JsonProperty("billPay")
    private int billPay;

    private int deliveryPay;

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public int getCashPayRatio() {
        return cashPayRatio;
    }

    public void setCashPayRatio(int cashPayRatio) {
        this.cashPayRatio = cashPayRatio;
    }

    public int getOtherPayRatio() {
        return otherPayRatio;
    }

    public void setOtherPayRatio(int otherPayRatio) {
        this.otherPayRatio = otherPayRatio;
    }

    public int getBillPay() {
        return billPay;
    }

    public void setBillPay(int billPay) {
        this.billPay = billPay;
    }

    public int getDeliveryPay() {
        return deliveryPay;
    }

    public void setDeliveryPay(int deliveryPay) {
        this.deliveryPay = deliveryPay;
    }
}
