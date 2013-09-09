package com.cplatform.sapi.repository;

import com.cplatform.sapi.entity.Member;
import com.cplatform.sapi.orm.hibernate.HibernateDao;
import org.springframework.stereotype.Component;

/**
 * User: cuikai
 * Date: 13-8-29
 * Time: 下午12:23
 */
@Component
public class TMemberDao extends HibernateDao<Member, Long> {
}
