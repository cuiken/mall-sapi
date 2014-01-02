package com.cplatform.sapi.repository.p2;

import com.cplatform.dbhelp.DbHelper;
import com.cplatform.sapi.entity.p2.ItemStoreInfo;
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
public class ItemStoreDao {

    @Autowired
    DbHelper dbHelper;

    @Serialization(value = SerializationType.JSON)
    @ReadThroughSingleCache(namespace = "sip.itemstoreinfo", expiration = 3600)
    public ItemStoreInfo findOne(@ParameterValueKeyProvider String storeId) throws SQLException {
        return dbHelper.getBean("select name, short_name from t_store where id = ?", ItemStoreInfo.class, storeId);
    }

    @InvalidateSingleCache(namespace = "sip.itemstoreinfo")
    public void invalidateItemStoreInfo(@ParameterValueKeyProvider String storeId) {
        // empty
    }
}
