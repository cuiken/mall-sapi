package com.cplatform.sapi.repository.profile;

import com.cplatform.sapi.entity.profile.MemberFavorite;
import com.cplatform.sapi.orm.hibernate.HibernateDao;
import org.springframework.stereotype.Component;

/**
 * User: cuikai
 * Date: 13-8-14
 * Time: 上午11:40
 */
@Component
public class MemberFavoriteDao extends HibernateDao<MemberFavorite,Long> {
}
