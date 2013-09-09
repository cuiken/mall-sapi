package com.cplatform.sapi.rest;

import com.cplatform.sapi.DTO.CategoryDTO;
import com.cplatform.sapi.DTO.CommentDTO;
import com.cplatform.sapi.DTO.EcKillDTO;
import com.cplatform.sapi.DTO.Product.ProductDTO;
import com.cplatform.sapi.DTO.QuestionDTO;
import com.cplatform.sapi.entity.TChannelType;
import com.cplatform.sapi.entity.TSysType;
import com.cplatform.sapi.entity.order.Deposit;
import com.cplatform.sapi.entity.order.TActOrder;
import com.cplatform.sapi.entity.order.TActOrderGoods;
import com.cplatform.sapi.entity.product.ItemPrice;
import com.cplatform.sapi.entity.product.ItemSale;
import com.cplatform.sapi.entity.product.ItemSaleExt;
import com.cplatform.sapi.entity.product.SysFileImg;
import com.cplatform.sapi.entity.profile.TItemComment;
import com.cplatform.sapi.mapper.BeanMapper;
import com.cplatform.sapi.orm.Page;
import com.cplatform.sapi.orm.PageRequest;
import com.cplatform.sapi.orm.PropertyFilter;
import com.cplatform.sapi.service.ItemSaleService;
import com.cplatform.sapi.service.ProfileService;
import com.cplatform.sapi.service.RegionService;
import com.cplatform.sapi.util.AppConfig;
import com.cplatform.sapi.util.MediaTypes;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: cuikai Date: 13-8-13 Time: 下午1:12
 */
@Controller
@RequestMapping(value = "/api/v1/product")
public class ProductController {

    private final Page<ItemSale> page = new Page<ItemSale>(100);

    private Page<TItemComment> commentPage = new Page<TItemComment>(10);

    private ItemSaleService itemSaleService;

    private RegionService regionService;

    private ProfileService profileService;

    @Autowired
    private AppConfig appConfig;

    @RequestMapping(value = "category", method = RequestMethod.GET)
    @ResponseBody
    public CategoryDTO list(HttpServletRequest request) {
        String areaCode = request.getParameter("AREA_CODE");
        String categoryId = request.getParameter("CATEGORY_ID");
        String regionCode = regionService.getRegionByAreaCode(areaCode).getRegionCode();
        List<TChannelType> channelTypes = regionService.getChannelTypeByRegionAndChannel(regionCode, 1L);

        List<CategoryDTO.Data> datas = Lists.newArrayList();
        CategoryDTO dto = new CategoryDTO();
        if (StringUtils.isBlank(categoryId) || categoryId.equals("0")) {
            for (TChannelType channelType : channelTypes) {
                CategoryDTO.Data data = new CategoryDTO.Data();
                data.setId(channelType.getSysType().getId());
                data.setName(channelType.getSysType().getName());
                datas.add(data);
            }
        } else {
            List<TSysType> sysTypes = regionService.getSysTypeByParentId(Long.valueOf(categoryId));
            for (TSysType sysType : sysTypes) {
                CategoryDTO.Data data = new CategoryDTO.Data();
                data.setName(sysType.getName());
                data.setId(sysType.getId());
                datas.add(data);
            }
        }
        dto.setData(datas);
        return dto;

    }

    @RequestMapping(value = "detail/{itemId}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    @ResponseBody
    public ProductDTO detail(@PathVariable("itemId") Long id) {

        ItemSale itemSale = itemSaleService.getItemSale(id);
        ItemSaleExt ext = itemSale.getItemSaleExt();

        List<ItemPrice> prices = itemSale.getItemPrice();

        Map<String, Object> memPrice = Maps.newHashMap();
        for (ItemPrice price : prices) {
            memPrice.put(price.getPriceTypeCode(), price.getPrice());
        }

        ProductDTO dto = BeanMapper.map(itemSale, ProductDTO.class);
        if (ext != null) {
            dto.setFare(ext.getLogisticsFee());
            dto.setLogisticsFeeType(ext.getLogisticsFeeType());
            dto.setSoldCount(ext.getSaleNum().intValue());
        }
        dto.setMemberPrice(memPrice);

        dto.setImages(Lists.newArrayList(itemSale.getImgPath()));
        return dto;
    }

    @RequestMapping(value = "graphicDetail", method = RequestMethod.GET, produces = MediaTypes.TEXT_PLAIN_UTF_8)
    @ResponseBody
    public String graphicDetail(@RequestParam("itemId") Long id) {

        ItemSale itemSale = itemSaleService.getItemSale(id);
        return itemSale.getRemark();
    }

    @RequestMapping(value = "comments", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    @ResponseBody
    public CommentDTO comments(HttpServletRequest request) {

        commentPage = buildPage(request, "1");

        List<TItemComment> comments = commentPage.getResult();
        CommentDTO dto = new CommentDTO();
        dto.setData(BeanMapper.mapList(comments, CommentDTO.CommentDataDTO.class));
        dto.setTotalRow(commentPage.getTotalItems());
        return dto;

    }

    @RequestMapping(value = "questions", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    @ResponseBody
    public QuestionDTO questions(HttpServletRequest request) {

        commentPage = buildPage(request, "2");
        List<TItemComment> comments = commentPage.getResult();
        QuestionDTO dto = new QuestionDTO();
        dto.setTotalRow(commentPage.getTotalItems());
        dto.setData(BeanMapper.mapList(comments, QuestionDTO.Data.class));
        return dto;
    }

    private Page<TItemComment> buildPage(HttpServletRequest request, String type) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        String itemId = request.getParameter("GOOD_ID");
        String pageNo = request.getParameter("PAGE_NO");
        String pageSize = request.getParameter("PAGE_SIZE");
        Validate.notNull(itemId, "商品ID不能为空");
        filters.add(new PropertyFilter("EQI_type", type));
        filters.add(new PropertyFilter("EQL_itemSale.id", itemId));

        if (StringUtils.isNotBlank(pageNo)) {
            commentPage.setPageNo(Integer.valueOf(pageNo));
        }
        if (StringUtils.isNotBlank(pageSize)) {
            commentPage.setPageSize(Integer.valueOf(pageSize));
        }

        return profileService.searchItemComment(commentPage, filters);
    }

    /**
     * filter_EQL_id,filter_EQS_name,filter_LES_saleStopTime,
     * filter_GES_saleStartTime
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
        // return encodeData(killDTOs);
        return killDTOs;
    }

    @RequestMapping(value = "spikeDetail", method = RequestMethod.GET)
    @ResponseBody
    public EcKillDTO spikeDetail(HttpServletRequest request) {
        String itemId = request.getParameter("itemId");

        ItemSale itemSale = itemSaleService.getItemSale(Long.valueOf(itemId));
        // EcKillDTO dto = converToDTO(itemSale);
        // return encodeData(dto);
        return converToDTO(itemSale);
    }

    @RequestMapping(value = "goodStatus", method = RequestMethod.GET)
    @ResponseBody
    public String goodsStatus(HttpServletRequest request) {
        String itemId = request.getParameter("itemId");
        String userId = request.getParameter("userId");
        Validate.notNull(userId, "用户ID不能为空");
        ItemSale itemSale = itemSaleService.getItemSale(Long.valueOf(itemId));
        List<TActOrderGoods> goodses = itemSale.getOrderGoodses();
        int status = 0;
        if (goodses.size() == 0) { // 未购买
            status = 0;
        } else {
            for (TActOrderGoods goods : goodses) {
                TActOrder order = goods.getOrder();
                if (Long.valueOf(userId) == order.getUserId()) {
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

    /**
     * 获取用户保证金
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "deposit", method = RequestMethod.GET)
    @ResponseBody
    public List<Deposit> deposit(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String itemIds = appConfig.getDepositItemIds();
        String[] idsStrings = itemIds.split(",");
        List<Deposit> deposits = new ArrayList<Deposit>();
        if (idsStrings != null && idsStrings.length > 0) {
            for (int i = 0; i < idsStrings.length; i++) {
                ItemSale itemSale = itemSaleService.getItemSale(Long.valueOf(idsStrings[i]));

                List<TActOrderGoods> goodses = itemSale.getOrderGoodses();
                if (goodses != null && goodses.size() > 0) {
                    for (TActOrderGoods goods : goodses) {
                        TActOrder order = goods.getOrder();
                        if (null != order) {
                            if (Long.parseLong(userId) == order.getUserId()) {

                                Deposit deposit = new Deposit();
                                deposit.setOrderId(order.getId());
                                deposit.setGoodsId(itemSale.getId());
                                deposit.setGoodsName(goods.getGoodsSubject());
                                deposit.setStoreId(order.getShopId());
                                deposit.setStoreName(order.getShopSubject());
                                deposit.setPrice(order.getPayAmount());
                                deposit.setStatus(order.getStatus());
                                deposit.setImgPath(itemSale.getImgPath());
                                deposit.setBuyTime(order.getCreateTime());
                                deposits.add(deposit);
                            }
                        }
                    }
                }
            }
        }
        return deposits;
    }

    // private String encodeData(Object object) {
    // JsonMapper mapper = JsonMapper.buildNormalMapper();
    // String base64 = Encodes.encodeBase64(mapper.toJson(object).getBytes());
    // return Encodes.encodeHex(base64.getBytes());
    // }

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
    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
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
