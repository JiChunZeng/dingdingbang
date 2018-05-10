package com.dingtaifu.dingdingbang.mvp.main.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dingtaifu.dingdingbang.mvp.main.view.fragment.FragmentFactory;

/**
 * Created by sakamichi on 2018/3/13.
 */

public class MainPageAdapter extends FragmentPagerAdapter {
    private String[] mTabNames = {"征信",/* "金融中心",*/ "个人中心"};

    public MainPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mTabNames[position];
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.create(position);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
