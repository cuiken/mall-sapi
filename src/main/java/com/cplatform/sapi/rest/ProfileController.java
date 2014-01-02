package com.cplatform.sapi.rest;

import com.cplatform.sapi.DTO.*;
import com.cplatform.sapi.entity.order.TActOrder;
import com.cplatform.sapi.entity.product.ItemSale;
import com.cplatform.sapi.entity.profile.MemberFavorite;
import com.cplatform.sapi.entity.profile.TItemComment;
import com.cplatform.sapi.mapper.BeanMapper;
import com.cplatform.sapi.orm.Page;
import com.cplatform.sapi.orm.PageRequest;
import com.cplatform.sapi.orm.PropertyFilter;
import com.cplatform.sapi.service.ItemSaleService;
import com.cplatform.sapi.service.ProfileService;
import com.cplatform.sapi.util.MediaTypes;
import com.cplatform.sapi.util.TimeUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 个人中心api
 * User: cuikai
 * Date: 13-8-14
 * Time: 上午9:57
 */
@Controller
@RequestMapping(value = "/api/v1/profile")
public class ProfileController {

    private Page<TActOrder> orderPage = new Page<TActOrder>(10);
    private Page<MemberFavorite> favoritePage = new Page<MemberFavorite>(10);
    private Page<TItemComment> commentPage = new Page<TItemComment>(10);

    private ProfileService profileService;
    private ItemSaleService itemSaleService;

    @RequestMapping(value = "myOrders", method = RequestMethod.GET)
    @ResponseBody
    public OrderDTO myOrders(HttpServletRequest request) {

        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);

        String userId = request.getParameter("U_ID");
        String pageNo = request.getParameter("PAGE_NO");
        String pageSize = request.getParameter("PAGE_SIZE");
        String payStatus = request.getParameter("STATUS");

        Validate.notNull(userId, "用户ID不能为空");
        filters.add(new PropertyFilter("EQL_userId", userId));
        //只区分已支付和未支付
        if (StringUtils.isNotBlank(payStatus)) {
            if (payStatus.equals("1") || payStatus.equals("0")) {
                filters.add(new PropertyFilter("LEI_payStatus", "1"));
                filters.add(new PropertyFilter("GEI_payStatus", "0"));
            } else {
                filters.add(new PropertyFilter("EQI_payStatus", payStatus));
            }
        }

        if (StringUtils.isNotBlank(pageNo)) {
            orderPage.setPageNo(Integer.valueOf(pageNo));
        }

        if (StringUtils.isNotBlank(pageSize)) {
            orderPage.setPageSize(Integer.valueOf(pageSize));
        }
        orderPage.setOrderBy("createTime");
        orderPage.setOrderDir(PageRequest.Sort.DESC);

        orderPage = profileService.searchOrder(orderPage, filters);
        List<TActOrder> orders = orderPage.getResult();

        // 转换结果为指定格式
        OrderDTO dto = new OrderDTO();
        dto.setTotalRow(orderPage.getTotalItems());
        List<OrderDataDTO> orderDataDTOs = Lists.newArrayList();

        for (TActOrder order : orders) {

            orderDataDTOs.add(profileService.buildOrderData(order));
        }
        dto.setOrderDatas(orderDataDTOs);
        return dto;
    }

    @RequestMapping(value = "orderDetail", method = RequestMethod.GET)
    @ResponseBody
    public OrderDataDTO orderDetail(@RequestParam(value = "ORDER_ID") Long orderId) {

        TActOrder order = profileService.getOrder(orderId);

        return profileService.buildOrderData(order);
    }

    @RequestMapping(value = "myCollects", method = RequestMethod.GET)
    @ResponseBody
    public ProductSearchMapperDTO myCollects(HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        String userId = request.getParameter("U_ID");

        Validate.notNull(userId, "用户ID不能为空");
        filters.add(new PropertyFilter("EQL_favoriteType", "1"));
        filters.add(new PropertyFilter("EQL_userId", userId));

        String pageNo = request.getParameter("PAGE_NO");
        String pageSize = request.getParameter("PAGE_SIZE");
        if (StringUtils.isNotBlank(pageNo)) {
            favoritePage.setPageNo(Integer.valueOf(pageNo));
        }


        if (StringUtils.isNotBlank(pageSize)) {
            favoritePage.setPageSize(Integer.valueOf(pageSize));
        }

        favoritePage = profileService.searchMemberFavorite(favoritePage, filters);
        List<MemberFavorite> favorites = favoritePage.getResult();
        List<ItemSale> itemSales = Lists.newArrayList();
        ProductSearchMapperDTO dto = new ProductSearchMapperDTO();
        dto.setTotalRow(favoritePage.getTotalItems());

        for (MemberFavorite favorite : favorites) {
            itemSales.add(favorite.getItemSale());
        }

        dto.setData(BeanMapper.mapList(itemSales, ProductSearchMapperDTO.Data.class));

        return dto;
    }

    @RequestMapping(value = "myComments", method = RequestMethod.GET)
    @ResponseBody
    public MyCommentDTO myComments(HttpServletRequest request) {

        MyCommentDTO dto = new MyCommentDTO();
        commentPage = searchCommentByType(request, "1");
        dto.setTotalRow(commentPage.getTotalItems());
        dto.setData(BeanMapper.mapList(commentPage.getResult(), MyCommentDTO.Data.class));
        return dto;
    }

    @RequestMapping(value = "myQuestions", method = RequestMethod.GET)
    @ResponseBody
    public MyQuestionDTO myQuestions(HttpServletRequest request) {

        MyQuestionDTO dto = new MyQuestionDTO();
        commentPage = searchCommentByType(request, "2");
        dto.setTotalRow(commentPage.getTotalItems());
        dto.setData(BeanMapper.mapList(commentPage.getResult(), MyQuestionDTO.Data.class));
        return dto;
    }

    @RequestMapping(value = "saveMyComment", method = RequestMethod.POST, consumes = MediaTypes.JSON)
    @ResponseBody
    public String saveMyComment(@RequestBody CreateCommentDTO dto) {

        TItemComment comment = this.buildTItemComment(dto, 1);
        profileService.saveComment(comment);
        return "{\"FLAG\":\"0\",\"MSG\":\"操作成功\"}";
    }

    @RequestMapping(value = "saveMyFavorite", method = RequestMethod.POST, consumes = MediaTypes.JSON)
    @ResponseBody
    public String saveMyFavorite(@RequestBody CreateFavoriteDTO dto) {
        MemberFavorite entity = new MemberFavorite();
        entity.setUserId(dto.getUserId());
        entity.setItemSale(itemSaleService.getItemSale(dto.getGoodId()));
        entity.setFavoriteType(1L);
        entity.setUpdateTime(TimeUtil.now());
        profileService.saveMemberFavorite(entity);
        return "{\"FLAG\":\"0\",\"MSG\":\"操作成功\"}";
    }

    @RequestMapping(value = "saveMyQuestion", method = RequestMethod.POST, consumes = MediaTypes.JSON)
    @ResponseBody
    public String saveMyQuestion(@RequestBody CreateCommentDTO dto) {

        TItemComment question = buildTItemComment(dto, 2);
        profileService.saveComment(question);
        return "{\"FLAG\":\"0\",\"MSG\":\"操作成功\"}";
    }

    private Page searchCommentByType(HttpServletRequest request, String type) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        String userId = request.getParameter("U_ID");

        Validate.notNull(userId, "用户ID不能为空");
        filters.add(new PropertyFilter("EQI_type", type));
        filters.add(new PropertyFilter("EQS_userId", userId));

        String pageNo = request.getParameter("PAGE_NO");
        String pageSize = request.getParameter("PAGE_SIZE");
        if (StringUtils.isNotBlank(pageNo)) {
            commentPage.setPageNo(Integer.valueOf(pageNo));
        }

        if (StringUtils.isNotBlank(pageSize)) {
            commentPage.setPageSize(Integer.valueOf(pageSize));
        }

        return profileService.searchItemComment(commentPage, filters);
    }

    private TItemComment buildTItemComment(CreateCommentDTO dto, int type) {
        TItemComment entity = new TItemComment();
        entity.setType(type);
        entity.setRank(dto.getLevel());
        entity.setContent(dto.getContent());
        entity.setItemSale(itemSaleService.getItemSale(dto.getGoodId()));
        entity.setUserId(String.valueOf(dto.getUserId()));
        entity.setUpdateTime(TimeUtil.now());
        return entity;
    }

    @Autowired
    public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Autowired
    public void setItemSaleService(ItemSaleService itemSaleService) {
        this.itemSaleService = itemSaleService;
    }
}
