package com.cplatform.sapi.rest;

import com.cplatform.sapi.DTO.ProductSearchMapperDTO;
import com.cplatform.sapi.DTO.SearchParam;
import com.cplatform.sapi.service.ProductService;
import com.cplatform.sapi.service.RegionService;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    /**
     * @param keyWord    搜索关键字
     * @param areaCode   地市搜索
     * @param pageSize
     * @param sort       排序：0：默认排序，按搜索结果与关键词匹配度排序,1：人气倒序,2：价格升序，3：价格倒序，4：上架时间倒序
     * @param pageNo     当前页数
     * @param categroyId
     * @return
     */
    @RequestMapping(value = "goodsInfo", method = RequestMethod.GET)
    @ResponseBody
    public ProductSearchMapperDTO list(@RequestParam(value = "KEY_WORD", required = false) String keyWord,
                                       @RequestParam(value = "AREA_CODE") String areaCode,
                                       @RequestParam(value = "PAGE_SIZE", required = false, defaultValue = "10") int pageSize,
                                       @RequestParam(value = "SORT", required = false, defaultValue = "0") int sort,
                                       @RequestParam(value = "PAGE_NO", required = false, defaultValue = "1") int pageNo,
                                       @RequestParam(value = "CATEGORY_ID", required = false, defaultValue = "") String categroyId) throws Exception {

        Validate.notEmpty(areaCode, "areaCode不能为空");
        String region_code;
        try {
            region_code = regionService.getRegionByAreaCode(areaCode).getRegionCode();
        } catch (Exception e) {
            throw new Exception("根据[" + areaCode + "]查询regionCode失败");
        }
        SearchParam params = new SearchParam();
        params.setCategoryId(categroyId);
        params.setKeyWord(keyWord);
        params.setPageNo(pageNo);
        params.setPageSize(pageSize);
        params.setSort(sort);
        params.setRegionCode(region_code);

        return productService.search(params);

    }
}
