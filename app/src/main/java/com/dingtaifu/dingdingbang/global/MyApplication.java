package com.dingtaifu.dingdingbang.global;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.StrictMode;

import com.dingtaifu.dingdingbang.BuildConfig;
import com.tencent.bugly.crashreport.CrashReport;

import org.xutils.x;

/**
 * Created by sakamichi on 2018/2/11.
 */

public class MyApplication extends Application {

    private static MyApplication instance;
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        /*第三个参数为SDK调试模式开关，调试模式的行为特性如下：
           输出详细的Bugly SDK的Log；
           每一条Crash都会被立即上报；
           自定义日志将会在Logcat中输出。
           建议在测试阶段建议设置成true，发布时设置为false。*/
        CrashReport.initCrashReport(getApplicationContext(), "824c8ed1c8", AppConstant.IS_DEV);
        instance = this;

        //对xUtils进行初始化
        x.Ext.init(this);
        //是否是开发、调试模式
        x.Ext.setDebug(BuildConfig.DEBUG);//是否输出debug日志，开启debug会影响性能


        SharedPreferences sp = getSharedPreferences("loginUser", Context.MODE_PRIVATE);
        AppConstant.IS_Login = sp.getBoolean("isLogin", false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}
