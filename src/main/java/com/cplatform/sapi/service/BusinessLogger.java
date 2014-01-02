package com.cplatform.sapi.service;

import com.cplatform.sapi.mapper.JsonMapper;
import com.cplatform.sapi.util.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 打印业务日志，格式为:
 * <p/>
 * 日期,实体类型,操作类型 ,操作用户,json格式的扩展字段
 */
@Component
public class BusinessLogger {
    public static final String BUSINESS_LOGGER_NAME = "business";
    public static final String BUSINESS_ERROR_LOGGER_NAME = "businessError";

    private Logger businessLogger = LoggerFactory.getLogger(BUSINESS_LOGGER_NAME);
    private Logger businessErrorLogger = LoggerFactory.getLogger(BUSINESS_ERROR_LOGGER_NAME);
    private JsonMapper jsonMapper = new JsonMapper();

    public void log(String entity, String action, Map data) {
        String json = (data != null ? jsonMapper.toJson(data) : "{}");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        businessLogger.info("{},{},{},{}", " IP:==>" + ServletUtils.findClientIPAddr(request), entity, action, json);
    }

    public void error(String entity, String action, Map data) {
        String json = (data != null ? jsonMapper.toJson(data) : "{}");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        businessErrorLogger.info("{},{},{},{}", " IP:==>" + ServletUtils.findClientIPAddr(request), entity, action, json);
    }
}
