package com.dingtaifu.dingdingbang.mvp.credit.view;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.dingtaifu.dingdingbang.R;
import com.dingtaifu.dingdingbang.base.BaseActivity;
import com.dingtaifu.dingdingbang.databinding.ActivityInputReportCodeBinding;
import com.dingtaifu.dingdingbang.utils.CountDownUtil;
import com.dingtaifu.dingdingbang.utils.ToastUtil;

public class InputReportCodeActivity extends BaseActivity implements View.OnClickListener {
    private ActivityInputReportCodeBinding mBinding;

    @Override
    public void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_input_report_code);
        mBinding.commonTitle.tvTitle.setText("获取信用报告");
        mBinding.tvInputReportVerificationCode.setOnClickListener(this);
        mBinding.btnInputReportCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_input_report_verification_code:
                new CountDownUtil(mBinding.tvInputReportVerificationCode)
                        .setCountDownMillis(60_000L)//倒计时60000ms
                        .setCountDownColor(R.color.white, R.color.white)//不同状态字体颜色
                        .start();


                break;
            case R.id.btn_input_report_commit:
                if (mBinding.etInputReportVerificationCode.getText().toString().equals("")) {
                    ToastUtil.show(this, "请填写验证码");
                    return;
                }


                break;
        }
    }
}
