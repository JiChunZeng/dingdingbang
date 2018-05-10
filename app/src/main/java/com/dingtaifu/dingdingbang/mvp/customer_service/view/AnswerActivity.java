package com.dingtaifu.dingdingbang.mvp.customer_service.view;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.dingtaifu.dingdingbang.R;
import com.dingtaifu.dingdingbang.base.BaseActivity;
import com.dingtaifu.dingdingbang.databinding.ActivityAnswerBinding;

public class AnswerActivity extends BaseActivity implements View.OnClickListener {

    private ActivityAnswerBinding mBinding;
    private String title;

    @Override
    public void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_answer);

        mBinding.commonTitle.tvTitle.setText(getIntent().getStringExtra("title"));
        if (getIntent().getStringExtra("content").equals("1")) {
            mBinding.tvAnswer.setText("用户注册时被拒绝可能有一下几种情况：\n" +
                    "目前个人征信系统尚未收录您的任何信息，无法进行注册。\n" +
                    "您已使用您的身份信息（姓名、证件类型和证件号码）注册过其他用户，并且那个用户已通过身份验证。");
        } else if (getIntent().getStringExtra("content").equals("2")) {
            mBinding.tvAnswer.setText("点击平台征信登录页面\"忘记登录名\"链接，输入相关信息，系统会以短信方式将登录名发送到您预留的手机号码上。\n" +
                    "注意：对于安全等级为低、未注册或已销户的用户，无法使用此功能找回登录名，可使用\"用户注册\"功能重新注册。");
        }else if (getIntent().getStringExtra("content").equals("3")) {
            mBinding.tvAnswer.setText("点击平台征信登录页面的“忘记密码”链接，输入登录名，设置新密码并通过手机动态码确认后，再进行身份验证，通过验证的用户密码重置成功。");
        }else if (getIntent().getStringExtra("content").equals("4")) {
            mBinding.tvAnswer.setText("正常情况下，会在您提交查询申请的24个小时后，将身份验证码反馈给您。若您在申请查询24个小时后仍未收到反馈结果，可能是由于以下原因：一是您注册时填写的手机号码不正确；二是您的手机对短信进行了拦截，或短信接收出现异常；三是您在提交查询申请时，使用问题验证方式，经审核您未通过身份验证。您可以用注册时填写的登录名和密码登录平台，平台会显示您的信用信息产品处理状态。");
        }else if (getIntent().getStringExtra("content").equals("5")) {
            mBinding.tvAnswer.setText("以下情况需要身份验证：\n" +
                    "（1）申请查询个人信用报告。\n" +
                    "（2）变更安全等级。\n" +
                    "（3）重置密码。\n" +
                    "（4）修改手机号码（开通快捷查询的用户）。\n" +
                    "（5）开通快捷查询（安全等级为高的用户）。\n" +
                    "（6）用户销户（未登录时）。");
        }

        mBinding.commonTitle.ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
