package com.cplatform.sapi.rest;

import com.cplatform.sapi.DTO.CallBalance;
import com.cplatform.sapi.entity.Member;
import com.cplatform.sapi.service.OpenRedDiamondStatus;
import com.cplatform.sapi.service.AuthService;
import com.cplatform.sapi.util.JsonRespWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    private AuthService authService;

    @RequestMapping(value = "checkRedDiamondByUser", method = RequestMethod.GET)
    @ResponseBody
    public String checkRedDiamondByUserId(HttpServletRequest request) {
        String userId = request.getParameter("U_ID");
        Member member = authService.getMember(Long.valueOf(userId));

        return checkStatus(member);
    }

    @RequestMapping(value = "checkRedDiamondByTerminal")
    @ResponseBody
    public String checkRedDiamondByTerminal(@RequestParam(value = "TERMINAL_ID") String terminalId) {
        Member member = authService.getMemberByTerminalId(terminalId);
        return checkStatus(member);
    }

    @RequestMapping(value = "openRedDiamondByTerminal")
    @ResponseBody
    public Object openRedDiamondByTerminal(@RequestParam(value = "TERMINAL_ID") String terminalId) {

        Member member = authService.getMemberByTerminalId(terminalId);

        return openStatus(member);
    }

    @RequestMapping(value = "openRedDiamondByUser", method = RequestMethod.GET)
    @ResponseBody
    public Object openRedDiamondByUserId(HttpServletRequest request) {
        String userId = request.getParameter("U_ID");
        Member member = authService.getMember(Long.valueOf(userId));

        return openStatus(member);
    }

    @RequestMapping(value = "queryBalance")
    @ResponseBody
    public CallBalance queryBalance(@RequestParam("terminalId") String terminalId) throws Exception{
        return authService.getBalance(terminalId);
    }

    private JsonRespWrapper openStatus(Member user) {
        OpenRedDiamondStatus status = OpenRedDiamondStatus.OPEN_FAILURE;
        try {
            status = authService.openRedDiamond(user);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        JsonRespWrapper result = new JsonRespWrapper();
        result.json("FLAG", "0");
        return result.json("RESULT", status.getCode()).json("DESC", status.getMsg());
    }

    private String checkStatus(Member member) {
        if (member != null && member.getRedMember() == 1L) {
            return "{\"FLAG\":\"0\",\"IS_VIP\":\"true\"}";
        } else {
            return "{\"FLAG\":\"0\",\"IS_VIP\":\"false\"}";
        }
    }

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }
}
