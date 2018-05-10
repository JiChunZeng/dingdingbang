package com.dingtaifu.dingdingbang.mvp.customer_service.view;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.dingtaifu.dingdingbang.R;
import com.dingtaifu.dingdingbang.base.BaseActivity;
import com.dingtaifu.dingdingbang.databinding.ActivityFeedBackBinding;
import com.dingtaifu.dingdingbang.utils.ToastUtil;

public class FeedBackActivity extends BaseActivity implements View.OnClickListener {
    private ActivityFeedBackBinding mBinding;
    private String textLen;

    @Override
    public void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_feed_back);
        mBinding.commonTitle.tvTitle.setText("意见反馈");

        mBinding.commonTitle.ivBack.setOnClickListener(this);
        mBinding.btnFeedbackCommit.setOnClickListener(this);
        textLen = mBinding.tvFeedbackTextLength.getText().toString();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_feedback_commit:
                if (mBinding.etFeedbackContext.getText().toString().equals("") |
                        mBinding.etFeedbackContact.getText().toString().equals("")) {
                    ToastUtil.show(this, "请填写完整信息");
                    return;
                }
                ToastUtil.show(this, "提交成功");
                break;
        }
    }
}
