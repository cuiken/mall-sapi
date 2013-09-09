package com.cplatform.sapi.repository.order;

import org.springframework.stereotype.Component;

import com.cplatform.sapi.entity.order.Seckill;
import com.cplatform.sapi.orm.hibernate.HibernateDao;

/**
 * 获取秒杀订单的Dao. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2013-8-28 下午9:50:44
 * <p>
 * Company: 北京宽连十方数字技术有限公司
 * <p>
 * 
 * @author yangxm@c-platform.com
 * @version 1.0.0
 */
@Component
public class TSeckillDao extends HibernateDao<Seckill, Long> {

}
