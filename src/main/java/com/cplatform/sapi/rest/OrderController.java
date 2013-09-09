package com.cplatform.sapi.rest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cplatform.sapi.DTO.CreateOrderDTO;
import com.cplatform.sapi.DTO.GCheapDTO;
import com.cplatform.sapi.DTO.PayDTO;
import com.cplatform.sapi.entity.Member;
import com.cplatform.sapi.entity.order.Deposit;
import com.cplatform.sapi.entity.order.MarketOrder;
import com.cplatform.sapi.entity.order.Seckill;
import com.cplatform.sapi.entity.order.TActOrder;
import com.cplatform.sapi.entity.product.ItemSale;
import com.cplatform.sapi.entity.product.TStore;
import com.cplatform.sapi.exceptions.OrderServiceException;
import com.cplatform.sapi.mapper.BeanMapper;
import com.cplatform.sapi.orm.Page;
import com.cplatform.sapi.orm.PageRequest;
import com.cplatform.sapi.orm.PropertyFilter;
import com.cplatform.sapi.service.ItemSaleService;
import com.cplatform.sapi.service.OrderService;
import com.cplatform.sapi.service.SecurityService;
import com.cplatform.sapi.util.JsonRespWrapper;
import com.cplatform.sapi.util.MediaTypes;

/**
 * 订单操作类 User: cuikai Date: 13-8-21 Time: 下午1:40
 */
@Controller
@RequestMapping(value = "/api/v1/order")
public class OrderController {

	private static Logger logger = Logger.getLogger(OrderController.class);

	private OrderService orderService;

	private SecurityService securityService;

	@Autowired
	private ItemSaleService itemSaleService;

	private final Page<Seckill> seckillPage = new Page<Seckill>(10);

	private final Page<MarketOrder> marketPage = new Page<MarketOrder>(10);

	@RequestMapping(value = "pay", method = RequestMethod.POST, consumes = MediaTypes.JSON)
	@ResponseBody
	public Object pay(@RequestBody PayDTO payDTO) throws Exception {
		if (payDTO.getCoin() < 0)
			throw new OrderServiceException(OrderServiceException.PAY_AMOUNT_ERROR);

		Member loginUser = securityService.getMember(payDTO.getUserId());
		// securityService.initProxy(loginUser);
		if (StringUtils.isBlank(loginUser.getTerminalId())) {
			throw new OrderServiceException(OrderServiceException.NEED_BIND_MOBILE);
		}

		return orderService.payOrder(payDTO, loginUser);
	}

	@RequestMapping(value = "/gcheap", method = RequestMethod.GET)
	@ResponseBody
	public GCheapDTO getGCheapOrders(HttpServletRequest request) throws UnsupportedEncodingException {

		List<Deposit> deposits = new ArrayList<Deposit>();
		String type = request.getParameter("type");

		String pageNo = request.getParameter("pageNo");
		String userId = request.getParameter("userId");
		// 组合G实惠查询条件
		List<PropertyFilter> filters = orderService.buildGCheapOrderFilter(request);

		if (type.equals("1")) {
			if (StringUtils.isNotBlank(pageNo)) {
				marketPage.setPageNo(Integer.valueOf(pageNo));
			}

			marketPage.setOrderBy("createTime");
			marketPage.setOrderDir(PageRequest.Sort.DESC);
			// 获得竞拍的成功记录
			List<MarketOrder> marketOrders = orderService.getMarketOrder(marketPage, filters).getResult();

			for (MarketOrder marketOrder : marketOrders) {
				TActOrder order = orderService.getOrderByExtInfo(Long.parseLong(userId), marketOrder.getId() + "", Integer.parseInt(type));

				ItemSale itemSale = itemSaleService.getItemSale(Long.parseLong(marketOrder.getProductId()));

				TStore store = itemSaleService.getTStore(itemSale.getStoreId());
				// 返回竞拍订单内容
				Deposit deposit = orderService.createMarketDeposit(itemSale, store, marketOrder, order);

				deposits.add(deposit);
			}
		} else if (type.equals("2")) {
			if (StringUtils.isNotBlank(pageNo)) {
				seckillPage.setPageNo(Integer.valueOf(pageNo));
			}

			seckillPage.setOrderBy("createTime");
			seckillPage.setOrderDir(PageRequest.Sort.DESC);
			// 获取成功秒杀的记录
			List<Seckill> seckills = orderService.getSeckillOrder(seckillPage, filters).getResult();
			for (Seckill seckill : seckills) {
				TActOrder order = orderService.getOrderByExtInfo(Long.parseLong(userId), seckill.getId() + "", Integer.parseInt(type));

				ItemSale itemSale = itemSaleService.getItemSale(seckill.getGoodsNo());

				TStore store = itemSaleService.getTStore(itemSale.getStoreId());
				// 组合秒杀成功订单内容
				Deposit deposit = orderService.createSeckillDeposit(itemSale, store, seckill, order);

				deposits.add(deposit);
			}
		}

		GCheapDTO gcheapDTO = new GCheapDTO();
		gcheapDTO.setTotalRow(deposits.size());
		gcheapDTO.setData(BeanMapper.mapList(deposits, GCheapDTO.Data.class));

		return gcheapDTO;

	}

	@RequestMapping(value = "payedInfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Integer> payedInfo(@RequestParam(value = "orderId") Long orderId) throws Exception {

		return orderService.getPayedInfo(orderId);
	}

	@RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaTypes.JSON)
	@ResponseBody
	public JsonRespWrapper create(@RequestBody CreateOrderDTO orderDTO) {

		long orderId = orderService.createOrder(orderDTO);

		JsonRespWrapper jsonRespWrapper = JsonRespWrapper.json();
		jsonRespWrapper.put("FLAG", "0");
		jsonRespWrapper.put("MSG", "操作成功");
		jsonRespWrapper.put("ORDER_ID", orderId);

		return jsonRespWrapper;
	}

	@Autowired
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	@Autowired
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

}
