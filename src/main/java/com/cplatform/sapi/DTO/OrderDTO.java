package com.cplatform.sapi.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * User: cuikai
 * Date: 13-8-15
 * Time: 上午11:10
 */
public class OrderDTO {
	
	@JsonProperty("FLAG")
	private String flag;
	
	@JsonProperty("MSG")
	private String msg;
	
	@JsonProperty("TOTAL_ROW")
    private Long totalRow;

	@JsonProperty("DATA")
    private List<OrderDataDTO> orderDatas;
	
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

    public Long getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Long totalRow) {
        this.totalRow = totalRow;
    }

    public List<OrderDataDTO> getOrderDatas() {
        return orderDatas;
    }

    public void setOrderDatas(List<OrderDataDTO> orderDatas) {
        this.orderDatas = orderDatas;
    }
}





