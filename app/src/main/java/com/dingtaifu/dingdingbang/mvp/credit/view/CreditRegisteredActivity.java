package com.dingtaifu.dingdingbang.mvp.credit.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.dingtaifu.dingdingbang.R;
import com.dingtaifu.dingdingbang.base.BaseActivity;
import com.dingtaifu.dingdingbang.bean.CreditRegister;
import com.dingtaifu.dingdingbang.bean.CreditRegisterFinish;
import com.dingtaifu.dingdingbang.data.APP;
import com.dingtaifu.dingdingbang.data.ViewShowHelper;
import com.dingtaifu.dingdingbang.data.XutilsHttp;
import com.dingtaifu.dingdingbang.databinding.ActivityCreditRegisteredBinding;
import com.dingtaifu.dingdingbang.utils.CountDownUtil;
import com.dingtaifu.dingdingbang.utils.Logs;
import com.dingtaifu.dingdingbang.utils.ToastUtil;

import org.xutils.http.RequestParams;

public class CreditRegisteredActivity extends BaseActivity implements View.OnClickListener {

    private ActivityCreditRegisteredBinding mBinding;
    private String iid;

    @Override
    public void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_credit_registered);

        mBinding.layoutCommonTitle.ivDoubt.setOnClickListener(this);
        mBinding.layoutCommonTitle.ivBack.setOnClickListener(this);
        mBinding.layoutCommonTitle.ivFront.setVisibility(View.INVISIBLE);
        mBinding.layoutCommonTitle.tvTitle.setText("征信注册");

        mBinding.layoutCreditRegisterBasic.btnRegisterNextStep.setOnClickListener(this);
        mBinding.layoutCreditRegisterBasic.tvRegisterToLogin.setOnClickListener(this);

        mBinding.layoutCreditRegisterSupplementary.tvSendVerificationCode.setOnClickListener(this);
        mBinding.layoutCreditRegisterSupplementary.btnRegisterNextStepFinish.setOnClickListener(this);

        mBinding.layoutCreditRegisterSupplementary.etRegisterLoginName.getText().toString();
        mBinding.layoutCreditRegisterSupplementary.etRegisterPassword.getText().toString();
        mBinding.layoutCreditRegisterSupplementary.etRegisterPhone.getText().toString();
        mBinding.layoutCreditRegisterSupplementary.etRegisterVerificationCode.getText().toString();

        mBinding.layoutCreditRegisterFinish.btnRegisterFinish.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_doubt:
                ToastUtil.show(this, "Where are you ?");
                break;
            case R.id.btn_register_next_step:
                if (mBinding.layoutCreditRegisterBasic.etRegisterName.getText().toString().equals("") |
                        mBinding.layoutCreditRegisterBasic.etRegisterId.getText().toString().equals("")) {
                    ToastUtil.show(this, "请填写完整信息");
                    return;
                }
                mBinding.layoutCreditRegisterBasic.llCreditRegisterBasic.setVisibility(View.GONE);
                mBinding.layoutCreditRegisterSupplementary.llCreditRegisterSupplementary.setVisibility(View.VISIBLE);
                mBinding.layoutCreditRegisterFinish.llCreditRegisterFinish.setVisibility(View.GONE);

                break;
            case R.id.tv_register_to_login:
                finish();
                break;
            case R.id.tv_send_verification_code:
                if (mBinding.layoutCreditRegisterSupplementary.etRegisterPhone.getText().toString().equals("")) {
                    ToastUtil.show(this, "请输入正确的手机号码");
                    return;
                }
                getCode();
                break;
            case R.id.btn_register_next_step_finish:
                if (mBinding.layoutCreditRegisterSupplementary.etRegisterLoginName.getText().toString().equals("") |
                        mBinding.layoutCreditRegisterSupplementary.etRegisterPassword.getText().toString().equals("") |
                        mBinding.layoutCreditRegisterSupplementary.etRegisterPhone.getText().toString().equals("") |
                        mBinding.layoutCreditRegisterSupplementary.etRegisterVerificationCode.getText().toString().equals("")) {
                    ToastUtil.show(this, "请填写完整信息");
                    return;
                }

                getRegister();

                break;
            case R.id.btn_register_finish:
                startActivity(new Intent(this, CreditLoginActivity.class));
                finish();
                break;


        }
    }

    private void getRegister() {
        RequestParams params = new RequestParams(APP.credit_regisiter_finish);
        params.addBodyParameter("activationCode", mBinding.layoutCreditRegisterSupplementary.etRegisterVerificationCode.getText().toString());
        params.addBodyParameter("iid", iid);
        XutilsHttp.getInstance().post(params, new ViewShowHelper.ClickListenr<CreditRegisterFinish>() {
            @Override
            public void onXUtilsSuccess(CreditRegisterFinish creditRegisterFinish) {
                if (creditRegisterFinish.getCode().equals("1")) {
                    ToastUtil.show(CreditRegisteredActivity.this, creditRegisterFinish.getMessage());

                    mBinding.layoutCreditRegisterBasic.llCreditRegisterBasic.setVisibility(View.GONE);
                    mBinding.layoutCreditRegisterSupplementary.llCreditRegisterSupplementary.setVisibility(View.GONE);
                    mBinding.layoutCreditRegisterFinish.llCreditRegisterFinish.setVisibility(View.VISIBLE);
                    new CountDownUtil(mBinding.layoutCreditRegisterFinish.tvRegisterAutoEnter)
                            .setCountDownMillis(10_000L)//倒计时60000ms
                            .setCountDownColor(R.color.text_grey, R.color.text_grey)//不同状态字体颜色
                            .setCountDownText("自动进入登录页")
                            .start();
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                sleep(10 * 1000);
                                startActivity(new Intent(mContext, CreditLoginActivity.class));
                                finish();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();

                } else {
                    //登录失败
                    ToastUtil.show(CreditRegisteredActivity.this, creditRegisterFinish.getMessage());
                }
            }

            @Override
            public void onXUtilsFaild(Throwable ex, boolean isOnCallback) {
                Logs.e("", ex.getMessage());

            }
        });

    }

    private void getCode() {
        RequestParams params = new RequestParams(APP.credit_regisiter);
        params.addBodyParameter("name", mBinding.layoutCreditRegisterBasic.etRegisterName.getText().toString());
        params.addBodyParameter("idCard", mBinding.layoutCreditRegisterBasic.etRegisterId.getText().toString());
        params.addBodyParameter("loginname", mBinding.layoutCreditRegisterSupplementary.etRegisterLoginName.getText().toString());
        params.addBodyParameter("loginpwd", mBinding.layoutCreditRegisterSupplementary.etRegisterPassword.getText().toString());
        params.addBodyParameter("phone", mBinding.layoutCreditRegisterSupplementary.etRegisterPhone.getText().toString());

        XutilsHttp.getInstance().post(params, new ViewShowHelper.ClickListenr<CreditRegister>() {
            @Override
            public void onXUtilsSuccess(CreditRegister creditRegister) {
                if (creditRegister.getCode().equals("1")) {
                    if (creditRegister.getMessage().equals("继续进行注册短信验证")) {

                        iid = creditRegister.getIid();
                        new CountDownUtil(mBinding.layoutCreditRegisterSupplementary.tvSendVerificationCode)
                                .setCountDownMillis(60_000L)//倒计时60000ms
                                .setCountDownColor(R.color.white, R.color.white)//不同状态字体颜色
                                .start();

                    } else {
                        ToastUtil.show(CreditRegisteredActivity.this, creditRegister.getMessage());
                    }
                } else {
                    //登录失败
                    ToastUtil.show(CreditRegisteredActivity.this, creditRegister.getMessage());
                }
            }

            @Override
            public void onXUtilsFaild(Throwable ex, boolean isOnCallback) {
                Logs.e("", ex.getMessage());

            }
        });

    }


}
