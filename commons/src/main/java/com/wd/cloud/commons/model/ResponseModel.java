package com.wd.cloud.commons.model;

import cn.hutool.http.HttpStatus;

/**
 * @author He Zhigang
 * @date 2018/5/3
 * @remark api返回的response对象
 */
public class ResponseModel<T> {
    private Integer code;
    private String msg;
    private T body;

    public ResponseModel() {
    }

    public ResponseModel(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.body = null;
    }

    public ResponseModel(int code, String msg, T body) {
        this.code = code;
        this.msg = msg;
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public ResponseModel code(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseModel msg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getBody() {
        return body;
    }

    public ResponseModel body(T body) {
        this.body = body;
        return this;
    }

    /**
     * 请求成功
     * @return
     */
    public static ResponseModel ok() {
        return ResponseModel.ok(null);
    }

    /**
     * 请求成功
     * @param data
     * @return
     */
    public static ResponseModel<Object> ok(Object data) {
        return ResponseModel.ok(HttpStatus.HTTP_OK,data);
    }

    public static ResponseModel<Object> ok(String msg) {
        return ResponseModel.ok(HttpStatus.HTTP_OK,msg,null);
    }

    public static ResponseModel<Object> ok(Integer code,String msg){
        return ResponseModel.ok(code,msg,null);
    }
    /**
     * 请求成功
     * @param code
     * @param data
     * @return
     */
    public static ResponseModel<Object> ok(int code, Object data) {
        return ResponseModel.ok(code, HttpMsg.OK, data);
    }

    public static ResponseModel<Object> ok(int code,String msg, Object data) {
        return new ResponseModel<Object>(code, msg, data);
    }

    /**
     * 未知错误
     * @return
     */
    public static ResponseModel error() {
        return ResponseModel.error(HttpMsg.ERROR);
    }

    /**
     * 未知错误
     * @param msg
     * @return
     */
    public static ResponseModel error(String msg) {
        return ResponseModel.error(0,msg);
    }

    /**
     * 未知错误
     * @param code
     * @param msg
     * @return
     */
    public static ResponseModel error(int code, String msg) {
        return new ResponseModel<Object>(code, msg);
    }

    /**
     * 数据未找到
     * @return
     */
    public static ResponseModel notFound(){
        return ResponseModel.notFound(HttpMsg.NOT_FOUND);
    }

    /**
     * 数据未找到
     * @param msg
     * @return
     */
    public static ResponseModel notFound(String msg){
        return new ResponseModel(HttpStatus.HTTP_NOT_FOUND ,msg);
    }

    /**
     * 重复提交导致数据冲突
     * @return
     */
    public static ResponseModel clientErr(){
        return ResponseModel.clientErr(HttpMsg.CLIENT_ERR);
    }

    /**
     * 重复提交导致数据冲突
     * @return
     */
    public static ResponseModel clientErr(String msg){
        return new ResponseModel(HttpStatus.HTTP_CONFLICT ,msg);
    }

    /**
     * 服务异常，一般由服务端有异常未捕获造成的
     * @return
     */
    public static ResponseModel serverErr(){
        return ResponseModel.serverErr(HttpMsg.SERVER_ERR);
    }

    /**
     * 服务异常，一般由服务端有异常未捕获造成的
     * @param msg
     * @return
     */
    public static ResponseModel serverErr(String msg){
        return new ResponseModel(HttpStatus.HTTP_INTERNAL_ERROR ,msg);
    }

    /**
     * 参数错误
     * @return
     */
    public static ResponseModel paramErr(){
        return ResponseModel.paramErr(HttpMsg.PARAMS_ERR);
    }

    /**
     * 参数错误
     * @return
     */
    public static ResponseModel paramErr(String msg){
        return new ResponseModel(HttpStatus.HTTP_BAD_REQUEST ,msg);
    }

}
