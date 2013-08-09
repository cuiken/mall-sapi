#<center>商城服务端内部接口规范</center>#

##1.商品模块 ``route:api/v1/product/``##
* B2C购物分类接口 ``category``
* B2C购物详情页接口 ``detail``
* B2C商品收藏接口 ``collects``
* B2C商品用户评价列表 ``comments``
* B2C商品用户咨询列表 ``questions``
* B2C详情图文介绍接口 ``graphicDetail``

##2.订单模块 ``route:api/v1/order/``
* B2C购物下单接口 ``create``
* 订单支付接口 ``pay``

##3.个人中心 ``route:api/v1/profile/``
* B2C购物我的订单接口 ``myOrders``
* 我的购物订单详情 ``orderDetail``
* 我的商品收藏 ``myCollects``
* 我的商品评价列表 ``myComments``
* 我的商品咨询列表 ``myQuestions``
* 评价商品接口 ``submitGoodComment``
* 咨询商品信息 ``submitGoodQes``

##4.基础信息 ``route:api/v1/address/`` ##
* 获取收货地址列表接口 ``list``
* 添加、更改收货地址接口 ``saveOrUpdate``
* 删除收货地址接口 ``delete``
* 获取省、市、区县列表接口 ``getAddressInfo``

##5.检索模块 ``route:api/v1/search``
* B2C购物列表页接口 ``goodsInfo``
* 统一搜索 ``union``

##6.验证模块 ``route:api/v1/security``
* 验证红钻 ``redDiamondCheck``
* 开通红钻 ``redDiamondOpen``

