package com.cplatform.sapi.service;

import com.cplatform.sapi.entity.order.TActOrder;
import com.cplatform.sapi.entity.order.TActOrderGoods;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


/**
 * User: cuikai
 * Date: 13-9-18
 * Time: 下午4:54
 */
public class ItemSaleServiceTest {

    @InjectMocks
    private ItemSaleService itemSaleService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void goodStatus() {

        String user = "100";
        String user2 = "200";

        //支付中
        TActOrder payingOrder = createOrderData(user, 1);

        //未支付
        TActOrder notPayOrder = createOrderData(user, 0);

        //已支付
        TActOrder payedOrder = createOrderData(user, 2);

        TActOrderGoods payingGood = createOrderGood(payingOrder);

        TActOrderGoods nopayGood = createOrderGood(notPayOrder);

        TActOrderGoods payedGood = createOrderGood(payedOrder);

        ItemSaleService.GoodStatus status = itemSaleService.getGoodStatus(Lists.newArrayList(payingGood, nopayGood), user);
        assertEquals("1", status.getFlag());

        status = itemSaleService.getGoodStatus(Lists.newArrayList(nopayGood, payingGood, payedGood), user);
        assertEquals("2", status.getFlag());

        status = itemSaleService.getGoodStatus(Lists.newArrayList(nopayGood, nopayGood), user);
        assertEquals("1", status.getFlag());

        status = itemSaleService.getGoodStatus(new ArrayList<TActOrderGoods>(), user);
        assertEquals("0", status.getFlag());

        status = itemSaleService.getGoodStatus(new ArrayList<TActOrderGoods>(), user2);
        assertEquals("0", status.getFlag());
    }

    private TActOrderGoods createOrderGood(TActOrder order) {
        TActOrderGoods good = new TActOrderGoods();
        good.setOrder(order);
        return good;
    }

    private TActOrder createOrderData(String user, int status) {
        TActOrder order = new TActOrder();
        order.setUserId(Long.valueOf(user));
        order.setStatus(status);
        order.setPayStatus(status);
        return order;
    }
}
