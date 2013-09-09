package com.cplatform.sapi.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TMemberAddressesDTO {

	@JsonProperty("FLAG")
	private String flag;
	
	@JsonProperty("MSG")
	private String msg;
	
	@JsonProperty("DATA")
	private List<TMemberAddressDTO> tMemberAddressDTOs;
	
	public String getFlag() {
		return flag == null ? "0" : flag;
	}
	
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public String getMsg() {
		return msg == null ? "操作成功" : msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public List<TMemberAddressDTO> gettMemberAddressDTOs() {
		return tMemberAddressDTOs;
	}
	
	public void settMemberAddressDTOs(List<TMemberAddressDTO> tMemberAddressDTOs) {
		this.tMemberAddressDTOs = tMemberAddressDTOs;
	}
}
