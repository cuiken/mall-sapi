package com.cplatform.sapi.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 客户端支付请求封装
 * User: cuikai
 * Date: 13-9-2
 * Time: 下午12:08
 */
public class PayDTO {

    @JsonProperty("U_ID")
    private Long userId;

    @JsonProperty("ORDER_ID")
    private Long orderId;

    @JsonProperty("PAY_TYPE")
    private String payType;

    @JsonProperty("SCORE")
    private int score;

    @JsonProperty("COIN")
    private int coin;

    @JsonProperty("CHANNEL")
    private String channel;

    @JsonProperty("RETURN_URL")
    private String returnURL;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
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

    public String getReturnURL() {
        return returnURL;
    }

    public void setReturnURL(String returnURL) {
        this.returnURL = returnURL;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
