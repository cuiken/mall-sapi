<%@ page language="java" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!Doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>接口首页</title>
    <style>
        body {
            font: 14px 'Helvetica Neue',Arial,sans-serif;
            background-color: #faf8f5;
            max-width: 960px;
            min-width: 320px;
            margin: 0 auto;
        }
        ul{
            list-style: none;
        }
        ul li{
            padding: 3px;
        }
        section{
            width: 200px;
            text-align: center;
            float: left;
        }
    </style>
</head>
<body>
<section id="profile">
    <h1>个人中心模块</h1>
    <ul>
        <li><a href="${ctx}/api/v1/profile/myOrders">我的订单</a></li>
        <li><a href="${ctx}/api/v1/profile/myCollects">我的收藏</a></li>
        <li><a href="${ctx}/api/v1/profile/myComments">我的评论</a></li>
        <li><a href="${ctx}/api/v1/profile/myQuestions">我的咨询</a></li>
        <li><a href="${ctx}/api/v1/address/user">我的收货地址</a></li>
    </ul>
</section>
<section>
    <h1>地市模块</h1>
    <ul>
        <li><a href="${ctx}/api/v1/address/getAddressInfo">省市联动</a></li>
    </ul>
</section>
<section>
    <h1>商品模块</h1>
    <ul>
        <li><a href="${ctx}/api/v1/product/spike">商品来源接口</a></li>
        <li><a href="${ctx}/api/v1/product/goodStatus">保证金接口</a></li>
        <li><a href="#">收藏列表</a></li>
        <li><a href="${ctx}/api/v1/product/comments">商品评价列表</a></li>
        <li><a href="${ctx}/api/v1/product/questions">商品咨询列表</a></li>
        <li><a href="${ctx}/api/v1/product/graphicDetail?">商品html描述</a></li>
        <li><a href="${ctx}/api/v1/product/detail/">商品详细页(5744)</a></li>
        <li><a href="${ctx}/api/v1/search/goodsInfo?SORT=0&AREA_CODE=0512&CATEGORY_ID=100888&PAGE_NO=1&PAGE_SIZE=30">商品列表(搜索引擎)</a></li>
    </ul>
</section>
<section>
    <h1>订单中心</h1>
    <ul>
        <li><a href="#">创建订单</a>(post:/api/v1/order/create)</li>
        <li><a href="${ctx}/api/v1/order/payedInfo?orderId=">订单支付信息</a></li>
    </ul>
</section>





</body>
</html>
