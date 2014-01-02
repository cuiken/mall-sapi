package com.cplatform.sapi.service;

import com.cplatform.order.ActOrderInfo;
import com.cplatform.order.ActOrderPaymentInfo;
import com.cplatform.order.ActOrderStatus;
import com.cplatform.pay.PayOrderInfo;
import com.cplatform.sapi.DTO.*;
import com.cplatform.sapi.data.ItemData;
import com.cplatform.sapi.data.OrderData;
import com.cplatform.sapi.entity.Member;
import com.cplatform.sapi.entity.TMemberAddress;
import com.cplatform.sapi.entity.order.TActOrder;
import com.cplatform.sapi.exceptions.OrderServiceException;
import com.cplatform.sapi.mapper.JsonMapper;
import com.cplatform.sapi.repository.SysRegionDao;
import com.cplatform.sapi.repository.TMemberAddressDao;
import com.cplatform.sapi.repository.ThirdInterfaceDao;
import com.cplatform.sapi.repository.order.TActOrderDao;
import com.cplatform.sapi.repository.product.ItemSaleDao;
import com.cplatform.sapi.util.AppConfig;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * User: cuikai
 * Date: 13-9-17
 * Time: 下午12:51
 */
public class OrderServiceTest {

    private static final String CREATE_ORDER_JSON = "{\"U_ID\":4047458,\"PAY_TYPE\":null,\"GOODS\":[{\"COUNT\":1,\"GOOD_ID\":228908}],\"ADDRESS_ID\":1313486,\"USER_REMARK\":\"测试订单-翟志鹏\",\"INVOICE_TYPE\":0,\"INVOICE_SUBJECT\":\"翟志鹏 - 个人\",\"INVOICE_CONTENT\":\"商品明细\"}";
    private static final String BUSINESS_RESPONSE = "{\"confirm_price\":133.0,\"confirm_type\":1,\"confirm_itemId\":1275583,\"confirm_userId\":1312369,\"businessId\":1275596,\"confirm_dealTime\":\"2013-08-26 18:02:19\"}";

    private JsonMapper mapper;

    @InjectMocks
    private OrderService orderService;

    @Mock
    private BusinessLogger businessLogger;
    @Mock
    private TMemberAddressDao memberAddressDao;
    @Mock
    private ItemSaleDao itemSaleDao;
    @Mock
    private OrderCenterDao orderCenterDao;
    @Mock
    private TActOrderDao orderDao;
    @Mock
    private ThirdInterfaceDao thirdInterfaceDao;
    @Mock
    private AppConfig appConfig;
    @Mock
    private SysRegionDao sysRegionDao;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mapper = JsonMapper.buildNormalMapper();
    }

    @Test
    public void createOrderTest() throws Exception {
        CreateOrderDTO orderDTO = mapper.fromJson(CREATE_ORDER_JSON, CreateOrderDTO.class);
        Member member = OrderData.createMember(1L);
        UnionMember unionMember = getUnionMember();
        TMemberAddress address = OrderData.createMemberAddress();
        ItemSaleDataDTO itemData = mapper.fromJson(ItemData.ITEM_INFO_NO_FEE, ItemSaleDataDTO.class);

//        ItemSaleDataDTO itemData = mapper.fromJson(ItemData.ITEM_WITH_FEE, ItemSaleDataDTO.class);

        when(itemSaleDao.getItemById(String.valueOf(itemData.getItem().getId()))).thenReturn(itemData);
        when(memberAddressDao.get(orderDTO.getAddressId())).thenReturn(address);
        when(thirdInterfaceDao.getUnionMemberInfo(member.getTerminalId())).thenReturn(unionMember);

        ActOrderInfo orderInfo = orderService.buildOrderInfo(orderDTO, member);

        when(orderCenterDao.createOrderRequest(orderInfo)).thenReturn(1L);

        assertNotNull(orderInfo.getGoodsInfos());
        assertNotNull(orderInfo.getExpressInfo());
        assertNotNull(orderInfo.getShopId());

//        运费为零
        assertEquals(0, orderInfo.getExpressInfo().getExpressCost());
////        有运费
//        assertEquals(200, orderInfo.getExpressInfo().getExpressCost());


        assertEquals(300045, orderInfo.getShopId());
        assertEquals(address.getName(), orderInfo.getExpressInfo().getReceiverName());

        assertEquals(1, orderInfo.getGoodsInfos().size());
        assertEquals(itemData.getItem().getId().longValue(), orderInfo.getGoodsInfos().get(0).getGoodsId());

        assertNotEquals(0, orderInfo.getTotalPayAmount());
        assertNotEquals(ActOrderStatus.PAY_STATUS_PAID, orderInfo.getPayStatus().intValue());

        //零元购测试
        itemData = mapper.fromJson(ItemData.ITEM_PAY_ZERO, ItemSaleDataDTO.class);
        when(itemSaleDao.getItemById(String.valueOf(itemData.getItem().getId()))).thenReturn(itemData);
        itemData.getItem().setStatus("3");
        orderInfo = orderService.buildOrderInfo(orderDTO, member);

        assertEquals(0, orderInfo.getTotalPayAmount());
        assertEquals(ActOrderStatus.PAY_STATUS_PAID, orderInfo.getPayStatus().intValue());

    }

    @Test
    public void deliveryTotalFeeTest() {

        List<ItemSaleDataDTO> items = Lists.newArrayList();
        ItemSaleDataDTO dto = mapper.fromJson(ItemData.ITEM_INFO_NO_FEE, ItemSaleDataDTO.class);
        items.add(dto);
        int noFee = orderService.getDeliveryTotalFee(items);
        assertEquals(0, noFee);

        ItemSaleDataDTO itemFee = mapper.fromJson(ItemData.ITEM_WITH_FEE, ItemSaleDataDTO.class);
        int totalFee = orderService.getDeliveryTotalFee(Lists.newArrayList(itemFee));
        assertEquals(200, totalFee);

        ItemSaleDataDTO itemFeeType1 = mapper.fromJson(ItemData.ITEM_WITH_FEE_TYPE, ItemSaleDataDTO.class);
        itemFeeType1.setCount(2);
        int totalFee1 = orderService.getDeliveryTotalFee(Lists.newArrayList(itemFeeType1, itemFeeType1));
        assertEquals(800, totalFee1);
    }

    @Test
    public void memberPrice() {
        ItemSaleDataDTO item = mapper.fromJson(ItemData.ITEM_FOR_RED_MEMBER, ItemSaleDataDTO.class);
        ItemSaleDataDTO itemNoRedPrice = mapper.fromJson(ItemData.ITEM_NO_RED_PRICE, ItemSaleDataDTO.class);
        Member user = OrderData.createMember(1000L);
        UnionMember unionMember = getUnionMember();
        when(thirdInterfaceDao.getUnionMemberInfo(user.getTerminalId())).thenReturn(unionMember);

        //有商城会员
        assertEquals(1, orderService.findItemRealPrice(item, user));
        assertEquals(1, orderService.findItemRealPrice(item, user));
        assertEquals(41000, orderService.findItemRealPrice(itemNoRedPrice, user));

        //无商城会员
        unionMember.setMember("false");
        assertEquals(2, orderService.findItemRealPrice(item, user));
        user.setRedMember(0);
        assertEquals(40000, orderService.findItemRealPrice(item, user));

        //红钻价低于商城价
        item = mapper.fromJson(ItemData.ITEM_FOR_EVERYONE, ItemSaleDataDTO.class);
        unionMember.setMember("true");
        user.setRedMember(1);
        assertEquals(2, orderService.findItemRealPrice(item, user));

    }

    @Test
    public void memberSpecial() {
        UnionMember unionMember = getUnionMember();
        Member user = OrderData.createMember(1000L);

        ItemSaleDataDTO redItem = mapper.fromJson(ItemData.ITEM_FOR_RED_MEMBER, ItemSaleDataDTO.class);
        ItemSaleDataDTO unionItem = mapper.fromJson(ItemData.ITEM_FOR_UNION_MEMBER, ItemSaleDataDTO.class);
        ItemSaleDataDTO item = mapper.fromJson(ItemData.ITEM_FOR_MEMBER, ItemSaleDataDTO.class);
        ItemSaleDataDTO everyoneItem = mapper.fromJson(ItemData.ITEM_FOR_EVERYONE, ItemSaleDataDTO.class);

        when(thirdInterfaceDao.getUnionMemberInfo(user.getTerminalId())).thenReturn(unionMember);

        //无专属商品
        orderService.checkMemberSpecial(Lists.newArrayList(everyoneItem), user);
        //专属商品
        orderService.checkMemberSpecial(Lists.newArrayList(redItem, unionItem, item), user);

        user.setRedMember(0);
        try {
            orderService.checkMemberSpecial(Lists.newArrayList(redItem), user);
            fail("不能购买红砖会员专属商品");
        } catch (IllegalStateException e) {
        }

        unionMember.setMember("false");
        try {
            orderService.checkMemberSpecial(Lists.newArrayList(unionItem), user);
            fail("不能购买商盟会员专属商品");
        } catch (IllegalStateException e) {
        }

        try {
            orderService.checkMemberSpecial(Lists.newArrayList(item), user);
            fail("不能购买会员专属商品");
        } catch (IllegalStateException e) {
        }
    }

    private UnionMember getUnionMember() {
        UnionMember unionMember = new UnionMember();
        unionMember.setMember("true");
        unionMember.setBossSet("20001");
        return unionMember;
    }

    @Test
    public void goodsPayType() {

        try {
            orderService.checkGoodPayType(Lists.newArrayList(
                    mapper.fromJson(ItemData.ITEM_INFO_NO_FEE, ItemSaleDataDTO.class),
                    mapper.fromJson(ItemData.ITEM_PAY_TYPE, ItemSaleDataDTO.class),
                    mapper.fromJson(ItemData.ITEM_PAY_TYPE_One, ItemSaleDataDTO.class)));

            fail("商品支付方式不一致");
        } catch (OrderServiceException e) {
            //expected exception
        }
        orderService.checkGoodPayType(Lists.newArrayList(
                mapper.fromJson(ItemData.ITEM_INFO_NO_FEE, ItemSaleDataDTO.class),
                mapper.fromJson(ItemData.ITEM_INFO_NO_FEE, ItemSaleDataDTO.class)));

        orderService.checkGoodPayType(Lists.newArrayList(
                mapper.fromJson(ItemData.ITEM_PAY_TYPE_One, ItemSaleDataDTO.class),
                mapper.fromJson(ItemData.ITEM_PAY_TYPE_Two, ItemSaleDataDTO.class)));
    }

    @Test
    public void initGoodPrice() {
        String businessId = "1275596";
        String orderType = "1";
        Member user = OrderData.createMember(1L);
        ItemSaleDataDTO item = mapper.fromJson(ItemData.ITEM_INFO_NO_FEE, ItemSaleDataDTO.class);
        item.setCount(1);
        when(thirdInterfaceDao.convertToBean(item.getItem().getId(), orderType, businessId))
                .thenReturn(mapper.fromJson(BUSINESS_RESPONSE, MarketingResponse.class));
        when(orderDao.getBusinessOrder(user.getId(), businessId)).thenReturn(new TActOrder());
        try {
            orderService.initGoodsPrice(item, user, businessId, orderType);
            fail("营销订单不唯一");
        } catch (OrderServiceException e) {

        }
        when(orderDao.getBusinessOrder(user.getId(), businessId)).thenReturn(null);
        try {
            orderService.initGoodsPrice(item, user, businessId, orderType);
            fail("用户不一致");
        } catch (OrderServiceException e) {

        }

        user = OrderData.createMember(1312369L);
        int price = orderService.initGoodsPrice(item, user, businessId, orderType);
        assertEquals(13300, price);
    }

    @Test
    public void pay() {

    }

    @Test
    public void initPayment() {

        PayDTO payInfo = OrderData.randomPayData(PayFormChoose.CASH_AND_COIN.getValue(), 20, 0);

        List<ActOrderPaymentInfo> payments = orderService.initOrderPayment(500, payInfo);
        assertEquals(2, payments.size());
        List<String> currenc = Lists.newArrayList(payments.get(0).getCurrency(), payments.get(1).getCurrency());
        assertTrue(currenc.contains("coin"));
        assertTrue(currenc.contains("cash"));

        payInfo = OrderData.randomPayData(PayFormChoose.CASH_AND_SCORE.getValue(), 20, 0);
        payments = orderService.initOrderPayment(500, payInfo);
        assertEquals(2, payments.size());
        currenc = Lists.newArrayList(payments.get(0).getCurrency(), payments.get(1).getCurrency());
        assertTrue(currenc.contains("score"));
        assertTrue(currenc.contains("cash"));

        payInfo = OrderData.randomPayData(PayFormChoose.ONLY_BALANCE.getValue(), 0, 500);
        payments = orderService.initOrderPayment(500, payInfo);
        assertEquals(1, payments.size());
        assertEquals("balance", payments.get(0).getCurrency());

        payInfo = OrderData.randomPayData(PayFormChoose.ONLY_CASH.getValue(), 0, 0);
        payments = orderService.initOrderPayment(500, payInfo);
        assertEquals("cash", payments.get(0).getCurrency());
        assertEquals(500, payments.get(0).getAmount());

        payInfo = OrderData.randomPayData(PayFormChoose.ONLY_COIN.getValue(), 500, 0);
        payments = orderService.initOrderPayment(500, payInfo);
        assertEquals(1, payments.size());
        assertEquals("coin", payments.get(0).getCurrency());

        payInfo = OrderData.randomPayData(PayFormChoose.ONLY_SCORE.getValue(), 500, 0);
        payments = orderService.initOrderPayment(500, payInfo);
        assertEquals(1, payments.size());
        assertEquals("score", payments.get(0).getCurrency());

        payInfo = OrderData.randomPayData(PayFormChoose.ONLY_SCORE.getValue(), 100, 0);
        try {
            orderService.initOrderPayment(500, payInfo);
            fail("单一支付数额不足");
        } catch (Exception e) {

        }

    }


    @Test
    public void checkValidPay() throws Exception {
        CheckPayParam param = OrderData.getCheckPayParam();
        ActOrderInfo actOrderInfo = OrderData.getActOrderInfo();
        List<PayOrderInfo> payOrderInfos = OrderData.getPayOrderInfo();

        when(orderCenterDao.getPayOrderInfos(actOrderInfo.getId())).thenReturn(payOrderInfos);
        when(orderCenterDao.getActOrder(param.getOrderId())).thenReturn(actOrderInfo);

//        第一次支付，独立支付
//        数额匹配
        param.setPayForm("only_score");
        orderService.checkValidPay(param);
//        第一次支付，独立支付
//        数额不匹配
        param.setScore(120);
        try {
            orderService.checkValidPay(param);
            fail("积分金额不符");
        } catch (Exception e) {
        }


//      第一次支付，混合支付
        param.setPayForm("cash_and_score");
        try {
            orderService.checkValidPay(param);
            fail("支付数额设置不正确");
        } catch (Exception e) {
        }

        actOrderInfo.setPayStatus(ActOrderStatus.PAY_STATUS_PAYING);
//        再次支付，数额不匹配
        param.setScore(90);
        try {
            orderService.checkValidPay(param);
            fail("支付数额设置不正确");
        } catch (Exception e) {
        }
//        再次支付，数额匹配
        param.setScore(100);
        orderService.checkValidPay(param);
    }


    @Test
    public void changeWithTariff() {

        int itemPrice = 10000;
        assertEquals(10600, orderService.changeWithTariff(itemPrice));

        itemPrice = 100;
        assertEquals(110, orderService.changeWithTariff(itemPrice));

        itemPrice = 1;
        assertEquals(10, orderService.changeWithTariff(itemPrice));

        itemPrice = 0;
        assertEquals(0, orderService.changeWithTariff(itemPrice));
    }

    @Test
    public void changeToScore() {
        int itemPrice = 2013;
        assertEquals(142600, orderService.changeToScore(itemPrice));

        itemPrice = 22;
        assertEquals(2000, orderService.changeToScore(itemPrice));

        itemPrice = 1;
        assertEquals(600, orderService.changeToScore(itemPrice));

        itemPrice = 0;
        assertEquals(0, orderService.changeToScore(itemPrice));
    }
}
