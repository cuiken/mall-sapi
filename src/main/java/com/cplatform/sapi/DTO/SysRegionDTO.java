package com.cplatform.sapi.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * User: cuikai
 * Date: 13-8-29
 * Time: 下午12:06
 */
public class SysRegionDTO {

    @JsonProperty("FLAG")
    private String ret;

    @JsonProperty("MSG")
    private String msg;

    @JsonProperty("DATA")
    private List<Data> data = Lists.newArrayList();

    public String getRet() {
        return "0";
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return "success";
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {

        @JsonProperty("ID")
        private Long id;

        @JsonProperty("REGION_CODE")
        private String regionCode;

        @JsonProperty("NAME")
        private String regionName;

        private Long regionLevel;

        private String areaCode;

        private String parentRegion;

        private String shortName;

        private String regionSpell;

        private Long show;

        private Long sortNum;

        private boolean checked;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getRegionCode() {
            return regionCode;
        }

        public void setRegionCode(String regionCode) {
            this.regionCode = regionCode;
        }

        public String getRegionName() {
            return regionName;
        }

        public void setRegionName(String regionName) {
            this.regionName = regionName;
        }

        public Long getRegionLevel() {
            return regionLevel;
        }

        public void setRegionLevel(Long regionLevel) {
            this.regionLevel = regionLevel;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getParentRegion() {
            return parentRegion;
        }

        public void setParentRegion(String parentRegion) {
            this.parentRegion = parentRegion;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public String getRegionSpell() {
            return regionSpell;
        }

        public void setRegionSpell(String regionSpell) {
            this.regionSpell = regionSpell;
        }

        public Long getShow() {
            return show;
        }

        public void setShow(Long show) {
            this.show = show;
        }

        public Long getSortNum() {
            return sortNum;
        }

        public void setSortNum(Long sortNum) {
            this.sortNum = sortNum;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }
    }


}
