package com.dingtaifu.dingdingbang.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dingtaifu.dingdingbang.bean.MessageEvent;
import com.dingtaifu.dingdingbang.global.AppConstant;
import com.dingtaifu.dingdingbang.utils.AppManager;
import com.dingtaifu.dingdingbang.utils.StatusBarUtil;
import com.dingtaifu.dingdingbang.utils.TUtil;

import org.greenrobot.eventbus.EventBus;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BaseActivity<T extends RxPresenter> extends AppCompatActivity {

    public T mPresenter;

    public Context mContext;
    public BaseActivity mActivity;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);

        mContext = this;
        mActivity = this;


        initView();

        initStatusBar();
        initPresenter();
        loadData();
    }

    private void initStatusBar() {
        StatusBarUtil.StatusBarLightMode(this);
    }

    public void initView() {
    }

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public void initPresenter() {
        mPresenter = TUtil.getT(this, 0);
    }

    public void loadData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        AppManager.getInstance().finishActivity(this);
    }
    public void logoout(){
        SharedPreferences mSharedPreferences = mActivity.getSharedPreferences("loginUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean("isLogin", false);
        AppConstant.IS_Login = false;
        editor.commit();
        AppConstant.IS_Refresh = true;
        // 发布事件
        EventBus.getDefault().post(new MessageEvent("Refresh"));
    }
}
