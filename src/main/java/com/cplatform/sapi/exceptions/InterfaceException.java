package com.cplatform.sapi.exceptions;

/**
 * 接口的通用错误
 * <p/>
 * Copyright: Copyright (c) 13-8-22 下午3:32
 * <p/>
 * Company: 北京宽连十方数字技术有限公司
 * <p/>
 * Author: chengyao
 * <p/>
 * Version: 1.0
 * <p/>
 */
public class InterfaceException extends RuntimeException {

    private int code;

    private int extCode;

    public int getCode() {
        return code;
    }

    public int getExtCode() {
        return extCode;
    }

    public InterfaceException(int extCode, String message) {
        super(message);
        this.code = ServiceException.INTERFACE_ERROR;
        this.extCode = extCode;
    }

}
