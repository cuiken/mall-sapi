#<center>商城服务端内部接口规范</center>#

##1.商品模块 ``route:api/v1/product/``##
###I. B2C购物分类接口 ``category``
* 说明：获取商品分类数据，（建议客户端定期更新后保存到本地）
* 请求格式：``{
			"AREA_CODE":"地市信息"(1),
			"CATEGORY_ID":"当前层级分类ID，不传则返回最上层分类"(?)
		}``
* 响应：``{
			"DATA":(1)
			[
				{
					"CATEGORY_ID":"分类ID"
					"CATEGORY_NAME":"分类名称"
				},
				...
			]
			}``

###II. B2C购物详情页接口 ``detail``
* 说明：根据商品ID，获取商品详细信息
* 请求：``{"ID":"商品ID"}``
* 响应：``{"ID":"商品ID"(1),``
``"IMAGES":"商品图片列表，多个图片时，使用“,”分割"(1),``
``"NAME":"商品名称"(1),``
``"MARKET_PRICE":"市场价"(1),``
``"MALL_PRICE":"商城价"(1), ``
``"SCORE_PRICE":"积分价"(1),``
``"MEMBER_PRICE":"会员价"(1), ``
``"MEMBER_SCORE_PRICE":"会员积分价"(1),``
``"FARE":"运费"(1),``
``"SOLD_COUNT":"已出售份数"(1),``
``"ALLOW_CASH":"是否允许现金支付"(1),``
``"ALLOW_COIN":"是否允许商城币支付"(1),``
``"ALLOW_SCORE":"是否允许积分支付"(1),``
``"COIN_POINT":"商城币支付限额"(1),``
``"STORE":"剩余库存"(1)``

###III. B2C商品收藏接口 ``collects``
###V. B2C商品用户评价列表 ``comments``
###VI. B2C商品用户咨询列表 ``questions``
###VII. B2C详情图文介绍接口 ``graphicDetail``

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

