<%@ page language="java" pageEncoding="utf-8" isErrorPage="true"%>

<%response.setStatus(200);%>

<%
    response.getWriter().print("{\"FLAG\":\"-1\",\"MSG\":\"403\"}");
%>