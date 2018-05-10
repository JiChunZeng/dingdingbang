package com.dingtaifu.dingdingbang.mvp.main.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dingtaifu.dingdingbang.R;
import com.dingtaifu.dingdingbang.base.BaseFragment;
import com.dingtaifu.dingdingbang.bean.MessageEvent;
import com.dingtaifu.dingdingbang.databinding.FragmentMeBinding;
import com.dingtaifu.dingdingbang.global.AppConstant;
import com.dingtaifu.dingdingbang.mvp.customer_service.view.CustomerServiceActivity;
import com.dingtaifu.dingdingbang.mvp.person.view.InformationActivity;
import com.dingtaifu.dingdingbang.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;


public class MeFragment extends BaseFragment implements View.OnClickListener {

    private FragmentMeBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_me, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        return mBinding.getRoot();
    }

    @Override
    public void initPresenter() {
        mBinding.rlMeInfo.setOnClickListener(this);
        mBinding.rlMeService.setOnClickListener(this);
        mBinding.rlMeUpdate.setOnClickListener(this);
        mBinding.rlMeLogout.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_me_info:
                startActivity(new Intent(mActivity, InformationActivity.class));
                break;
            case R.id.rl_me_service:
                startActivity(new Intent(mActivity, CustomerServiceActivity.class));
                break;
            case R.id.rl_me_update:
                ToastUtil.show(mActivity, "已经是最新版本了");
                break;
            case R.id.rl_me_logout:
                SharedPreferences mSharedPreferences = mActivity.getSharedPreferences("loginUser", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putBoolean("isLogin", false);
                AppConstant.IS_Login = false;
                editor.commit();
                AppConstant.IS_Refresh = true;
//                // 发布事件
                EventBus.getDefault().post(new MessageEvent("Refresh"));
                break;
            default:
                break;
        }
    }

}
