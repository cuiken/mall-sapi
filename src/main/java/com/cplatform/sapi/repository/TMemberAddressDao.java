package com.cplatform.sapi.repository;

import com.cplatform.sapi.entity.TMemberAddress;
import com.cplatform.sapi.orm.hibernate.HibernateDao;
import org.springframework.stereotype.Component;

/**
 * User: cuikai
 * Date: 13-8-15
 * Time: 下午2:30
 */
@Component
public class TMemberAddressDao extends HibernateDao<TMemberAddress, Long> {
}
