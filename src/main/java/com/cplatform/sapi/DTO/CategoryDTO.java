package com.cplatform.sapi.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * User: cuikai
 * Date: 13-8-29
 * Time: 下午5:03
 */
public class CategoryDTO {

    @JsonProperty("MSG")
    private String msg;

    @JsonProperty("FLAG")
    private String flag;

    @JsonProperty("DATA")
    private List<Data> data = Lists.newArrayList();


    public String getMsg() {
        return "success";
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFlag() {
        return "0";
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data {
        @JsonProperty("CATEGORY_ID")
        private Long id;

        @JsonProperty("CATEGORY_NAME")
        private String name;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
