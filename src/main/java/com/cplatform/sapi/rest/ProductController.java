package com.cplatform.sapi.rest;

import com.cplatform.sapi.DTO.EcKillDTO;
import com.cplatform.sapi.entity.order.TActOrder;
import com.cplatform.sapi.entity.order.TActOrderGoods;
import com.cplatform.sapi.entity.product.ItemSale;
import com.cplatform.sapi.entity.product.SysFileImg;
import com.cplatform.sapi.entity.product.TSysType;
import com.cplatform.sapi.entity.profile.TItemComment;
import com.cplatform.sapi.mapper.BeanMapper;
import com.cplatform.sapi.orm.Page;
import com.cplatform.sapi.orm.PageRequest;
import com.cplatform.sapi.orm.PropertyFilter;
import com.cplatform.sapi.service.ProfileService;
import com.cplatform.sapi.service.product.ItemSaleService;
import com.cplatform.sapi.service.product.TSysTypeService;
import com.cplatform.sapi.util.MediaTypes;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * User: cuikai
 * Date: 13-8-13
 * Time: 下午1:12
 */
@Controller
@RequestMapping(value = "/api/v1/product")
public class ProductController {

    private Page<ItemSale> page = new Page<ItemSale>(100);
    private Page<TItemComment> commentPage = new Page<TItemComment>(10);

    private TSysTypeService typeService;
    private ItemSaleService itemSaleService;
    private ProfileService profileService;

    @RequestMapping(value = "category", method = RequestMethod.GET)
    @ResponseBody
    public List<TSysType> list() {
        return typeService.getByChannel(0L);

    }

    @RequestMapping(value = "detail/{itemId}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    @ResponseBody
    public ItemSale detail(@PathVariable("itemId") Long id) {

        ItemSale itemSale = itemSaleService.getItemSale(id == null ? 0L : id);
        itemSaleService.initProxy(itemSale.getSysFileImgs());
        return itemSale;
    }

    @RequestMapping(value = "graphicDetail", method = RequestMethod.GET, produces = MediaTypes.TEXT_PLAIN_UTF_8)
    @ResponseBody
    public String graphicDetail(@RequestParam("itemId") Long id) {

        ItemSale itemSale = itemSaleService.getItemSale(id == null ? 0L : id);
        return itemSale.getRemark();
    }

    @RequestMapping(value = "comments", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    @ResponseBody
    public List<TItemComment> comments(HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        String itemId = request.getParameter("itemId");
        filters.add(new PropertyFilter("EQI_type", "1"));
        filters.add(new PropertyFilter("EQL_itemSale.id", (itemId == null ? "205405" : itemId)));
        return profileService.searchItemComment(commentPage, filters).getResult();
    }

    @RequestMapping(value = "questions", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    @ResponseBody
    public List<TItemComment> questions(HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        String itemId = request.getParameter("itemId");
        filters.add(new PropertyFilter("EQI_type", "2"));
        filters.add(new PropertyFilter("EQL_itemSale.id", (itemId == null ? "205405" : itemId)));
        List<TItemComment> comments = profileService.searchItemComment(commentPage, filters).getResult();
        return comments;
    }

    /**
     * filter_EQL_id,filter_EQS_name,filter_LES_saleStopTime,filter_GES_saleStartTime
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "spike", method = RequestMethod.GET)
    @ResponseBody
    public List<EcKillDTO> spike(HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);

        filters.add(new PropertyFilter("EQL_iseckill", "1"));
        filters.add(new PropertyFilter("EQL_isValid", "1"));

        page.setOrderBy("createTime");
        page.setOrderDir(PageRequest.Sort.DESC);
        List<ItemSale> itemSales = itemSaleService.searchItemSale(page, filters).getResult();

        List<EcKillDTO> killDTOs = Lists.newArrayList();
        for (ItemSale itemSale : itemSales) {
            killDTOs.add(converToDTO(itemSale));
        }
//        return encodeData(killDTOs);
        return killDTOs;
    }

    @RequestMapping(value = "spikeDetail", method = RequestMethod.GET)
    @ResponseBody
    public EcKillDTO spikeDetail(HttpServletRequest request) {
        String itemId = request.getParameter("itemId");

        ItemSale itemSale = itemSaleService.getItemSale(Long.valueOf(itemId == null ? "4849" : itemId));
//        EcKillDTO dto = converToDTO(itemSale);
//        return encodeData(dto);
        return converToDTO(itemSale);
    }

    @RequestMapping(value = "goodStatus", method = RequestMethod.GET)
    @ResponseBody
    public String goodsStatus(HttpServletRequest request) {
        String itemId = request.getParameter("itemId");
        String userId = request.getParameter("userId");
        ItemSale itemSale = itemSaleService.getItemSale(Long.valueOf(itemId == null ? "4849" : itemId));
        List<TActOrderGoods> goodses = itemSale.getOrderGoodses();
        int status = 0;
        if (goodses.size() == 0) { //未购买
            status = 0;
        } else {
            for (TActOrderGoods goods : goodses) {
                TActOrder order = goods.getOrder();
                if (Long.valueOf(userId == null ? "150964216" : userId) == order.getUserId()) {
                    status = order.getStatus();
                    if (status == 2) {
                        break;
                    } else {
                        status = 1;
                        continue;
                    }
                }
            }
        }
        int flag = 0;
        String msg = "";
        switch (status) {
            case 0:
                msg = "未购买";
                break;
            case 1:
                flag = 1;
                msg = "待付款";
                break;
            case 2:
                flag = 2;
                msg = "已付款";
                break;
        }
        return "{\"flag\":\"" + flag + "\",\"msg\":\"" + msg + "\"}";
    }


//    private String encodeData(Object object) {
//        JsonMapper mapper = JsonMapper.buildNormalMapper();
//        String base64 = Encodes.encodeBase64(mapper.toJson(object).getBytes());
//        return Encodes.encodeHex(base64.getBytes());
//    }

    private EcKillDTO converToDTO(ItemSale itemSale) {
        EcKillDTO dto = BeanMapper.map(itemSale, EcKillDTO.class);
        List<String> thumbs = Lists.newArrayList();
        for (SysFileImg fileImg : itemSale.getSysFileImgs()) {
            thumbs.add(fileImg.getFileName());
        }
        dto.setThumbs(thumbs);
        return dto;
    }

    @Autowired
    public void setTypeService(TSysTypeService typeService) {
        this.typeService = typeService;
    }

    @Autowired
    public void setItemSaleService(ItemSaleService itemSaleService) {
        this.itemSaleService = itemSaleService;
    }

    @Autowired
    public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }
}
