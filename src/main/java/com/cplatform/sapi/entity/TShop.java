package com.cplatform.sapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: cuikai
 * Date: 13-11-22
 * Time: 下午2:59
 */
@Entity
@Table(name = "t_shop")
public class TShop {

    private Long id;
    /**
     * 结算商户编号
     */
    private Long acShopId;
    /**
     * 商户名称
     */
    private String name;
    /**
     * 商户简称
     */
    private String shortName;
    /**
     * 归属地市
     */
    private String areaCode;
    /**
     * 是否签约 1是 0否
     */
    private Long sort;
    /**
     * 折扣内容
     */
    private String discountDetail;
    /**
     * 是否连锁 1是 0否
     */
    private Long chain;
    /**
     * 是否总店 1是 0否
     */
    private String baseShop;
    /**
     * 总店名称
     */
    private String baseShopName;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 有效开始时间
     */
    private String startTime;
    /**
     * 有效结束时间
     */
    private String stopTime;
    /**
     * 商户地址
     */
    private String address;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 营业时间
     */
    private String openTime;
    /**
     * 公交路线
     */
    private String busLine;
    /**
     * 面积
     */
    private String area;
    /**
     * 停车位
     */
    private String parkPlace;
    /**
     * 人均消费
     */
    private Long avgSpend;
    /**
     * 包间数
     */
    private String roomNum;
    /**
     * 地图坐标经度
     */
    private String mapLong;
    /**
     * 地图坐标纬度
     */
    private String mapDim;
    /**
     * 创建商户帐号ID
     */
    private Long shopUserId;
    /**
     * 0--草稿
     * 1--待审核
     * 2--审核中
     * 3--审核通过
     * 4--审核驳回
     */
    private Long status;
    /**
     * 商户是否有效 0--下架
     * 1--上架
     */
    private Long valid;
    /**
     * 1--业务门店
     * 2--结算商户
     * 3--渠道商
     * 此处默认1
     */
    private Long shopClass;
    /**
     * 门店logo图路径
     */
    private String logo;
    /**
     * 详细介绍
     */
    private String remark;

    @SequenceGenerator(name = "seq_item", sequenceName = "SEQ_SHOP")
    @Id
    @GeneratedValue(generator = "seq_item")
    @JsonProperty
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAcShopId() {
        return acShopId;
    }

    public void setAcShopId(Long acShopId) {
        this.acShopId = acShopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getDiscountDetail() {
        return discountDetail;
    }

    public void setDiscountDetail(String discountDetail) {
        this.discountDetail = discountDetail;
    }

    @Column(name = "is_chain")
    public Long getChain() {
        return chain;
    }

    public void setChain(Long chain) {
        this.chain = chain;
    }

    @Column(name = "is_base_shop")
    public String getBaseShop() {
        return baseShop;
    }

    public void setBaseShop(String baseShop) {
        this.baseShop = baseShop;
    }

    public String getBaseShopName() {
        return baseShopName;
    }

    public void setBaseShopName(String baseShopName) {
        this.baseShopName = baseShopName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getBusLine() {
        return busLine;
    }

    public void setBusLine(String busLine) {
        this.busLine = busLine;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getParkPlace() {
        return parkPlace;
    }

    public void setParkPlace(String parkPlace) {
        this.parkPlace = parkPlace;
    }

    public Long getAvgSpend() {
        return avgSpend;
    }

    public void setAvgSpend(Long avgSpend) {
        this.avgSpend = avgSpend;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public String getMapLong() {
        return mapLong;
    }

    public void setMapLong(String mapLong) {
        this.mapLong = mapLong;
    }

    public String getMapDim() {
        return mapDim;
    }

    public void setMapDim(String mapDim) {
        this.mapDim = mapDim;
    }

    public Long getShopUserId() {
        return shopUserId;
    }

    public void setShopUserId(Long shopUserId) {
        this.shopUserId = shopUserId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Column(name = "is_valid")
    public Long getValid() {
        return valid;
    }

    public void setValid(Long valid) {
        this.valid = valid;
    }

    public Long getShopClass() {
        return shopClass;
    }

    public void setShopClass(Long shopClass) {
        this.shopClass = shopClass;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
