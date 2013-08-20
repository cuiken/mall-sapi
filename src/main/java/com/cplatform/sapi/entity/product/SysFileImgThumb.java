package com.cplatform.sapi.entity.product;

import com.cplatform.sapi.entity.IdEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Title.缩略图存储类 <br>
 * Description.
 * <p/>
 * Copyright: Copyright (c) 2013-5-29 下午5:45:18
 * <p/>
 * Company: 北京宽连十方数字技术有限公司
 * <p/>
 * Author: BaiJie
 * <p/>
 * Version: 1.0
 * <p/>
 */

@javax.persistence.Table(name = "T_SYS_FILE_IMG_THUMB")
@Entity
public class SysFileImgThumb extends IdEntity {

//	private Long fileId;

    private SysFileImg sysFileImg;
    /**
     * 图片大小 50*50
     */
    private String imgSize;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 文件物理路径
     */
    private String imgAbsPath;

    /**
     * web路径
     */
    private String imgWebPath;

    @Column(name = "update_time")
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
//
//	@Column(name = "file_id")
//	public Long getFileId() {
//		return fileId;
//	}

    @ManyToOne
    @JoinColumn(name = "file_id")
    @JsonIgnore
    public SysFileImg getSysFileImg() {
        return sysFileImg;
    }

    public void setSysFileImg(SysFileImg sysFileImg) {
        this.sysFileImg = sysFileImg;
    }

//    public void setFileId(Long fileId) {
//        this.fileId = fileId;
//    }

    @Column(name = "img_size")
    public String getImgSize() {
        return imgSize;
    }

    public void setImgSize(String imgSize) {
        this.imgSize = imgSize;
    }

    @Column(name = "img_abs_path")
    public String getImgAbsPath() {
        return imgAbsPath;
    }

    public void setImgAbsPath(String imgAbsPath) {
        this.imgAbsPath = imgAbsPath;
    }

    @Column(name = "img_web_path")
    public String getImgWebPath() {
        return imgWebPath;
    }

    public void setImgWebPath(String imgWebPath) {
        this.imgWebPath = imgWebPath;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
