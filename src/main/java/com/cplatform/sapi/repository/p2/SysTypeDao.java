package com.cplatform.sapi.repository.p2;

import com.cplatform.dbhelp.DbHelper;
import com.cplatform.sapi.entity.p2.SysType;
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
 * Copyright: Copyright (c) 13-9-10 下午2:55
 * <p/>
 * Company: 北京宽连十方数字技术有限公司
 * <p/>
 * Author: nicky
 * <p/>
 * Version: 1.0
 * <p/>
 */
@Component
public class SysTypeDao {

    @Autowired
    DbHelper dbHelper;

    @Serialization(value = SerializationType.JSON)
    @ReadThroughSingleCache(namespace = "sip.systype", expiration = 3600)
    public SysType findOne(@ParameterValueKeyProvider String id) throws SQLException {
        return dbHelper.getBean("select * from t_sys_type where id = ?", SysType.class, id);
    }

    @InvalidateSingleCache(namespace = "sip.systype")
    public void invalidateSysType(@ParameterValueKeyProvider String id) {
        // empty
    }
}
