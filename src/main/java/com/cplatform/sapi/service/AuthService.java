package com.cplatform.sapi.service;

import com.cplatform.sapi.DTO.CallBalance;
import com.cplatform.sapi.entity.Member;
import com.cplatform.sapi.entity.SysSegment;
import com.cplatform.sapi.entity.TBossRequest;
import com.cplatform.sapi.repository.ThirdInterfaceDao;
import com.cplatform.sapi.repository.SysSegmentDao;
import com.cplatform.sapi.repository.TBossRequestDao;
import com.cplatform.sapi.repository.TMemberDao;
import com.cplatform.sapi.util.AppConfig;
import com.cplatform.sapi.util.TimeUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * User: cuikai
 * Date: 13-8-29
 * Time: 下午12:24
 */
@Component
@Transactional
public class AuthService {

    private static final String VIP_OPEN = "01"; //开通
    private static final Long UNTREATED = 0L;    //未处理

    @Autowired
    private TMemberDao memberDao;
    @Autowired
    private TBossRequestDao bossRequestDao;
    @Autowired
    private SysSegmentDao sysSegmentDao;
    @Autowired
    private ThirdInterfaceDao thirdInterfaceDao;
    @Autowired
    private BusinessLogger businessLogger;
    @Autowired
    private AppConfig appConfig;

    public Member getMember(Long id) {
        Validate.notNull(id, "用户ID不能为空");
        return memberDao.findUnique("from Member m where m.id=?", id);
    }

    public Member getMemberByTerminalId(String terminalId) {

        Long id = memberDao.findUnique("select max(m.id) from Member m where m.terminalId=?", terminalId);
        return memberDao.findUniqueBy("id", id);
    }


    private void openRedDiamond(String terminalId, String areaCode) {
        TBossRequest bossRequest = new TBossRequest();
        bossRequest.setAreaCode(areaCode);
        bossRequest.setTerminalId(terminalId);
        bossRequest.setType(VIP_OPEN);
        bossRequest.setProductId(appConfig.getProductId());
        bossRequest.setReqSrc(appConfig.getReqSrc());
        bossRequest.setStatus(UNTREATED);
        bossRequest.setInsertTime(TimeUtil.now());
        bossRequestDao.save(bossRequest);

        Map log = Maps.newHashMap();
        log.put("TBossRequest", bossRequest);
        businessLogger.log("TBossRequest", "save", log);
    }


    public OpenRedDiamondStatus openRedDiamond(Member member) {

        if (member == null) {
            return OpenRedDiamondStatus.USER_NOT_EXIST;
        } else if (member.getRedMember() == 1L) {
            return OpenRedDiamondStatus.ALREADY_RED_DIAMOND;
        } else {
            return checkSegAndBoss(member.getTerminalId());
        }
    }

    private OpenRedDiamondStatus checkSegAndBoss(String terminalId) {

        SysSegment segment = sysSegmentDao.findUniqueBy("segmentCode", StringUtils.substring(terminalId, 0, 7));
        if (segment == null) {
            return OpenRedDiamondStatus.OPEN_FAILURE;
        }

        openRedDiamond(terminalId, segment.getAreaCode());
        return OpenRedDiamondStatus.OPEN_SUCCESS;
    }

    public CallBalance getBalance(String mobile) throws Exception{
        return thirdInterfaceDao.getBalanceInfo(mobile);
    }
}
