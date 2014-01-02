package com.cplatform.sapi.service;

import com.cplatform.sapi.DTO.GoodsInfoDTO;
import com.cplatform.sapi.DTO.OrderDataDTO;
import com.cplatform.sapi.DTO.PaymentDTO;
import com.cplatform.sapi.entity.SysLogistic;
import com.cplatform.sapi.entity.order.*;
import com.cplatform.sapi.entity.profile.MemberFavorite;
import com.cplatform.sapi.entity.profile.TItemComment;
import com.cplatform.sapi.orm.Page;
import com.cplatform.sapi.orm.PropertyFilter;
import com.cplatform.sapi.repository.SysLogisticDao;
import com.cplatform.sapi.repository.order.TActOrderDao;
import com.cplatform.sapi.repository.order.VerifyCodeInfoDao;
import com.cplatform.sapi.repository.profile.ItemCommentDao;
import com.cplatform.sapi.repository.profile.MemberFavoriteDao;
import com.cplatform.sapi.util.AppConfig;
import com.cplatform.sapi.util.PathUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * User: cuikai
 * Date: 13-8-14
 * Time: 上午11:41
 */
@Component
@Transactional
public class ProfileService {

    @Autowired
    private MemberFavoriteDao memberFavoriteDao;
    @Autowired
    private TActOrderDao orderDao;
    @Autowired
    private ItemCommentDao itemCommentDao;
    @Autowired
    private SysLogisticDao logisticDao;
    @Autowired
    private VerifyCodeInfoDao verifyCodeInfoDao;

    @Autowired
    private BusinessLogger businessLogger;

    @Autowired
    private PathUtil pathUtil;
    @Autowired
    private AppConfig appConfig;

    public void saveMemberFavorite(MemberFavorite entity) {
        memberFavoriteDao.save(entity);
        //业务日志
        Map logData = Maps.newHashMap();
        logData.put("MemberFavorite", entity);
        businessLogger.log("MemberFavorite", "save", logData);
    }

    public void saveComment(TItemComment entity) {
        itemCommentDao.save(entity);

        Map logData = Maps.newHashMap();
        logData.put("TItemComment", entity);
        businessLogger.log("TItemComment", "save", logData);
    }

    public Page<MemberFavorite> searchMemberFavorite(final Page<MemberFavorite> page, final List<PropertyFilter> filters) {
        Map logData = Maps.newHashMap();
        logData.put("filters", filters);
        logData.put("pageNo", page.getPageNo());
        logData.put("pageSize", page.getPageSize());
        businessLogger.log("MemberFavorite", "search", logData);

        return memberFavoriteDao.findPage(page, filters);
    }

    public Page<TActOrder> searchOrder(final Page<TActOrder> page, final List<PropertyFilter> filters) {
        Map logData = Maps.newHashMap();
        logData.put("filters", filters);
        logData.put("pageNo", page.getPageNo());
        logData.put("pageSize", page.getPageSize());
        businessLogger.log("TActOrder", "search", logData);

        return orderDao.findPage(page, filters);
    }

    public Page<TItemComment> searchItemComment(final Page<TItemComment> page, final List<PropertyFilter> filters) {
        Map logData = Maps.newHashMap();
        logData.put("filters", filters);
        logData.put("pageNo", page.getPageNo());
        logData.put("pageSize", page.getPageSize());
        businessLogger.log("TItemComment", "search", logData);

        return itemCommentDao.findPage(page, filters);
    }

    public TActOrder getOrder(Long id) {
        return orderDao.get(id);
    }


    public OrderDataDTO buildOrderData(TActOrder order) {

        OrderDataDTO orderDataDTO = new OrderDataDTO();
        orderDataDTO.setOrderId(order.getId());  //订单编号
        orderDataDTO.setSubject(order.getSubject()); //订单标题
        orderDataDTO.setStatus(order.getStatus()); //订单状态
        orderDataDTO.setOrderTime(order.getCreateTime()); //下单时间
        orderDataDTO.setCloseTime(order.getCloseTime());
        BigDecimal amount = new BigDecimal(order.getTotalPayAmount());
        orderDataDTO.setAmount(amount.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP)); //订单金额 元
        orderDataDTO.setPayStatus(order.getPayStatus()); //订单支付状态
        orderDataDTO.setPayDescription(order.getPayDescription()); //支付说明
        orderDataDTO.setPayTime(order.getPayTime()); //订单支付时间

        List<VerifyCodeInfo> codeInfo=verifyCodeInfoDao.findBy("actOrderId", order.getId());
        orderDataDTO.setVerifyCodeInfos(codeInfo);

        TActOrderExpress orderExpress = order.getExpressInfo();
        if (orderExpress != null) {
            BigDecimal fare = new BigDecimal(orderExpress.getExpressCost());
            orderDataDTO.setFare(fare.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP)); // 运费，展示用，单位元
            if (orderExpress.getExpressCompanyId() != null && orderExpress.getExpressCompanyId() != 0) {
                SysLogistic logistic = logisticDao.get(orderExpress.getExpressCompanyId());
                orderDataDTO.setExpressId(logistic.getEname());
            } else {
                orderDataDTO.setExpressId(null);
            }
            orderDataDTO.setExpressCode(orderExpress.getExpressNo());
            orderDataDTO.setExpressName(orderExpress.getExpressCompanyName());
            orderDataDTO.setAddress(orderExpress.getAddress());
            orderDataDTO.setDeliveryPhone(orderExpress.getCellphoneNumber());
            orderDataDTO.setDeliveryName(orderExpress.getReceiverName());
        }

        //订单商品信息
        List<GoodsInfoDTO> goodsInfoDTOs = Lists.newArrayList(); //商品信息
        orderDataDTO.setGoodsInfoDTOs(goodsInfoDTOs);
        for (TActOrderGoods orderGood : order.getGoodsInfos()) {
            GoodsInfoDTO goodsInfoDTO = new GoodsInfoDTO();
            BigDecimal price = new BigDecimal(orderGood.getPayPrice());
            goodsInfoDTO.setPrice(price.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP)); //支付价格
            goodsInfoDTO.setCount(orderGood.getCount()); //商品数量
            BigDecimal totalAmount = new BigDecimal(orderGood.getTotalAmount());
            goodsInfoDTO.setTotalAmount(totalAmount.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP)); //商品总额

            goodsInfoDTO.setGoodsId(orderGood.getItemSale().getId());
            goodsInfoDTO.setGoodsName(orderGood.getItemSale().getName());

            String image = appConfig.getServerHost()
                    + pathUtil.getPathById(2, orderGood.getItemSale()
                    .getId()) + "N4/"
                    + orderGood.getItemSale().getImgPath();
            goodsInfoDTO.setGoodsImage(image);

            goodsInfoDTOs.add(goodsInfoDTO);
        }

        //订单支付信息
        List<PaymentDTO> paymentDTOs = Lists.newArrayList();
        orderDataDTO.setPaymentDTOs(paymentDTOs); //订单支付信息
        for (TActOrderPayment payment : order.getPayments()) {
            PaymentDTO paymentDTO = new PaymentDTO();

            paymentDTO.setAmount(payment.getAmount());
            paymentDTO.setCurrency(payment.getCurrency());

            paymentDTOs.add(paymentDTO);
        }

        return orderDataDTO;
    }
}
