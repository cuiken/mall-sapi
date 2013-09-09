package com.cplatform.sapi.rest;

import com.cplatform.sapi.DTO.*;
import com.cplatform.sapi.entity.order.TActOrder;
import com.cplatform.sapi.entity.order.TActOrderExpress;
import com.cplatform.sapi.entity.order.TActOrderGoods;
import com.cplatform.sapi.entity.order.TActOrderPayment;
import com.cplatform.sapi.entity.product.ItemSale;
import com.cplatform.sapi.entity.product.SysFileImg;
import com.cplatform.sapi.entity.profile.MemberFavorite;
import com.cplatform.sapi.entity.profile.TItemComment;
import com.cplatform.sapi.mapper.BeanMapper;
import com.cplatform.sapi.orm.Page;
import com.cplatform.sapi.orm.PropertyFilter;
import com.cplatform.sapi.service.ItemSaleService;
import com.cplatform.sapi.service.ProfileService;
import com.cplatform.sapi.util.MediaTypes;
import com.cplatform.sapi.util.PathUtil;
import com.cplatform.sapi.util.TimeUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @Autowired
    private PathUtil pathUtil;

    @RequestMapping(value = "myOrders", method = RequestMethod.GET)
    @ResponseBody
    public OrderDTO myOrders(HttpServletRequest request) {

        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);

        String userId = request.getParameter("U_ID");
        String pageNo = request.getParameter("PAGE_NO");
        String pageSize = request.getParameter("PAGE_SIZE");
        String payStauts = request.getParameter("STATUS");

        Validate.notNull(userId, "用户ID不能为空");
        filters.add(new PropertyFilter("EQL_userId", userId));
        if(StringUtils.isNotBlank(payStauts)){
            if(payStauts.equals("1")){
                payStauts="0";
            }

            filters.add(new PropertyFilter("EQI_payStatus",payStauts));
        }

        if (StringUtils.isNotBlank(pageNo)) {
            orderPage.setPageNo(Integer.valueOf(pageNo));
        }

        if (StringUtils.isNotBlank(pageSize)) {
            orderPage.setPageSize(Integer.valueOf(pageSize));
        }

        orderPage = profileService.searchOrder(orderPage, filters);
        List<TActOrder> orders = orderPage.getResult();

        // 转换结果为指定格式
        OrderDTO dto = new OrderDTO();
        dto.setTotalRow(orderPage.getTotalItems());
        List<OrderDataDTO> datas = Lists.newArrayList();
        dto.setOrderDatas(datas);
        for (TActOrder order : orders) {
            OrderDataDTO data = new OrderDataDTO();
            data.setOrderId(order.getId());  //订单编号
            data.setSubject(order.getSubject()); //订单标题
            data.setStatus(order.getStatus()); //订单状态
            data.setOrderTime(order.getCreateTime()); //下单时间
            data.setAmount(order.getTotalPayAmount()); //订单金额
            data.setPayStatus(order.getPayStatus()); //订单支付状态
            data.setPayDescription(order.getPayDescription()); //支付说明
            data.setPayTime(order.getPayTime()); //订单支付时间
            TActOrderExpress orderExpress = order.getExpressInfo();
            if (orderExpress != null) {
                data.setFare(orderExpress.getExpressCost()); // 运费，展示用，单位分
            }

            //订单商品信息
            List<GoodsInfoDTO> goodsInfoDTOs = Lists.newArrayList(); //商品信息
            data.setGoodsInfoDTOs(goodsInfoDTOs);
            for (TActOrderGoods orderGood : order.getGoodsInfos()) {
                GoodsInfoDTO goodsInfoDTO = new GoodsInfoDTO();

                goodsInfoDTO.setPrice(orderGood.getPayPrice()); //支付价格
                goodsInfoDTO.setCount(orderGood.getCount()); //商品数量
                goodsInfoDTO.setTotalAmount(orderGood.getTotalAmount()); //商品总额

                goodsInfoDTO.setGoodsId(orderGood.getItemSale().getId());
                goodsInfoDTO.setGoodsName(orderGood.getItemSale().getName());
                List<SysFileImg> sysFileImgs = orderGood.getItemSale()
                        .getSysFileImgs();
                if (sysFileImgs != null && !sysFileImgs.isEmpty()) { // 设置列表图片
                    String image = "http://mall2.12580life.com"
                            + pathUtil.getPathById(2, orderGood.getItemSale()
                            .getId()) + "N4/"
                            + sysFileImgs.get(0).getFileName();
                    goodsInfoDTO.setGoodsImage(image);
                }
                goodsInfoDTOs.add(goodsInfoDTO);
            }

            //订单支付信息
            List<PaymentDTO> paymentDTOs = Lists.newArrayList();
            data.setPaymentDTOs(paymentDTOs); //订单支付信息
            for (TActOrderPayment payment : order.getPayments()) {
                PaymentDTO paymentDTO = new PaymentDTO();

                paymentDTO.setAmount(payment.getAmount());
                paymentDTO.setCurrency(payment.getCurrency());

                paymentDTOs.add(paymentDTO);
            }

            datas.add(data);
        }

        return dto;
    }

    @RequestMapping(value = "orderDetail", method = RequestMethod.GET)
    @ResponseBody
    public OrderDataDTO orderDetail(HttpServletRequest request) {
        String orderId = request.getParameter("ORDER_ID");
        TActOrder order = profileService.getOrder(Long.valueOf(orderId == null ? "" : orderId));

        OrderDataDTO orderDataDTO = new OrderDataDTO();
        orderDataDTO.setOrderId(order.getId());  //订单编号
        orderDataDTO.setSubject(order.getSubject()); //订单标题
        orderDataDTO.setStatus(order.getStatus()); //订单状态
        orderDataDTO.setOrderTime(order.getCreateTime()); //下单时间
        orderDataDTO.setAmount(order.getTotalPayAmount()); //订单金额
        orderDataDTO.setPayStatus(order.getPayStatus()); //订单支付状态
        orderDataDTO.setPayDescription(order.getPayDescription()); //支付说明
        orderDataDTO.setPayTime(order.getPayTime()); //订单支付时间

        TActOrderExpress orderExpress = order.getExpressInfo();
        if (orderExpress != null) {
            orderDataDTO.setFare(orderExpress.getExpressCost()); // 运费，展示用，单位分
            orderDataDTO.setExpressId(orderExpress.getExpressCompanyId());
            orderDataDTO.setExpressCode(orderExpress.getExpressNo());
            orderDataDTO.setExpressName(orderExpress.getExpressCompanyName());
            orderDataDTO.setAddress(orderExpress.getAddress());
            orderDataDTO.setDeliveryPhone(orderExpress.getCellphoneNumber());
            orderDataDTO.setDeliveryName(orderExpress.getReceiverName());
        }

        //订单商品信息
        List<GoodsInfoDTO> goodsInfoDTOs = Lists.newArrayList(); //商品信息
        orderDataDTO.setGoodsInfoDTOs(goodsInfoDTOs);
        for (TActOrderGoods orderGood : order.getGoodsInfos()) {
            GoodsInfoDTO goodsInfoDTO = new GoodsInfoDTO();

            goodsInfoDTO.setPrice(orderGood.getPayPrice()); //支付价格
            goodsInfoDTO.setCount(orderGood.getCount()); //商品数量
            goodsInfoDTO.setTotalAmount(orderGood.getTotalAmount()); //商品总额

            goodsInfoDTO.setGoodsId(orderGood.getItemSale().getId());
            goodsInfoDTO.setGoodsName(orderGood.getItemSale().getName());
            List<SysFileImg> sysFileImgs = orderGood.getItemSale()
                    .getSysFileImgs();
            if (sysFileImgs != null && !sysFileImgs.isEmpty()) { // 设置列表图片
                String image = "http://mall2.12580life.com"
                        + pathUtil.getPathById(2, orderGood.getItemSale()
                        .getId()) + "N4/"
                        + sysFileImgs.get(0).getFileName();
                goodsInfoDTO.setGoodsImage(image);
            }
            goodsInfoDTOs.add(goodsInfoDTO);
        }

        //订单支付信息
        List<PaymentDTO> paymentDTOs = Lists.newArrayList();
        orderDataDTO.setPaymentDTOs(paymentDTOs); //订单支付信息
        for (TActOrderPayment payment : order.getPayments()) {
            PaymentDTO paymentDTO = new PaymentDTO();

            paymentDTO.setAmount(payment.getAmount());
            paymentDTO.setCurrency(payment.getCurrency());

            paymentDTOs.add(paymentDTO);
        }

        return orderDataDTO;
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
