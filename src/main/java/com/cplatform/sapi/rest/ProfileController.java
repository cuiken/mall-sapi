package com.cplatform.sapi.rest;

import com.cplatform.sapi.entity.order.TActOrder;
import com.cplatform.sapi.entity.profile.MemberFavorite;
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
 * User: cuikai
 * Date: 13-8-14
 * Time: 上午9:57
 */
@Controller
@RequestMapping(value = "/api/v1/profile")
public class ProfileController {

    private Page<TActOrder> orderPage = new Page<TActOrder>(10);
    private Page<MemberFavorite> favoritePage = new Page<MemberFavorite>(10);

    private ProfileService profileService;

    @RequestMapping(value = "myOrders", method = RequestMethod.GET)
    @ResponseBody
    public List<TActOrder> myOrders(HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        List<TActOrder> orders = profileService.searchOrder(orderPage, filters).getResult();
        return orders;
    }

    @RequestMapping(value = "myCollects", method = RequestMethod.GET)
    @ResponseBody
    public List<MemberFavorite> myCollects(HttpServletRequest request) {
        List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
        filters.add(new PropertyFilter("EQL_favoriteType","1"));
        filters.add(new PropertyFilter("EQL_userId","150964214"));
        List<MemberFavorite> favorites = profileService.searchMemberFavorite(favoritePage, filters).getResult();
        return favorites;
    }

    @Autowired
    public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }
}
