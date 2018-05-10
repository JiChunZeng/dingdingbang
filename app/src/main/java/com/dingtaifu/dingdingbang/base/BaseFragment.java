package com.dingtaifu.dingdingbang.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dingtaifu.dingdingbang.utils.TUtil;


/**
 * Description:
 * Author     : Xiccc.
 * Date       : 2017/12/19 15:07
 */

public abstract class BaseFragment<T extends RxPresenter> extends Fragment {

    public T mPresenter;

    protected View mView;
    public Context mContext;
    public AppCompatActivity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        initView();
        mContext = getContext();
        mActivity = (AppCompatActivity) getActivity();
        mPresenter = TUtil.getT(this, 0);
        initPresenter();
        loadData();
        return mView;
    }

    public void initView() {
    }

    /*********************
     * 子类实现
     *****************************/

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    //加载View、设置数据
    public abstract void loadData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();

        }
//        loadData();
    }
}
