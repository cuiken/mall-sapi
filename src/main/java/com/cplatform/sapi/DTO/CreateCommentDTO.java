package com.cplatform.sapi.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User: cuikai
 * Date: 13-8-29
 * Time: 下午7:09
 */
public class CreateCommentDTO {

    @JsonProperty("U_ID")
    private Long userId;

    @JsonProperty("CONTENT")
    private String content;

    @JsonProperty("GOOD_ID")
    private Long goodId;

    @JsonProperty("LEVEL")
    private int level;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
