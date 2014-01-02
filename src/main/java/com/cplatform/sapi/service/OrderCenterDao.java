package com.cplatform.sapi.service;

import com.cplatform.act.*;
import com.cplatform.order.*;
import com.cplatform.pay.PayChannel;
import com.cplatform.pay.PayOrderInfo;
import com.cplatform.pay.PayServiceClient;
import com.cplatform.sapi.DTO.OrderCenterErrResp;
import com.cplatform.sapi.DTO.PayDTO;
import com.cplatform.sapi.exceptions.InterfaceException;
import com.cplatform.sapi.exceptions.OrderServiceException;
import com.cplatform.sapi.mapper.JsonMapper;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.cplatform.sapi.util.Constants.*;

/**
 * User: cuikai
 * Date: 13-9-18
 * Time: 上午9:43
 */
@Component
public class OrderCenterDao {

    @Autowired
    private ActServiceClient actBusinessClient;

    @Autowired
    private PayServiceClient payServiceClient;

    @Autowired
    private ActOrderServiceClient actOrderClient;

    @Autowired
    private BusinessLogger businessLogger;

    /**
     * 请求接口创建订单
     *
     * @param orderInfo
     * @return
     */
    public long createOrderRequest(ActOrderInfo orderInfo) {
        ActOrderCreateRequest request = new ActOrderCreateRequest();
        request.setActOrderInfo(orderInfo);
        ActOrderCreateResponse resp = actBusinessClient.createActOrder(request);

        if (resp.getStatus() != ActOrderCreateResponse.STATUS_OK) {
            JsonMapper mapper = JsonMapper.buildNormalMapper();
            Map logData = Maps.newHashMap();

            logData.put("request", mapper.toJson(request));
            logData.put("response", mapper.toJson(resp));
            businessLogger.error("orderCenter", "createOrder:error", logData);
            String statusText = resp.getStatusText();

            OrderCenterErrResp orderCenterErrResp = mapper.fromJson(statusText, OrderCenterErrResp.class);

            if (orderCenterErrResp != null) {
                statusText = OrderCenterStatus.getMsg(orderCenterErrResp.getCode());
            }

            throw new InterfaceException(resp.getStatus(), statusText);
        }
        return resp.getActOrderId();
    }

    /**
     * 请求支付商城币
     *
     * @return
     */
    public PayResponse procCoinPay(Long orderId, Long payInfoId) {
        PayRequest payRequest = new PayRequest();
        payRequest.setActOrderId(orderId);
        payRequest.setPaymentId(payInfoId);
        payRequest.setPayType(PayRequest.PAY_TYPE_BACKGROUND);
        payRequest.setPayChannel(PayChannel.UNIFY_CART_COIN);

        return requestPayAndLog(payRequest, CURRENCY_COIN);
    }

    /**
     * 请求支付现金接口
     */
    public PayResponse procCashPay(Long orderId, PayDTO dto, Long payInfoId) {
        PayRequest payRequest = new PayRequest();
        payRequest.setWebPayReturnUrl(dto.getReturnURL());
        payRequest.setActOrderId(orderId);
        payRequest.setPaymentId(payInfoId);
        if (StringUtils.isNotBlank(dto.getPayType()) && dto.getPayType().equalsIgnoreCase(WAP)) {
            payRequest.setPayType(PayRequest.PAY_TYPE_WAP);
        } else {
            payRequest.setPayType(PayRequest.PAY_TYPE_WEB);
        }

        payRequest.setPayChannel(dto.getChannel());

        return requestPayAndLog(payRequest, CURRENCY_CASH);
    }

    public PayResponse procScorePay(Long orderId, Long paymentId) {
        PayRequest payRequest = new PayRequest();
        payRequest.setActOrderId(orderId);
        payRequest.setPaymentId(paymentId);
        payRequest.setPayType(PayRequest.PAY_TYPE_BACKGROUND);
        payRequest.setPayChannel(PayChannel.UNIFY_CART_SCORE);

        return requestPayAndLog(payRequest, CURRENCY_SCORE);
    }

    public PayResponse procDeliveryCashPay(Long orderId, Long payId) {
        PayRequest payRequest = new PayRequest();
        payRequest.setActOrderId(orderId);
        payRequest.setPaymentId(payId);
        payRequest.setPayChannel(PayChannel.EMS_PAY_ON_DELIVERY);

        return requestPayAndLog(payRequest, CURRENCY_CASH);
    }

    /**
     * 支付话费接口
     *
     * @param orderId
     * @param paymentInfoId
     * @return
     */
    public PayResponse procBalancePay(Long orderId, Long paymentInfoId) {
        PayRequest payRequest = new PayRequest();
        payRequest.setActOrderId(orderId);
        payRequest.setPaymentId(paymentInfoId);
        payRequest.setPayType(PayRequest.PAY_TYPE_BACKGROUND);
        payRequest.setPayChannel(PayChannel.UNIFY_BALANCE);

        return requestPayAndLog(payRequest, CURRENCY_BALANCE);
    }

    private PayResponse requestPayAndLog(PayRequest payRequest, String payType) {
        PayResponse payResponse = actBusinessClient.pay(payRequest);

        if (payResponse.getStatus() != PayResponse.STATUS_OK) {
            JsonMapper mapper = JsonMapper.buildNormalMapper();
            Map<String, Object> logData = Maps.newHashMap();
            logData.put("request", mapper.toJson(payRequest));
            logData.put("response", mapper.toJson(payResponse));
            businessLogger.error(payType, "pay:error", logData);

            throw new InterfaceException(payResponse.getStatus(), payResponse.getStatusText());
        }
        return payResponse;
    }

    public boolean updateOrderToPaying(Long orderId) {
        try {

            CommonResponse response = actOrderClient.updateStatus(orderId, ActOrderStatus.STATUS_TYPE_PAY, ActOrderStatus.PAY_STATUS_PAYING, "正在支付中",
                    "{'method':'updateOrderToPaying'}");
            if (response.getStatusCode() != STATUS_OK) {
                Map<String, Object> logData = Maps.newHashMap();
                logData.put("orderId", orderId);
                logData.put("status", response.getStatusCode());
                logData.put("statusText", response.getStatusText());
                businessLogger.error("orderCenter", "updateOrderToPaying", logData);
            }
        } catch (Exception ex) {
            throw new OrderServiceException(OrderServiceException.INTERFACE_FATAL, "更新订单状态失败。");
        }
        return true;
    }


    public ActOrderInfo getActOrder(Long orderId) throws Exception {
        return actOrderClient.getActOrderInfo(orderId);
    }

    public List<PayOrderInfo> getPayOrderInfos(Long orderId) throws Exception {
        return payServiceClient.getPayOrderInfosByActOrderId(orderId);
    }

    public boolean bindingPaymentToOrder(Long orderId, List<ActOrderPaymentInfo> paymentInfos) throws Exception {

        // 更新支付项目，接口不支持一次写入订单
        try {
            CommonResponse response = actOrderClient.updatePaymentInfo(orderId, paymentInfos);
            if (response.getStatusCode() != STATUS_OK) {
                Map<String, Object> logData = Maps.newHashMap();
                logData.put("orderId", orderId);
                logData.put("status", response.getStatusCode());
                logData.put("statusText", response.getStatusText());
                businessLogger.error("orderCenter", "bindingPaymentToOrder", logData);
            }
        } catch (Exception ex) {
            throw new OrderServiceException(OrderServiceException.INTERFACE_FATAL, "更新支付项目出错。");
        }
        return true;
    }

    public boolean updateGoodsInfo(Long orderId, List<ActOrderGoodsInfo> goodsInfos) throws Exception {
        CommonResponse response = actOrderClient.updateGoodsInfo(orderId, goodsInfos);
        if (response.getStatusCode() != STATUS_OK) {
            Map<String, Object> logData = Maps.newHashMap();
            logData.put("orderId", orderId);
            logData.put("status", response.getStatusCode());
            logData.put("statusText", response.getStatusText());
            businessLogger.error("orderCenter", "updateGoodsInfo", logData);
            throw new OrderServiceException(OrderServiceException.INTERFACE_FATAL, "更新商品信息出错。");
        }
        return true;
    }
}
