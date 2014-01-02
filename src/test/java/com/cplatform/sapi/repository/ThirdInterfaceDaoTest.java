package com.cplatform.sapi.repository;

import com.cplatform.sapi.DTO.MarketingResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

/**
 * User: cuikai
 * Date: 13-9-22
 * Time: 上午10:55
 */
public class ThirdInterfaceDaoTest {

    private static final String json = "{\"confirm_price\":133.0,\"confirm_type\":1,\"confirm_itemId\":1275583,\"confirm_userId\":1312369,\"businessId\":1275596,\"confirm_dealTime\":\"2013-08-26 18:02:19\"}";
    @InjectMocks
    private ThirdInterfaceDao thirdInterfaceDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void mapper() {

        MarketingResponse marketing = thirdInterfaceDao.convertJsonToBean(json);
        assertEquals("1275596", marketing.getBusinessId());
        assertEquals("2013-08-26 18:02:19", marketing.getConfirm_dealTime());
        assertEquals("1275583", marketing.getConfirm_itemId());
        assertEquals("133.0", marketing.getConfirm_price());
        assertEquals("1", marketing.getConfirm_type());
        assertEquals("1312369", marketing.getConfirm_userId());
    }
}
