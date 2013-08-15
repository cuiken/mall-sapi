package com.cplatform.sapi.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Transient;

@javax.persistence.Table(name = "T_SYS_REGION")
@Entity
public class SysRegion extends IdEntity {

	private String regionCode;

	private String regionName;

	private Long regionLevel;

	private String parentRegion;

	private String shortName;

	private String regionSpell;

	private Long show;

	private Long sortNum;
	
	private boolean checked;

	@javax.persistence.Column(name = "REGION_CODE")
	@Basic
	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	@javax.persistence.Column(name = "REGION_NAME")
	@Basic
	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	@javax.persistence.Column(name = "REGION_LEVEL")
	@Basic
	public Long getRegionLevel() {
		return regionLevel;
	}

	public void setRegionLevel(Long regionLevel) {
		this.regionLevel = regionLevel;
	}

	@javax.persistence.Column(name = "PARENT_REGION")
	@Basic
	public String getParentRegion() {
		return parentRegion;
	}

	public void setParentRegion(String parentRegion) {
		this.parentRegion = parentRegion;
	}

	@javax.persistence.Column(name = "SHORT_NAME")
	@Basic
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@javax.persistence.Column(name = "REGION_SPELL")
	@Basic
	public String getRegionSpell() {
		return regionSpell;
	}

	public void setRegionSpell(String regionSpell) {
		this.regionSpell = regionSpell;
	}

	@javax.persistence.Column(name = "IS_SHOW")
	@Basic
	public Long getShow() {
		return show;
	}

	public void setShow(Long show) {
		this.show = show;
	}

	@javax.persistence.Column(name = "SORT_NUM")
	@Basic
	public Long getSortNum() {
		return sortNum;
	}

	public void setSortNum(Long sortNum) {
		this.sortNum = sortNum;
	}
	@Transient
	public void setChecked(boolean checked) {
	    this.checked = checked;
    }
	@Transient
	public boolean isChecked() {
	    return checked;
    }

}
