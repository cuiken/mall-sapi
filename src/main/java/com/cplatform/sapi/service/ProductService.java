package com.cplatform.sapi.service;

import com.cplatform.sapi.DTO.ProductSearchDTO;
import com.cplatform.sapi.DTO.ProductSearchMapperDTO;
import com.cplatform.sapi.DTO.SearchParam;
import com.cplatform.sapi.mapper.BeanMapper;
import com.cplatform.sapi.mapper.JsonMapper;
import com.cplatform.sapi.util.AppConfig;
import com.cplatform.sapi.util.HttpClientUtil;
import com.cplatform.sapi.util.PathUtil;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.List;

/**
 * User: cuikai
 * Date: 13-8-27
 * Time: 上午8:49
 */
@Component
public class ProductService {

    private static final String KEYS = "abcdefghijklmnopqrstuvwx";

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private PathUtil pathUtil;

    private String getSearchJson(SearchParam searchParam) throws Exception {

        SearchParam params = searchParam;

        JSONObject jsonBody = new JSONObject();
        try {
            String region_code = params.getRegionCode();
            params.setRegionCode(region_code.substring(0, region_code.length() - 2));

            if (StringUtils.isNotBlank(params.getCategoryId())) {
                params.setKeyWord("");
            }
            jsonBody = makeJsonBody(params);

        } catch (Exception e) {
            logger.error("search make http body error:" + e.toString());
        }

        String jsonResponce = HttpClientUtil.httpPost(appConfig.getSearch_Http_Url(), new String(Base64.encodeBase64(jsonBody.toString().getBytes("UTF-8"))));

        return new String(Base64.decodeBase64(jsonResponce.getBytes("UTF-8")), "UTF-8");

    }

    public ProductSearchMapperDTO search(SearchParam param) throws Exception {
        String searchJson = this.getSearchJson(param);
        JsonMapper mapper = JsonMapper.buildNormalMapper();
        ProductSearchDTO dto = mapper.fromJson(searchJson, ProductSearchDTO.class);
        if(dto==null)
            return new ProductSearchMapperDTO();
        List<ProductSearchDTO.Data> datas = dto.getData();

        for (ProductSearchDTO.Data data : datas) {
            data.setWebPath(appConfig.getServerHost() + pathUtil.getPathById(2, data.getId()) + "N3/" + data.getWebPath());
        }

        return BeanMapper.map(dto, ProductSearchMapperDTO.class);
    }

    /**
     * 获取签名串
     */
    private static String doSign(String baseStr) throws Exception {
        SecretKeySpec key = new SecretKeySpec(KEYS.getBytes("UTF-8"),
                "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(key);
        byte[] bytes = mac.doFinal(baseStr.substring(1).getBytes("UTF-8"));
        return new String(Base64.encodeBase64(bytes));
    }

    /**
     * 获取 nonce
     *
     * @return nonce
     */
    private static String getNonce() {
        return RandomStringUtils.random(6, false, true);
    }

    /**
     * 获取 timestamp
     *
     * @return timestamp
     */
    private static String getTimestamp() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 组建post方法的body
     */
    private static JSONObject makeJsonBody(SearchParam param) throws Exception {
        String jsonStr = "{\"query\":\"goodsList\"}";
        JSONObject json = JSONObject.fromObject(jsonStr);
        String nonce_ = getNonce();
        json.put("nonce", nonce_);
        json.put("ckey", "uniongoods");
        json.put("timestamp", getTimestamp());
        json.put("type_id", param.getCategoryId());
        json.put("keyword", param.getKeyWord());
//		json.put("store_id", store_id);
        json.put("region_code", param.getRegionCode());
//		json.put("brand", brand);
//		json.put("params", params);// intel;AMD
        json.put("sort", param.getSort());
        json.put("page", param.getPageNo());
        json.put("pagesize", param.getPageSize() + "");

        String baseStr = json.toString() + nonce_;

        json.put("signature", doSign(baseStr));

        return json;
    }
}
