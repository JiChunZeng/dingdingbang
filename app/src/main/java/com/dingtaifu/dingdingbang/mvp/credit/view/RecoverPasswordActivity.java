package com.dingtaifu.dingdingbang.mvp.credit.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dingtaifu.dingdingbang.R;
import com.dingtaifu.dingdingbang.base.BaseActivity;
import com.dingtaifu.dingdingbang.bean.RecoverPassword;
import com.dingtaifu.dingdingbang.data.APP;
import com.dingtaifu.dingdingbang.data.ViewShowHelper;
import com.dingtaifu.dingdingbang.data.XutilsHttp;
import com.dingtaifu.dingdingbang.databinding.ActivityRecoverPasswordBinding;
import com.dingtaifu.dingdingbang.utils.Logs;
import com.dingtaifu.dingdingbang.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.dingtaifu.dingdingbang.data.APP.url;

public class RecoverPasswordActivity extends BaseActivity implements View.OnClickListener {
    private ActivityRecoverPasswordBinding mBinding;
    Timer timer = new Timer();
    int check1 = 0;
    int check2 = 0;
    int check3 = 0;
    int check4 = 0;
    int check5 = 0;
    String certifyId;
    String iid;
    String retCode;



    /*找回密码*/
    @Override
    public void initView() {

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recover_password);
        mBinding.layoutCommonTitle.ivBack.setOnClickListener(this);
        mBinding.layoutCommonTitle.tvTitle.setText("找回密码");
        mBinding.layoutRecoverNameId.btnRecoverNext.setOnClickListener(this);

        mBinding.layoutRecoverNameId.layoutRecoverNameId.setVisibility(View.VISIBLE);
        mBinding.layoutRecoverPasswordPhone.layoutRecoverPasswordPhone.setVisibility(View.GONE);
        mBinding.layoutRecoverQuestion.layoutRecoverQuestion.setVisibility(View.GONE);
        mBinding.layoutRecoverWeb.layoutRecoverWebview.setVisibility(View.GONE);
        mBinding.layoutRecoverBanggain.layoutRecoverBanggain.setVisibility(View.GONE);
        mBinding.layoutRecoverPasswordPhone.tvRecoverSendVerificationCode.setOnClickListener(this);
        mBinding.layoutRecoverPasswordPhone.btnRecoverFinish.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_recover_next:
                if (mBinding.layoutRecoverNameId.etRecoverName.getText().toString().equals("") |
                        mBinding.layoutRecoverNameId.etRecoverId.getText().toString().equals("") |
                        mBinding.layoutRecoverNameId.etRecoverLoginName.getText().toString().equals("")) {
                    ToastUtil.show(this, "请填写完整信息");
                    return;
                }

                nextRecoverPsd();
                break;

//            case R.id.tv_recover_send_verification_code:
//
//
//                new CountDownUtil(mBinding.layoutRecoverPasswordPhone.tvRecoverSendVerificationCode)
//                        .setCountDownMillis(60_000L)//倒计时60000ms
//                        .setCountDownColor(R.color.white, R.color.white)//不同状态字体颜色
//                        .start();
//                break;
            case R.id.btn_recover_finish:
                if (mBinding.layoutRecoverPasswordPhone.etRecoverPasswordFirst.getText().toString().equals("") |
                        mBinding.layoutRecoverPasswordPhone.etRecoverPasswordSecond.getText().toString().equals("") |
                        mBinding.layoutRecoverPasswordPhone.etRecoverPasswordVerificationCode.getText().toString().equals("")) {
                    ToastUtil.show(this, "请输入完整的信息");
                    return;
                }
                if (!mBinding.layoutRecoverPasswordPhone.etRecoverPasswordFirst.getText().toString().
                        equals(mBinding.layoutRecoverPasswordPhone.etRecoverPasswordSecond.getText().toString())) {
                    ToastUtil.show(this, "两次输入密码不一致");
                    return;
                }
                recoverPsdFinish();
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
                                mBinding.layoutRecoverNameId.layoutRecoverNameId.setVisibility(View.GONE);
                                mBinding.layoutRecoverPasswordPhone.layoutRecoverPasswordPhone.setVisibility(View.GONE);
                                mBinding.layoutRecoverQuestion.layoutRecoverQuestion.setVisibility(View.GONE);
                                mBinding.layoutRecoverWeb.layoutRecoverWebview.setVisibility(View.GONE);
                                mBinding.layoutRecoverBanggain.layoutRecoverBanggain.setVisibility(View.VISIBLE);
                                mBinding.layoutRecoverBanggain.btnRecoverBanggain.setOnClickListener(RecoverPasswordActivity.this);
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
                RequestParams params = new RequestParams(APP.bangGain);
                params.addBodyParameter("verifyCode", mBinding.layoutRecoverBanggain.etRecoverBanggain.getText().toString());
                params.addBodyParameter("iid", iid);
                final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);
                pDialog.show();
                XutilsHttp.getInstance().post(params, new ViewShowHelper.ClickListenr<RecoverPassword>() {

                    @Override
                    public void onXUtilsSuccess(RecoverPassword recoverPassword) {
                        ToastUtil.show(RecoverPasswordActivity.this, recoverPassword.getMessage());
                        pDialog.dismissWithAnimation();
                        pDialog.cancel();
                        if (recoverPassword.getCode().equals("1")) {
                            RecoverPasswordActivity.this.finish();

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

    private void recoverPsdFinish() {
        RequestParams params = new RequestParams(APP.credit_recover_psd_finish);
        params.addBodyParameter("activationCode", mBinding.layoutRecoverPasswordPhone.etRecoverPasswordVerificationCode.getText().toString());
        params.addBodyParameter("newpwd", mBinding.layoutRecoverPasswordPhone.etRecoverPasswordFirst.getText().toString());
        params.addBodyParameter("iid", iid);
        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        XutilsHttp.getInstance().post(params, new ViewShowHelper.ClickListenr<RecoverPassword>() {
            @Override
            public void onXUtilsSuccess(RecoverPassword recoverPassword) {
                pDialog.dismissWithAnimation();
                pDialog.cancel();
                if (recoverPassword.getCode().equals("1")) {
                    iid = recoverPassword.getIid();
                    retCode = recoverPassword.getRetCode();
                    if (recoverPassword.getMessage().equals("继续进行问题验证")) {
                        question(recoverPassword.getData());

                    }else if(recoverPassword.getMessage().equals("继续进行银行卡验证")){
                        initWeb();
                    }else {
                        ToastUtil.show(RecoverPasswordActivity.this, recoverPassword.getMessage());

                    }

                } else {
                    //登录失败
                    ToastUtil.show(RecoverPasswordActivity.this, recoverPassword.getMessage());
                }
            }

            @Override
            public void onXUtilsFaild(Throwable ex, boolean isOnCallback) {
                Logs.e("", ex.getMessage());

            }
        });
    }

    private void initWeb() {

        mBinding.layoutRecoverNameId.layoutRecoverNameId.setVisibility(View.GONE);
        mBinding.layoutRecoverPasswordPhone.layoutRecoverPasswordPhone.setVisibility(View.GONE);
        mBinding.layoutRecoverQuestion.layoutRecoverQuestion.setVisibility(View.GONE);
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
        x.http().get(params,new Callback.CommonCallback<String>() {
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


    private void question(String json) {
        try {
            timer.schedule(task, 1000, 1000);       // timeTask
            mBinding.layoutRecoverNameId.layoutRecoverNameId.setVisibility(View.GONE);
            mBinding.layoutRecoverPasswordPhone.layoutRecoverPasswordPhone.setVisibility(View.GONE);
            mBinding.layoutRecoverQuestion.layoutRecoverQuestion.setVisibility(View.VISIBLE);
            mBinding.layoutRecoverWeb.layoutRecoverWebview.setVisibility(View.GONE);
            mBinding.layoutRecoverBanggain.layoutRecoverBanggain.setVisibility(View.GONE);
            JSONObject jsonobj = new JSONObject(json);
            JSONArray certify = jsonobj.getJSONArray("certify");
            certifyId = jsonobj.getString("certifyId");

            for (int i = 0; i < certify.length(); i++) {
                String question = ((JSONObject) certify.get(i)).getString("question");
                Logs.i(question);
                TextView tv = new TextView(this);
                LinearLayout.LayoutParams layoutpargms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutpargms.setMargins(0, 10, 0, 10);
                tv.setLayoutParams(layoutpargms);
                tv.setText((i + 1) + ". " + question);
                tv.setTextColor(this.getResources().getColor(R.color.color_font_grey));
                mBinding.layoutRecoverQuestion.rvCreditPasswordLoginTipQuestion.addView(tv);
                JSONArray options = ((JSONObject) certify.get(i)).getJSONArray("options");
                RadioGroup rg = new RadioGroup(this);
                rg.setOrientation(LinearLayout.VERTICAL);
                for (int j = 0; j < options.length(); j++) {
                    String option1 = options.getString(j);
                    RadioButton rb = new RadioButton(this);
                    rb.setText(option1);
                    rb.setId(j + 1);
                    rb.setTextColor(this.getResources().getColor(R.color.color_font_grey));
                    rg.addView(rb);
                    Logs.i(option1);
                }
                final int finalI = i;
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        switch (finalI) {
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

                mBinding.layoutRecoverQuestion.rvCreditPasswordLoginTipQuestion.addView(rg);
            }
        }catch (Exception e){
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
                        mBinding.layoutRecoverQuestion.tvCreditPasswordLoginTip.setVisibility(View.VISIBLE);
                        mBinding.layoutRecoverQuestion.tvCreditPasswordLoginTipTime.setText(timeParse( recLen));
                        if(recLen < 0){
                            timer.cancel();

                            RecoverPasswordActivity.this.finish();
                        }
                    }
                });
            }
        };
    private int recLen = 1000*60*10;
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
    private void nextRecoverPsd() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        RequestParams params = new RequestParams(APP.credit_recover_psd);
        params.addBodyParameter("name", mBinding.layoutRecoverNameId.etRecoverName.getText().toString());
        params.addBodyParameter("idCard", mBinding.layoutRecoverNameId.etRecoverId.getText().toString());
        params.addBodyParameter("loginname", mBinding.layoutRecoverNameId.etRecoverLoginName.getText().toString());
        XutilsHttp.getInstance().post(params, new ViewShowHelper.ClickListenr<RecoverPassword>() {
            @Override
            public void onXUtilsSuccess(RecoverPassword recoverPassword) {
                if (recoverPassword.getCode().equals("1")) {

                    mBinding.layoutRecoverNameId.layoutRecoverNameId.setVisibility(View.GONE);
                    mBinding.layoutRecoverPasswordPhone.layoutRecoverPasswordPhone.setVisibility(View.VISIBLE);
                    mBinding.layoutRecoverQuestion.layoutRecoverQuestion.setVisibility(View.GONE);
                    mBinding.layoutRecoverWeb.layoutRecoverWebview.setVisibility(View.GONE);
                    mBinding.layoutRecoverBanggain.layoutRecoverBanggain.setVisibility(View.GONE);
                    mBinding.layoutRecoverPasswordPhone.etRecoverPasswordPhoneNum.setText(recoverPassword.getData());
                    iid = recoverPassword.getIid();

                } else {
                    //登录失败
                    ToastUtil.show(RecoverPasswordActivity.this, recoverPassword.getMessage());
                }
                pDialog.dismissWithAnimation();
                pDialog.cancel();
            }

            @Override
            public void onXUtilsFaild(Throwable ex, boolean isOnCallback) {
                Logs.e("", ex.getMessage());
                pDialog.cancel();
            }
        });

    }
}
