package com.cplatform.sapi.service;

import com.cplatform.sapi.entity.SysRegion;
import com.cplatform.sapi.entity.TMemberAddress;
import com.cplatform.sapi.repository.SysRegionDao;
import com.cplatform.sapi.repository.TMemberAddressDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<TMemberAddress> getAddressByUser(Long userId) {
        return memberAddressDao.findBy("mid", userId);
    }

    public void deleteAddress(Long id) {
        memberAddressDao.delete(id);
    }

    public void saveAddress(TMemberAddress entity) {
        memberAddressDao.save(entity);
    }

    public List<SysRegion> getRegionByLevel(Long level, String name) {
        if (level == 0L) {
            return regionDao.findBy("regionLevel", level);
        } else {
            SysRegion region = regionDao.findUnique("from SysRegion r where r.regionName=? ", name);
            if(region==null) return null;
            return regionDao.createQuery("from SysRegion r where r.parentRegion=?", region.getRegionCode()).list();
        }
    }

    @Autowired
    public void setMemberAddressDao(TMemberAddressDao memberAddressDao) {
        this.memberAddressDao = memberAddressDao;
    }

    @Autowired
    public void setRegionDao(SysRegionDao regionDao) {
        this.regionDao = regionDao;
    }
}
