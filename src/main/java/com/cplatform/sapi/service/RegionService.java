package com.cplatform.sapi.service;

import com.cplatform.sapi.entity.SysRegion;
import com.cplatform.sapi.entity.TChannelType;
import com.cplatform.sapi.entity.TSysType;
import com.cplatform.sapi.repository.SysRegionDao;
import com.cplatform.sapi.repository.TChannelTypeDao;
import com.cplatform.sapi.repository.TSysTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: cuikai
 * Date: 13-8-29
 * Time: 下午4:07
 */
@Component
@Transactional
public class RegionService {

    private TSysTypeDao tSysTypeDao;
    private TChannelTypeDao channelTypeDao;
    private SysRegionDao regionDao;

    public List<TSysType> getByChannel(Long channelId) {
        return tSysTypeDao.findBy("type", channelId);
    }

    public List<TChannelType> getChannelTypeByRegionAndChannel(String regionCode, Long channel) {
        return channelTypeDao.createQuery("from TChannelType tt where tt.regionCode=? and tt.channel=?", regionCode, channel).list();
    }

    public SysRegion getRegionByAreaCode(String areaCode) {
        return regionDao.findUnique("select r from SysRegion r where r.areaCode=? and r.regionLevel=1L", areaCode);
    }

    public TSysType getSysType(Long id) {
        return tSysTypeDao.get(id);
    }

    public List<TSysType> getSysTypeByParentId(Long pid) {
        return tSysTypeDao.findBy("PId", pid);
    }

    @Autowired
    public void settSysTypeDao(TSysTypeDao tSysTypeDao) {
        this.tSysTypeDao = tSysTypeDao;
    }

    @Autowired
    public void setChannelTypeDao(TChannelTypeDao channelTypeDao) {
        this.channelTypeDao = channelTypeDao;
    }

    @Autowired
    public void setRegionDao(SysRegionDao regionDao) {
        this.regionDao = regionDao;
    }
}
