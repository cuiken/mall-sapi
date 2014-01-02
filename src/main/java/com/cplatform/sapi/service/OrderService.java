package com.cplatform.sapi.service;

import com.cplatform.act.PayResponse;
import com.cplatform.order.*;
import com.cplatform.pay.PayChannel;
import com.cplatform.pay.PayOrderInfo;
import com.cplatform.pay.PaymentInfo;
import com.cplatform.pay.RequestOperate;
import com.cplatform.sapi.DTO.*;
import com.cplatform.sapi.entity.Member;
import com.cplatform.sapi.entity.TMemberAddress;
import com.cplatform.sapi.entity.order.MarketOrder;
import com.cplatform.sapi.entity.order.Seckill;
import com.cplatform.sapi.entity.order.TActOrder;
import com.cplatform.sapi.entity.product.ItemSale;
import com.cplatform.sapi.entity.product.MarketGoods;
import com.cplatform.sapi.entity.product.TStore;
import com.cplatform.sapi.exceptions.OrderServiceException;
import com.cplatform.sapi.orm.Page;
import com.cplatform.sapi.orm.PropertyFilter;
import com.cplatform.sapi.repository.SysRegionDao;
import com.cplatform.sapi.repository.TMemberAddressDao;
import com.cplatform.sapi.repository.ThirdInterfaceDao;
import com.cplatform.sapi.repository.order.TActOrderDao;
import com.cplatform.sapi.repository.order.TMarketOrderDao;
import com.cplatform.sapi.repository.order.TSeckillDao;
import com.cplatform.sapi.repository.product.ItemSaleDao;
import com.cplatform.sapi.util.AppConfig;
import com.cplatform.sapi.util.TimeUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cplatform.sapi.util.Constants.*;

@Component
public class OrderService {

    @Autowired
    private TMarketOrderDao marketOrderDao;

    @Autowired
    private TSeckillDao seckillDao;

    @Autowired
    private TActOrderDao orderDao;

    @Autowired
    private OrderCenterDao orderCenterDao;

    @Autowired
    private TMemberAddressDao memberAddressDao;

    @Autowired
    private SysRegionDao sysRegionDao;

    @Autowired
    private ItemSaleDao itemSaleDao;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private ThirdInterfaceDao thirdInterfaceDao;

    @Autowired
    private BusinessLogger businessLogger;

    public Long createOrder(ActOrderInfo orderInfo) throws Exception {

        Long orderId = orderCenterDao.createOrderRequest(orderInfo);
        if (orderInfo.getPayOnDelivery() == ActOrderInfo.PAY_ON_DELIVERY_TRUE) {
            boolean updateOk = orderCenterDao.bindingPaymentToOrder(orderId, Lists.newArrayList(buildPayment(orderInfo.getTotalPayAmount(),
                    PayChannel.EMS_PAY_ON_DELIVERY,
                    CURRENCY_CASH)));
            if (updateOk) {
                orderInfo = orderCenterDao.getActOrder(orderId);
            }
            List<ActOrderPaymentInfo> paymentInfos = orderInfo.getPaymentInfos();
            if (paymentInfos.size() == 1)
                orderCenterDao.procDeliveryCashPay(orderId, paymentInfos.get(0).getId());
        }
        return orderId;
    }

    public ActOrderInfo buildOrderInfo(CreateOrderDTO orderDTO, Member user) throws Exception {
        Validate.notNull(user, "用户不能为空");

        // 日志记录
        Map logData = Maps.newHashMap();
        logData.put("orderDTO", orderDTO);
        businessLogger.log("order", "create", logData);

        ActOrderInfo order = new ActOrderInfo();
        if (StringUtils.isNotBlank(orderDTO.getOrderType())) {
            order.setOrderType(Integer.valueOf(orderDTO.getOrderType()));
        }
        order.setActType(ORDER_FROM_WEB);
        order.setUserId(user.getId());
        order.setCreateSource(orderDTO.getCreateSource() == null ? ActOrderInfo.CREATE_SOURCE_CLIENT : orderDTO.getCreateSource().intValue());
        order.setExpireTime(chooseExpireTime(orderDTO.getOrderType()));
        order.setSubject(orderDTO.getSubject());
        order.setExtInfo(orderDTO.getBusinessId());
        order.setRemark(orderDTO.getUserRemark());
        order.setPayOnDelivery(orderDTO.getPayOnDelivery());
        // 发票信息
        order.setInvoiceContent(orderDTO.getInvoiceContent());
        order.setInvoiceSubject(orderDTO.getInvoiceSubject());
        order.setInvoiceType(orderDTO.getInvoiceType());

        List<ItemSaleDataDTO> items = this.getItemsByGoods(orderDTO.getGoods());

        checkMemberSpecial(items, user);
        checkGoodPayType(items);

        if (!items.isEmpty()) {
            order.setShopId(items.get(0).getItem().getStoreId());
            order.setShopSubject(items.get(0).getItem().getStoreName());
        }
        // 物流信息
        if (orderDTO.getAddressId() != null || orderDTO.getAddressInfo() != null) {
            if (!isVirtualItem(items))
                order.setExpressInfo(this.initOrderExpressInfo(orderDTO.getAddressInfo(), orderDTO.getAddressId(), items));
        }
        // 订单对应的商品信息清单
        order.setGoodsInfos(this.initActOrderGoodsInfo(items, user, orderDTO.getBusinessId(), orderDTO.getOrderType()));
        if (order.getTotalPayAmount() == 0) {
            order.setPayStatus(ActOrderStatus.PAY_STATUS_PAID);
        }
        return order;
    }

    /**
     * 判断虚拟商品和自提商品
     *
     * @param items
     * @return
     */
    public boolean isVirtualItem(List<ItemSaleDataDTO> items) {
        for (ItemSaleDataDTO item : items) {
            if (item.getItem().getItemMode() == VIRTUAL_ITEM || item.getItem().getPostFlag() == GOODS_F_MENTIONING) {
                return true;
            }
        }
        return false;
    }

    public boolean isUnionMember(String terminalId) {
        // 免费会员不参与价格优惠，故做为非会员处理
        UnionMember unionMember = thirdInterfaceDao.getUnionMemberInfo(terminalId);
        return unionMember.getMember() != null && unionMember.getMember().equals("true") && !unionMember.getBossSet().equals("540001")
                && !unionMember.getBossSet().equals("540005") && !unionMember.getBossSet().equals("700001");
    }

    public boolean isRedMember(Member user) {

        return user != null && user.getRedMember() == 1;
    }

    /**
     * 根据订单类型设置有效时间
     *
     * @param orderType
     * @return
     */
    private int chooseExpireTime(String orderType) {
        if (StringUtils.isNotBlank(orderType)) {
            int type = Integer.valueOf(orderType);
            if (type == ORDER_TYPE_SPIKE) {
                return appConfig.getSpikeOrderExpireTime();
            } else if (type == ORDER_TYPE_AUCTION) {
                return appConfig.getAuctionOrderExpireTime();
            } else if (type == ORDER_TYPE_SPECIAL) {
                return appConfig.getSpecialOrderExpireTime();
            }
        }
        return appConfig.getCommonOrderExpireTime();
    }

    public PayResponse payOrder(PayDTO payDTO, Member loginUser) throws Exception {
        Validate.notNull(payDTO.getOrderId(), "订单ID不能为空");
        // 日志记录
        Map logData = Maps.newHashMap();
        logData.put("payDTO", payDTO);
        businessLogger.log("order", "pay", logData);

        ActOrderInfo orderInfo = orderCenterDao.getActOrder(payDTO.getOrderId());

        checkStatusBeforePay(orderInfo, loginUser);

        if (isFirstPay(orderInfo.getPayStatus())) {

            Validate.notNull(payDTO.getPayForm(), "支付类型不能为空");

            if (payDTO.getPayForm().equals(PayFormChoose.CASH_AND_SCORE.getValue())
                    || payDTO.getPayForm().equals(PayFormChoose.CASH_AND_COIN.getValue())
                    || payDTO.getPayForm().equals(PayFormChoose.ONLY_CASH.getValue())
                    || payDTO.getPayForm().equals(PayFormChoose.CASH_AND_BALANCE.getValue())) {
                Validate.notNull(payDTO.getChannel(), "现金支付渠道不能为空");
            }

            if (orderInfo.getOrderType() == ORDER_TYPE_LASHOU || orderInfo.getOrderType() == ORDER_TYPE_MTICKET) {
                orderInfo = updateGoodsInfoToOrder(orderInfo, payDTO.getPayForm());
            }
            List<ActOrderPaymentInfo> payments = initOrderPayment(orderInfo.getTotalPayAmount(), payDTO);
            boolean updateOk = orderCenterDao.bindingPaymentToOrder(orderInfo.getId(), payments);
            if (updateOk) {
                orderInfo = orderCenterDao.getActOrder(orderInfo.getId());
            }

            orderCenterDao.updateOrderToPaying(orderInfo.getId());

        }

        return payRequest(orderInfo, payDTO);

    }

    private boolean isFirstPay(Integer payStatus) {

        return payStatus == ActOrderStatus.PAY_STATUS_UNPAID;
    }

    /**
     * 请求支付接口
     *
     * @param orderInfo
     * @return
     * @throws Exception
     */
    private PayResponse payRequest(ActOrderInfo orderInfo, PayDTO payDTO) throws Exception {

        Map<String, Object> paymentInfo = this.getPaymentInfoStatus(orderInfo);

        List<ActOrderPaymentInfo> leftPayInfos = (List<ActOrderPaymentInfo>) paymentInfo.get("leftPayInfos");
        Map<String, ActOrderPaymentInfo> leftPayInfoMap = this.toPaymentMap(leftPayInfos);

        if (leftPayInfoMap.containsKey(CURRENCY_COIN)) {
            orderCenterDao.procCoinPay(orderInfo.getId(), leftPayInfoMap.get(CURRENCY_COIN).getId());
        }

        if (leftPayInfoMap.containsKey(CURRENCY_BALANCE)) {
            orderCenterDao.procBalancePay(orderInfo.getId(), leftPayInfoMap.get(CURRENCY_BALANCE).getId());
        }

        if (leftPayInfoMap.containsKey(CURRENCY_SCORE)) {
            orderCenterDao.procScorePay(orderInfo.getId(), leftPayInfoMap.get(CURRENCY_SCORE).getId());
        }

        PayResponse cashResponse = null;
        if (leftPayInfoMap.containsKey(CURRENCY_CASH)) {
            cashResponse = orderCenterDao.procCashPay(orderInfo.getId(), payDTO, leftPayInfoMap.get(CURRENCY_CASH).getId());
        }
        return cashResponse;
    }

    private ActOrderExpressInfo initOrderExpressInfo(CreateOrderDTO.Address addressInfo, Long addressId, List<ItemSaleDataDTO> items) {
        ActOrderExpressInfo actOrderExpressInfo = new ActOrderExpressInfo();
        if (addressId == null && addressInfo != null) {
            actOrderExpressInfo.setCellphoneNumber(addressInfo.getMobile());
            actOrderExpressInfo.setReceiverName(addressInfo.getReceiverName());
            actOrderExpressInfo.setTelephoneNumber(addressInfo.getPhone());
            actOrderExpressInfo.setZipCode(addressInfo.getZipcode());
            actOrderExpressInfo.setAddress(addressInfo.getAddress());
            actOrderExpressInfo.setRemark(addressInfo.getRemark());

        } else if (addressId != null) {
            TMemberAddress address = memberAddressDao.get(addressId);
            if (address != null) {
                actOrderExpressInfo.setCellphoneNumber(address.getMobile());
                actOrderExpressInfo.setReceiverName(address.getName());
                actOrderExpressInfo.setTelephoneNumber(address.getPhone());
                actOrderExpressInfo.setZipCode(address.getZipcode());
                actOrderExpressInfo.setRemark(address.getRemark());
                actOrderExpressInfo.setAddress(sysRegionDao.getFullName(address.getRegion()) + " " + address.getAddress());

            }
        }
        actOrderExpressInfo.setExpressCost(getDeliveryTotalFee(items));
        return actOrderExpressInfo;
    }

    /**
     * 获取运费
     *
     * @param items
     * @return
     */
    public int getDeliveryTotalFee(List<ItemSaleDataDTO> items) {
        int totalFee = 0;
        for (ItemSaleDataDTO item : items) {
            if (item.getItem().getLogisticsFeeType() == 0) {
                totalFee += item.getItem().getLogisticsFee().multiply(new BigDecimal(100)).intValue();

            } else {
                // 按商品数量累计运费
                totalFee += item.getItem().getLogisticsFee().multiply(new BigDecimal(item.getCount())).multiply(new BigDecimal(100)).intValue();
            }
        }
        return totalFee;
    }

    /**
     * 初始化订单商品信息
     *
     * @param items
     * @param user
     * @param businessId
     * @param orderType
     * @return a list of {@link ActOrderGoodsInfo}
     */
    private List<ActOrderGoodsInfo> initActOrderGoodsInfo(List<ItemSaleDataDTO> items, Member user, String businessId, String orderType) {

        List<ActOrderGoodsInfo> goodsInfos = Lists.newArrayList();
        for (ItemSaleDataDTO item : items) {
            if (!GOOD_IS_VALID.equals(item.getItem().getIsvalid())) {
                throw new OrderServiceException(-1, "商品【" + item.getItem().getId() + "】未上架");
            }
            if (!PASSED_REVIEW.equals(item.getItem().getStatus())) {
                throw new OrderServiceException(-1, "商品【" + item.getItem().getId() + "】审核未通过");
            }
            ActOrderGoodsInfo goodsInfo = new ActOrderGoodsInfo();

            goodsInfo.setPayPrice(initGoodsPrice(item, user, businessId, orderType));
            goodsInfo.setCount(item.getCount());
            goodsInfo.setDiscount(item.getDiscount());
            goodsInfo.setGoodsId(item.getItem().getId());
            goodsInfo.setGoodsSubject(item.getItem().getName());
            goodsInfos.add(goodsInfo);
        }
        return goodsInfos;
    }

    public List<ItemSaleDataDTO> getItemsByGoods(List<CreateOrderDTO.Good> goods) throws Exception {
        List<ItemSaleDataDTO> dtos = Lists.newArrayList();
        for (CreateOrderDTO.Good good : goods) {
            ItemSaleDataDTO item = itemSaleDao.getItemById(String.valueOf(good.getId()));
            item.setCount(good.getCount());
            item.setDiscount(good.getDiscount());
            dtos.add(item);
        }
        return dtos;
    }

    /**
     * 检查商品支付方式是否一致
     *
     * @param items
     */
    public void checkGoodPayType(List<ItemSaleDataDTO> items) {

        List<Map<String, Object>> exists = Lists.newArrayList();

        for (int i = 0; i < items.size(); i++) {
            Map<String, Object> pay = Maps.newHashMap();
            ItemSaleDataDTO item = items.get(i);
            pay.put("payType", item.getItem().getPayType());
            pay.put("cash", item.getItem().getCashIdgoods());
            pay.put("coin", item.getItem().getCoinIdgoods());
            pay.put("score", item.getItem().getScoreIdgoods());
            pay.put("billPay", item.getItem().getBillPay());
            pay.put("payOnDelivery", item.getItem().getDeliveryPay());
            if (i == 0) {
                exists.add(pay);
            } else {
                if (!exists.contains(pay)) {
                    throw new OrderServiceException(OrderServiceException.ORDER_PAYTYPE_ERROR);
                }
            }
        }
    }

    /**
     * 获取商品最终的支付价格
     *
     * @param item
     * @param user
     * @param businessId
     * @param orderType
     * @return
     */
    public int initGoodsPrice(ItemSaleDataDTO item, Member user, String businessId, String orderType) {
        if (StringUtils.isNotBlank(businessId) && NumberUtils.isNumber(businessId)) {
            if (String.valueOf(ORDER_TYPE_AUCTION).equals(orderType) || String.valueOf(ORDER_TYPE_SPIKE).equals(orderType)) {
                if (item.getCount() != 1) {
                    throw new OrderServiceException(OrderServiceException.GOOD_NUMBER_ERROR);
                }
                TActOrder businessOrder = orderDao.getBusinessOrder(user.getId(), businessId);
                if (businessOrder != null) {
                    throw new OrderServiceException(OrderServiceException.ORDER_NUMBER_ERROR);
                }
                MarketingResponse marketEntity = thirdInterfaceDao.convertToBean(item.getItem().getId(), orderType, businessId);
                if (marketEntity == null) {
                    return findItemRealPrice(item, user);
                } else if (!marketEntity.getConfirm_userId().equals(String.valueOf(user.getId()))) {
                    throw new OrderServiceException(OrderServiceException.USER_INCORRECT_ERROR);
                }
                return new BigDecimal(marketEntity.getConfirm_price()).multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
            }
        }
        return findItemRealPrice(item, user);
    }

    /**
     * 获取商品实际价格
     */
    public int findItemRealPrice(ItemSaleDataDTO itemInfo, Member user) {
        String priceString = "";
        boolean redMember = isRedMember(user);
        boolean unionMember = isUnionMember(user.getTerminalId());

        Map logMap = Maps.newHashMap();

        if (PANIC_BUYING.equals(itemInfo.getItem().getIseckill())) {
            if (itemInfo.getMarketGoodsProperty() != null) {
                priceString =
                        String.valueOf(itemInfo.getMarketGoodsProperty().getPromotionPrice());
                logMap.put("限时特价", priceString);
            }
        } else {
            if (redMember && unionMember) {
                logMap.put(user.getTerminalId(), "即是红钻，亦是商盟会员");

                Map<String, String> prices = this.getPayPrices(itemInfo.getItemPrice());
                priceString = getMinPrice(prices.get(RED_USER_LEVELS), prices.get(UNION_USER_LEVELS));
            } else if (redMember) {
                logMap.put(user.getTerminalId(), "是红钻");

                priceString = getPayPrices(itemInfo.getItemPrice()).get(RED_USER_LEVELS);
            } else if (unionMember) {
                logMap.put(user.getTerminalId(), "是商盟会员");

                priceString = getPayPrices(itemInfo.getItemPrice()).get(UNION_USER_LEVELS);
            }
        }

        if (StringUtils.isBlank(priceString)) {
            priceString = String.valueOf(itemInfo.getItem().getShopPrice());
        }
        logMap.put("userId", user.getId());
        logMap.put("goodsId", itemInfo.getItem().getId());
        logMap.put("finallyPrice", priceString);
        businessLogger.log("order", "findItemRealPrice", logMap);

        return new BigDecimal(priceString).multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }

    private Map<String, String> getPayPrices(List<ItemSaleDataDTO.ItemPrice> itemPrices) {
        Map<String, String> map = Maps.newHashMap();
        for (ItemSaleDataDTO.ItemPrice itemPrice : itemPrices) {
            String type = itemPrice.getPriceTypeCode();
            String price = String.valueOf(itemPrice.getPrice());
            map.put(type, price);
        }
        return map;
    }

    /**
     * 获取两个会员价的最低
     *
     * @param redMemberPrice
     * @param unionMemberPrice
     * @return
     */
    private String getMinPrice(String redMemberPrice, String unionMemberPrice) {
        String priceString = "";
        if (StringUtils.isNotBlank(redMemberPrice) && StringUtils.isNotBlank(unionMemberPrice)) {
            int result = new BigDecimal(redMemberPrice).compareTo(new BigDecimal(unionMemberPrice));
            if (result > 0) { // 红钻价 > 商盟价
                priceString = unionMemberPrice;
            } else {
                priceString = redMemberPrice;
            }
        } else if (StringUtils.isBlank(redMemberPrice)) {
            priceString = unionMemberPrice;
        } else if (StringUtils.isBlank(unionMemberPrice)) {
            priceString = redMemberPrice;
        }
        return priceString;
    }

    /**
     * 专属商品购买检查
     */
    public void checkMemberSpecial(List<ItemSaleDataDTO> items, Member user) {

        for (ItemSaleDataDTO item : items) {
            String userLevels = item.getItem().getUserLevels();
            MarketGoods marketGoodsDTO = item.getMarketGoodsProperty();
            if (PANIC_BUYING.equals(item.getItem().getIseckill()) && marketGoodsDTO != null) {

                String buyLimit = marketGoodsDTO.getBuyLimit(); // 数据库里蛋疼的格式：null
                // 0为无限制，1为商盟会员
                // 2为红砖会员
                if (StringUtils.isBlank(buyLimit) || "0".equals(buyLimit)) {
                    return;
                }
                if (StringUtils.equals(buyLimit, "2")) {
                    userLevels = RED_USER_LEVELS;
                } else if (StringUtils.equals(buyLimit, "1")) {
                    userLevels = UNION_USER_LEVELS;
                } else if (StringUtils.contains(buyLimit, "1") && StringUtils.contains(buyLimit, "1")) {
                    userLevels = RED_USER_LEVELS + "," + UNION_USER_LEVELS;
                }
            }
            if (StringUtils.isNotBlank(userLevels)) {
                checkMemberSpecial(user, userLevels);
            }
        }
    }

    private void checkMemberSpecial(Member user, String userLevels) {
        boolean isUnionMember = this.isUnionMember(user.getTerminalId());
        boolean isRedMember = (user.getRedMember() == RED_MEMBER);
        if (RED_USER_LEVELS.equals(userLevels)) {
            Preconditions.checkState(isRedMember, "不能购买红砖会员专属商品");
        } else if (UNION_USER_LEVELS.equals(userLevels)) {
            Preconditions.checkState(isUnionMember, "不能购买商盟会员专属商品");
        } else if (userLevels.contains(RED_USER_LEVELS) && userLevels.contains(UNION_USER_LEVELS)) {
            Preconditions.checkState(isRedMember || isUnionMember, "不能购买会员专属商品");
        }
    }

    public void checkValidPay(CheckPayParam param) throws Exception {
        Validate.notNull(param.getPayForm(), "支付方式不能为空");

        Map log = Maps.newHashMap();
        log.put("param", param);
        businessLogger.log("order", "payCheck", log);

        ActOrderInfo orderInfo = orderCenterDao.getActOrder(param.getOrderId());
        checkStatusBeforePay(orderInfo, param.getUser());

        // 检查支付信息
        PayFormChoose payMethod = Enum.valueOf(PayFormChoose.class, StringUtils.upperCase(param.getPayForm()));
        switch (payMethod) {
            case ONLY_SCORE:
                Validate.isTrue(param.getScore() == orderInfo.getTotalPayAmount(), "积分金额不符");
                checkPayMoney(orderInfo, param.getScore(), CURRENCY_SCORE);
                break;
            case ONLY_COIN:
                Validate.isTrue(param.getCoin() == orderInfo.getTotalPayAmount(), "商城币金额不符");
                checkPayMoney(orderInfo, param.getCoin(), CURRENCY_COIN);
                break;
            case ONLY_CASH:
                Validate.isTrue(param.getBalance() == 0 && param.getScore() == 0 && param.getCoin() == 0);
                checkPayMoney(orderInfo, orderInfo.getTotalPayAmount(), CURRENCY_CASH);
                break;
            case ONLY_BALANCE:
                Validate.isTrue(param.getBalance() == orderInfo.getTotalPayAmount(), "话费金额不符");
                checkPayMoney(orderInfo, param.getBalance(), CURRENCY_BALANCE);
                break;
            case COIN_AND_BALANCE:
                Validate.isTrue(param.getCoin() + param.getBalance() == orderInfo.getTotalPayAmount(), "支付金额不足");
                break;
            case SCORE_AND_BALANCE:
                Validate.isTrue(param.getScore() + param.getBalance() == orderInfo.getTotalPayAmount(), "支付金额不足");
                break;
            case CASH_AND_BALANCE:
                checkPayMoney(orderInfo, param.getBalance(), CURRENCY_BALANCE);
                break;
            case CASH_AND_COIN:
                checkPayMoney(orderInfo, param.getCoin(), CURRENCY_COIN);
                break;
            case CASH_AND_SCORE:
                checkPayMoney(orderInfo, param.getScore(), CURRENCY_SCORE);
                break;
        }
    }

    private void checkPayMoney(ActOrderInfo orderInfo, int payMoney, String payType) throws Exception {
        int totalAmount = orderInfo.getTotalPayAmount();
        if (orderInfo.getOrderType() == ORDER_TYPE_LASHOU || orderInfo.getOrderType() == ORDER_TYPE_MTICKET) {
            totalAmount = changeWithTariff(totalAmount);
        }
        Validate.isTrue(payMoney >= 0, "支付金额需大于零");
        if (isFirstPay(orderInfo.getPayStatus())) {
            if (payMoney > totalAmount) {
                throw new OrderServiceException(OrderServiceException.PAY_AMOUNT_ERROR);
            }
        } else {
            Map<String, Object> paymentInfo = this.getPaymentInfoStatus(orderInfo);
            List<ActOrderPaymentInfo> leftPayInfos = (List<ActOrderPaymentInfo>) paymentInfo.get("leftPayInfos");
            Map<String, ActOrderPaymentInfo> leftPayInfoMap = this.toPaymentMap(leftPayInfos);
            if (leftPayInfoMap.containsKey(payType)) {
                if (payMoney != leftPayInfoMap.get(payType).getAmount()) {
                    throw new OrderServiceException(OrderServiceException.PAY_AMOUNT_ERROR);
                }
            } else {
                Validate.isTrue(payMoney == 0, "无[" + payType + "]支付项");
            }

        }
    }

    private void checkStatusBeforePay(ActOrderInfo orderInfo, Member user) throws Exception {
        Validate.notNull(orderInfo, "订单不存在");
        Validate.notNull(user, "用户不能为空");

        if (StringUtils.isBlank(user.getTerminalId())) {
            throw new OrderServiceException(OrderServiceException.NEED_BIND_MOBILE);
        }
        if (!user.getId().equals(orderInfo.getUserId())) {
            throw new OrderServiceException(OrderServiceException.PAY_CHECK_ERROR, "下单用户和付款用户不一致。");
        }

        // 订单已关闭
        if (orderInfo.getCloseStatus() == ActOrderStatus.CLOSE_STATUS_CLOSED) {
            throw new OrderServiceException(OrderServiceException.PAY_CHECK_ERROR, "订单已关闭。");
        }

        // 订单已删除
        if (orderInfo.getDeleteStatus() == ActOrderInfo.DELETE_YES) {
            throw new OrderServiceException(OrderServiceException.PAY_CHECK_ERROR, "订单已删除。");
        }

        // 订单已支付
        if (orderInfo.getPayStatus() == ActOrderStatus.PAY_STATUS_PAID) {
            throw new OrderServiceException(OrderServiceException.PAY_CHECK_ERROR, "订单已支付成功。");
        }

        List<PayOrderInfo> payOrderInfos = orderCenterDao.getPayOrderInfos(orderInfo.getId());
        if (existRefundInfo(payOrderInfos)) {
            throw new OrderServiceException(OrderServiceException.PAY_CHECK_ERROR, "该订单在退款中或已退款，不能再次支付。");
        }
    }

    /**
     * 检查是否存在退款信息
     *
     * @param payOrderInfos
     * @return
     */
    public boolean existRefundInfo(List<PayOrderInfo> payOrderInfos) {
        for (PayOrderInfo payOrderInfo : payOrderInfos) {
            if (payOrderInfo.getOperate() == RequestOperate.Refund) {
                return true;
            }
        }
        return false;
    }

    public Map<String, Object> getPaymentInfoStatus(ActOrderInfo orderInfo) throws Exception {
        Map<String, Object> infos = Maps.newHashMap();
        List<PayOrderInfo> payOrderInfos = orderCenterDao.getPayOrderInfos(orderInfo.getId());
        List<ActOrderPaymentInfo> paymentInfos = orderInfo.getPaymentInfos();
        Map<String, Integer> payedInfo = getAllPayedInformation(payOrderInfos);
        List<ActOrderPaymentInfo> leftPayInfos = getLeftPayInfos(paymentInfos, payedInfo);
        infos.put("paymentInfos", paymentInfos);
        infos.put("payedInfo", payedInfo);
        infos.put("leftPayInfos", leftPayInfos);
        return infos;
    }

    public Map<String, Integer> getPayedInfo(Long orderId) throws Exception {

        List<PayOrderInfo> payOrderInfos = orderCenterDao.getPayOrderInfos(orderId);

        return getAllPayedInformation(payOrderInfos);
    }

    /**
     * 计算已付款的金额，及类型
     *
     * @param payOrderInfos
     * @return
     */
    public Map<String, Integer> getAllPayedInformation(List<PayOrderInfo> payOrderInfos) {
        Map<String, Integer> result = new HashMap<String, Integer>();
        for (PayOrderInfo payOrderInfo : payOrderInfos) {
            if (payOrderInfo.getStatus() != PayOrderInfo.STATUS_SUCCESS)
                continue;
            List<PaymentInfo> paymentInfos = payOrderInfo.getPayments();
            for (PaymentInfo paymentInfo : paymentInfos) {
                String currency = paymentInfo.getCurrency();
                int countAmount = 0;
                if (result.containsKey(currency)) {
                    countAmount = result.get(currency);
                }
                result.put(currency, countAmount + paymentInfo.getAmount());
            }
        }
        return result;
    }

    /**
     * 获取到剩余还需要支付的支付项目
     *
     * @param paymentInfos
     * @param payedInfo
     * @return
     */
    public List<ActOrderPaymentInfo> getLeftPayInfos(List<ActOrderPaymentInfo> paymentInfos, Map<String, Integer> payedInfo) {
        List<ActOrderPaymentInfo> leftPayInfos = new ArrayList<ActOrderPaymentInfo>();
        for (ActOrderPaymentInfo paymentInfo : paymentInfos) {
            ActOrderPaymentInfo leftpay = new ActOrderPaymentInfo();
            try {
                BeanUtils.copyProperties(leftpay, paymentInfo);
            } catch (Exception e) {
                // ignore;
            }

            if (payedInfo.containsKey(leftpay.getCurrency())) {
                leftpay.setAmount(leftpay.getAmount() - payedInfo.get(leftpay.getCurrency()));
            }

            if (leftpay.getAmount() > 0) {
                leftPayInfos.add(leftpay);
            }
        }
        return leftPayInfos;
    }

    public Map<String, ActOrderPaymentInfo> toPaymentMap(List<ActOrderPaymentInfo> infos) {
        Map<String, ActOrderPaymentInfo> map = new HashMap<String, ActOrderPaymentInfo>();
        for (ActOrderPaymentInfo info : infos) {
            map.put(info.getCurrency(), info);
        }
        return map;
    }

    /**
     * 初始化订单支付信息
     *
     * @param payAmount
     * @param payInfo
     * @return a list of {@link ActOrderPaymentInfo}
     */
    public List<ActOrderPaymentInfo> initOrderPayment(int payAmount, PayDTO payInfo) {

        validatePayAmount(payInfo, payAmount);

        int coinAmount = payInfo.getCoin();
        int balanceAmount = payInfo.getBalance();
        int scoreAmount = payInfo.getScore();

        List<ActOrderPaymentInfo> paymentInfos = Lists.newArrayList();

        if (coinAmount > 0) {
            paymentInfos.add(buildPayment(coinAmount, PayChannel.UNIFY_CART_COIN, CURRENCY_COIN));
        }
        if (scoreAmount > 0) {
            paymentInfos.add(buildPayment(scoreAmount, PayChannel.UNIFY_CART_SCORE, CURRENCY_SCORE));
        }
        if (balanceAmount > 0) {
            paymentInfos.add(buildPayment(balanceAmount, PayChannel.UNIFY_BALANCE, CURRENCY_BALANCE));
        }
        int needCash = payAmount - scoreAmount - coinAmount - balanceAmount;
        needCash = needCash < 0 ? 0 : needCash;

        if (needCash > 0) {
            paymentInfos.add(buildPayment(needCash, payInfo.getChannel(), CURRENCY_CASH));

        }
        Validate.isTrue(paymentInfos.size() > 0 && paymentInfos.size() <= 2, "最多支持两种组合支付");
        return paymentInfos;
    }

    public void validatePayAmount(PayDTO payDTO, int payAmount) {

        int scoreAmount = payDTO.getScore();
        int coinAmount = payDTO.getCoin();
        int balanceAmount = payDTO.getBalance();
        String payForm = payDTO.getPayForm();
        String msg = "支付金额设置不正确";
        Validate.isTrue(coinAmount >= 0 && balanceAmount >= 0 && scoreAmount >= 0, "金额必需是正数");
        Validate.isTrue(payAmount >= scoreAmount && payAmount >= coinAmount && payAmount >= balanceAmount, "总金额小于支付金额");
        PayFormChoose payMethod = Enum.valueOf(PayFormChoose.class, StringUtils.upperCase(payForm));
        switch (payMethod) {
            case CASH_AND_COIN:
                Validate.isTrue(balanceAmount == 0 && scoreAmount == 0, msg);
                break;
            case CASH_AND_SCORE:
                if (scoreAmount > 0) {
                    Validate.isTrue(scoreAmount >= 10, msg);
                }
                Validate.isTrue(balanceAmount == 0 && coinAmount == 0, msg);
                break;
            case CASH_AND_BALANCE:
                Validate.isTrue(coinAmount == 0 && scoreAmount == 0, msg);
                break;
            case COIN_AND_BALANCE:
                Validate.isTrue(scoreAmount == 0 && (coinAmount + balanceAmount) == payAmount, msg);
                break;
            case SCORE_AND_BALANCE:
                Validate.isTrue(coinAmount == 0 && scoreAmount + balanceAmount == payAmount, msg);
                break;
            case ONLY_BALANCE:
                Validate.isTrue(scoreAmount == 0 && coinAmount == 0 && balanceAmount == payAmount, msg);
                break;
            case ONLY_COIN:
                Validate.isTrue(scoreAmount == 0 && balanceAmount == 0 && coinAmount == payAmount, msg);
                break;
            case ONLY_SCORE:
                Validate.isTrue(coinAmount == 0 && balanceAmount == 0 && scoreAmount >= 10 && scoreAmount == payAmount, msg);
                break;
        }
    }

    public ActOrderInfo updateGoodsInfoToOrder(ActOrderInfo actOrderInfo, String payForm) throws Exception {

        PayFormChoose payMethod = Enum.valueOf(PayFormChoose.class, StringUtils.upperCase(payForm));
        switch (payMethod) {
            case ONLY_BALANCE:
            case ONLY_COIN:
            case ONLY_SCORE:
                List<ActOrderGoodsInfo> goodsInfos = actOrderInfo.getGoodsInfos();
                boolean lashou = actOrderInfo.getOrderType() == ORDER_TYPE_LASHOU;
                boolean mticket = actOrderInfo.getOrderType() == ORDER_TYPE_MTICKET;
                for (ActOrderGoodsInfo goodsInfo : goodsInfos) {
                    if (lashou) {
                        goodsInfo.setPayPrice(changeWithTariff(goodsInfo.getPayPrice()));
                    }
                    if (mticket) {
                        goodsInfo.setPayPrice(changeWithMTicket(goodsInfo.getPayPrice()));
                    }
                }
                boolean updateOk = orderCenterDao.updateGoodsInfo(actOrderInfo.getId(), goodsInfos);
                if (updateOk) {
                    return orderCenterDao.getActOrder(actOrderInfo.getId());
                }
        }

        return actOrderInfo;
    }

    /**
     * 拉手商品 话费 ，商城币 金额计算
     *
     * @param price 商品价格, 单位：分
     * @return 加上税率后价格
     */
    public int changeWithTariff(int price) {

        return new BigDecimal(price).divide(new BigDecimal(100)).multiply(new BigDecimal("1.06")).setScale(1, BigDecimal.ROUND_UP)
                .multiply(new BigDecimal(100)).intValue();

    }

    /**
     * 客户端电影票 支付金额
     *
     * @param price 商品单价，单位：分
     * @return 加手续费后的价格
     */
    public int changeWithMTicket(int price) {
        return new BigDecimal(price).divide(new BigDecimal(100)).multiply(new BigDecimal("1.1")).setScale(2, BigDecimal.ROUND_UP)
                .multiply(new BigDecimal(100)).intValue();
    }

    /**
     * RMB->积分换算 比例 1:66
     *
     * @param totalAmount 订单总价 单位：rmb分
     * @return 积分
     */
    public int changeToScore(int totalAmount) {
        int tariff = changeWithTariff(totalAmount);
        return new BigDecimal(tariff).divide(new BigDecimal(100)).divide(new BigDecimal("0.015"), 0, BigDecimal.ROUND_DOWN)
                .multiply(new BigDecimal(100)).intValue();
    }

    public ActOrderPaymentInfo buildPayment(int amount, String channel, String currency) {
        Validate.isTrue(amount > 0, "金额必须大于0");

        ActOrderPaymentInfo paymentInfo = new ActOrderPaymentInfo();
        paymentInfo.setChannal(channel);
        paymentInfo.setCurrency(currency);
        paymentInfo.setAmount(amount);

        return paymentInfo;
    }

    public Page<MarketOrder> getMarketOrder(final Page<MarketOrder> page, final List<PropertyFilter> filters) {
        return marketOrderDao.findPage(page, filters);
    }

    public Page<Seckill> getSeckillOrder(final Page<Seckill> page, final List<PropertyFilter> filters) {
        return seckillDao.findPage(page, filters);
    }

    public TActOrder getOrderByExtInfo(Long userId, String businessId, Long type) {
        return orderDao.findUnique(Restrictions.eq("userId", userId), Restrictions.eq("extInfo", businessId), Restrictions.eq("orderType", type));
    }

    /**
     * 设置G实惠查询条件
     *
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    public List<PropertyFilter> buildGCheapOrderFilter(HttpServletRequest request) throws UnsupportedEncodingException {
        List<PropertyFilter> filters = Lists.newArrayList();
        String idString = request.getParameter("userId");
        if (StringUtils.isNotEmpty(idString)) {
            filters.add(new PropertyFilter("EQL_userId", idString));
        }
        String nameString = request.getParameter("goodsName");
        if (StringUtils.isNotEmpty(nameString)) {
            nameString = new String(nameString.getBytes("ISO-8859-1"), "UTF-8");
            nameString = URLDecoder.decode(nameString, "UTF-8");
            filters.add(new PropertyFilter("LIKES_goodsName", nameString));
        }
        String startTime = request.getParameter("startTime");
        if (StringUtils.isNotEmpty(startTime)) {
            filters.add(new PropertyFilter("GED_createTime", startTime));
        }
        String endTime = request.getParameter("endTime");
        if (StringUtils.isNotEmpty(endTime)) {
            filters.add(new PropertyFilter("LED_createTime", endTime));
        }
        return filters;
    }

    /**
     * 组合竞拍订单返回的内容
     *
     * @param itemSale
     * @param store
     * @param marketOrder
     * @param order
     * @return
     */
    public GCheapDTO.Data createMarketDeposit(ItemSale itemSale, TStore store, MarketOrder marketOrder, TActOrder order) {

        GCheapDTO.Data deposit = new GCheapDTO.Data();
        deposit.setImgPath(itemSale.getImgPath());
        deposit.setStoreId(itemSale.getStoreId());
        deposit.setStoreName(store.getName());
        deposit.setGoodsName(itemSale.getName());
        deposit.setBusinessId(marketOrder.getId());
        deposit.setGoodsId(Long.parseLong(marketOrder.getProductId()));
        deposit.setCreateTime(TimeUtil.formartString(marketOrder.getCreateTime()));
        deposit.setPrice(marketOrder.getAuctionPrice() + "");
        if (null != order) {
            deposit.setOrderId(order.getId());
            deposit.setStatus(order.getStatus());
            deposit.setBuyTime(order.getCreateTime());
            deposit.setPayTime(order.getPayTime());
        } else {
            deposit.setStatus(0);
            deposit.setBuyTime("");
            deposit.setPayTime("");
        }
        return deposit;
    }

    /**
     * 组合秒杀订单返回的内容
     *
     * @param itemSale
     * @param store
     * @param seckill
     * @param order
     * @return
     */
    public GCheapDTO.Data createSeckillDeposit(ItemSale itemSale, TStore store, Seckill seckill, TActOrder order) {
        GCheapDTO.Data deposit = new GCheapDTO.Data();
        deposit.setBusinessId(seckill.getId());
        deposit.setGoodsId(seckill.getGoodsNo());
        deposit.setCreateTime(TimeUtil.formartString(seckill.getCreateTime()));
        deposit.setPrice(seckill.getSeckillPrice() + "");
        deposit.setImgPath(itemSale.getImgPath());
        deposit.setStoreId(itemSale.getStoreId());
        deposit.setStoreName(store.getName());
        deposit.setGoodsName(itemSale.getName());
        if (null != order) {
            deposit.setOrderId(order.getId());
            deposit.setStatus(order.getStatus());
            deposit.setBuyTime(order.getCreateTime());
            deposit.setPayTime(order.getPayTime());
        } else {
            deposit.setStatus(0);
            deposit.setBuyTime("");
            deposit.setPayTime("");
        }
        return deposit;
    }

}
