package com.cplatform.sapi.repository;

import com.cplatform.sapi.entity.SysRegion;
import com.cplatform.sapi.orm.hibernate.HibernateDao;
import org.springframework.stereotype.Component;

/**
 * User: cuikai
 * Date: 13-8-15
 * Time: 下午5:15
 */
@Component
public class SysRegionDao extends HibernateDao<SysRegion, Long> {

    public SysRegion getRegionByCode(String regionCode) {
        return findUnique("from SysRegion r where r.regionCode = ? ", regionCode);
    }

    /**
     * 获取地区中文全名
     *
     * @param regionCode 地区编码
     * @return 全名
     */
    public String getFullName(String regionCode) {
        if (regionCode == null && regionCode.length() != 6) return "";
        StringBuilder sb = new StringBuilder(40);
        sb.append(this.getRegionByCode(regionCode.substring(0, 2).concat("0000")).getRegionName());
        if (!regionCode.endsWith("0000")) {
            sb.append(this.getRegionByCode(regionCode.substring(0, 4).concat("00")).getRegionName());
            if (!regionCode.endsWith("00")) {
                sb.append(this.getRegionByCode(regionCode).getRegionName());
            }
        }
        return sb.toString();
    }
}
