package com.cplatform.sapi.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * User: cuikai
 * Date: 13-8-15
 * Time: 上午11:10
 */
public class OrderDTO {
    private Long totalRow;
    private List<OrderDataDTO> orderDatas;

    public Long getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(Long totalRow) {
        this.totalRow = totalRow;
    }

    @JsonProperty("data")
    public List<OrderDataDTO> getOrderDatas() {
        return orderDatas;
    }

    public void setOrderDatas(List<OrderDataDTO> orderDatas) {
        this.orderDatas = orderDatas;
    }
}





