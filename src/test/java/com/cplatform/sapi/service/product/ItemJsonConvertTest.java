package com.cplatform.sapi.service.product;

import com.cplatform.sapi.DTO.ItemSaleDataDTO;
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
    private static final String ITEM_INFO_JSON = "{\"item\":{\"id\":\"4541\",\"itemId\":null,\"name\":\"累计物流实物配送相机（BenQ）\",\"shortName\":\"明基（BenQ） GH600 数码相机\",\"storeId\":\"1550\",\"storeName\":\"周周周内网新建商户的一\",\"storeShortName\":null,\"typeId\":\"100818\",\"typeName\":\"数码相机\",\"brand\":\"飞毛腿\",\"createTime\":\"20130802090432\",\"updatTime\":\"20130803121648\",\"marketContent\":\"营销语超级微距/全景\",\"property\":\"3.0英寸,619088\",\"clicknum\":\"40\",\"tags\":\"明基（BenQ） ，\",\"webPath\":\"p_02541.png\",\"marketPrice\":\"5999.00\",\"shopPrice\":\"0.03\",\"otherPrice\":null,\"salenum\":\"2\",\"commentnum\":null,\"usernum\":\"2\",\"collectnum\":\"0\",\"stocknum\":\"17\",\"logisticsFee\":\"0.01\",\"logisticsFeeType\":\"1\",\"postFlag\":\"1\",\"itemMode\":\"0\",\"startTime\":\"20130802090330\",\"stopTime\":\"20130830090331\",\"rank\":\"0\",\"warmPrompt\":\"温馨提示一机在手出游不愁\",\"groupFlag\":\"0\",\"paymentCash\":\"0\",\"paymentCoin\":\"0\",\"paymentScore\":\"0\",\"isValid\":\"1\",\"iseckill\":\"0\",\"minPrice\":\"0.01\"},\"properties\":{},\"itemPrice\":[{\"saleId\":\"4541\",\"priceTypeCode\":\"L1\",\"priceType\":\"红钻会员\",\"price\":0.02},{\"saleId\":\"4541\",\"priceTypeCode\":\"L2\",\"priceType\":\"蓝钻会员\",\"price\":0.01}]}";


    private JsonMapper mapper;

    @Before
    public void setUp() {
        mapper = JsonMapper.buildNormalMapper();
    }

    @Test
    public void convertTest() {
        ItemSaleDataDTO dto = mapper.fromJson(ITEM_INFO_JSON, ItemSaleDataDTO.class);
        assertEquals(2, dto.getItemPrice().size());
        assertEquals(4541L, dto.getItem().getId().longValue());
        assertNotNull(dto.getProperties());

    }

}
