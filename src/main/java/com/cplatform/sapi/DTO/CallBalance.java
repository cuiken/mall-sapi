package com.cplatform.sapi.DTO;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: cuikai
 * Date: 13-10-21
 * Time: 下午3:01
 */
public class CallBalance {
    /**
     * 余额.
     */
    private int balance;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 商城币余额.
     */
    private String coin;

    /**
     * 有效期.
     */
    private String invalidDate;

    /**
     * 限额.
     */
    private String limitFee;

    /**
     * 可用金额.
     */
    private int payFee;

    /**
     * 积分余额
     */
    private String score;

    /**
     * 当前已用费用.
     */
    private String useFee;

    private String statusCode;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(String invalidDate) {
        this.invalidDate = invalidDate;
    }

    public String getLimitFee() {
        return limitFee;
    }

    public void setLimitFee(String limitFee) {
        this.limitFee = limitFee;
    }

    public int getPayFee() {
        return payFee;
    }

    public void setPayFee(int payFee) {
        this.payFee = payFee;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getUseFee() {
        return useFee;
    }

    public void setUseFee(String useFee) {
        this.useFee = useFee;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
