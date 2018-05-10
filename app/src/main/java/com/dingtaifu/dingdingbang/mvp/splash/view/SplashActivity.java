package com.dingtaifu.dingdingbang.mvp.splash.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Handler;
import android.view.animation.AlphaAnimation;

import com.dingtaifu.dingdingbang.R;
import com.dingtaifu.dingdingbang.base.BaseActivity;
import com.dingtaifu.dingdingbang.databinding.ActivitySplashBinding;
import com.dingtaifu.dingdingbang.mvp.main.view.activity.MainActivity;
import com.dingtaifu.dingdingbang.utils.Logs;


public class SplashActivity extends BaseActivity {
    private ActivitySplashBinding mBinding;
    private AlphaAnimation mAnimation;
    private CustomVideoView customVideoView;

    @Override
    public void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
//        initAnim();
       /* mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });*/


        /**播放视频**/

        customVideoView = new CustomVideoView(this);
        customVideoView = findViewById(R.id.cv);
        customVideoView.playVideo(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash));
        boolean launch = this.getSharedPreferences("launch", Context.MODE_PRIVATE).getBoolean("launch",false);
        Logs.e( launch+"!!!!!!!!!!!!!");
        Handler handler = new Handler();
        if (launch) {


            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));

                    finish();
                }
            }, 1000);
        }else{
            this.getSharedPreferences("launch", Context.MODE_PRIVATE).edit().putBoolean("launch",true).commit();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, LaunchActivity.class));
                    finish();
                }
            }, 10000);
        }
    }

    /*private void initAnim() {
        mAnimation = new AlphaAnimation(0.1f, 1.0f);
        mAnimation.setDuration(5000);
        mBinding.ivSplash.setAnimation(mAnimation);
        finish();
    }*/


    /**
     * 记得在销毁的时候让播放的视频终止
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (customVideoView != null) {
            customVideoView.stopPlayback();
        }
    }

}
