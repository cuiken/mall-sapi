package com.cplatform.sapi.service;

import com.cplatform.sapi.entity.profile.TItemComment;
import com.cplatform.sapi.orm.Page;
import com.cplatform.sapi.orm.PropertyFilter;
import com.cplatform.sapi.repository.profile.ItemCommentDao;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 评价咨询
 * User: chenke
 * Date: 13-10-30
 * Time: 下午4:42
 */
@Component
@Transactional
public class CommentService {

    @Autowired
    private ItemCommentDao itemCommentDao;

    @Autowired
    private BusinessLogger businessLogger;

    public Page<TItemComment> searchItemComment(final Page<TItemComment> page, final List<PropertyFilter> filters) {
        Map logData = Maps.newHashMap();
        logData.put("filters", filters);
        logData.put("pageNo", page.getPageNo());
        logData.put("pageSize", page.getPageSize());
        businessLogger.log("TItemComment", "search", logData);

        return itemCommentDao.findPage(page, filters);
    }
}
