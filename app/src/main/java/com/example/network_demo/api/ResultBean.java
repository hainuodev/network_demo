package com.example.network_demo.api;

import java.util.List;

public class ResultBean {
    private List<DataDTO> data;
    private int errorCode;
    private String errorMsg;

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
