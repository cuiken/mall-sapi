package com.cplatform.sapi.repository.profile;

import com.cplatform.sapi.entity.profile.TItemComment;
import com.cplatform.sapi.orm.hibernate.HibernateDao;
import org.springframework.stereotype.Component;

/**
 * User: cuikai
 * Date: 13-8-14
 * Time: 下午2:23
 */
@Component
public class ItemCommentDao extends HibernateDao<TItemComment, Long> {
}
