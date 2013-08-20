package com.cplatform.sapi.service.product;

import com.cplatform.sapi.entity.product.TChannelType;
import com.cplatform.sapi.entity.product.TSysType;
import com.cplatform.sapi.repository.product.TChannelTypeDao;
import com.cplatform.sapi.repository.product.TSysTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: cuikai
 * Date: 13-8-13
 * Time: 下午12:16
 */
@Component
@Transactional
public class TSysTypeService {

    private TSysTypeDao tSysTypeDao;
    private TChannelTypeDao channelTypeDao;

    public List<TSysType> getByChannel(Long channelId) {
        return tSysTypeDao.findBy("type", channelId);
    }

    public List<TChannelType> getChannelTypeByRegionAndChannel(String regionCode, Long channel) {
        return channelTypeDao.createQuery("from TChannelType tt where tt.regionCode=? and tt.channel=?", regionCode, channel).list();
    }


    @Autowired
    public void settSysTypeDao(TSysTypeDao tSysTypeDao) {
        this.tSysTypeDao = tSysTypeDao;
    }

    @Autowired
    public void setChannelTypeDao(TChannelTypeDao channelTypeDao) {
        this.channelTypeDao = channelTypeDao;
    }
}
