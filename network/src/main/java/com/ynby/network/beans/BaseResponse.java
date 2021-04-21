package com.ynby.network.beans;

class BaseResponse<T> {
    public int errorCode;
    public String errorMsg;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
