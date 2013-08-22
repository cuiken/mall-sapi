package com.cplatform.sapi.DTO;

/**
 * 客户端下单数据集
 * User: cuikai
 * Date: 13-8-22
 * Time: 上午10:10
 */
public class CreateOrderDTO {
    private Long userId;
    private Long goodsId;

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
}
