package com.cplatform.sapi.service;

import com.cplatform.sapi.DTO.SysRegionDTO;
import com.cplatform.sapi.DTO.TMemberAddressDTO;
import com.cplatform.sapi.DTO.TMemberAddressesDTO;
import com.cplatform.sapi.DTO.UserAddressDTO;
import com.cplatform.sapi.entity.SysRegion;
import com.cplatform.sapi.entity.TMemberAddress;
import com.cplatform.sapi.mapper.BeanMapper;
import com.cplatform.sapi.repository.SysRegionDao;
import com.cplatform.sapi.repository.TMemberAddressDao;
import com.cplatform.sapi.util.TimeUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang3.StringUtils;
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
        Validate.notNull(userId, "用户ID不能为空");
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

    public TMemberAddressesDTO convertToDTO(List<TMemberAddress> addresses) {
        TMemberAddressesDTO tMemberAddressesDTO = new TMemberAddressesDTO();
        List<TMemberAddressDTO> destinationList = Lists.newArrayList();

        for (TMemberAddress tMemberAddress : addresses) {
            TMemberAddressDTO tMemberAddressDTO = BeanMapper.map(tMemberAddress, TMemberAddressDTO.class);
            String region = tMemberAddress.getRegion();
            if (StringUtils.isNotBlank(region)) {
                SysRegion province = regionDao.getRegionByCode(region.substring(0, 2) + "0000");
                SysRegion city = regionDao.getRegionByCode(region.substring(0, 4) + "00");
                SysRegion county = regionDao.getRegionByCode(region);
                tMemberAddressDTO.setProvince(BeanMapper.map(province, SysRegionDTO.Data.class));
                tMemberAddressDTO.setCity(BeanMapper.map(city, SysRegionDTO.Data.class));
                tMemberAddressDTO.setCounty(BeanMapper.map(county, SysRegionDTO.Data.class));
            }
            destinationList.add(tMemberAddressDTO);
        }
        tMemberAddressesDTO.settMemberAddressDTOs(destinationList);
        return tMemberAddressesDTO;
    }

    public void saveOrUpdateAddress(UserAddressDTO dto) {

        TMemberAddress memberAddress;
        if (dto.getId() == null || StringUtils.isBlank(String.valueOf(dto.getId()))) {
            memberAddress = new TMemberAddress();

        } else {
            memberAddress = getMemberAddress(dto.getId());
        }
        memberAddress.setDefaultShipping(dto.getDefault());
        memberAddress.setName(dto.getName());
        memberAddress.setZipcode(dto.getZipCode());
        memberAddress.setAddress(dto.getAddress());
        memberAddress.setMobile(dto.getTerminalId());
        memberAddress.setMid(dto.getUserId());
        memberAddress.setCreateTime(TimeUtil.now());
        memberAddress.setRegion(dto.getRegionCode());

        this.saveAddress(memberAddress);
    }

    public void setDefault(Long id, Long userId) {

        memberAddressDao.batchExecute("update TMemberAddress t set t.defaultShipping = '1' where t.id = ? and t.mid = ?", id, userId);
        memberAddressDao.batchExecute("update TMemberAddress t set t.defaultShipping = '0' where t.id <> ? and t.mid = ?", id, userId);
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
