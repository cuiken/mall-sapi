package com.cplatform.sapi.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dozer.Mapping;

import java.util.List;

/**
 * User: cuikai
 * Date: 13-8-23
 * Time: 下午2:25
 */
public class MyQuestionDTO {
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

        @JsonProperty("CONTENT")
        private String comment;

        @JsonProperty("TIME")
        private String time;


        @JsonProperty("GOODS")
        private Good good;

        @JsonProperty("REPLAYS")
        private List<Replay> replays = Lists.newArrayList();

        @Mapping("replyContent")
        public List<Replay> getReplays() {
            return replays;
        }

        public void setReplays(List<Replay> replays) {
            this.replays = replays;
        }

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


        public static class Replay {

            @JsonProperty("REPLAY_TIME")
            private String replayTime;

            @JsonProperty("REPLAY_CONTENT")
            private String replayContent;

            @Mapping("updateTime")
            public String getReplayTime() {
                return replayTime;
            }

            public void setReplayTime(String replayTime) {
                this.replayTime = replayTime;
            }

            @Mapping("content")
            public String getReplayContent() {
                return replayContent;
            }

            public void setReplayContent(String replayContent) {
                this.replayContent = replayContent;
            }
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
