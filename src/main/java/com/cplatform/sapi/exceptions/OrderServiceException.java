package com.cplatform.sapi.exceptions;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Title. <br>
 * Description.
 * <p/>
 * Copyright: Copyright (c) 13-8-21 上午9:40
 * <p/>
 * Company: 北京宽连十方数字技术有限公司
 * <p/>
 * Author: nicky
 * <p/>
 * Version: 1.0
 * <p/>
 */
public class OrderServiceException extends ServiceException {

    public static final int ITEM_NOT_EXIST = 2001;

    public static final int DISCOUNT_WRONG = 2002;

    public static final int PRICE_NOT_MATCH = 2003;

    public static final int PAY_CHECK_ERROR = 2005;

    public static final int GOOD_NUMBER_ERROR = 3001;

    public static final int USER_INCORRECT_ERROR = 3002;

    public static final int ORDER_NUMBER_ERROR = 3003;

    public static final int ORDER_PAYTYPE_ERROR = 3004;
    public static final int ORDER_NOT_EXIST = 3005;

    /**
     * 需要绑定手机号码
     */
    public static final int NEED_BIND_MOBILE = 2006;

    public static final int PAY_AMOUNT_ERROR = 2007;

    public static final int INTERFACE_FATAL = 2008;

    private static Map<Integer, String> errorMessageMapping = Maps.newHashMap();

    static {
        errorMessageMapping.put(ITEM_NOT_EXIST, "商品不存在。");
        errorMessageMapping.put(DISCOUNT_WRONG, "折扣金额不能大于商品价格。");
        errorMessageMapping.put(PRICE_NOT_MATCH, "输入价格参数和商品价格不相等。");
        errorMessageMapping.put(NEED_BIND_MOBILE, "需要绑定手机号码。");
        errorMessageMapping.put(PAY_AMOUNT_ERROR, "支付数额设置不正确。");
        errorMessageMapping.put(GOOD_NUMBER_ERROR, "营销商品购买数量错误");
        errorMessageMapping.put(USER_INCORRECT_ERROR, "营销订单用户不正确");
        errorMessageMapping.put(ORDER_NUMBER_ERROR, "营销订单不允许重复");
        errorMessageMapping.put(ORDER_PAYTYPE_ERROR, "订单商品支付方式不一致");
        errorMessageMapping.put(ORDER_NOT_EXIST, "订单不存在");
    }

    public OrderServiceException(int code) {
        super(code, errorMessageMapping.get(code));
    }

    public OrderServiceException(int code, String message) {
        super(code, message);
    }
}
