package com.cplatform.sapi.exceptions;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 通用业务逻辑错误
 * <p/>
 * Copyright: Copyright (c) 13-8-9 下午2:52
 * <p/>
 * Company: 北京宽连十方数字技术有限公司
 * <p/>
 * Author: chengyao
 * <p/>
 * Version: 1.0
 * <p/>
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 2738023698126903301L;

    public static final int UNCATCHED_EXCEPTION = -99;

    public static final int ILLEGAL_ARGUMENT_EXCEPTION = 1;

    public static final int SQL_ExCEPTION = 2;

    public static final int IO_EXCEPTION = 3;

    public static final int XML_EXCEPTION = 4;

    public static final int TRANS_EXCEPTION = 5;

    public static final int WRONG_TRANS_CODE = 6;

    public static final int NOT_FOUND_404 = 404;

    public static final int INTERFACE_ERROR = 7;

    private int code;

    private static Map<Integer, String> errorMessageMapping = Maps.newHashMap();

    static {
        errorMessageMapping.put(TRANS_EXCEPTION, "事务已过期或不存在。");
        errorMessageMapping.put(WRONG_TRANS_CODE, "验证码错误。");
    }

    public ServiceException(final int code) {
        this(code, errorMessageMapping.get(code));
    }

    public ServiceException(final int code, final String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(final int code, final Throwable throwable) {
        super(throwable);
        this.code = code;
    }

    public ServiceException(final int code, final String message, final Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }

    public final int getCode() {
        return this.code;
    }

}
