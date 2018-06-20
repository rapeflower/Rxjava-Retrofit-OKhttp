package com.android.lily.model;

import com.google.gson.annotations.SerializedName;

/**
 * @Author rape flower
 * @Date 2018-06-20 12:31
 * @Describe
 */
public class BaseEntity<T> {

    @SerializedName("code")
    private int code;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private T data;

    public boolean isSuccess() {
        return true;
    }

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
