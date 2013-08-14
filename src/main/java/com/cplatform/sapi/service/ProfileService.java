package com.cplatform.sapi.service;

import com.cplatform.sapi.entity.order.TActOrder;
import com.cplatform.sapi.entity.profile.MemberFavorite;
import com.cplatform.sapi.orm.Page;
import com.cplatform.sapi.orm.PropertyFilter;
import com.cplatform.sapi.repository.order.TActOrderDao;
import com.cplatform.sapi.repository.profile.MemberFavoriteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: cuikai
 * Date: 13-8-14
 * Time: 上午11:41
 */
@Component
@Transactional
public class ProfileService {

    private MemberFavoriteDao memberFavoriteDao;
    private TActOrderDao orderDao;

    public Page<MemberFavorite> searchMemberFavorite(final Page<MemberFavorite> page, final List<PropertyFilter> filters) {
        return memberFavoriteDao.findPage(page, filters);
    }

    public Page<TActOrder> searchOrder(final Page<TActOrder> page, final List<PropertyFilter> filters) {

        return orderDao.findPage(page, filters);
    }

    @Autowired
    public void setOrderDao(TActOrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Autowired
    public void setMemberFavoriteDao(MemberFavoriteDao memberFavoriteDao) {
        this.memberFavoriteDao = memberFavoriteDao;
    }
}
