<%@ page language="java" pageEncoding="utf-8" isErrorPage="true"%>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory,java.io.*" %>
<%response.setStatus(200);%>

<%
    Throwable ex = null;
    if (exception != null)
        ex = exception;
    if (request.getAttribute("javax.servlet.error.exception") != null)
        ex = (Throwable) request.getAttribute("javax.servlet.error.exception");

    //记录日志
    Logger logger = LoggerFactory.getLogger("com.cplatform.uncatchedException");
    logger.error(ex.getMessage(), ex);
%>
<%
   response.getWriter().print("{\"FLAG\":\"-1\",\"MSG\":\""+ex.getMessage()+"\"}");
%>
