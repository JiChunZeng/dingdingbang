package com.dingtaifu.dingdingbang.mvp.main.view.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dingtaifu.dingdingbang.R;
import com.dingtaifu.dingdingbang.base.BaseFragment;
import com.dingtaifu.dingdingbang.databinding.FragmentFinanceBinding;

public class FinanceFragment extends BaseFragment implements View.OnClickListener {

    private FragmentFinanceBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_finance, container, false);
        super.onCreateView(inflater, container, savedInstanceState);
        return mBinding.getRoot();
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void loadData() {

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
       
        }
    }
}
