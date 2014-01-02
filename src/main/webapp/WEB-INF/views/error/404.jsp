<%@ page language="java" pageEncoding="utf-8" isErrorPage="true" %>

<%response.setStatus(200);%>

<%
    response.getWriter().print("{\"FLAG\":\"404\",\"MSG\":\"访问资源不存在\"}");
%>
