<%@ page language="java" pageEncoding="utf-8" isErrorPage="true" %>

<%response.setStatus(200);%>

<%
    response.getWriter().print("{\"FLAG\":\"403\",\"MSG\":\"授权失败，无法访问该资源\"}");
%>