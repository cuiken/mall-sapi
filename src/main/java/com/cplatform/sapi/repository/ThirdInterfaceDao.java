package com.cplatform.sapi.repository;

import com.cplatform.sapi.DTO.CallBalance;
import com.cplatform.sapi.DTO.MarketingResponse;
import com.cplatform.sapi.DTO.UnionMember;
import com.cplatform.sapi.exceptions.InterfaceException;
import com.cplatform.sapi.mapper.JsonMapper;
import com.cplatform.sapi.util.AppConfig;
import com.cplatform.sapi.util.Encodes;
import com.cplatform.sapi.util.HttpClientUtil;
import com.cplatform.sapi.util.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 第三方接口
 * User: cuikai
 * Date: 13-9-22
 * Time: 上午10:40
 */
@Component
public class ThirdInterfaceDao {

    private static Logger logger = LoggerFactory.getLogger("http");

    private static final String SHOP_COCE = "12580777";
    private static final String VERSION = "1.0";

    @Autowired
    private AppConfig appConfig;

    public String getMarketingResponse(Long goodsId, String orderType, String businessId) {

        Validate.notNull(goodsId, "商品ID不能为空");
        Validate.notNull(businessId, "businessId不能为空");

        orderType = (StringUtils.isBlank(orderType) ? "0" : orderType);
        String param = "goodsNo=" + goodsId + "&type=" + orderType + "&businessId=" + businessId;
        try {
            return HttpClientUtil.httpGet(appConfig.getAuctionOrderUrl(), param);
        } catch (Exception e) {
            logger.error("===请求营销接口失败===");
        }
        return null;
    }

    public CallBalance getBalanceInfo(String mobile) throws Exception {

        Validate.notBlank(mobile, "手机号码不能为空");

        String resp = HttpClientUtil.httpGet(appConfig.getBalanceQueryUrl(), "mobile=" + mobile);

        JsonMapper mapper = JsonMapper.buildNormalMapper();
        return mapper.fromJson(resp, CallBalance.class);

    }

    public UnionMember getUnionMemberInfo(String terminalId) {
        Validate.notBlank(terminalId, "手机号码不能为空");
        JsonMapper jsonMapper = JsonMapper.buildNormalMapper();

        String time = TimeUtil.now();
        String headerValue = time + "#" + Encodes.encodeMd5(SHOP_COCE + org.apache.commons.lang.StringUtils.substring(time, 0, 8) + VERSION + org.apache.commons.lang.StringUtils.substring(time, 8));
        String queryString = "{\"terminalId\":\"" + terminalId + "\"}";
        queryString = Encodes.encodeHex(Encodes.encodeBase64(queryString.getBytes()).getBytes());
        try {
            String hexString = HttpClientUtil.httpPost(appConfig.getUnionMemberUri(), queryString, headerValue);
            String json = new String(Encodes.decodeBase64(new String(Encodes.decodeHex(hexString))));

            return jsonMapper.fromJson(json, UnionMember.class);
        } catch (Exception e) {
            throw new InterfaceException(-1, e.getMessage()+"网络异常，[" + terminalId + "]商盟会员查询失败");
        }

    }

    public MarketingResponse convertToBean(Long goodsId, String orderType, String businessId) {
        String json = getMarketingResponse(goodsId, orderType, businessId);
        return convertJsonToBean(json);
    }

    public MarketingResponse convertJsonToBean(String json) {
        if (json.equals("{}")) return null;
        JsonMapper mapper = JsonMapper.buildNormalMapper();
        return mapper.fromJson(json, MarketingResponse.class);

    }
}
