package com.cplatform.sapi.repository.p2;

import com.cplatform.dbhelp.DbHelper;
import com.cplatform.sapi.entity.p2.ItemPayment;
import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import com.google.code.ssm.api.format.Serialization;
import com.google.code.ssm.api.format.SerializationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * Title. <br>
 * Description.
 * <p/>
 * Copyright: Copyright (c) 13-10-30 上午9:48
 * <p/>
 * Company: 北京宽连十方数字技术有限公司
 * <p/>
 * Author: nicky
 * <p/>
 * Version: 1.0
 * <p/>
 */
@Component
public class ItemPaymentDao {

    @Autowired
    DbHelper dbHelper;

    @Serialization(value = SerializationType.JSON)
    @ReadThroughSingleCache(namespace = "sip.itempayment", expiration = 3600)
    public ItemPayment findOne(@ParameterValueKeyProvider String id) throws SQLException {
        return dbHelper.getBean("select pay_type, cash_pay_ratio, other_pay_ratio, bill_pay,delivery_pay " +
                "from t_item_sale_payment where item_id = ?", ItemPayment.class, id);
    }

    @InvalidateSingleCache(namespace = "sip.itempayment")
    public void invalidateItemPayment(@ParameterValueKeyProvider String id) {
        // empty
    }
}
