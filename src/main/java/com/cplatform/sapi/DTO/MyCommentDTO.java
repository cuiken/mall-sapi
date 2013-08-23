package com.cplatform.sapi.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dozer.Mapping;

import java.util.List;

/**
 * User: cuikai
 * Date: 13-8-23
 * Time: 下午1:36
 */
public class MyCommentDTO {

    @JsonProperty("TOTAL_ROW")
    private Long totalRow;

    @JsonProperty("DATA")
    private List<Data> data = Lists.newArrayList();

    public Long getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Long totalRow) {
        this.totalRow = totalRow;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static class Data {

        @JsonProperty("ID")
        private Long id;

        @JsonProperty("COMMENT")
        private String comment;

        @JsonProperty("TIME")
        private String time;

        @JsonProperty("LEVEL")
        private String level;

        @JsonProperty("GOODS")
        private Good good;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        @Mapping("content")
        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        @Mapping("updateTime")
        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        @Mapping("itemSale")
        public Good getGood() {
            return good;
        }

        public void setGood(Good good) {
            this.good = good;
        }

        @Mapping("rank")
        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public static class Good {
            @JsonProperty("GOOD_ID")
            private Long goodId;

            @JsonProperty("NAME")
            private String name;

            @Mapping("id")
            public Long getGoodId() {
                return goodId;
            }

            public void setGoodId(Long goodId) {
                this.goodId = goodId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
