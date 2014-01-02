package com.cplatform.sapi.entity.product;

import com.cplatform.sapi.entity.IdEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: cuikai
 * Date: 13-12-11
 * Time: 下午4:13
 */
@Entity
@Table(name = "t_market_promotion")
public class MarketPromotion extends IdEntity {

    private String promotionName;
    private String promotionStartTime;
    private String promotionEndTime;
    private String createTime;
    private String promotionArea;
    private Long discount;
    private String status;              //0未审核 1待审核 2审核通过 3审核未通过
    private String checkContent;
    private String checkMan;
    private String checkTime;
    private String isdel;              //是否已经删除，1为已经删除

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public String getPromotionStartTime() {
        return promotionStartTime;
    }

    public void setPromotionStartTime(String promotionStartTime) {
        this.promotionStartTime = promotionStartTime;
    }

    public String getPromotionEndTime() {
        return promotionEndTime;
    }

    public void setPromotionEndTime(String promotionEndTime) {
        this.promotionEndTime = promotionEndTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPromotionArea() {
        return promotionArea;
    }

    public void setPromotionArea(String promotionArea) {
        this.promotionArea = promotionArea;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCheckContent() {
        return checkContent;
    }

    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent;
    }

    public String getCheckMan() {
        return checkMan;
    }

    public void setCheckMan(String checkMan) {
        this.checkMan = checkMan;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getIsdel() {
        return isdel;
    }

    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
