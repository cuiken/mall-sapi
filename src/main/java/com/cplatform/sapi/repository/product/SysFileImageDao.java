package com.cplatform.sapi.repository.product;

import com.cplatform.sapi.entity.product.SysFileImg;
import com.cplatform.sapi.orm.hibernate.HibernateDao;
import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import com.google.code.ssm.api.format.Serialization;
import com.google.code.ssm.api.format.SerializationType;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * User: cuikai
 * Date: 13-10-9
 * Time: 上午10:07
 */
@Component
public class SysFileImageDao extends HibernateDao<SysFileImg, Long> {

    private static final String SINGLE_NS = "file.image";

    @Serialization(SerializationType.JSON)
    @ReadThroughSingleCache(namespace = SINGLE_NS, expiration = 3600)
    public List<SysFileImg> getByBsIdAndBsKey(@ParameterValueKeyProvider(order = 0) String type,
                                              @ParameterValueKeyProvider(order = 1) Long id) {
        return createQuery("from SysFileImg img where img.bsKey=? and img.bsId=?", type, id).list();
    }

    @InvalidateSingleCache(namespace = SINGLE_NS)
    public void invalidateFileImage(@ParameterValueKeyProvider(order = 0) String type,
                                    @ParameterValueKeyProvider(order = 1) Long id) {

    }
}
