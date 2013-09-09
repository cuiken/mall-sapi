package com.cplatform.sapi.model;

/**
 * Title. <br>
 * Description.
 * <p/>
 * Copyright: Copyright (c) 13-8-21 下午4:29
 * <p/>
 * Company: 北京宽连十方数字技术有限公司
 * <p/>
 * Author: nicky
 * <p/>
 * Version: 1.0
 * <p/>
 */
public class OrderPayBean extends BaseTransBean {

    public OrderPayBean(boolean genCertCode) {
        super(genCertCode);
    }

    private long orderId;

    private String cashPayType;

    private int score;

    private int coin;

    private String returnUrl;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getCashPayType() {
        return cashPayType;
    }

    public void setCashPayType(String cashPayType) {
        this.cashPayType = cashPayType;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}
