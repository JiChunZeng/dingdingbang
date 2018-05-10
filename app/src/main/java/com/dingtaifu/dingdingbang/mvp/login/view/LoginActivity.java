package com.dingtaifu.dingdingbang.mvp.login.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.dingtaifu.dingdingbang.R;
import com.dingtaifu.dingdingbang.base.BaseActivity;
import com.dingtaifu.dingdingbang.bean.MessageEvent;
import com.dingtaifu.dingdingbang.bean.UserLogin;
import com.dingtaifu.dingdingbang.data.APP;
import com.dingtaifu.dingdingbang.data.ViewShowHelper;
import com.dingtaifu.dingdingbang.data.XutilsHttp;
import com.dingtaifu.dingdingbang.databinding.ActivityLoginBinding;
import com.dingtaifu.dingdingbang.global.AppConstant;
import com.dingtaifu.dingdingbang.mvp.customer_service.view.CustomerServiceActivity;
import com.dingtaifu.dingdingbang.mvp.main.view.activity.MainActivity;
import com.dingtaifu.dingdingbang.utils.CountDownUtil;
import com.dingtaifu.dingdingbang.utils.Logs;
import com.dingtaifu.dingdingbang.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.xutils.http.RequestParams;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ActivityLoginBinding mBinding;
    String PhoneNumber;
    String VerificationCode;


    @Override
    public void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mBinding.commonTitle.ivBack.setOnClickListener(this);
        mBinding.commonTitle.tvTitle.setText("手机验证");
        mBinding.btnLogin.setOnClickListener(this);
        mBinding.tvSendVerificationCode.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_send_verification_code://获取验证码
                if (mBinding.etPhoneNumber.getText().toString().equals("")) {
                    ToastUtil.show(this, "请填写手机号");
                    return;
                }
                getCode();
                new CountDownUtil(mBinding.tvSendVerificationCode)
                        .setCountDownMillis(60_000L)//倒计时60000ms
                        .setCountDownColor(R.color.white, R.color.white)//不同状态字体颜色
                        .start();
                break;
            case R.id.btn_login://点击登录
                PhoneNumber = mBinding.etPhoneNumber.getText().toString();
                VerificationCode = mBinding.etVerificationCode.getText().toString();
                if (PhoneNumber.equals("") || VerificationCode.equals("")) {
                    ToastUtil.show(this, "请填写手机号或者验证码");
                    return;
                }
                loginData();
                break;

        }
    }

    private void getCode() {
        RequestParams params = new RequestParams(APP.sendNote);
        params.addBodyParameter("phone", mBinding.etPhoneNumber.getText().toString());
        XutilsHttp.getInstance().post(params, new ViewShowHelper.ClickListenr<UserLogin>() {
            @Override
            public void onXUtilsSuccess(UserLogin user) {
                if (user.getCode().equals("1")) {
                    ToastUtil.show(LoginActivity.this, user.getMessage());
                } else {
                    //登录失败
                    ToastUtil.show(LoginActivity.this, user.getMessage());
                }
            }

            @Override
            public void onXUtilsFaild(Throwable ex, boolean isOnCallback) {
                Logs.e("", ex.getMessage());
            }
        });

    }

    private void loginData() {
        RequestParams params = new RequestParams(APP.login);
        params.addBodyParameter("phone", mBinding.etPhoneNumber.getText().toString());
        params.addBodyParameter("authCode", mBinding.etVerificationCode.getText().toString());
        XutilsHttp.getInstance().post(params, new ViewShowHelper.ClickListenr<UserLogin>() {
            @Override
            public void onXUtilsSuccess(UserLogin user) {
                if (user.getCode().equals("1")) {
                    ToastUtil.show(LoginActivity.this, user.getMessage());

                    SharedPreferences mSharedPreferences = getSharedPreferences("loginUser", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString("user_id", mBinding.etPhoneNumber.getText().toString());

                    editor.putString("user_iid", user.getIid());
                    editor.putBoolean("isLogin", true);
                    editor.commit();
                    AppConstant.IS_Login=true;
                    // 发布事件
                    EventBus.getDefault().post(new MessageEvent("RefreshLogin"));
                    finish();
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    //登录失败
                    ToastUtil.show(LoginActivity.this, user.getMessage());
                }
            }

            @Override
            public void onXUtilsFaild(Throwable ex, boolean isOnCallback) {
                Logs.e("", ex.getMessage());

            }
        });

    }


}
