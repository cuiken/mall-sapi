package com.cplatform.sapi.entity.product;

import com.cplatform.sapi.entity.IdEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * User: cuikai
 * Date: 13-12-11
 * Time: 下午4:13
 */
@Entity
@Table(name = "t_market_p_goods")
public class MarketGoods extends IdEntity {

    private BigDecimal storePrice;
    private BigDecimal promotionPrice;
    private Long promotionStock;
    private String goodsId;
    private Long numLimit;              //购买数量(0为不限制购买数量)
    private String buyLimit;            //0为无限制，1为商盟会员  2为红砖会员
    private String isdel;               //是否已经删除，1为已经删除

    private MarketPromotion marketPromotion;

    @Column(precision = 10, scale = 2)
    public BigDecimal getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(BigDecimal promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public Long getPromotionStock() {
        return promotionStock;
    }

    public void setPromotionStock(Long promotionStock) {
        this.promotionStock = promotionStock;
    }

    public Long getNumLimit() {
        return numLimit;
    }

    public void setNumLimit(Long numLimit) {
        this.numLimit = numLimit;
    }

    public String getBuyLimit() {
        return buyLimit;
    }

    public void setBuyLimit(String buyLimit) {
        this.buyLimit = buyLimit;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    @Column(precision = 10, scale = 2)
    public BigDecimal getStorePrice() {
        return storePrice;
    }

    public void setStorePrice(BigDecimal storePrice) {
        this.storePrice = storePrice;
    }

    public String getIsdel() {
        return isdel;
    }

    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    public MarketPromotion getMarketPromotion() {
        return marketPromotion;
    }

    public void setMarketPromotion(MarketPromotion marketPromotion) {
        this.marketPromotion = marketPromotion;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
