package com.sunjianlin.sunspringboot.common.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 页面响应结果集
 * Created by sunjianlin
 * 2018年03月09日 16:11:06
 */
public class Result extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public Result() {
        put("code", 0);
    }

    public static Result error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static Result error(String msg) {
        return error(500, msg);
    }

    public static Result error(int code, String msg) {
        Result Result = new Result();
        Result.put("code", code);
        Result.put("msg", msg);
        return Result;
    }

    public static Result ok(String msg) {
        Result Result = new Result();
        Result.put("msg", msg);
        return Result;
    }

    public static Result ok(Map<String, Object> map) {
        Result Result = new Result();
        Result.putAll(map);
        return Result;
    }

    public static Result ok() {
        return new Result();
    }

    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
