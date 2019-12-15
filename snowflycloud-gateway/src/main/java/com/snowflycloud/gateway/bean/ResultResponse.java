package com.snowflycloud.gateway.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultResponse<T> implements Serializable {

    private static final int SUCCESS_CODE = 0;

    private static final int ERROR_CODE = -1;

    private static final String SUCCESS_MSG = "success";

    /**
     * 成功标识
     */
    private boolean success;

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回信息
     */
    private String  message;

    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();

    /**
     * 返回值
     */
    private T data;

    public ResultResponse() {

    }

    public ResultResponse(boolean success, int code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /************************************************ 成功 ********************************************************/
    public static ResultResponse ok() {
        return new ResultResponse(true, SUCCESS_CODE, SUCCESS_MSG, null);
    }

    public static ResultResponse ok(Object data) {
        return new ResultResponse(true, SUCCESS_CODE, SUCCESS_MSG, data);
    }

    public static ResultResponse ok(Object data, String message) {
        return new ResultResponse(true, SUCCESS_CODE, message, data);
    }

    /************************************************ 成功 ********************************************************/
    public static ResultResponse error() {
        return new ResultResponse(false, ERROR_CODE, null, null);
    }

    public static ResultResponse error(String message) {
        return new ResultResponse(false, ERROR_CODE, message, null);
    }

    public static ResultResponse error(int code, String message) {
        return new ResultResponse(false, code, message, null);
    }

    public static ResultResponse error(int code, String message, Object data) {
        return new ResultResponse(false, code, message, data);
    }

}
