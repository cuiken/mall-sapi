package com.cplatform.sapi.rest;

import com.cplatform.sapi.DTO.ProductSearchDTO;
import com.cplatform.sapi.DTO.ProductSearchMapperDTO;
import com.cplatform.sapi.entity.SysRegion;
import com.cplatform.sapi.mapper.BeanMapper;
import com.cplatform.sapi.mapper.JsonMapper;
import com.cplatform.sapi.service.ProductService;
import com.cplatform.sapi.service.RegionService;
import com.cplatform.sapi.util.PathUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * User: cuikai
 * Date: 13-8-27
 * Time: 上午8:48
 */
@Controller
@RequestMapping(value = "/api/v1/search")
public class SearchController {

    @Autowired
    private ProductService productService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private PathUtil pathUtil;

    @RequestMapping(value = "goodsInfo", method = RequestMethod.GET)
    @ResponseBody
    public ProductSearchMapperDTO goodsInfo(HttpServletRequest request) {

        // 搜索关键字
        String searchKey = request.getParameter("KEY_WORD");

        //地市搜索
        String region_code = request.getParameter("AREA_CODE");


        region_code = regionService.getRegionByAreaCode(region_code).getRegionCode();


        String pageSize = request.getParameter("PAGE_SIZE");

        // 排序：0：默认排序，按搜索结果与关键词匹配度排序,1：人气倒序,2：价格升序，3：价格倒序，4：上架时间倒序
        String sort = request.getParameter("SORT");
        if (StringUtils.isBlank(sort)) {
            sort = "0";
        }

        // 当前页数
        String curpage = request.getParameter("PAGE_NO");
        if (StringUtils.isBlank(curpage)) {
            curpage = "1";
        }
        String type_id = request.getParameter("CATEGORY_ID");
        if (StringUtils.isBlank(type_id)) {
            type_id = "0";
        }
        // httpclient 获取搜索返回的json
        String searchJson = null;
        try {
            searchJson = productService.getSearchJson(searchKey, region_code,
                    type_id, sort, curpage, Integer.valueOf(pageSize == null ? "10" : pageSize));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isBlank(searchJson)) {
            searchJson = "{'RET':'404'}";
        }

        JsonMapper mapper = JsonMapper.buildNormalMapper();
        ProductSearchDTO dto = mapper.fromJson(searchJson, ProductSearchDTO.class);
        List<ProductSearchDTO.Data> datas = dto.getData();

        for (ProductSearchDTO.Data data : datas) {
            data.setWebPath("http://mall2.12580life.com" + pathUtil.getPathById(2,data.getId())+"N4/"+data.getWebPath());
        }

        return BeanMapper.map(dto, ProductSearchMapperDTO.class);
    }
}
