package com.dingtaifu.dingdingbang.bean;

public class CreditLoginName {

// "code": 0,
//         "message": "您无法使用该功能找回登录名，可能是因为您的安全等级为低、未注册或已销户，请重新注册。",
//         "data": "{\"iid\":\"\",\"retCode\":\"ff\",\"retMsg\":\"您无法使用该功能找回登录名，可能是因为您的安全等级为低、未注册或已销户，请重新注册。\"}",
//         "retCode": null,
//         "iid": null


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
