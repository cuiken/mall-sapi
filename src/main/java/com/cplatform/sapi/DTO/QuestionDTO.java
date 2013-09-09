package com.cplatform.sapi.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import org.dozer.Mapping;

import java.util.List;

/**
 * 客户端咨询DTO
 * User: cuikai
 * Date: 13-8-23
 * Time: 下午12:23
 */
public class QuestionDTO {

    @JsonProperty("TOTAL_ROW")
    private long totalRow;

    @JsonProperty("DATA")
    private List<Data> data = Lists.newArrayList();

    public long getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(long totalRow) {
        this.totalRow = totalRow;
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

        @JsonProperty("CONTENT")
        private String content;

        @JsonProperty("TIME")
        private String time;

        @JsonProperty("NAME")
        private String name;

        @JsonProperty("TERMINAL_ID")
        private String terminalId;

        //@JsonProperty("REPLAYS")
        @JsonIgnore
        private List<Replay> replays = Lists.newArrayList();
        
        @JsonProperty("REPLY_TIME")
        private String replyTime;
        
        @JsonProperty("REPLY_CONTENT")
        private String replyContent;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        @Mapping("replyContent")
        public List<Replay> getReplays() {
            return replays;
        }

        public void setReplays(List<Replay> replays) {
            this.replays = replays;
        }
        

        public String getReplyTime() {
        	if(this.getReplays().size() > 0){
        		return this.getReplays().get(0).getReplayTime();
        	}
			return replyTime;
		}

		public void setReplyTime(String replyTime) {
			this.replyTime = replyTime;
		}

		public String getReplyContent() {
			if(this.getReplays().size() > 0){
				return this.getReplays().get(0).getReplayContent();
			}
			return replyContent;
		}

		public void setReplyContent(String replyContent) {
			this.replyContent = replyContent;
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
    }

}
