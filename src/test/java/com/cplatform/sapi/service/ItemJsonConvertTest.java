package com.cplatform.sapi.service;

import com.cplatform.sapi.DTO.ItemSaleDataDTO;
import com.cplatform.sapi.data.ItemData;
import com.cplatform.sapi.mapper.JsonMapper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * User: cuikai
 * Date: 13-8-21
 * Time: 下午5:24
 */

public class ItemJsonConvertTest {
    private static String ITEM_INFO_JSON = "";


    private JsonMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = JsonMapper.buildNormalMapper();
        ITEM_INFO_JSON = ItemData.ITEM_INFO_NO_FEE;
    }

    @Test
    public void convertTest() {
        ItemSaleDataDTO dto = mapper.fromJson(ITEM_INFO_JSON, ItemSaleDataDTO.class);
        assertEquals(2, dto.getItemPrice().size());
        assertEquals(228908, dto.getItem().getId().longValue());
        assertNotNull(dto.getProperties());

    }

}
