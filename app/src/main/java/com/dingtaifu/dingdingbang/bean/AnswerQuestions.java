package com.dingtaifu.dingdingbang.bean;

public class AnswerQuestions {

    /**
     * code : 1
     * message : 继续进行问题验证
     * data : {"countime":"599","certify":[{"question":"您在2008年06月办理的信用卡额度是多少？","options":["5001-15000","15001-25000","25001-35000","35001-45000","以上都不是"]},
     * {"question":"您于2008年06月在哪家机构办理过信用卡？","options":["浙商银行","中国工商银行","广发银行","汉口银行","以上都不是"]},
     * {"question":"您的初始身份证领取地是哪里？","options":["甘肃省天水市","江苏省南京市","广西壮族自治区南宁市","湖南省邵阳市","以上都不是"]},
     * {"question":"您2008年06月的婚姻状况是什么？","options":["丧偶","已婚","离婚","未婚","以上都不是"]},
     * {"question":"您在2005年12月办理的贷款每月应还款额（即还款计划表上和银行约定的还款金额）是多少？","options":["4701-5700","5701-6700","6701-7700","7701-8700","以上都不是"]}],"certifyId":""}
     * retCode : 01
     * iid : 6a1d36b8-3100-46e3-99fa-ed5983b27b4c
     */

    private int code;
    private String message;
    private String data;
    private String retCode;
    private String iid;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
