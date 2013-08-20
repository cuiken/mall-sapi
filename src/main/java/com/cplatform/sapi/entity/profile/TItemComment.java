package com.cplatform.sapi.entity.profile;

import com.cplatform.sapi.entity.product.ItemSale;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: cuikai
 * Date: 13-8-12
 * Time: 下午5:48
 */
@Entity
@Table(name = "T_ITEM_COMMENT")
public class TItemComment {

    private Long id;

    private String content;

    private Integer type;

    private Integer questionType;

    private String updateTime;

    private String nickname;

    private String userId;

    private Integer status;

//    private Long saleId;

    private Long auditUser;

    private String auditTime;

    private Integer rank;

    private Long usefulNum;

    private Long uselessNum;

    private ItemSale itemSale;
    private List<TItemCommentReply> replyContent = Lists.newArrayList();

    // --BS METHOD----

    public static Map<Integer, String> statusMap = null;

    static {
        statusMap = new HashMap<Integer, String>();
        statusMap.put(0, "未审核");
        statusMap.put(1, "审核通过");
        statusMap.put(2, "审核驳回");
    }

    @Transient
    public String getStatusName() {
        return statusMap.get(this.status);
    }

    /**
     * 商品评论类别
     */
    public static Map<Integer, String> commentTypeMap = null;

    static {
        commentTypeMap = new HashMap<Integer, String>();
        commentTypeMap.put(1, "评论");
        commentTypeMap.put(2, "咨询");
    }

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

    /**
     * 获得评论名称
     *
     * @return
     */
    @Transient
    public String getTypeName() {
        return commentTypeMap.get(this.getType());
    }

    public static Map<Integer, String> questionTypeMap = null;

    static {
        questionTypeMap = new HashMap<Integer, String>();
        questionTypeMap.put(1, "商品咨询");
        questionTypeMap.put(2, "活动咨询");
    }

    /**
     * 获得咨询分类名称
     *
     * @return
     */
    @Transient
    public String getQuestionTypeName() {
        return questionTypeMap.get(this.getQuestionType());
    }

    /**
     * 查询使用
     */
    private String startTime;

    private String endTime;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 是否回复
     */
    private String isreply;


    @Transient
    public String getIsreply() {
        return isreply;
    }


    public void setIsreply(String isreply) {
        this.isreply = isreply;
    }

    @Transient
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Transient
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Transient
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "CONTENT")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "QUESTION_TYPE")
    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    @Column(name = "UPDATE_TIME")
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "NICKNAME")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Column(name = "USER_ID")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

//    @Column(name = "SALE_ID")
//    public Long getSaleId() {
//        return saleId;
//    }
//
//    public void setSaleId(Long saleId) {
//        this.saleId = saleId;
//    }

    @Column(name = "AUDIT_USER")
    public Long getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(Long auditUser) {
        this.auditUser = auditUser;
    }

    @Column(name = "AUDIT_TIME")
    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    @Column(name = "RANK")
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Column(name = "USEFUL_NUM")
    public Long getUsefulNum() {
        return usefulNum;
    }

    public void setUsefulNum(Long usefulNum) {
        this.usefulNum = usefulNum;
    }

    @Column(name = "USELESS_NUM")
    public Long getUselessNum() {
        return uselessNum;
    }

    public void setUselessNum(Long uselessNum) {
        this.uselessNum = uselessNum;
    }

    @OneToMany(mappedBy = "comment",cascade = {CascadeType.REMOVE},fetch = FetchType.LAZY,orphanRemoval = true)
    public List<TItemCommentReply> getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(List<TItemCommentReply> replyContent) {
        this.replyContent = replyContent;
    }

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "sale_id")
    public ItemSale getItemSale() {
        return itemSale;
    }

    public void setItemSale(ItemSale itemSale) {
        this.itemSale = itemSale;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
