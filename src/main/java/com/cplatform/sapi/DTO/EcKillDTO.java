package com.cplatform.sapi.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * User: cuikai
 * Date: 13-8-13
 * Time: 下午4:20
 */
public class EcKillDTO {

    private Long id;
    private String remark;
    private String name;
    private String imgPath;
    private Float marketPrice;
    private Float killPrice;
    private List<String> thumbs;

    public List<String> getThumbs() {
        return thumbs;
    }

    public void setThumbs(List<String> thumbs) {
        this.thumbs = thumbs;
    }

    /**
     * 销售有效开始时间 *
     */
    private String saleStartTime;

    /**
     * 销售有效结束时间 *
     */
    private String saleStopTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("description")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Float getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Float marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getSaleStartTime() {
        return saleStartTime;
    }

    public void setSaleStartTime(String saleStartTime) {
        this.saleStartTime = saleStartTime;
    }

    public String getSaleStopTime() {
        return saleStopTime;
    }

    public void setSaleStopTime(String saleStopTime) {
        this.saleStopTime = saleStopTime;
    }

    public Float getKillPrice() {
        return killPrice;
    }

    public void setKillPrice(Float killPrice) {
        this.killPrice = killPrice;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
