package com.cplatform.sapi.entity.profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: cuikai
 * Date: 13-8-12
 * Time: 下午5:49
 */
@Entity
@Table(name = "T_ITEM_COMMENT_REPLY")
public class TItemCommentReply {
    private Long id;

    private String updateTime;

    private String nickName;

    private Long userId;

//    private Long commentId;

    private TItemComment comment;

    @SequenceGenerator(name = "seq_item_COMMENT", sequenceName = "SEQ_ITEM_COMMENT")
    @Id
    @GeneratedValue(generator = "seq_item_COMMENT")
    @JsonProperty
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String content;

    @Column(name = "CONTENT")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "UPDATE_TIME")
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "NICKNAME")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Column(name = "USER_ID")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @ManyToOne
    @JoinColumn(name = "COMMENT_ID")
    @JsonIgnore
    public TItemComment getComment() {
        return comment;
    }

    public void setComment(TItemComment comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
