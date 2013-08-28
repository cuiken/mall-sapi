package com.cplatform.sapi.service;

import com.cplatform.sapi.entity.SysRegion;
import com.cplatform.sapi.repository.SysRegionDao;
import com.cplatform.sapi.util.AppConfig;
import com.cplatform.sapi.util.HttpClientUtils;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * User: cuikai
 * Date: 13-8-27
 * Time: 上午8:49
 */
@Component
public class ProductService {

    private static final Logger logger = Logger.getLogger(ProductService.class);
    //每页数据数
//    private static final int SEARCH_PAGE_ROWS = 10;
    @Autowired
    private AppConfig appConfig;

    @Autowired
    private SysRegionDao regionDao;

    public SysRegion getRegionByAreaCode(String areaCode) {
        return regionDao.findUnique("select r from SysRegion r where r.areaCode=? and r.regionLevel=1L", areaCode);
    }

    public String getSearchJson(String keyword, String region_code, String type_id,
                                String sort, String curpage, int pageSize) throws Exception {

        //创建http的body
        JSONObject jsonBody = new JSONObject();
        try {
            region_code = region_code.substring(0, region_code.length() - 2);
            if (type_id != null && type_id != "" && !type_id.equals("0")) {
                keyword = "";
            }
            jsonBody = HttpClientUtils.makeJsonBody(keyword, region_code, type_id, sort, curpage, pageSize);

        } catch (Exception e) {
            logger.error("search make http body error:" + e.toString());
        }

        String jsonResponce = "";
        try {
            //执行post请求
            jsonResponce = HttpClientUtils.httpPost(appConfig.getSearch_Http_Url(), new String(Base64.encodeBase64(jsonBody.toString().getBytes("UTF-8"))), appConfig.getConnection_Manager_Timeout(), appConfig.getSo_Timeout());

            String stringJson = new String(Base64.decodeBase64(jsonResponce.getBytes("UTF-8")), "UTF-8");
            if (stringJson != null && !stringJson.equals("")) {
                return stringJson;
            }
        } catch (Exception e) {
            logger.error("httpPost error:" + e.toString());
            throw new Exception(e);
        }
        return jsonResponce;

    }
}
