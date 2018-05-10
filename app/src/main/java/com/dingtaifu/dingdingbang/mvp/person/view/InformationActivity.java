package com.dingtaifu.dingdingbang.mvp.person.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.dingtaifu.dingdingbang.R;
import com.dingtaifu.dingdingbang.base.BaseActivity;
import com.dingtaifu.dingdingbang.databinding.ActivityInformationBinding;

public class InformationActivity extends BaseActivity implements View.OnClickListener {

    private ActivityInformationBinding mBinding;

    @Override
    public void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_information);
        mBinding.commonTitle.tvTitle.setText("个人资料");
        mBinding.commonTitle.ivBack.setOnClickListener(this);

        mBinding.informationPhone.setText( this.getSharedPreferences("loginUser", Context.MODE_PRIVATE).getString("user_id",""));
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
