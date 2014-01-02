package com.cplatform.sapi.DTO;

import com.cplatform.sapi.entity.Member;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: cuikai
 * Date: 13-11-7
 * Time: 上午11:52
 */
public class CheckPayParam {

    private Long orderId;
    private int coin;
    private int score;
    private int balance;
    private String payForm;
    private Member user;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getPayForm() {
        return payForm;
    }

    public void setPayForm(String payForm) {
        this.payForm = payForm;
    }

    public Member getUser() {
        return user;
    }

    public void setUser(Member user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
