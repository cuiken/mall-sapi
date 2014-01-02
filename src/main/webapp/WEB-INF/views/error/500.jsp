<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ page import="org.apache.commons.lang3.StringUtils,org.slf4j.Logger" %>
<%@ page import="org.slf4j.LoggerFactory" %>

<%
    response.setStatus(200);
    Throwable ex = null;
    if (exception != null)
        ex = exception;
    if (request.getAttribute("javax.servlet.error.exception") != null)
        ex = (Throwable) request.getAttribute("javax.servlet.error.exception");

    //记录日志
    Logger logger = LoggerFactory.getLogger("com.cplatform.uncatchedException");
    logger.error(ex.getMessage(), ex);

    String msg;
    String text = StringUtils.substringBetween(ex.getMessage(), "text\":\"", "\"}");

    if (StringUtils.isNotBlank(text)) {
        msg = text;
    } else {
        msg = ex.getMessage();
    }
    if (StringUtils.length(msg) > 20) {
        msg = "未知异常";
    }

    response.getWriter().print("{\"FLAG\":\"-1\",\"MSG\":\"" + msg + "\"}");
%>
