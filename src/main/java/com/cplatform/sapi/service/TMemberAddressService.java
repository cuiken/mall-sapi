package com.cplatform.sapi.service;

import com.cplatform.sapi.entity.SysRegion;
import com.cplatform.sapi.entity.TMemberAddress;
import com.cplatform.sapi.repository.SysRegionDao;
import com.cplatform.sapi.repository.TMemberAddressDao;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * User: cuikai
 * Date: 13-8-15
 * Time: 下午2:31
 */
@Component
@Transactional
public class TMemberAddressService {

    private TMemberAddressDao memberAddressDao;
    private SysRegionDao regionDao;

    private BusinessLogger businessLogger;

    public List<TMemberAddress> getAddressByUser(Long userId) {
        return memberAddressDao.findBy("mid", userId);
    }

    public TMemberAddress getMemberAddress(Long id) {
        return memberAddressDao.get(id);
    }

    public void deleteAddress(Long id) {
        memberAddressDao.delete(id);

        Map logData = Maps.newHashMap();
        logData.put("id", id);
        businessLogger.log("TMemeberAddress", "delete", logData);
    }

    public void saveAddress(TMemberAddress entity) {
        memberAddressDao.save(entity);

        Map logData = Maps.newHashMap();
        logData.put("TMemberAddress", entity);
        businessLogger.log("TMemberAddress", "save", logData);
    }

    public List<SysRegion> getRegionByLevel(Long level, Long id) {
        if (level == 0L) {
            return regionDao.findBy("regionLevel", level);
        } else if (level == 1) {
            SysRegion region = regionDao.get(id);
            if (region == null) return null;
            return regionDao.createQuery("from SysRegion r where r.parentRegion=?", region.getRegionCode()).list();
        } else {
            SysRegion entity = regionDao.get(id);
            regionDao.initProxyObject(entity);
            return Lists.newArrayList(entity);
        }
    }

    public SysRegion getRegionByRegionCode(String regionCode) {
        return regionDao.findUnique("from SysRegion r where r.regionCode = ? ", regionCode);
    }

    @Autowired
    public void setMemberAddressDao(TMemberAddressDao memberAddressDao) {
        this.memberAddressDao = memberAddressDao;
    }

    @Autowired
    public void setRegionDao(SysRegionDao regionDao) {
        this.regionDao = regionDao;
    }

    @Autowired
    public void setBusinessLogger(BusinessLogger businessLogger) {
        this.businessLogger = businessLogger;
    }
}
