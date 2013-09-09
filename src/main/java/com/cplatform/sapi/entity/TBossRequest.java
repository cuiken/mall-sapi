package com.cplatform.sapi.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: cuikai
 * Date: 13-8-29
 * Time: 下午1:04
 */
@Entity
@Table(name = "T_BOSS_REQUEST")
public class TBossRequest {

    private Long id;
    private String terminalId;
    private String areaCode;
    private String type;
    private String sendDate;
    private Long status;
    private String error;
    private String productId;
    private String reqSrc;
    private String insertTime;

    @SequenceGenerator(name = "seq_boss", sequenceName = "SEQ_BOSS_REQUEST")
    @Id
    @GeneratedValue(generator = "seq_boss")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getReqSrc() {
        return reqSrc;
    }

    public void setReqSrc(String reqSrc) {
        this.reqSrc = reqSrc;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
