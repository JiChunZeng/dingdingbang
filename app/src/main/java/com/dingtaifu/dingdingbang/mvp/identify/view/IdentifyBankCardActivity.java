package com.dingtaifu.dingdingbang.mvp.identify.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dingtaifu.dingdingbang.R;
import com.dingtaifu.dingdingbang.databinding.ActivityIdentifyBankCardBinding;

public class IdentifyBankCardActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityIdentifyBankCardBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_identify_bank_card);
        mBinding.ivBack.setOnClickListener(this);
        mBinding.btnFinishCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
            case R.id.btn_finish_card:
                finish();
                break;
        }
    }
}
