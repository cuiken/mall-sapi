package com.cplatform.sapi.entity.product;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 店铺表. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2013-8-30 上午10:15:31
 * <p>
 * Company: 北京宽连十方数字技术有限公司
 * <p>
 * 
 * @author yangxm@c-platform.com
 * @version 1.0.0
 */
@Table(name = "T_STORE")
@Entity
public class TStore {

	private Long id;

	private String name;

	private String shortName;

	private String areaCode;

	private int sort;

	private String baseShopName;

	private String createTime;

	private String updateTime;

	private String address;

	private Long sysUserId;

	private Long updateUserId;

	private String bsManagerName;

	private String bsManagerPhone;

	private String fcManagerName;

	private String fcManagerPhone;

	private String linkName;

	private String linkPhone;

	private String linkFax;

	private int shopClass;

	private int status;

	private int isValid;

	private int itemEditAuditFlag;

	private int itemPublishAuditFlag;

	private int shopEditAuditFlag;

	private int syncGyFlag;

	private String bsScope;

	private String servicePhone;

	private String areaId;

	private String merchid;

	private String stopTime;

	private String startTime;

	@SequenceGenerator(name = "seq_item", sequenceName = "SEQ_SHOP")
	@Id
	@GeneratedValue(generator = "seq_item")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NAME")
	@Basic
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "SHORT_NAME")
	@Basic
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "AREA_CODE")
	@Basic
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Column(name = "SORT")
	@Basic
	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	@Column(name = "BASE_SHOP_NAME")
	@Basic
	public String getBaseShopName() {
		return baseShopName;
	}

	public void setBaseShopName(String baseShopName) {
		this.baseShopName = baseShopName;
	}

	@Column(name = "CREATE_TIME")
	@Basic
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_TIME")
	@Basic
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "ADDRESS")
	@Basic
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "SYS_USER_ID")
	@Basic
	public Long getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Long sysUserId) {
		this.sysUserId = sysUserId;
	}

	@Column(name = "UPDATE_USER_ID")
	@Basic
	public Long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	@Column(name = "BS_MANAGER_NAME")
	@Basic
	public String getBsManagerName() {
		return bsManagerName;
	}

	public void setBsManagerName(String bsManagerName) {
		this.bsManagerName = bsManagerName;
	}

	@Column(name = "BS_MANAGER_PHONE")
	@Basic
	public String getBsManagerPhone() {
		return bsManagerPhone;
	}

	public void setBsManagerPhone(String bsManagerPhone) {
		this.bsManagerPhone = bsManagerPhone;
	}

	@Column(name = "FC_MANAGER_NAME")
	@Basic
	public String getFcManagerName() {
		return fcManagerName;
	}

	public void setFcManagerName(String fcManagerName) {
		this.fcManagerName = fcManagerName;
	}

	@Column(name = "FC_MANAGER_PHONE")
	@Basic
	public String getFcManagerPhone() {
		return fcManagerPhone;
	}

	public void setFcManagerPhone(String fcManagerPhone) {
		this.fcManagerPhone = fcManagerPhone;
	}

	@Column(name = "LINK_NAME")
	@Basic
	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	@Column(name = "LINK_PHONE")
	@Basic
	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	@Column(name = "LINK_FAX")
	@Basic
	public String getLinkFax() {
		return linkFax;
	}

	public void setLinkFax(String linkFax) {
		this.linkFax = linkFax;
	}

	@Column(name = "SHOP_CLASS")
	@Basic
	public int getShopClass() {
		return shopClass;
	}

	public void setShopClass(int shopClass) {
		this.shopClass = shopClass;
	}

	@Column(name = "STATUS")
	@Basic
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "IS_VALID")
	@Basic
	public int getIsValid() {
		return isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}

	@Column(name = "ITEM_EDIT_AUDIT_FLAG")
	@Basic
	public int getItemEditAuditFlag() {
		return itemEditAuditFlag;
	}

	public void setItemEditAuditFlag(int itemEditAuditFlag) {
		this.itemEditAuditFlag = itemEditAuditFlag;
	}

	@Column(name = "ITEM_PUBLISH_AUDIT_FLAG")
	@Basic
	public int getItemPublishAuditFlag() {
		return itemPublishAuditFlag;
	}

	public void setItemPublishAuditFlag(int itemPublishAuditFlag) {
		this.itemPublishAuditFlag = itemPublishAuditFlag;
	}

	@Column(name = "SHOP_EDIT_AUDIT_FLAG")
	@Basic
	public int getShopEditAuditFlag() {
		return shopEditAuditFlag;
	}

	public void setShopEditAuditFlag(int shopEditAuditFlag) {
		this.shopEditAuditFlag = shopEditAuditFlag;
	}

	@Column(name = "SYNC_GY_FLAG")
	@Basic
	public int getSyncGyFlag() {
		return syncGyFlag;
	}

	public void setSyncGyFlag(int syncGyFlag) {
		this.syncGyFlag = syncGyFlag;
	}

	@Column(name = "BS_SCOPE")
	@Basic
	public String getBsScope() {
		return bsScope;
	}

	public void setBsScope(String bsScope) {
		this.bsScope = bsScope;
	}

	@Column(name = "SERVICE_PHONE")
	@Basic
	public String getServicePhone() {
		return servicePhone;
	}

	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}

	@Column(name = "AREA_ID")
	@Basic
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	@Column(name = "MERCHID")
	@Basic
	public String getMerchid() {
		return merchid;
	}

	public void setMerchid(String merchid) {
		this.merchid = merchid;
	}

	@Column(name = "STOP_TIME")
	@Basic
	public String getStopTime() {
		return stopTime;
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}

	@Column(name = "START_TIME")
	@Basic
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

}
