package com.dingtaifu.dingdingbang.bean;

public class CreditLogin {

//    "code":1,
//            "message":"继续进行注册短信验证",
//            "data":"{"countime":"119","phone":"11"}",
//            "retCode":"00",
//            "iid":"a9fd7828-9bf5-4580-a201-4c7143081f84"

    String code;
    String message;
    String data;
    String retCode;
    String iid;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }
}
