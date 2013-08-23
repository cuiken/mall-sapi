package com.cplatform.sapi.service;

import com.cplatform.act.ActOrderCreateRequest;
import com.cplatform.act.ActOrderCreateResponse;
import com.cplatform.act.ActServiceClient;
import com.cplatform.order.ActOrderGoodsInfo;
import com.cplatform.order.ActOrderInfo;
import com.cplatform.sapi.DTO.ItemSaleDataDTO;
import com.cplatform.sapi.mapper.JsonMapper;
import com.cplatform.sapi.util.AppConfig;
import com.cplatform.sapi.util.CommonUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * User: cuikai
 * Date: 13-8-21
 * Time: 下午3:16
 */
@Component
public class OrderService {

    private static Logger logger = Logger.getLogger(OrderService.class);
    private static final String CHARSET_UTF8 = "UTF-8";

    private AppConfig appConfig;

    private ActServiceClient actOrderClient;


    public String createOrder(ActOrderInfo orderInfo) {
        ActOrderCreateRequest request = new ActOrderCreateRequest();
        request.setActOrderInfo(orderInfo);
        ActOrderCreateResponse resp = actOrderClient.createActOrder(request);
//        logger.info("下单回包： " + resp);
//        if (resp.getStatus() != ActOrderCreateResponse.STATUS_OK) {
//            logger.info("创建订单失败: " + resp.getStatusText());
//            return false;
//        } else {
//            return true;
//        }
        JsonMapper mapper=JsonMapper.buildNormalMapper();
        return mapper.toJson(resp);
    }

    /**
     * 获取商品信息
     *
     * @param itemId
     * @return 如果商品接口返回的数据不正确，则返回null
     */
    public ItemSaleDataDTO getItemInfo(String itemId) {
        if (itemId == null) return null;
        String itemJson = CommonUtils.getResponseFromServer(appConfig.getInterfaceItemInfo() + "?saleId=" + itemId, CHARSET_UTF8);
        JsonMapper mapper = JsonMapper.buildNormalMapper();
        return mapper.fromJson(itemJson, ItemSaleDataDTO.class);
    }

//    public List<ActOrderGoodsInfo> fillOrderGoods(ItemSaleDataDTO item){
//        ActOrderGoodsInfo goodsInfo=new ActOrderGoodsInfo();
//        goodsInfo.setGoodsId(item.getItem().getId());
//        goodsInfo.setGoodsSubject(item.getItem().getName());
//        goodsInfo.setPayPrice(11);
//    }

    @Autowired
    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Autowired
    public void setActOrderClient(ActServiceClient actOrderClient) {
        this.actOrderClient = actOrderClient;
    }
}
