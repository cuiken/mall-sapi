package com.cplatform.sapi.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: cuikai
 * Date: 13-9-9
 * Time: 上午9:38
 */
@Entity
@Table(name = "T_SYS_SEGMENT")
public class SysSegment {

    private Long id;
    private String segmentCode;
    private String operatorCode;
    private String areaCode;
    private String mmscId;

    @Id
    @SequenceGenerator(name = "seq_comm", sequenceName = "SEQ_SEGMENT")
    @GeneratedValue(generator = "seq_comm")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSegmentCode() {
        return segmentCode;
    }

    public void setSegmentCode(String segmentCode) {
        this.segmentCode = segmentCode;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getMmscId() {
        return mmscId;
    }

    public void setMmscId(String mmscId) {
        this.mmscId = mmscId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
