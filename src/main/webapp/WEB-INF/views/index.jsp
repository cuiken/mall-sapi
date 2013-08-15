<%@ page language="java" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<a href="api/v1/product/spike">商品来源接口</a>
<a href="${ctx}/api/v1/profile/myOrders">我的订单</a>
<a href="${ctx}/api/v1/profile/myCollects">我的收藏</a>
<a href="${ctx}/api/v1/profile/myComments">我的评论</a>
<a href="${ctx}/api/v1/profile/myQuestions">我的咨询</a>
<a href="${ctx}/api/v1/address/user">我的收货地址</a>
<a href="${ctx}/api/v1/address/getAddressInfo">省市联动</a>
