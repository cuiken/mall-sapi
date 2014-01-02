package com.cplatform.sapi.data;

import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * User: cuikai
 * Date: 13-11-1
 * Time: 下午1:36
 */
@Component
public class CacheData {


    @ReadThroughSingleCache(namespace = "sip.iteminfo", expiration = 1)
    public String getOne(@ParameterValueKeyProvider String id, String flag) throws SQLException {

        if (StringUtils.isNotBlank(flag)) {
            return id;
        } else {
            return null;
        }
    }

    @InvalidateSingleCache(namespace = "sip.iteminfo")
    public void invalidCache(@ParameterValueKeyProvider String id) {

    }
}
