package com.dingtaifu.dingdingbang.mvp.splash.view;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dingtaifu.dingdingbang.R;
import com.dingtaifu.dingdingbang.base.BaseActivity;
import com.dingtaifu.dingdingbang.databinding.ActivityLaunchBinding;
import com.dingtaifu.dingdingbang.databinding.ActivitySplashBinding;
import com.dingtaifu.dingdingbang.mvp.main.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class LaunchActivity extends BaseActivity {
    private ActivityLaunchBinding mBinding;
    private View view1, view2;
    private ImageView imageView1,imageView2;
    private List<View> viewList;//view数组
    private boolean isLastPage = false;
    private boolean isDragPage = false;
    private boolean canJumpPage = true;

    @Override
    public void initView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_launch);
        LayoutInflater inflater=getLayoutInflater();
        view1 = inflater.inflate(R.layout.launch_vp_item, null);
        view2 = inflater.inflate(R.layout.launch_vp_item,null);
        imageView1 = view1.findViewById(R.id.launch_vp_item_imageview);
        imageView1.setImageDrawable(this.getResources().getDrawable(R.drawable.launch_2));
        imageView2 = view2.findViewById(R.id.launch_vp_item_imageview);
        imageView2.setImageDrawable(this.getResources().getDrawable(R.drawable.launch_1));
        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                container.addView(viewList.get(position));
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_in_left);
                return viewList.get(position);

            }
        };
        mBinding.launchVp.setAdapter(pagerAdapter);
        //监听ViewPager的跳转状态，当跳转到最后一页时，执行jumpToNext()方法
        mBinding.launchVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * 在屏幕滚动过程中不断被调用
             * @param position
             * @param positionOffset   是当前页面滑动比例，如果页面向右翻动，这个值不断变大，最后在趋近1的情况后突变为0。如果页面向左翻动，这个值不断变小，最后变为0
             * @param positionOffsetPixels   是当前页面滑动像素，变化情况和positionOffset一致
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.v("AAA",isLastPage+"   "+isDragPage+"   "+positionOffsetPixels);
                if (isLastPage && isDragPage && positionOffsetPixels == 0){
                    //当前页是最后一页，并且是拖动状态，并且像素偏移量为0
                    if (canJumpPage){
                        canJumpPage = false;
                        JumpToNext();
                    }
                }
            }

            @Override
            public void onPageSelected(int position ) {
                isLastPage = position == viewList.size()-1;
            }


            /**
             * 在手指操作屏幕的时候发生变化
             * @param state   有三个值：0（END）,1(PRESS) , 2(UP) 。
             */
            @Override
            public void onPageScrollStateChanged(int state) {

                isDragPage = state == 1;

            }
        });

    }
    /**
     * viewpager滑动到最后一页做跳转逻辑
     */
    private void JumpToNext() {

        startActivity(new Intent(LaunchActivity.this, MainActivity.class));
        this.finish();
    }
}
