package com.dingtaifu.dingdingbang.bean;

public class RecoverPassword {

    /**
     * code : 1
     * message :  继续进行重置密码的短信验证
     * data : {"countime":"119","phone":"153*****345"}
     * retCode : 00
     * iid : dfa06ff6-a8fe-48ba-bcb4-a2565b8552bb
     */

    private String code;
    private String message;
    private String data;
    private String retCode;
    private String iid;

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

        return data.substring(data.length() - 13, data.length()-2);
    }

    public void setData(String data) {
        this.data = data;
    }

    //    public DataBean getData() {
//        return data;
//    }
//
//    public void setData(DataBean data) {
//        this.data = data;
//    }

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

    public static class DataBean {
        /**
         * countime : 119
         * phone : 153*****345
         */

        private String countime;
        private String phone;

        public String getCountime() {
            return countime;
        }

        public void setCountime(String countime) {
            this.countime = countime;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
