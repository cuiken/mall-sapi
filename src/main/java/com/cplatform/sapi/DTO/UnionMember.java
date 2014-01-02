package com.cplatform.sapi.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: chenke
 * Date: 13-11-11
 * Time: 上午10:51
 */
public class UnionMember {

    private String flag;
    private String msg;
    @JsonProperty("isMember")
    private String member;
    private String bossSet;
    private String level;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getBossSet() {
        return bossSet;
    }

    public void setBossSet(String bossSet) {
        this.bossSet = bossSet;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
