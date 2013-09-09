package com.cplatform.sapi.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User: cuikai
 * Date: 13-8-29
 * Time: 下午7:12
 */
public class CreateFavoriteDTO {

    @JsonProperty("U_ID")
    private Long userId;

    @JsonProperty("GOOD_ID")
    private Long goodId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }
}
