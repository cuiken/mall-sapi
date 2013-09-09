package com.cplatform.sapi.service;

import com.cplatform.sapi.entity.Member;
import com.cplatform.sapi.entity.SysSegment;
import com.cplatform.sapi.entity.TBossRequest;
import com.cplatform.sapi.repository.SysSegmentDao;
import com.cplatform.sapi.repository.TBossRequestDao;
import com.cplatform.sapi.repository.TMemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: cuikai
 * Date: 13-8-29
 * Time: 下午12:24
 */
@Component
@Transactional
public class SecurityService {

    private TMemberDao memberDao;
    private TBossRequestDao bossRequestDao;
    private SysSegmentDao sysSegmentDao;

    public Member getMember(Long id) {

        return memberDao.findUnique("from Member m where m.id=?", id);
    }

    public Member getMemberByTerminalId(String terminalId) {

        Long id = memberDao.findUnique("select max(m.id) from Member m where m.terminalId=?", terminalId);
        return memberDao.findUniqueBy("id", id);
    }

    public SysSegment getSysSegmentBySegCode(String segCode) {

        return sysSegmentDao.findUniqueBy("segmentCode", segCode);
    }

    public TBossRequest getTBossRequestByTerminalId(String terminalId) {

        return bossRequestDao.findUniqueBy("terminalId", terminalId);
    }

    public void saveBossRequest(TBossRequest entity) {
        bossRequestDao.save(entity);
    }

    @Autowired
    public void setMemberDao(TMemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Autowired
    public void setBossRequestDao(TBossRequestDao bossRequestDao) {
        this.bossRequestDao = bossRequestDao;
    }

    @Autowired
    public void setSysSegmentDao(SysSegmentDao sysSegmentDao) {
        this.sysSegmentDao = sysSegmentDao;
    }
}
