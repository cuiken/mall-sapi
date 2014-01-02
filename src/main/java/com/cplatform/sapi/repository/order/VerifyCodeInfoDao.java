package com.cplatform.sapi.repository.order;

import com.cplatform.sapi.entity.order.VerifyCodeInfo;
import com.cplatform.sapi.orm.hibernate.HibernateDao;
import org.springframework.stereotype.Component;

/**
 * 团购验证码. <br>
 * dao.
 * <p/>
 * Copyright: Copyright (c) ${date} ${time}
 * <p/>
 * Company: 苏州宽连十方数字技术有限公司
 * <p/>
 *
 * @author cuikai-ca@c-platform.com
 * @version 1.0.0
 */
@Component
public class VerifyCodeInfoDao extends HibernateDao<VerifyCodeInfo, Long> {
}
