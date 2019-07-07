package com.example.zgg.http;

public class ResponseBean<T> {
    private Float errorCode;
    private String errorMsg;
    private T data;

    @Override
    public String toString() {
        return "ResponseBean{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }

    public Float getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Float errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
