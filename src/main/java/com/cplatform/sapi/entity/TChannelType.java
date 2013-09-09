package com.cplatform.sapi.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: cuikai
 * Date: 13-8-13
 * Time: 上午11:40
 */
@Entity
@Table(name = "T_CHANNEL_TYPE")
public class TChannelType {

    private Long id;
    //    private Long typeId;
    private TSysType sysType;
    private String displayName;
    private String regionCode;
    private Long channel;

    @Id
    @Column(name = "ID", unique = true, nullable = false, precision = 20, scale = 0)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    @Column(name = "TYPE_ID", nullable = false, precision = 20, scale = 0)
//    public Long getTypeId() {
//        return this.typeId;
//    }
//
//    public void setTypeId(Long typeId) {
//        this.typeId = typeId;
//    }

    @ManyToOne
    @JoinColumn(name = "TYPE_ID")
    public TSysType getSysType() {
        return sysType;
    }

    public void setSysType(TSysType sysType) {
        this.sysType = sysType;
    }

    @Column(name = "DISPLAY_NAME", length = 100)
    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Column(name = "REGION_CODE", length = 100)
    public String getRegionCode() {
        return this.regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    @Column(name = "CHANNEL", precision = 4, scale = 0)
    public Long getChannel() {
        return this.channel;
    }

    public void setChannel(Long channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
