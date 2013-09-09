package com.cplatform.sapi.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import org.dozer.Mapping;

import java.util.List;

/**
 * 客户端咨询评论DTO
 * User: cuikai
 * Date: 13-8-14
 * Time: 下午3:29
 */
public class CommentDTO {

    @JsonProperty("TOTAL_ROW")
    private long totalRow;

    @JsonProperty("DATA")
    private List<CommentDataDTO> data = Lists.newArrayList();

    public long getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(long totalRow) {
        this.totalRow = totalRow;
    }

    public List<CommentDataDTO> getData() {
        return data;
    }

    public void setData(List<CommentDataDTO> data) {
        this.data = data;
    }

    public static class CommentDataDTO {
        @JsonProperty("ID")
        private Long id;

        @JsonProperty("COMMENT")
        private String comment;

        @JsonProperty("TIME")
        private String time;

        @JsonProperty("NAME")
        private String name;

        @JsonProperty("TERMINAL_ID")
        private String terminalId;

        @JsonProperty("LEVEL")
        private String level;

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

        @Mapping("nickname")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTerminalId() {
            return terminalId;
        }

        public void setTerminalId(String terminalId) {
            this.terminalId = terminalId;
        }

        @Mapping("rank")
        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }
    }


}
