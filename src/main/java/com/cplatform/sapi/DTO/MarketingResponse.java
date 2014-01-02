package com.cplatform.sapi.DTO;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 营销接口返回数据
 * User: cuikai
 * Date: 13-9-22
 * Time: 上午9:43
 */
public class MarketingResponse {

    private String confirm_price;
    private String confirm_type;
    private String confirm_itemId;
    private String confirm_userId;
    private String businessId;
    private String confirm_dealTime;

    public String getConfirm_price() {
        return confirm_price;
    }

    public void setConfirm_price(String confirm_price) {
        this.confirm_price = confirm_price;
    }

    public String getConfirm_type() {
        return confirm_type;
    }

    public void setConfirm_type(String confirm_type) {
        this.confirm_type = confirm_type;
    }

    public String getConfirm_itemId() {
        return confirm_itemId;
    }

    public void setConfirm_itemId(String confirm_itemId) {
        this.confirm_itemId = confirm_itemId;
    }

    public String getConfirm_userId() {
        return confirm_userId;
    }

    public void setConfirm_userId(String confirm_userId) {
        this.confirm_userId = confirm_userId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getConfirm_dealTime() {
        return confirm_dealTime;
    }

    public void setConfirm_dealTime(String confirm_dealTime) {
        this.confirm_dealTime = confirm_dealTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
