package com.dingtaifu.dingdingbang.mvp.main.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;

import com.dingtaifu.dingdingbang.R;
import com.dingtaifu.dingdingbang.base.BaseActivity;
import com.dingtaifu.dingdingbang.bean.CreditLogin;
import com.dingtaifu.dingdingbang.bean.MessageEvent;
import com.dingtaifu.dingdingbang.bean.UserLogin;
import com.dingtaifu.dingdingbang.data.APP;
import com.dingtaifu.dingdingbang.data.ViewShowHelper;
import com.dingtaifu.dingdingbang.data.XutilsHttp;
import com.dingtaifu.dingdingbang.databinding.ActivityMainBinding;
import com.dingtaifu.dingdingbang.global.AppConstant;
import com.dingtaifu.dingdingbang.mvp.credit.view.CreditLoginActivity;
import com.dingtaifu.dingdingbang.mvp.login.view.LoginActivity;
import com.dingtaifu.dingdingbang.mvp.main.view.adapter.MainPageAdapter;
import com.dingtaifu.dingdingbang.mvp.main.view.fragment.EvaluateFragment;
import com.dingtaifu.dingdingbang.mvp.share.view.ShareActivity;
import com.dingtaifu.dingdingbang.utils.Logs;
import com.dingtaifu.dingdingbang.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding mBinding;

    @Override
    public void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.tlMain.setupWithViewPager(mBinding.vpMain);
        mBinding.vpMain.setAdapter(new MainPageAdapter(getSupportFragmentManager()));

        mBinding.tlMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 1) {
                    if (AppConstant.IS_Login) {

                    } else {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        mBinding.vpMain.setCurrentItem(0);
                        mBinding.vpMain.setAdapter(new MainPageAdapter(getSupportFragmentManager()));
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // 注册订阅者
        EventBus.getDefault().register(this);

    }


    public void CreditQuery(View v) {

        isLogin(CreditLoginActivity.class);
    }

    public void click_to_sign_in(View v) {
        isLogin(ShareActivity.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Log.i("EventBus", "message is " + event.getMessage());
        // 更新界面

        if ("RefreshLogin".equals(event.getMessage())) {
            mBinding.vpMain.setCurrentItem(1);
            mBinding.vpMain.setAdapter(new MainPageAdapter(getSupportFragmentManager()));
        }else{
            mBinding.vpMain.setCurrentItem(0);
            mBinding.vpMain.setAdapter(new MainPageAdapter(getSupportFragmentManager()));
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void isLogin(Class<?> clazz) {
        if (AppConstant.IS_Login) {
            //已登录
            startActivity(new Intent(mContext, clazz));
        } else {
            startActivity(new Intent(mContext, LoginActivity.class));
            mBinding.vpMain.setCurrentItem(0);
            mBinding.vpMain.setAdapter(new MainPageAdapter(getSupportFragmentManager()));
        }
    }

    /*重新加载布局*/


}
