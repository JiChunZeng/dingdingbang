package com.dingtaifu.dingdingbang.mvp.identify.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dingtaifu.dingdingbang.R;
import com.dingtaifu.dingdingbang.databinding.ActivityIdentifyWorkBinding;

public class IdentifyWorkActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityIdentifyWorkBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_identify_work);
        mBinding.ivBack.setOnClickListener(this);
        mBinding.btnFinishWork.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
            case R.id.btn_finish_work:
                finish();
                break;
        }
    }
}
