package com.cplatform.sapi.util;

import java.util.HashMap;
import java.util.List;

/**
 * 返回结果对象封装
 * <p/>
 * Copyright: Copyright (c) 13-8-13 上午9:11
 * <p/>
 * Company: 北京宽连十方数字技术有限公司
 * <p/>
 * Author: chengyao
 * <p/>
 * Version: 1.0
 * <p/>
 */
public class JsonRespWrapper extends HashMap<String, Object> {


    /**
     * 只返回成功标志
     * @return
     */
    public static JsonRespWrapper success() {
        return json(0);
    }

    /**
     * 成功，且传入键值对
     * @param key
     * @param value
     * @return
     */
    public static JsonRespWrapper success(List<String> key, List<Object> value) {
        return json(key, value, 0);
    }

    /**
     * 错误，错误码及错误提示
     * @param code 错误码
     * @param msg 提示
     * @return
     */
    public static JsonRespWrapper error(int code, String msg) {
        return json("msg", msg, code);
    }

    /**
     * 加入键值对
     * @param key
     * @param value
     * @return
     */
    public JsonRespWrapper json(String key, Object value) {
        this.put(key, value);
        return this;
    }

    /**
     * 加入多个键值对
     * @param keys
     * @param values
     * @return
     */
    public JsonRespWrapper json(List<String> keys, List<Object> values) {
        if (keys == null) return this;
        for (int i = 0; i < keys.size(); i++) {
            this.put(keys.get(i), values.get(i));
        }
        return this;
    }

    /**
     * new json object对象
     * @return
     */
    public static JsonRespWrapper json() {
        JsonRespWrapper map = new JsonRespWrapper();
        return map;
    }

    /**
     * 加入返回码
     * @param code
     * @return
     */
    public static JsonRespWrapper json(int code) {
        return json().json("ret", code);
    }

    /**
     * 加入返回码及键值对
     * @param key
     * @param value
     * @param code
     * @return
     */
    public static JsonRespWrapper json(String key, Object value, int code) {
        return json(code).json(key, value);
    }

    /**
     * 加入返回码及多个键值对
     * @param keys
     * @param values
     * @param code
     * @return
     */
    public static JsonRespWrapper json(List<String> keys, List<Object> values, int code) {
        JsonRespWrapper obj = json(code);
        return obj.json(keys, values);
    }

}
