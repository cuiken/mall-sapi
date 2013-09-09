package com.cplatform.sapi.service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cplatform.act.ActOrderCreateRequest;
import com.cplatform.act.ActOrderCreateResponse;
import com.cplatform.act.ActServiceClient;
import com.cplatform.act.PayRequest;
import com.cplatform.act.PayResponse;
import com.cplatform.order.ActOrderExpressInfo;
import com.cplatform.order.ActOrderGoodsInfo;
import com.cplatform.order.ActOrderInfo;
import com.cplatform.order.ActOrderPaymentInfo;
import com.cplatform.order.ActOrderServiceClient;
import com.cplatform.order.ActOrderStatus;
import com.cplatform.pay.PayOrderInfo;
import com.cplatform.pay.PayServiceClient;
import com.cplatform.pay.PaymentInfo;
import com.cplatform.pay.RequestOperate;
import com.cplatform.sapi.DTO.CreateOrderDTO;
import com.cplatform.sapi.DTO.ItemSaleDataDTO;
import com.cplatform.sapi.DTO.PayDTO;
import com.cplatform.sapi.entity.Member;
import com.cplatform.sapi.entity.TMemberAddress;
import com.cplatform.sapi.entity.order.Deposit;
import com.cplatform.sapi.entity.order.MarketOrder;
import com.cplatform.sapi.entity.order.Seckill;
import com.cplatform.sapi.entity.order.TActOrder;
import com.cplatform.sapi.entity.product.ItemSale;
import com.cplatform.sapi.entity.product.TStore;
import com.cplatform.sapi.exceptions.InterfaceException;
import com.cplatform.sapi.exceptions.OrderServiceException;
import com.cplatform.sapi.mapper.JsonMapper;
import com.cplatform.sapi.orm.Page;
import com.cplatform.sapi.orm.PropertyFilter;
import com.cplatform.sapi.repository.order.TActOrderDao;
import com.cplatform.sapi.repository.order.TMarketOrderDao;
import com.cplatform.sapi.repository.order.TSeckillDao;
import com.cplatform.sapi.util.AppConfig;
import com.cplatform.sapi.util.CommonUtils;
import com.cplatform.sapi.util.JsonRespWrapper;
import com.cplatform.sapi.util.TimeUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author Administrator
 */
@Component
public class OrderService {

	private static Logger logger = Logger.getLogger(OrderService.class);

	public static String CURRENCY_COIN = "coin";

	public static String CURRENCY_CASH = "cash";

	private static final int ORDER_FROM_WEB = 40;

	private static final int ORDER_EXPRIRE_TIME = 3600;

	@Autowired
	AppConfig appConfig;

	@Autowired
	ActServiceClient actBusinessClient;

	@Autowired
	private PayServiceClient payServiceClient;

	@Autowired
	ActOrderServiceClient actOrderClient;

	@Autowired
	private TMarketOrderDao marketOrderDao;

	@Autowired
	private TSeckillDao seckillDao;

	@Autowired
	private TActOrderDao orderDao;

	@Autowired
	private TMemberAddressService memberAddressService;

	@Autowired
	private BusinessLogger businessLogger;

	/**
	 * 转化参数来源为接口请求来源值
	 *
	 * @param channel
	 * @return
	 */
	public int genChannel(String channel) {
		if ("SMS".equals(channel)) {
			return ActOrderInfo.CREATE_SOURCE_SMS;
		} else if ("WEB".equals(channel)) {
			return ActOrderInfo.CREATE_SOURCE_WEB;
		} else if ("WAP".equals(channel)) {
			return ActOrderInfo.CREATE_SOURCE_WAP;
		} else if ("CLIENT".equals(channel)) {
			return ActOrderInfo.CREATE_SOURCE_CLIENT;
		} else {
			return ActOrderInfo.CREATE_SOURCE_OTHER;
		}
	}

	/**
	 * 创建订单
	 *
	 * @param orderInfo
	 * @return
	 */
	public long createOrderRequest(ActOrderInfo orderInfo) {
		ActOrderCreateRequest request = new ActOrderCreateRequest();
		request.setActOrderInfo(orderInfo);
		ActOrderCreateResponse resp = actBusinessClient.createActOrder(request);
		logger.debug("下单回包： " + resp);
		if (resp.getStatus() != ActOrderCreateResponse.STATUS_OK) {
			logger.info("创建订单失败: " + resp.getStatusText());
			throw new InterfaceException(resp.getStatus(), resp.getStatusText());
		}
		return resp.getActOrderId();
	}

	public Long createOrder(CreateOrderDTO orderDTO) {
		// 日志记录
		Map logData = Maps.newHashMap();
		logData.put("orderDTO", orderDTO);
		businessLogger.log("order", "create", logData);

		ActOrderInfo order = new ActOrderInfo();
		order.setActType(ORDER_FROM_WEB);
		order.setUserId(orderDTO.getUserId());
		order.setCreateSource(ActOrderInfo.CREATE_SOURCE_CLIENT);
		order.setExpireTime(ORDER_EXPRIRE_TIME);
		// 发票信息
		order.setInvoiceContent(orderDTO.getInvoiceContent());
		order.setInvoiceSubject(orderDTO.getInvoiceSubject());
		order.setInvoiceType(orderDTO.getInvoiceType());

		ItemSaleDataDTO item = this.getItemInfo(String.valueOf(orderDTO.getGoodsId()));

		order.setShopId(item.getItem().getStoreId());
		order.setShopSubject(item.getItem().getStoreName());

		order.setExpressInfo(this.initOrderExpressInfo(orderDTO.getAddressId()));

		ActOrderGoodsInfo goodsInfo = new ActOrderGoodsInfo();
		goodsInfo.setCount(orderDTO.getCount());
		// goodsInfo.setDiscount(discount);
		goodsInfo.setPayPrice(item.getItem().getMarketPrice().intValue());
		goodsInfo.setGoodsId(item.getItem().getId());
		goodsInfo.setGoodsSubject(item.getItem().getName());
		order.getGoodsInfos().add(goodsInfo);
		return this.createOrderRequest(order);
	}

	public JsonRespWrapper payOrder(PayDTO payDTO, Member loginUser) throws Exception {

		// 日志记录
		Map logData = Maps.newHashMap();
		logData.put("payDTO", payDTO);
		businessLogger.log("order", "pay", logData);

		ActOrderInfo orderInfo = actOrderClient.getActOrderInfo(payDTO.getOrderId());
		if (orderInfo == null) {
			JsonRespWrapper jsonRespWrapper = JsonRespWrapper.json();
			jsonRespWrapper.put("FLAG", "-1");
			jsonRespWrapper.put("MSG", "订单信息不存在");
			return jsonRespWrapper;
		}
		this.payCheck(orderInfo, loginUser);

		// cmpay,alipay,scorepay,coinpay
		// 对应:手机支付、支付宝支付、积分支付、商城币支付
		if (!"alipay".equals(payDTO.getChannel())) {
			payDTO.setChannel("cmpay");
		}

		if (orderInfo.getPayStatus() == ActOrderStatus.PAY_STATUS_UNPAID) {
			orderInfo = this.bindingPaymentToOrder(orderInfo, payDTO.getCoin(), payDTO.getChannel());
		}
		Map<String, Object> paymentInfo = this.getPaymentInfoStatus(orderInfo);

		try {
			this.updateOrderToPaying(orderInfo);
		}
		catch (Exception ex) {
			throw new OrderServiceException(OrderServiceException.INTERFACE_FATAL, "更新订单状态失败。");
		}

		List<ActOrderPaymentInfo> leftPayInfos = (List<ActOrderPaymentInfo>) paymentInfo.get("leftPayInfos");
		Map<String, ActOrderPaymentInfo> leftPayInfoMap = this.toPaymentMap(leftPayInfos);

		if (leftPayInfoMap.containsKey(OrderService.CURRENCY_COIN)) {
			PayResponse coinResponse = this.procCoinPay(orderInfo, leftPayInfoMap.get(OrderService.CURRENCY_COIN));
			if (coinResponse.getStatus() != PayResponse.STATUS_OK) {
				throw new InterfaceException(coinResponse.getStatus(), coinResponse.getStatusText());
			}
		}

		PayResponse cashResponse = null;
		if (leftPayInfoMap.containsKey(OrderService.CURRENCY_CASH)) {
			cashResponse = this.procCashPay(orderInfo, payDTO.getPayType(), payDTO.getChannel(), leftPayInfoMap.get(OrderService.CURRENCY_CASH), payDTO.getReturnURL());
			if (cashResponse.getStatus() != PayResponse.STATUS_OK) {
				throw new InterfaceException(cashResponse.getStatus(), cashResponse.getStatusText());
			}
		}

		JsonRespWrapper result = JsonRespWrapper.json();
		result.json("FLAG", "0");
		result.json("MSG", "操作成功");

		result.json("ORDER_ID", orderInfo.getId());
		result.json("POST_TYPE", "none");

		// 没有现金支付
		if (!leftPayInfoMap.containsKey(OrderService.CURRENCY_CASH)) {
			ActOrderPaymentInfo payinfo = leftPayInfoMap.get(OrderService.CURRENCY_COIN);
			if (payinfo != null) {
				result.json("COIN", payinfo.getAmount());
			}
			return result;
		}

		if (cashResponse != null) {
			ActOrderPaymentInfo payinfo = leftPayInfoMap.get(OrderService.CURRENCY_CASH);
			if (payinfo != null) {
				result.json("CASH", payinfo.getAmount());
			}
			String url = cashResponse.getRedirectUrl();
			if (StringUtils.isNotEmpty(url)) {
				result.json("POST_TYPE", "redirect");
				result.json("REDIRECT_URL", url);
				return result;
			}
			result.json("POST_TYPE", "form");
			result.json("FORM_ACTION_URL", cashResponse.getFormActionUrl());
			result.json("HTML", cashResponse.getHtml());
			return result;
		}
		return result;
	}

	// /**
	// * 生成订单
	// *
	// * @param itemId
	// * @param quantity
	// * @param price
	// * @param discount
	// * @param channel
	// * @param remark
	// * @return
	// */
	// public ActOrderInfo newOrder(String itemId, int quantity, int price, int
	// discount, String channel, String remark, Member loginUser) {
	//
	// JSONObject itemInfo = getItemFromInterface(itemId);
	// if (itemInfo == null) {
	// throw new OrderServiceException(OrderServiceException.ITEM_NOT_EXIST);
	// }
	// JSONObject item = itemInfo.getJSONObject("item");
	// int realPrice = findItemRealPrice(item, loginUser);
	// if (realPrice != price) {
	// throw new OrderServiceException(OrderServiceException.PRICE_NOT_MATCH);
	// }
	// long shopId = item.getLong("storeId");
	//
	// ActOrderInfo actOrderInfo = new ActOrderInfo();
	// actOrderInfo.setActType(40); // WEB下单
	// actOrderInfo.setUserId(loginUser.getId());
	// actOrderInfo.setCreateSource(genChannel(channel));
	// actOrderInfo.setExpireTime(appConfig.getOrderExpireTime());
	// actOrderInfo.setRemark(remark);
	// actOrderInfo.setExpressInfo(new ActOrderExpressInfo());
	// actOrderInfo.setShopId(shopId);
	// actOrderInfo.setShopSubject(item.getString("storeName"));
	// // actOrderInfo.setSubject(busiOrderId);
	//
	// ActOrderGoodsInfo goodsInfo = new ActOrderGoodsInfo();
	// goodsInfo.setCount(quantity);
	// goodsInfo.setDiscount(discount);
	// goodsInfo.setPayPrice(price);
	// goodsInfo.setGoodsId(item.getLong("id"));
	// goodsInfo.setGoodsSubject(item.getString("name"));
	// actOrderInfo.getGoodsInfos().add(goodsInfo);
	// return actOrderInfo;
	// }

	private ActOrderExpressInfo initOrderExpressInfo(Long addressId) {
		ActOrderExpressInfo actOrderExpressInfo = new ActOrderExpressInfo();
		TMemberAddress address = memberAddressService.getMemberAddress(addressId);
		if (address != null) {
			actOrderExpressInfo.setCellphoneNumber(address.getMobile());
			actOrderExpressInfo.setReceiverName(address.getName());
			actOrderExpressInfo.setTelephoneNumber(address.getPhone());
			actOrderExpressInfo.setZipCode(address.getZipcode());
			actOrderExpressInfo.setRemark(address.getRemark());
			actOrderExpressInfo.setAddress(address.getAddress());
		}
		return actOrderExpressInfo;
	}

	/**
	 * 获取商品信息
	 *
	 * @param itemId
	 * @return 如果商品接口返回的数据不正确，则返回null
	 */
	public ItemSaleDataDTO getItemInfo(String itemId) {
		if (itemId == null)
			return null;
		String itemJson = CommonUtils.getResponseFromServer(appConfig.getInterfaceItemInfo() + "?saleId=" + itemId, "utf-8");
		JsonMapper mapper = JsonMapper.buildNormalMapper();
		return mapper.fromJson(itemJson, ItemSaleDataDTO.class);
	}

	// /**
	// * 获取商品信息
	// *
	// * @param itemId
	// * @return 如果商品接口返回的数据不正确，则返回null
	// */
	// public JSONObject getItemFromInterface(String itemId) {
	// if (itemId == null)
	// return null;
	// String resp =
	// CommonUtils.getResponseFromServer(appConfig.getInterfaceItemInfo() +
	// "?saleId=" + itemId, "utf-8");
	// JSONObject jsonObject = JSONObject.fromObject(resp);
	// if (jsonObject.getJSONObject("item").isNullObject()) {
	// return null;
	// } else {
	// return jsonObject;
	// }
	// }

	/**
	 * 获取商品实际价格
	 *
	 * @param itemInfo
	 * @param loginUser
	 * @return
	 */
	public int findItemRealPrice(JSONObject itemInfo, Member loginUser) {
		String priceString = "";
		if (loginUser.getRedMember() == 1) {
			JSONArray array = itemInfo.getJSONArray("itemPrice");
			for (int i = 0; i < array.size(); i++) {
				if (array.getJSONObject(i).getString("priceTypeCode").equals("L1")) {
					priceString = array.getJSONObject(i).getString("price");
				}
			}
		}
		if ("".equals(priceString)) {
			priceString = itemInfo.getString("shopPrice");
		}

		Double realTempPrice = Double.valueOf(priceString);
		return new BigDecimal(realTempPrice).multiply(new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
	}

	public void payCheck(ActOrderInfo orderInfo, Member loginUser) throws Exception {
		payCheckOrderStatus(orderInfo, loginUser);
		List<PayOrderInfo> payOrderInfos = payServiceClient.getPayOrderInfosByActOrderId(orderInfo.getId());
		if (existRefundInfo(payOrderInfos)) {
			throw new OrderServiceException(OrderServiceException.PAY_CHECK_ERROR, "该订单在退款中或已退款，不能再次支付。");
		}
	}

	public boolean checkSendSms(ActOrderInfo orderInfo, int coin) throws Exception {
		if (orderInfo.getPayStatus() == ActOrderStatus.PAY_STATUS_PAYING) {
			// 正在支付中，已生成了支付项目
			Map<String, Object> paymentInfo = getPaymentInfoStatus(orderInfo);
			List<ActOrderPaymentInfo> leftPayInfos = (List<ActOrderPaymentInfo>) paymentInfo.get("leftPayInfos");
			Map<String, ActOrderPaymentInfo> leftPayInfoMap = toPaymentMap(leftPayInfos);
			if (leftPayInfoMap.containsKey(OrderService.CURRENCY_COIN)) {
				return true;
			}
		} else {
			// 首次支付
			int payAmount = orderInfo.getTotalPayAmount();
			if (coin > payAmount) {
				throw new OrderServiceException(OrderServiceException.PAY_AMOUNT_ERROR);
			}
			if (coin > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检查order信息，是否可以支付
	 *
	 * @return
	 */
	private void payCheckOrderStatus(ActOrderInfo orderInfo, Member loginUser) {
		// LoginUserBean loginUser = ExtensionAssertion.getUser();

		if (!loginUser.getId().equals(orderInfo.getUserId())) {
			throw new OrderServiceException(OrderServiceException.PAY_CHECK_ERROR, "下单用户和付款订单用户不匹配。");
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
		List<PayOrderInfo> payOrderInfos = payServiceClient.getPayOrderInfosByActOrderId(orderInfo.getId());
		List<ActOrderPaymentInfo> paymentInfos = orderInfo.getPaymentInfos();
		Map<String, Integer> payedInfo = getAllPayedInformation(payOrderInfos);
		List<ActOrderPaymentInfo> leftPayInfos = getLeftPayInfos(paymentInfos, payedInfo);
		infos.put("paymentInfos", paymentInfos);
		infos.put("payedInfo", payedInfo);
		infos.put("leftPayInfos", leftPayInfos);
		return infos;
	}

	public Map<String, Integer> getPayedInfo(Long orderId) throws Exception {
		ActOrderInfo order = actOrderClient.getActOrderInfo(orderId);
		List<PayOrderInfo> payOrderInfos = payServiceClient.getPayOrderInfosByActOrderId(orderId);
		List<ActOrderPaymentInfo> paymentInfos = order.getPaymentInfos();
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
			}
			catch (Exception e) {
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
	 * 接口请求支付商城币
	 *
	 * @param orderInfo
	 * @param payInfo
	 * @return
	 */
	public PayResponse procCoinPay(ActOrderInfo orderInfo, ActOrderPaymentInfo payInfo) {
		PayRequest payRequest = new PayRequest();
		payRequest.setActOrderId(orderInfo.getId());
		payRequest.setPaymentId(payInfo.getId());
		payRequest.setPayType(PayRequest.PAY_TYPE_BACKGROUND);
		payRequest.setPayChannel("coinpay");
		PayResponse response = actBusinessClient.pay(payRequest);
		// logger.debug(response);
		return response;
	}

	/**
	 * 接口请求支付现金接口
	 *
	 * @param orderInfo
	 * @param payType
	 * @param payInfo
	 * @return
	 */
	public PayResponse procCashPay(ActOrderInfo orderInfo, String payType,String channel, ActOrderPaymentInfo payInfo, String returnUrl) {
		PayRequest payRequest = new PayRequest();
		payRequest.setWebPayReturnUrl(returnUrl);
		payRequest.setActOrderId(orderInfo.getId());
		payRequest.setPaymentId(payInfo.getId());
        if(payType.equalsIgnoreCase("wap")){
            payRequest.setPayType(PayRequest.PAY_TYPE_WAP);
        }else{
		    payRequest.setPayType(PayRequest.PAY_TYPE_WEB);
        }
		payRequest.setPayChannel(channel);
		PayResponse response = actBusinessClient.pay(payRequest);
		// logger.debug(response);
		return response;
	}

	/**
	 * 填充订单支付信息
	 *
	 * @param orderInfo
	 * @param coin
	 */
	public void fillOrderPayment(ActOrderInfo orderInfo, int coin, String payType) {

		int payAmount = orderInfo.getTotalPayAmount();

		List<ActOrderPaymentInfo> paymentInfos = orderInfo.getPaymentInfos();

		if (coin > 0) {
			ActOrderPaymentInfo paymentInfo = new ActOrderPaymentInfo();
			paymentInfo.setChannal(payType);
			paymentInfo.setCurrency("coin");
			paymentInfo.setAmount(coin);
			paymentInfos.add(paymentInfo);
		}
		payAmount -= coin;

		if (payAmount > 0) {
			ActOrderPaymentInfo paymentInfo = new ActOrderPaymentInfo();
			paymentInfo.setChannal(payType);
			paymentInfo.setCurrency("cash");
			paymentInfo.setAmount(payAmount);
			paymentInfos.add(paymentInfo);
		}

	}

	public ActOrderInfo bindingPaymentToOrder(ActOrderInfo actOrderInfo, int coin, String payType) throws Exception {
		fillOrderPayment(actOrderInfo, coin, payType);
		// 更新支付项目，接口不支持一次写入订单
		try {
			actOrderClient.updatePaymentInfo(actOrderInfo.getId(), actOrderInfo.getPaymentInfos());
		}
		catch (Exception ex) {
			logger.warn("更新支付项目出错", ex);
			throw new OrderServiceException(OrderServiceException.INTERFACE_FATAL, "更新支付项目出错。");
		}
		return actOrderClient.getActOrderInfo(actOrderInfo.getId());
	}

	public void updateOrderToPaying(ActOrderInfo orderInfo) throws Exception {
		actOrderClient.updateStatus(orderInfo.getId(), ActOrderStatus.STATUS_TYPE_PAY, ActOrderStatus.PAY_STATUS_PAYING, "正在支付中",
		                            "{'method':'updateOrderToPaying'}");
	}

	public void updateOrderStatusToPayed(ActOrderInfo orderInfo) throws Exception {
		actOrderClient.updateStatus(orderInfo.getId(), ActOrderStatus.STATUS_TYPE_PAY, ActOrderStatus.PAY_STATUS_PAID, "已支付",
		                            "{'method':'updateOrderPayed'}");
	}

	public Page<MarketOrder> getMarketOrder(final Page<MarketOrder> page, final List<PropertyFilter> filters) {
		return marketOrderDao.findPage(page, filters);
	}

	public Page<Seckill> getSeckillOrder(final Page<Seckill> page, final List<PropertyFilter> filters) {
		return seckillDao.findPage(page, filters);
	}

	public TActOrder getOrderByExtInfo(Long userId, String businessId, int type) {
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
	public Deposit createMarketDeposit(ItemSale itemSale, TStore store, MarketOrder marketOrder, TActOrder order) {
		Deposit deposit = new Deposit();
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

		} else {
			deposit.setStatus(0);
			deposit.setBuyTime("");
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
	public Deposit createSeckillDeposit(ItemSale itemSale, TStore store, Seckill seckill, TActOrder order) {
		Deposit deposit = new Deposit();
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
		} else {
			deposit.setStatus(0);
			deposit.setBuyTime("");
		}
		return deposit;
	}

}
