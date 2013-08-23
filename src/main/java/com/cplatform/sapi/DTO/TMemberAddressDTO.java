package com.cplatform.sapi.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.dozer.Mapping;

/**
 * User: cuikai
 * Date: 13-8-23
 * Time: 下午4:26
 */
public class TMemberAddressDTO {

    @JsonProperty("ID")
    private Long id;

    @JsonProperty("NAME")
    private String name;

    @JsonProperty("ZIP_CODE")
    private String zipCode;

    @JsonProperty("ADDRESS")
    private String address;

    @JsonProperty("TERMINAL_ID")
    private String terminalId;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Mapping("mobile")
    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }
}
