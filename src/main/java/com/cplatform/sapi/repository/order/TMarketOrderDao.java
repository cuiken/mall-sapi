package com.cplatform.sapi.repository.order;

import org.springframework.stereotype.Component;

import com.cplatform.sapi.entity.order.MarketOrder;
import com.cplatform.sapi.orm.hibernate.HibernateDao;

/**
 * 获取竞拍订单的dao. <br>
 * 类详细说明.
 * <p>
 * Copyright: Copyright (c) 2013-8-28 下午9:49:40
 * <p>
 * Company: 北京宽连十方数字技术有限公司
 * <p>
 * 
 * @author yangxm@c-platform.com
 * @version 1.0.0
 */
@Component
public class TMarketOrderDao extends HibernateDao<MarketOrder, Long> {

}
