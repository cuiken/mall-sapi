package com.cplatform.sapi.repository;

import com.cplatform.sapi.entity.TShop;
import com.cplatform.sapi.orm.hibernate.HibernateDao;
import org.springframework.stereotype.Component;

/**
 * User: cuikai
 * Date: 13-11-22
 * Time: 下午3:15
 */
@Component
public class ShopDao extends HibernateDao<TShop, Long> {
}
