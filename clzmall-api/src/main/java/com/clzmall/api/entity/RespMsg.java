package com.clzmall.api.entity;

import java.io.Serializable;

/**
 * 请求返回信息
 *
 * @author jjs 2016年10月14日 上午11:39:35
 */
public class RespMsg<T> implements Serializable{

    private int code; // 0： 成功  1：失败   -1 | 500：服务器失败
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
