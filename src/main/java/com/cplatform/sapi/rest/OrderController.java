package com.cplatform.sapi.rest;

import com.cplatform.order.ActOrderInfo;
import com.cplatform.sapi.DTO.CreateOrderDTO;
import com.cplatform.sapi.DTO.ItemSaleDataDTO;
import com.cplatform.sapi.service.OrderService;
import com.cplatform.sapi.util.MediaTypes;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: cuikai
 * Date: 13-8-21
 * Time: 下午1:40
 */
@Controller
@RequestMapping(value = "/api/v1/order")
public class OrderController {
    private static Logger logger = Logger.getLogger(OrderController.class);

    private static final int ORDER_TYPE = 40;
    private OrderService orderService;


    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaTypes.JSON)
    @ResponseBody
    public String create(@RequestBody CreateOrderDTO orderDTO) {

        ActOrderInfo order = new ActOrderInfo();
        order.setActType(ORDER_TYPE);
        order.setUserId(150964214L);
        order.setCreateSource(ActOrderInfo.CREATE_SOURCE_WEB);
        order.setExpireTime(3600);

        ItemSaleDataDTO item = orderService.getItemInfo(String.valueOf(orderDTO.getGoodsId()));
        order.setShopId(item.getItem().getStoreId());
        order.setShopSubject(item.getItem().getStoreName());

        // 调用接口下单
     return orderService.createOrder(order);

    }

    @RequestMapping(value = "pay")
    public String pay(){
        return null;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
