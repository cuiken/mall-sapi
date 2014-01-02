package com.cplatform.sapi.repository.p2;

import com.cplatform.dbhelp.DbHelper;
import com.cplatform.sapi.entity.p2.ItemPriceInfo;
import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import com.google.code.ssm.api.format.Serialization;
import com.google.code.ssm.api.format.SerializationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

/**
 * Title. <br>
 * Description.
 * <p/>
 * Copyright: Copyright (c) 13-9-10 下午5:03
 * <p/>
 * Company: 北京宽连十方数字技术有限公司
 * <p/>
 * Author: nicky
 * <p/>
 * Version: 1.0
 * <p/>
 */
@Component
public class ItemPriceInfoDao {

    @Autowired
    DbHelper dbHelper;

    @InvalidateSingleCache(namespace = "sip.itempriceinfo")
    public void invalidateItemPriceInfo(@ParameterValueKeyProvider String itemId) {
        // empty
    }

    @Serialization(value = SerializationType.JSON)
    @ReadThroughSingleCache(namespace = "sip.itempriceinfo", expiration = 3600)
    public List<ItemPriceInfo> findPrices(@ParameterValueKeyProvider String itemId)
            throws SQLException {
        String sql = " select p.sale_id, type.price_type_code , type.price_type , " +
                "to_char(nvl(p.price, 0), 'fm999999990.00') price "
                + "        from t_item_price p "
                + "               left join t_item_price_type type on type.price_type_code=p.price_type_code "
                + "     where p.sale_id=? order by p.price asc";
        return dbHelper.getBeanList(sql, ItemPriceInfo.class, itemId);
    }
}
