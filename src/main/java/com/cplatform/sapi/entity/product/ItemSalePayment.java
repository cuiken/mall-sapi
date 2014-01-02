package com.cplatform.sapi.entity.product;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: cuikai
 * Date: 13-10-29
 * Time: 上午9:29
 */
@Entity
@Table(name = "T_ITEM_SALE_PAYMENT")
public class ItemSalePayment {
    private Long id;
    private Long itemId;
    private Long payType;
    private Long cashPayRatio;
    private Long otherPayRatio;
    private Long billPay;

    @Id
    @SequenceGenerator(name = "uuid", sequenceName = "SEQ_ITEM_PAYMENT")
    @GeneratedValue(generator = "uuid")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getPayType() {
        return payType;
    }

    public void setPayType(Long payType) {
        this.payType = payType;
    }

    public Long getCashPayRatio() {
        return cashPayRatio;
    }

    public void setCashPayRatio(Long cashPayRatio) {
        this.cashPayRatio = cashPayRatio;
    }

    public Long getOtherPayRatio() {
        return otherPayRatio;
    }

    public void setOtherPayRatio(Long otherPayRatio) {
        this.otherPayRatio = otherPayRatio;
    }

    public Long getBillPay() {
        return billPay;
    }

    public void setBillPay(Long billPay) {
        this.billPay = billPay;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
