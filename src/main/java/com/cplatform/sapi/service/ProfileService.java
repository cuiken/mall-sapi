package com.cplatform.sapi.service;

import com.cplatform.sapi.entity.order.TActOrder;
import com.cplatform.sapi.entity.profile.MemberFavorite;
import com.cplatform.sapi.entity.profile.TItemComment;
import com.cplatform.sapi.orm.Page;
import com.cplatform.sapi.orm.PropertyFilter;
import com.cplatform.sapi.repository.order.TActOrderDao;
import com.cplatform.sapi.repository.profile.ItemCommentDao;
import com.cplatform.sapi.repository.profile.MemberFavoriteDao;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
    private ItemCommentDao itemCommentDao;

    private BusinessLogger businessLogger;

    public void saveMemberFavorite(MemberFavorite entity) {
        memberFavoriteDao.save(entity);
        //业务日志
        Map logData = Maps.newHashMap();
        logData.put("MemberFavorite", entity);
        businessLogger.log("MemberFavorite", "save", logData);
    }

    public void saveComment(TItemComment entity) {
        itemCommentDao.save(entity);

        Map logData = Maps.newHashMap();
        logData.put("TItemComment", entity);
        businessLogger.log("TItemComment", "save", logData);
    }

    public Page<MemberFavorite> searchMemberFavorite(final Page<MemberFavorite> page, final List<PropertyFilter> filters) {
        Map logData = Maps.newHashMap();
        logData.put("filters", filters);
        logData.put("pageNo", page.getPageNo());
        logData.put("pageSize", page.getPageSize());
        businessLogger.log("MemberFavorite", "search", logData);

        return memberFavoriteDao.findPage(page, filters);
    }

    public Page<TActOrder> searchOrder(final Page<TActOrder> page, final List<PropertyFilter> filters) {
        Map logData = Maps.newHashMap();
        logData.put("filters", filters);
        logData.put("pageNo", page.getPageNo());
        logData.put("pageSize", page.getPageSize());
        businessLogger.log("TActOrder", "search", logData);

        return orderDao.findPage(page, filters);
    }

    public Page<TItemComment> searchItemComment(final Page<TItemComment> page, final List<PropertyFilter> filters) {
        Map logData = Maps.newHashMap();
        logData.put("filters", filters);
        logData.put("pageNo", page.getPageNo());
        logData.put("pageSize", page.getPageSize());
        businessLogger.log("TItemComment", "search", logData);

        return itemCommentDao.findPage(page, filters);
    }

    public TActOrder getOrder(Long id) {
        return orderDao.get(id);
    }

    @Autowired
    public void setOrderDao(TActOrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Autowired
    public void setMemberFavoriteDao(MemberFavoriteDao memberFavoriteDao) {
        this.memberFavoriteDao = memberFavoriteDao;
    }

    @Autowired
    public void setItemCommentDao(ItemCommentDao itemCommentDao) {
        this.itemCommentDao = itemCommentDao;
    }

    @Autowired
    public void setBusinessLogger(BusinessLogger businessLogger) {
        this.businessLogger = businessLogger;
    }
}
