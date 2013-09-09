package com.cplatform.sapi.rest;

import com.cplatform.sapi.entity.Member;
import com.cplatform.sapi.entity.SysSegment;
import com.cplatform.sapi.entity.TBossRequest;
import com.cplatform.sapi.service.SecurityService;
import com.cplatform.sapi.util.TimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * User: cuikai
 * Date: 13-8-29
 * Time: 上午11:35
 */
@Controller
@RequestMapping("/api/v1/auth")
public class SecurityController {

    private SecurityService securityService;

    @RequestMapping(value = "checkRedDiamondByUser", method = RequestMethod.GET)
    @ResponseBody
    public String checkRedDiamond(HttpServletRequest request) {
        String userId = request.getParameter("U_ID");
        Member member = securityService.getMember(Long.valueOf(userId));

        return getCheckStauts(member);
    }

    @RequestMapping(value = "checkRedDiamondByTerminal")
    @ResponseBody
    public String checkRedDiamondByTerminal(@RequestParam(value = "TERMINAL_ID") String terminalId) {
        Member member = securityService.getMemberByTerminalId(terminalId);
        return getCheckStauts(member);
    }

    @RequestMapping(value = "openRedDiamondByTerminal")
    @ResponseBody
    public String openRedDiamondByTerminal(@RequestParam(value = "TERMINAL_ID") String terminalId) {

        Member member = securityService.getMemberByTerminalId(terminalId);
        TBossRequest bossRequest = securityService.getTBossRequestByTerminalId(terminalId);
        return getOpenStatus(member, bossRequest);
    }

    @RequestMapping(value = "openRedDiamondByUser", method = RequestMethod.GET)
    @ResponseBody
    public String openRedDiamond(HttpServletRequest request) {
        String userId = request.getParameter("U_ID");
        Member member = securityService.getMember(Long.valueOf(userId));
        TBossRequest bossRequest = securityService.getTBossRequestByTerminalId(member.getTerminalId());
        return getOpenStatus(member, bossRequest);
    }

    private String getOpenStatus(Member member, TBossRequest existRequest) {

        if (member == null) {
            return "{\"FLAG\":\"0\",\"RESULT\":\"07\",\"DESC\":\"用户不存在\"}";
        }

        SysSegment segment = securityService.getSysSegmentBySegCode(StringUtils.substring(member.getTerminalId(), 0, 7));
        if (segment != null && !segment.getAreaCode().equals("320500")) {
            return "{\"FLAG\":\"0\",\"RESULT\":\"08\",\"DESC\":\"号码无法开通\"}";
        }

        if (member.getRedMember() == 1L) {
            return "{\"FLAG\":\"0\",\"RESULT\":\"01\",\"DESC\":\"已是红钻会员\"}";
        } else if (existRequest != null) {
            return "{\"FLAG\":\"0\",\"RESULT\":\"03\",\"DESC\":\"在商城已经预约开通\"}";
        } else {
            TBossRequest bossRequest = new TBossRequest();
            bossRequest.setAreaCode(member.getAreaCode());
            bossRequest.setTerminalId(member.getTerminalId());
            bossRequest.setType("01");
            bossRequest.setProductId("000");
            bossRequest.setReqSrc("sapi");
            bossRequest.setStatus(0L);
            bossRequest.setInsertTime(TimeUtil.now());
            securityService.saveBossRequest(bossRequest);
            return "{\"FLAG\":\"0\",\"RESULT\":\"00\",\"DESC\":\"开通红钻成功\"}";
        }
    }

    private String getCheckStauts(Member member) {
        if (member != null && member.getRedMember() == 1L) {
            return "{\"FLAG\":\"0\",\"IS_VIP\":\"true\"}";
        } else {
            return "{\"FLAG\":\"0\",\"IS_VIP\":\"false\"}";
        }
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
