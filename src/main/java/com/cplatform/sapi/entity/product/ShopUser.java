package com.cplatform.sapi.entity.product;

import com.cplatform.sapi.entity.IdEntity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Title. <br>
 * Description.
 * <p/>
 * Copyright: Copyright (c) 13-5-30 下午2:30
 * <p/>
 * Company: 北京宽连十方数字技术有限公司
 * <p/>
 * Author: nicky
 * <p/>
 * Version: 1.0
 * <p/>
 */
@javax.persistence.Table(name = "T_SHOP_USER")
@Entity
public class ShopUser extends IdEntity {

    /* 用户状态 */

    /**
     * 用户状态 － 有效
     */
    public static final int STATUS_VALID = 1;

    /**
     * 用户状态 － 无效
     */
    public static final int STATUS_INVALID = 0;

    /**
     * 用户状态 － 暂停
     */
    public static final int STATUS_PAUSE = 2;

	/* 用户类型 */

    /**
     * 用户类型 － 超级管理员
     */
    public static final int TYPE_ADMIN = 1;

    /**
     * 用户类型 － 普通用户
     */
    public static final int TYPE_USER = 2;

    private String code;

    @Column(name = "CODE")
    @Basic
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String pwd;

    @Column(name = "PWD")
    @Basic
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    private int status;

    @Column(name = "STATUS")
    @Basic
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private String updateTime;

    @Column(name = "UPDATE_TIME")
    @Basic
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    private int type;

    @Column(name = "TYPE")
    @Basic
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private String email;

    @Column(name = "EMAIL")
    @Basic
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String mobile;

    @Column(name = "MOBILE")
    @Basic
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    private String nickName;

    @Column(name = "NICK_NAME")
    @Basic
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Column(name = "SHOP_CLASS")
    private int shopClass;

    public int getShopClass() {
        return shopClass;
    }

    public void setShopClass(int shopClass) {
        this.shopClass = shopClass;
    }

    @Column(name = "SHOP_ID")
    private Long shopId;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }


    private String oldPass;

    private String confirmPass;

    private String newPass;
    @Transient
    public String getOldPass() {
        return oldPass;
    }
    @Transient
    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    @Transient
    public String getNewPass() {
        return newPass;
    }
    @Transient
    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
    @Transient
    public String getConfirmPass() {
        return confirmPass;
    }
    @Transient
    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }
    
    private List<Long> roleId = new ArrayList<Long>();

    @Transient
    public List<Long> getRoleId() {
    	return roleId;
    }

    @Transient
    public void setRoleId(List<Long> roleId) {
    	this.roleId = roleId;
    }
    
    private int mainType;

    @Transient
	public int getMainType() {
		return mainType;
	}
    @Transient
	public void setMainType(int mainType) {
		this.mainType = mainType;
	}
    
    
}
