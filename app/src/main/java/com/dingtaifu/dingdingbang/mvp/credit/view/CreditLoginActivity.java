package com.dingtaifu.dingdingbang.mvp.credit.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dingtaifu.dingdingbang.R;
import com.dingtaifu.dingdingbang.base.BaseActivity;
import com.dingtaifu.dingdingbang.bean.CreditLogin;
import com.dingtaifu.dingdingbang.bean.RecoverPassword;
import com.dingtaifu.dingdingbang.data.APP;
import com.dingtaifu.dingdingbang.data.ViewShowHelper;
import com.dingtaifu.dingdingbang.data.XutilsHttp;
import com.dingtaifu.dingdingbang.databinding.ActivityCreditLoginBinding;
import com.dingtaifu.dingdingbang.mvp.login.view.LoginActivity;
import com.dingtaifu.dingdingbang.mvp.main.view.activity.MainActivity;
import com.dingtaifu.dingdingbang.mvp.main.view.adapter.MainPageAdapter;
import com.dingtaifu.dingdingbang.utils.CountDownUtil;
import com.dingtaifu.dingdingbang.utils.DateUtil;
import com.dingtaifu.dingdingbang.utils.Logs;
import com.dingtaifu.dingdingbang.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CreditLoginActivity extends BaseActivity implements View.OnClickListener {
    private ActivityCreditLoginBinding mBinding;
    private String LoginLoginInName, LoginName, LoginId, LoginPassword;
    private int recLen = 1000*60*10;
    Timer timer = new Timer();
    int check1 = 0;
    int check2 = 0;
    int check3 = 0;
    int check4 = 0;
    int check5 = 0;
    String certifyId;
    String iid;
    @Override
    public void initView() {
        super.initView();
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_credit_login);
        mBinding.commonTitle.ivBack.setOnClickListener(this);
        mBinding.commonTitle.tvTitle.setText("征信登录");
        RequestParams survey = new RequestParams(APP.queryLogin);
        XutilsHttp.getInstance().get(survey, new ViewShowHelper.ClickListenr<CreditLogin>(){
            @Override
            public void onXUtilsSuccess(CreditLogin creditLogin) {
                Logs.e(creditLogin.getRetCode()     );
                if (creditLogin.getCode().equals("0")){
                    ToastUtil.show(CreditLoginActivity.this, creditLogin.getMessage());
//                    startActivity(new Intent(mContext, CreditLoginActivity.class));
//                    CreditLoginActivity.this.finish();
                    return;
                }else {
                    if (creditLogin.getRetCode().equals("0")) {
                        ToastUtil.show(CreditLoginActivity.this, creditLogin.getMessage());

                    } else if (creditLogin.getRetCode().equals("1")) {
                        new SweetAlertDialog(CreditLoginActivity.this)
                                .setTitleText(creditLogin.getMessage())
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        CreditLoginActivity.this.finish();
                                    }
                                })
                                .show();
                    } else if (creditLogin.getRetCode().equals("2")) {
                        try {
                            JSONObject jsonObject = new JSONObject(creditLogin.getData());
                            mBinding.layoutCreditLoginLogin.etLoginLoginInName.setText(jsonObject.getString("loginname"));
                            mBinding.layoutCreditLoginLogin.etLoginId.setText(jsonObject.getString("idno"));
                            mBinding.layoutCreditLoginLogin.etLoginName.setText(jsonObject.getString("idname"));
                            mBinding.layoutCreditLoginLogin.etLoginPassword.setText(jsonObject.getString("loginpwd"));
                            getLoginFirstStep();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else if (creditLogin.getRetCode().equals("3")) {
                        ToastUtil.show(CreditLoginActivity.this, creditLogin.getMessage());
                        mBinding.layoutCreditLoginLogin.layoutCreditLoginLogin.setVisibility(View.GONE);
                        mBinding.layoutCreditLoginIdentify.layoutCreditLoginIdentify.setVisibility(View.GONE);
                        mBinding.layoutCreditLoginAppling.layoutCreditLoginAppling.setVisibility(View.GONE);
                        mBinding.layoutCreditLoginGetReport.layoutCreditLoginGetReport.setVisibility(View.VISIBLE);
                        mBinding.layoutRecoverWeb.layoutRecoverWebview.setVisibility(View.GONE);
                        mBinding.layoutRecoverBanggain.layoutRecoverBanggain.setVisibility(View.GONE);
                        mBinding.layoutCreditLoginGetReport.etLoginGetCode.setText("");
                    }
                }
            }

            @Override
            public void onXUtilsFaild(Throwable ex, boolean isOnCallback) {

            }
        });
    }

    @Override
    public void initPresenter() {
        mBinding.layoutCreditLoginLogin.layoutCreditLoginLogin.setVisibility(View.VISIBLE);
        mBinding.layoutCreditLoginIdentify.layoutCreditLoginIdentify.setVisibility(View.GONE);
        mBinding.layoutCreditLoginAppling.layoutCreditLoginAppling.setVisibility(View.GONE);
        mBinding.layoutCreditLoginGetReport.layoutCreditLoginGetReport.setVisibility(View.GONE);
        mBinding.layoutRecoverWeb.layoutRecoverWebview.setVisibility(View.GONE);
        mBinding.layoutRecoverBanggain.layoutRecoverBanggain.setVisibility(View.GONE);
        mBinding.layoutCreditLoginLogin.btnCreditLogin.setOnClickListener(this);
        mBinding.layoutCreditLoginLogin.tvCreditRetrieveLoginName.setOnClickListener(this);
        mBinding.layoutCreditLoginLogin.tvCreditForgetPassword.setOnClickListener(this);
        mBinding.layoutCreditLoginLogin.tvCreditToregister.setOnClickListener(this);
        mBinding.layoutCreditLoginLogin.tvCreditRetrieveLoginName.setOnClickListener(this);

        mBinding.layoutCreditLoginIdentify.btnCreditLoginCommit.setOnClickListener(this);
        mBinding.layoutCreditLoginAppling.tvApplingReceivedVerificationCode.setOnClickListener(this);

        mBinding.layoutCreditLoginGetReport.btnLoginGetreportCommit.setOnClickListener(this);
        mBinding.layoutCreditLoginGetReport.btnLoginReacquireAuthentication.setOnClickListener(this);
        mBinding.layoutCreditLoginGetReport.tvLoginUnreceivedReportedCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_credit_login:
                LoginLoginInName = mBinding.layoutCreditLoginLogin.etLoginLoginInName.getText().toString();
                LoginName = mBinding.layoutCreditLoginLogin.etLoginName.getText().toString();
                LoginId = mBinding.layoutCreditLoginLogin.etLoginId.getText().toString();
                LoginPassword = mBinding.layoutCreditLoginLogin.etLoginPassword.getText().toString();
                if (LoginLoginInName.equals("") | LoginName.equals("") | LoginId.equals("") | LoginPassword.equals("")) {
                    ToastUtil.show(this, "请填写完整的登录信息");
                    return;
                }
                getLoginFirstStep();

                break;
            case R.id.tv_credit_retrieve_login_name://找回登录名
                startActivity(new Intent(this, RecoverLoginNameActivity.class));
                break;
            case R.id.tv_credit_forget_password://忘记密码
                startActivity(new Intent(this, RecoverPasswordActivity.class));
                break;
            case R.id.tv_credit_toregister://注册
                startActivity(new Intent(this, CreditRegisteredActivity.class));
                break;
            case R.id.btn_credit_login_commit:
//                ToastUtil.show(this, check1+","+check2+","+check3+","+check4+","+check5+",");
                if (check1!=0&check2!=0&check3!=0&check4!=0&check5!=0) {
                    RequestParams params = new RequestParams(APP.credit_issue_gain);
                    params.addBodyParameter("certifyId", certifyId);
                    params.addBodyParameter("str", check1+","+check2+","+check3+","+check4+","+check5);
                    params.addBodyParameter("iid", iid);
                    XutilsHttp.getInstance().post(params, new ViewShowHelper.ClickListenr<CreditLogin>(){
                        @Override
                        public void onXUtilsSuccess(CreditLogin creditLogin) {
                            if (creditLogin.getCode().equals("1")) {
                                mBinding.layoutCreditLoginLogin.layoutCreditLoginLogin.setVisibility(View.GONE);
                                mBinding.layoutCreditLoginIdentify.layoutCreditLoginIdentify.setVisibility(View.GONE);
                                mBinding.layoutCreditLoginAppling.layoutCreditLoginAppling.setVisibility(View.VISIBLE);
                                mBinding.layoutCreditLoginGetReport.layoutCreditLoginGetReport.setVisibility(View.GONE);
                                mBinding.layoutRecoverWeb.layoutRecoverWebview.setVisibility(View.GONE);
                                mBinding.layoutRecoverBanggain.layoutRecoverBanggain.setVisibility(View.GONE);
                            } else {
                                ToastUtil.show(CreditLoginActivity.this, creditLogin.getMessage());
                            }
                        }

                        @Override
                        public void onXUtilsFaild(Throwable ex, boolean isOnCallback) {

                        }
                    });

                }else{
                    ToastUtil.show(this, "还有问题没回答");
                }
                break;
            case R.id.tv_appling_received_verification_code:

                    mBinding.layoutCreditLoginLogin.layoutCreditLoginLogin.setVisibility(View.GONE);
                    mBinding.layoutCreditLoginIdentify.layoutCreditLoginIdentify.setVisibility(View.GONE);
                    mBinding.layoutCreditLoginAppling.layoutCreditLoginAppling.setVisibility(View.GONE);
                    mBinding.layoutCreditLoginGetReport.layoutCreditLoginGetReport.setVisibility(View.VISIBLE);
                mBinding.layoutRecoverWeb.layoutRecoverWebview.setVisibility(View.GONE);
                mBinding.layoutRecoverBanggain.layoutRecoverBanggain.setVisibility(View.GONE);
                    mBinding.layoutCreditLoginGetReport.etLoginGetCode.setText("");

                break;
            case R.id.btn_login_getreport_commit://获取报告后提交
                if (mBinding.layoutCreditLoginGetReport.etLoginGetCode.getText().toString().equals("")) {
                    ToastUtil.show(this, "请输入正确的验证码");
                    return;
                }
                RequestParams params = new RequestParams(APP.credit_report_gain);
                params.addBodyParameter("tradecode", mBinding.layoutCreditLoginGetReport.etLoginGetCode.getText().toString());
                XutilsHttp.getInstance().post(params, new ViewShowHelper.ClickListenr<CreditLogin>(){

                    @Override
                    public void onXUtilsSuccess(CreditLogin creditLogin) {
                        if (creditLogin.getCode().equals("1")) {

                        } else {
                            ToastUtil.show(CreditLoginActivity.this, creditLogin.getMessage());
                            return;
                        }
                    }

                    @Override
                    public void onXUtilsFaild(Throwable ex, boolean isOnCallback) {

                    }
                });
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
            case R.id.btn_login_reacquire_authentication://重新获取身份验证
                mBinding.layoutCreditLoginLogin.layoutCreditLoginLogin.setVisibility(View.VISIBLE);
                mBinding.layoutCreditLoginIdentify.layoutCreditLoginIdentify.setVisibility(View.GONE);
                mBinding.layoutCreditLoginAppling.layoutCreditLoginAppling.setVisibility(View.GONE);
                mBinding.layoutCreditLoginGetReport.layoutCreditLoginGetReport.setVisibility(View.GONE);
                mBinding.layoutRecoverWeb.layoutRecoverWebview.setVisibility(View.GONE);
                mBinding.layoutRecoverBanggain.layoutRecoverBanggain.setVisibility(View.GONE);
                break;
            case R.id.tv_login_unreceived_reported_code:
                mBinding.layoutCreditLoginLogin.layoutCreditLoginLogin.setVisibility(View.GONE);
                mBinding.layoutCreditLoginIdentify.layoutCreditLoginIdentify.setVisibility(View.GONE);
                mBinding.layoutCreditLoginAppling.layoutCreditLoginAppling.setVisibility(View.VISIBLE);
                mBinding.layoutCreditLoginGetReport.layoutCreditLoginGetReport.setVisibility(View.GONE);
                mBinding.layoutRecoverWeb.layoutRecoverWebview.setVisibility(View.GONE);
                mBinding.layoutRecoverBanggain.layoutRecoverBanggain.setVisibility(View.GONE);
                break;
            case R.id.btn_credit_password_login_webview_commit:
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("提示")
                        .setContentText("确认已获取验证码?")
                        .setConfirmText("确认")
                        .setCancelText("继续")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                mBinding.layoutCreditLoginLogin.layoutCreditLoginLogin.setVisibility(View.GONE);
                                mBinding.layoutCreditLoginIdentify.layoutCreditLoginIdentify.setVisibility(View.GONE);
                                mBinding.layoutCreditLoginAppling.layoutCreditLoginAppling.setVisibility(View.GONE);
                                mBinding.layoutCreditLoginGetReport.layoutCreditLoginGetReport.setVisibility(View.GONE);
                                mBinding.layoutRecoverWeb.layoutRecoverWebview.setVisibility(View.GONE);
                                mBinding.layoutRecoverBanggain.layoutRecoverBanggain.setVisibility(View.VISIBLE);
                                mBinding.layoutRecoverBanggain.btnRecoverBanggain.setOnClickListener(CreditLoginActivity.this);
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();

                break;
            case R.id.btn_recover_banggain:
                RequestParams param = new RequestParams(APP.bangGain);
                param.addBodyParameter("verifyCode", mBinding.layoutRecoverBanggain.etRecoverBanggain.getText().toString());
                param.addBodyParameter("iid", iid);
                final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);
                pDialog.show();
                XutilsHttp.getInstance().post(param, new ViewShowHelper.ClickListenr<RecoverPassword>() {

                    @Override
                    public void onXUtilsSuccess(RecoverPassword recoverPassword) {
                        ToastUtil.show(CreditLoginActivity.this, recoverPassword.getMessage());
                        pDialog.dismissWithAnimation();
                        pDialog.cancel();
                        if (recoverPassword.getCode().equals("1")) {
                            mBinding.layoutCreditLoginLogin.layoutCreditLoginLogin.setVisibility(View.GONE);
                            mBinding.layoutCreditLoginIdentify.layoutCreditLoginIdentify.setVisibility(View.GONE);
                            mBinding.layoutCreditLoginAppling.layoutCreditLoginAppling.setVisibility(View.VISIBLE);
                            mBinding.layoutCreditLoginGetReport.layoutCreditLoginGetReport.setVisibility(View.GONE);
                            mBinding.layoutRecoverWeb.layoutRecoverWebview.setVisibility(View.GONE);
                            mBinding.layoutRecoverBanggain.layoutRecoverBanggain.setVisibility(View.GONE);

                        }
                    }

                    @Override
                    public void onXUtilsFaild(Throwable ex, boolean isOnCallback) {
                        pDialog.dismissWithAnimation();
                        pDialog.cancel();
                    }
                });
        break;
        }
    }

    private void getLoginFirstStep() {
        RequestParams params = new RequestParams(APP.credit_login_first_step);
        params.addBodyParameter("loginname", mBinding.layoutCreditLoginLogin.etLoginLoginInName.getText().toString());
        params.addBodyParameter("idCard", mBinding.layoutCreditLoginLogin.etLoginId.getText().toString());
        params.addBodyParameter("name", mBinding.layoutCreditLoginLogin.etLoginName.getText().toString());
        params.addBodyParameter("loginpwd", mBinding.layoutCreditLoginLogin.etLoginPassword.getText().toString());
        XutilsHttp.getInstance().post(params, new ViewShowHelper.ClickListenr<CreditLogin>() {
            @Override
            public void onXUtilsSuccess(CreditLogin creditLogin) {
                if (creditLogin.getCode().equals("1")) {
                    if (creditLogin.getMessage().equals("继续进行问题验证")) {
                        iid =  creditLogin.getIid();
                       // String json = "{\"countime\":\"599\",\"certify\":[{\"question\":\"2015年01月至2018年01月期间，您办理的所有贷款中，最高一笔贷款额度为多少？\",\"options\":[\"6501-7500\",\"7501-8500\",\"8501-9500\",\"9501-10500\",\"以上都不是\"]},{\"question\":\"您于2017年12月在何处办理过贷款？\",\"options\":[\"河北省邢台市\",\"海南省海口市\",\"广东省深圳市\",\"北京市\",\"以上都不是\"]},{\"question\":\"您于2017年04月在哪家机构办理过贷款？\",\"options\":[\"汉口银行\",\"黑龙江省农村信用社联合社\",\"北京银行\",\"中国建设银行\",\"以上都不是\"]},{\"question\":\"您2017年12月的学历是什么？\",\"options\":[\"大学本科（简称\\\"大学\\\"）\",\"大学专科和专科学校（简称\\\"大专\\\"）\",\"文盲或半文盲\",\"研究生\",\"以上都不是\"]},{\"question\":\"您在2006年06月办理的贷款每月应还款额（即还款计划表上和银行约定的还款金额）是多少？\",\"options\":[\"4301-5300\",\"5301-6300\",\"6301-7300\",\"7301-8300\",\"以上都不是\"]}],\"certifyId\":\"\"}";
                        timer.schedule(task, 1000, 1000);       // timeTask
                        mBinding.layoutCreditLoginLogin.layoutCreditLoginLogin.setVisibility(View.GONE);
                        mBinding.layoutCreditLoginIdentify.layoutCreditLoginIdentify.setVisibility(View.VISIBLE);
                        mBinding.layoutCreditLoginAppling.layoutCreditLoginAppling.setVisibility(View.GONE);
                        mBinding.layoutCreditLoginGetReport.layoutCreditLoginGetReport.setVisibility(View.GONE);
                        mBinding.layoutRecoverWeb.layoutRecoverWebview.setVisibility(View.GONE);
                        mBinding.layoutRecoverBanggain.layoutRecoverBanggain.setVisibility(View.GONE);
                        question(creditLogin.getData());
                    } else if(creditLogin.getMessage().equals("继续进行银行卡验证")){
                    initWeb();
                    }
                else {
                        ToastUtil.show(CreditLoginActivity.this, creditLogin.getMessage());
                    }
                } else {
                    //登录失败
                    ToastUtil.show(CreditLoginActivity.this, creditLogin.getMessage());
                }
            }
            @Override
            public void onXUtilsFaild(Throwable ex, boolean isOnCallback) {
                Logs.e("", ex.getMessage());

            }
        });
    }

    private void initWeb() {

        mBinding.layoutCreditLoginLogin.layoutCreditLoginLogin.setVisibility(View.GONE);
        mBinding.layoutCreditLoginIdentify.layoutCreditLoginIdentify.setVisibility(View.GONE);
        mBinding.layoutCreditLoginAppling.layoutCreditLoginAppling.setVisibility(View.GONE);
        mBinding.layoutCreditLoginGetReport.layoutCreditLoginGetReport.setVisibility(View.GONE);
        mBinding.layoutRecoverWeb.layoutRecoverWebview.setVisibility(View.VISIBLE);
        mBinding.layoutRecoverBanggain.layoutRecoverBanggain.setVisibility(View.GONE);
        mBinding.layoutRecoverWeb.btnCreditPasswordLoginWebviewCommit.setOnClickListener(this);
        mBinding.layoutRecoverWeb.rvCreditPasswordLoginTipWebview.setWebViewClient(new WebViewClient());
        mBinding.layoutRecoverWeb.rvCreditPasswordLoginTipWebview.getSettings().setSupportMultipleWindows(false);
        mBinding.layoutRecoverWeb.rvCreditPasswordLoginTipWebview.getSettings().setSupportZoom(true);
        mBinding.layoutRecoverWeb.rvCreditPasswordLoginTipWebview.getSettings().setBuiltInZoomControls(true);
        mBinding.layoutRecoverWeb.rvCreditPasswordLoginTipWebview.getSettings().setBlockNetworkImage(false);
        mBinding.layoutRecoverWeb.rvCreditPasswordLoginTipWebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mBinding.layoutRecoverWeb.rvCreditPasswordLoginTipWebview.getSettings().setJavaScriptEnabled(true);
        mBinding.layoutRecoverWeb.rvCreditPasswordLoginTipWebview.setWebChromeClient(new WebChromeClient());
        RequestParams params = new RequestParams(APP.queryweb);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String o) {
                Logs.d(getClass().getName(), o);
                mBinding.layoutRecoverWeb.rvCreditPasswordLoginTipWebview.loadData(o.toString(), "text/html", "UTF-8");

            }


            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public static String timeParse(long duration) {
        String time = "" ;
        long minute = duration / 60000 ;
        long seconds = duration % 60000 ;
        long second = Math.round((float)seconds/1000) ;
        if( minute < 10 ){
            time += "0" ;
        }
        time += minute+":" ;
        if( second < 10 ){
            time += "0" ;
        }
        time += second ;
        return time ;
    }
    private void question(String json) {
        try {
            JSONObject jsonobj = new JSONObject(json);
            JSONArray certify = jsonobj.getJSONArray("certify");
            certifyId = jsonobj.getString("certifyId");

            for (int i = 0 ;i<certify.length() ; i++){
                String question =  ((JSONObject) certify.get(i)).getString("question");
                Logs.i(question);
                TextView tv = new TextView(this);
                LinearLayout.LayoutParams layoutpargms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutpargms.setMargins(0,10,0,10);
                tv.setLayoutParams(layoutpargms);
                tv.setText((i+1) +". "+question);
                tv.setTextColor(this.getResources().getColor(R.color.color_font_grey));
                mBinding.layoutCreditLoginIdentify.rvCreditLoginTipQuestion.addView(tv);
                JSONArray options  = ((JSONObject) certify.get(i)).getJSONArray("options");
                RadioGroup rg = new RadioGroup(this);
                rg.setOrientation(LinearLayout.VERTICAL);
                for (int j = 0 ;j < options.length() ; j++){
                    String option1 = options.getString(j);
                    RadioButton rb = new RadioButton(this);
                    rb.setText(option1);
                    rb.setId(j+1);
                    rb.setTextColor(this.getResources().getColor(R.color.color_font_grey));
                    rg.addView(rb);
                    Logs.i(option1);
                }
                final int finalI = i;
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        switch (finalI){
                            case 0:
                                check1 = checkedId;
                                return;
                            case 1:
                                check2 = checkedId;
                                return;
                            case 2:
                                check3 = checkedId;
                                return;
                            case 3:
                                check4 = checkedId;
                                return;
                            case 4:
                                check5 = checkedId;
                                return;
                        }
                    }
                });

                mBinding.layoutCreditLoginIdentify.rvCreditLoginTipQuestion.addView(rg);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    recLen-=1000;
                    mBinding.layoutCreditLoginIdentify.tvCreditLoginTipTime.setVisibility(View.VISIBLE);
                    mBinding.layoutCreditLoginIdentify.tvCreditLoginTipTime.setText(timeParse( recLen));
                    if(recLen < 0){
                        timer.cancel();

                        CreditLoginActivity.this.finish();
                    }
                }
            });
        }
    };

}
