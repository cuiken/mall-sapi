package com.cplatform.sapi.repository;

import com.cplatform.sapi.entity.product.ShopInvoice;
import com.cplatform.sapi.orm.hibernate.HibernateDao;
import org.springframework.stereotype.Component;

/**
 * User: cuikai
 * Date: 13-9-9
 * Time: 上午11:30
 */
@Component
public class ShopInvoiceDao extends HibernateDao<ShopInvoice, Long> {
}
