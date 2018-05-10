package com.dingtaifu.dingdingbang.mvp.main.view.fragment;

import android.support.v4.app.Fragment;
import android.util.SparseArray;

/**
 * Created by sakamichi on 2018/2/22.
 */

public class FragmentFactory {
    private static SparseArray<Fragment> mFragmentMap = new SparseArray<>();

    public static Fragment create(int pos) {
        Fragment mFragment = mFragmentMap.get(pos);
        if (mFragment == null) {
            switch (pos) {
                case 0:
                    mFragment = new EvaluateFragment();
                    break;
//                case 1:
//                    mFragment = new FinanceFragment();
//                    break;
                case 1:
                    mFragment = new MeFragment();
                    break;
            }
            mFragmentMap.put(pos, mFragment);
        }
        return mFragment;
    }
}
