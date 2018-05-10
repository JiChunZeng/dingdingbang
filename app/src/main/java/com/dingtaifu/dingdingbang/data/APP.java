package com.dingtaifu.dingdingbang.data;

public interface APP {

    String url = "http://120.79.218.0:8080";
//    String url = "http:/192.168.31.47:8080";

    String sendNote = url + "/app/sendNote";
    String login = url + "/app/login";
    String credit_regisiter = url + "/app/registerReport";
    String credit_regisiter_finish = url + "/app/noteRegister";
    String credit_findName = url + "/app/findName";
    String credit_login_first_step=url+"/app/applyReport";
    String credit_recover_psd = url + "/app/resetPwd";
    String credit_recover_psd_finish = url + "/app/noteReset";

    
    String credit_issue_gain = url + "/app/issueGain";
    String credit_report_gain = url + "/app/reportGain";
    String share_Punch = url + "/app/punchTheClock";
    String share_query = url + "/app/queryClock";

    String performace = url+"/app/performance";
    String history = url+"/app/history";
    String liabilities = url+"/app/liabilities";
    String survey = url+"/app/survey";
    String queryLogin = url+"/app/queryLogin";
    String queryweb = url+"/app/continueBank";
    String bangGain = url + "/app/bangGain";
//    String dictum = url+"/app/dictum";
    String questionnaire = url+"/app/questionnaire";


}
