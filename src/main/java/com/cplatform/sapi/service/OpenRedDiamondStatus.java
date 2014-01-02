package com.cplatform.sapi.service;

/**
 * User: cuikai
 * Date: 13-9-11
 * Time: 下午3:59
 */
public enum OpenRedDiamondStatus {

    OPEN_FAILURE("-1", "开通红钻失败"),

    OPEN_SUCCESS("00", "开通红钻成功"),

    ALREADY_RED_DIAMOND("01", "已是红钻会员"),

    USER_NOT_EXIST("07", "用户不存在");


    OpenRedDiamondStatus(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
