package com.cplatform.sapi.rest;

import com.cplatform.act.PayResponse;
import com.cplatform.order.ActOrderInfo;
import com.cplatform.sapi.DTO.CheckPayParam;
import com.cplatform.sapi.DTO.CreateOrderDTO;
import com.cplatform.sapi.DTO.GCheapDTO;
import com.cplatform.sapi.DTO.PayDTO;
import com.cplatform.sapi.entity.Member;
import com.cplatform.sapi.entity.order.MarketOrder;
import com.cplatform.sapi.entity.order.Seckill;
import com.cplatform.sapi.entity.order.TActOrder;
import com.cplatform.sapi.entity.product.ItemSale;
import com.cplatform.sapi.entity.product.TStore;
import com.cplatform.sapi.orm.Page;
import com.cplatform.sapi.orm.PageRequest;
import com.cplatform.sapi.orm.PropertyFilter;
import com.cplatform.sapi.service.AuthService;
import com.cplatform.sapi.service.ItemSaleService;
import com.cplatform.sapi.service.OrderService;
import com.cplatform.sapi.util.JsonRespWrapper;
import com.cplatform.sapi.util.MediaTypes;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * 订单操作类 User: cuikai Date: 13-8-21 Time: 下午1:40
 */
@Controller
@RequestMapping(value = "/api/v1/order")
public class OrderController {

    private OrderService orderService;

    private AuthService authService;

    @Autowired
    private ItemSaleService itemSaleService;

    private final Page<Seckill> seckillPage = new Page<Seckill>(10);

    private final Page<MarketOrder> marketPage = new Page<MarketOrder>(10);


    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaTypes.JSON)
    @ResponseBody
    public JsonRespWrapper create(@RequestBody CreateOrderDTO orderDTO) throws Exception {
        JsonRespWrapper result = JsonRespWrapper.json();


        Member user = authService.getMember(orderDTO.getUserId());
        ActOrderInfo orderInfo = orderService.buildOrderInfo(orderDTO, user);
        long orderId = orderService.createOrder(orderInfo);
        result.put("FLAG", "0");
        result.put("MSG", "success");
        result.put("ORDER_ID", orderId);
        result.put("TOTAL_PAY_AMOUNT", orderInfo.getTotalPayAmount());
        int expressCost = 0;
        if (orderInfo.getExpressInfo() != null){
            expressCost = orderInfo.getExpressInfo().getExpressCost();
        }
        result.put("EXPRESS_COST", expressCost);
        return result;


    }

    @RequestMapping(value = "pay", method = RequestMethod.POST, consumes = MediaTypes.JSON)
    @ResponseBody
    public Object pay(@RequestBody PayDTO payDTO) throws Exception {

        Member loginUser = authService.getMember(payDTO.getUserId());
        JsonRespWrapper result = JsonRespWrapper.json();

        PayResponse payResponse = orderService.payOrder(payDTO, loginUser);
        result.json("FLAG", "0");
        result.json("MSG", "success");
        result.json("ORDER_ID", payDTO.getOrderId());
        if (payResponse == null) {
            result.json("REDIRECT_URL", "");
            result.json("FORM_ACTION_URL", "");
            result.json("HTML", "");
        } else {
            result.json("REDIRECT_URL", payResponse.getRedirectUrl());
            result.json("FORM_ACTION_URL", payResponse.getFormActionUrl());
            result.json("HTML", payResponse.getHtml());
        }

        return result;
    }

    @RequestMapping(value = "payCheck")
    @ResponseBody
    public JsonRespWrapper checkPay(@RequestParam(value = "orderId") Long orderId,
                                    @RequestParam(value = "coin") int coin,
                                    @RequestParam(value = "userId") Long userId,
                                    @RequestParam(value = "score")int score,
                                    @RequestParam(value = "balance")int balance,
                                    @RequestParam(value = "payForm")String payForm) throws Exception {
        JsonRespWrapper result = JsonRespWrapper.json();

        Member user = authService.getMember(userId);

        CheckPayParam params=new CheckPayParam();
        params.setPayForm(payForm);
        params.setCoin(coin);
        params.setOrderId(orderId);
        params.setBalance(balance);
        params.setScore(score);
        params.setUser(user);

        orderService.checkValidPay(params);

        result.json("FLAG", "0");
        result.json("MSG", "success");

        return result;

    }

    @RequestMapping(value = "payedInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Integer> payedInfo(@RequestParam(value = "orderId") Long orderId) throws Exception {

        return orderService.getPayedInfo(orderId);
    }

    @RequestMapping(value = "/gcheap", method = RequestMethod.GET)
    @ResponseBody
    public GCheapDTO getGCheapOrders(HttpServletRequest request) throws UnsupportedEncodingException {

        String type = request.getParameter("type");

        String pageNo = request.getParameter("pageNo");
        String userId = request.getParameter("userId");
        // 组合G实惠查询条件
        List<PropertyFilter> filters = orderService.buildGCheapOrderFilter(request);

        List<GCheapDTO.Data> deposits = Lists.newArrayList();

        if (type.equals("1")) {
            deposits = buildMarketDeposit(pageNo, userId, type, filters);
        } else if (type.equals("2")) {
            deposits = buildKillDeposit(pageNo, userId, type, filters);
        }

        GCheapDTO gcheapDTO = new GCheapDTO();
        gcheapDTO.setTotalRow(deposits.size());
        gcheapDTO.setData(deposits);

        return gcheapDTO;

    }

    private List<GCheapDTO.Data> buildKillDeposit(String pageNo, String userId, String type, List<PropertyFilter> filters) {
        List<GCheapDTO.Data> deposits = Lists.newArrayList();
        if (StringUtils.isNotBlank(pageNo)) {
            seckillPage.setPageNo(Integer.valueOf(pageNo));
        }

        seckillPage.setOrderBy("createTime");
        seckillPage.setOrderDir(PageRequest.Sort.DESC);
        // 获取成功秒杀的记录
        List<Seckill> seckills = orderService.getSeckillOrder(seckillPage, filters).getResult();
        for (Seckill seckill : seckills) {
            TActOrder order = orderService.getOrderByExtInfo(Long.parseLong(userId), seckill.getId() + "", Long.parseLong(type));

            ItemSale itemSale = itemSaleService.getItemSale(seckill.getGoodsNo());

            TStore store = itemSaleService.getTStore(itemSale.getStoreId());
            // 组合秒杀成功订单内容
            GCheapDTO.Data deposit = orderService.createSeckillDeposit(itemSale, store, seckill, order);

            deposits.add(deposit);
        }
        return deposits;
    }

    private List<GCheapDTO.Data> buildMarketDeposit(String pageNo, String userId, String type, List<PropertyFilter> filters) {
        List<GCheapDTO.Data> deposits = Lists.newArrayList();
        if (StringUtils.isNotBlank(pageNo)) {
            marketPage.setPageNo(Integer.valueOf(pageNo));
        }

        marketPage.setOrderBy("createTime");
        marketPage.setOrderDir(PageRequest.Sort.DESC);
        // 获得竞拍的成功记录
        List<MarketOrder> marketOrders = orderService.getMarketOrder(marketPage, filters).getResult();

        for (MarketOrder marketOrder : marketOrders) {
            TActOrder order = orderService.getOrderByExtInfo(Long.parseLong(userId), marketOrder.getId() + "", Long.parseLong(type));

            ItemSale itemSale = itemSaleService.getItemSale(Long.parseLong(marketOrder.getProductId()));

            TStore store = itemSaleService.getTStore(itemSale.getStoreId());
            // 返回竞拍订单内容
            GCheapDTO.Data deposit = orderService.createMarketDeposit(itemSale, store, marketOrder, order);

            deposits.add(deposit);
        }
        return deposits;
    }

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

}
