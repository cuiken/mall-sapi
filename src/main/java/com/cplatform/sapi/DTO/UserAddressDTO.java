package com.cplatform.sapi.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: cuikai
 * Date: 13-8-29
 * Time: 下午1:46
 */
public class UserAddressDTO {

    @JsonProperty("U_ID")
    private Long userId;

    @JsonProperty("ID")
    private Long id;

    @JsonProperty("NAME")
    private String name;

    @JsonProperty("ZIP_CODE")
    private String zipCode;

    @JsonProperty("REGION_CODE")
    private String regionCode;

    @JsonProperty("ADDRESS")
    private String address;

    @JsonProperty("TERMINAL_ID")
    private String terminalId;

    @JsonProperty("IS_DEFAULT")
    private String isDefault;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getDefault() {
        return isDefault;
    }

    public void setDefault(String aDefault) {
        isDefault = aDefault;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
