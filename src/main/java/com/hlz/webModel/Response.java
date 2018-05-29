package com.hlz.webModel;

/**
 * @author Administrator
 * @create 2018/5/29
 */
public class Response<T> {
    private Integer code;
    private String message;
    private T result;

    public Integer getCode() {
        return code;
    }

    public Response setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Response setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getResult() {
        return result;
    }

    public Response setResult(T result) {
        this.result = result;
        return this;
    }
}
