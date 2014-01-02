<%@ page language="java" pageEncoding="utf-8" isErrorPage="true"%>

<%response.setStatus(200);%>

<%
    response.getWriter().print("{\"FLAG\":\"400\",\"MSG\":\"请求参数格式错误\"}");
%>
