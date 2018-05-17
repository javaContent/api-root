package com.wd.cloud.commons.model;

/**
 * @author He Zhigang
 * @date 2018/5/3
 * @remark api返回的response对象
 */
public class ResponseModel<T> {
    private int code;
    private String msg;
    private T data;

    private static final int SUCCESS_CODE = 200;
    private static final int FAIL_CODE = 500;
    private static final String SUCCESS_MSG = "success";
    private static final String FAIL_MSG = "fail";

    public ResponseModel() {
    }

    public ResponseModel(T data) {
        this.data = data;
    }

    public ResponseModel(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    public ResponseModel(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public ResponseModel setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseModel setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseModel setData(T data) {
        this.data = data;
        return this;
    }

    public static ResponseModel success(int code, String msg) {
        return new ResponseModel<Object>(code, msg);
    }

    public static ResponseModel success(String msg) {
        return new ResponseModel<Object>(SUCCESS_CODE, msg);
    }

    public static ResponseModel success() {
        return new ResponseModel<Object>(SUCCESS_CODE, SUCCESS_MSG);
    }

    public static ResponseModel success(Object data) {
        return new ResponseModel<Object>(SUCCESS_CODE, SUCCESS_MSG, data);
    }

    public static ResponseModel fail(int code, String msg) {
        return new ResponseModel<Object>(code, msg);
    }

    public static ResponseModel fail(String msg) {
        return new ResponseModel<Object>(FAIL_CODE, msg);
    }

    public static ResponseModel fail() {
        return new ResponseModel<Object>(FAIL_CODE, FAIL_MSG);
    }
    public static ResponseModel fail(Object data) {
        return new ResponseModel<Object>(FAIL_CODE, FAIL_MSG, data);
    }
}
