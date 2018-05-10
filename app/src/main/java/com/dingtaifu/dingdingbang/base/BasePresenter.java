package com.dingtaifu.dingdingbang.base;

/**
 * Created by sakamichi on 2017/7/6.
 */

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);

    void detachView();
}
