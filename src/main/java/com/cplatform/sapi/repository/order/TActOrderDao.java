package com.cplatform.sapi.repository.order;

import com.cplatform.sapi.entity.order.TActOrder;
import com.cplatform.sapi.orm.hibernate.HibernateDao;
import org.springframework.stereotype.Component;

/**
 * User: cuikai
 * Date: 13-8-14
 * Time: 上午9:46
 */
@Component
public class TActOrderDao extends HibernateDao<TActOrder, Long> {

    public TActOrder getBusinessOrder(Long userId, String businessId) {

        return findUnique("from TActOrder t where t.userId=? and t.extInfo=?", userId, businessId);
    }
}
