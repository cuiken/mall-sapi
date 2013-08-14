package com.cplatform.sapi.rest;

import com.cplatform.sapi.entity.order.TActOrder;
import com.cplatform.sapi.entity.profile.MemberFavorite;
import com.cplatform.sapi.entity.profile.TItemComment;
import com.cplatform.sapi.orm.Page;
import com.cplatform.sapi.orm.PropertyFilter;
import com.cplatform.sapi.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @RequestMapping(value = "myOrders", method = RequestMethod.GET)
    @ResponseBody
    public List<TActOrder> myOrders(HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        List<TActOrder> orders = profileService.searchOrder(orderPage, filters).getResult();
        return orders;
    }

    @RequestMapping(value = "orderDetail", method = RequestMethod.GET)
    @ResponseBody
    public TActOrder orderDetail(HttpServletRequest request) {
        String orderId = request.getParameter("ORDER_ID");
        TActOrder order = profileService.getOrder(Long.valueOf(orderId == null ? "214748367575" : orderId));

        profileService.initOrderProxyObject(order.getExpressInfo());
        profileService.initOrderProxyObject(order.getGoodsInfos());
        profileService.initOrderProxyObject(order.getPayments());
        return order;
    }

    @RequestMapping(value = "myCollects", method = RequestMethod.GET)
    @ResponseBody
    public List<MemberFavorite> myCollects(HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQL_favoriteType", "1"));
        filters.add(new PropertyFilter("EQL_userId", "150964214"));
        List<MemberFavorite> favorites = profileService.searchMemberFavorite(favoritePage, filters).getResult();
        return favorites;
    }

    @RequestMapping(value = "myComments", method = RequestMethod.GET)
    @ResponseBody
    public List<TItemComment> myComments(HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQI_type", "1"));
        filters.add(new PropertyFilter("EQS_userId", "150964214"));
        List<TItemComment> comments = profileService.searchItemComment(commentPage, filters).getResult();
        return comments;
    }

    @RequestMapping(value = "myQuestions", method = RequestMethod.GET)
    @ResponseBody
    public List<TItemComment> myQuestions(HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQI_type", "2"));
        filters.add(new PropertyFilter("EQS_userId", "150964214"));
        List<TItemComment> comments = profileService.searchItemComment(commentPage, filters).getResult();
        return comments;
    }

    @Autowired
    public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }
}
