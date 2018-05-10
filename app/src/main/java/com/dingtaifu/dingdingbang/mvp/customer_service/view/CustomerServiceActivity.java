package com.dingtaifu.dingdingbang.mvp.customer_service.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.dingtaifu.dingdingbang.R;
import com.dingtaifu.dingdingbang.base.BaseActivity;
import com.dingtaifu.dingdingbang.databinding.ActivityCustomerServiceBinding;

public class CustomerServiceActivity extends BaseActivity implements View.OnClickListener {
    private ActivityCustomerServiceBinding mBinding;

    @Override
    public void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_customer_service);
        mBinding.commonTitle.ivBack.setOnClickListener(this);
        mBinding.commonTitle.tvTitle.setText("客服中心");

        mBinding.btnService.setOnClickListener(this);//立即咨询
        mBinding.tvService1.setOnClickListener(this);
        mBinding.tvService2.setOnClickListener(this);
        mBinding.tvService3.setOnClickListener(this);
        mBinding.tvService4.setOnClickListener(this);
        mBinding.tvService5.setOnClickListener(this);
        mBinding.clServiceWechat.setOnClickListener(this);
        mBinding.tvServiceFeedback.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_service:
//                startActivity(new Intent(this, AnswerActivity.class));
                break;
            case R.id.tv_service_1:
                Intent intent1 = new Intent(this, AnswerActivity.class);
                intent1.putExtra("title", "为什么我注册用户时被拒绝？");
                intent1.putExtra("content", "1");
                startActivity(intent1);
                break;
            case R.id.tv_service_2:
                Intent intent2 = new Intent(this, AnswerActivity.class);
                intent2.putExtra("title", "忘记登录名怎么办？");
                intent2.putExtra("content", "2");
                startActivity(intent2);
                break;
            case R.id.tv_service_3:
                Intent intent3 = new Intent(this, AnswerActivity.class);
                intent3.putExtra("title", "忘记密码怎么办？");
                intent3.putExtra("content", "3");
                startActivity(intent3);
                break;
            case R.id.tv_service_4:
                Intent intent4 = new Intent(this, AnswerActivity.class);
                intent4.putExtra("title", "为什么我提交查询申请后，没有收到身份验证码？");
                intent4.putExtra("content", "4");
                startActivity(intent4);
                break;
            case R.id.tv_service_5:
                Intent intent5= new Intent(this, AnswerActivity.class);
                intent5.putExtra("title", "哪些情况需要身份验证？" );
                intent5.putExtra("content", "5");
                startActivity(intent5);
                break;
            case R.id.cl_service_wechat:

                break;
            case R.id.tv_service_feedback:
                startActivity(new Intent(mActivity, FeedBackActivity.class));
                break;
        }
    }
}
