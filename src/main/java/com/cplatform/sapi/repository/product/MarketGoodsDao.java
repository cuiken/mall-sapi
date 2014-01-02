package com.cplatform.sapi.repository.product;

import com.cplatform.sapi.entity.product.MarketGoods;
import com.cplatform.sapi.orm.hibernate.HibernateDao;
import com.cplatform.sapi.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * User: cuikai
 * Date: 13-12-11
 * Time: 下午4:41
 */
@Component
public class MarketGoodsDao extends HibernateDao<MarketGoods, Long> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String Q_ITEM_IN_PROMOTION = "select g from MarketGoods g left join g.marketPromotion p where  p.isdel<> '1' and p.status='2' and g.isdel<> '1' and p.promotionStartTime<=? and p.promotionEndTime>=? and g.goodsId=?";

    public MarketGoods getByGoodsId(String itemId) {
        String now = TimeUtil.formartString(new Date());
        MarketGoods marketGoods = findUnique(Q_ITEM_IN_PROMOTION, now, now, itemId);
        if (marketGoods == null) {
            logger.error("查询商品[" + itemId + "]营销活动异常");
            return null;
        }
        return marketGoods;
    }
}
