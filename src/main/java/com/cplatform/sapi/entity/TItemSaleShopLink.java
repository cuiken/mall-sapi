package com.cplatform.sapi.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: cuikai
 * Date: 13-11-22
 * Time: 下午4:20
 */
@Entity
@Table(name = "T_ITEM_VERIFY_SHOP_LINK")
public class TItemSaleShopLink extends IdEntity {

    private Long storeId;
    private Long saleId;
    private Long shopId;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
