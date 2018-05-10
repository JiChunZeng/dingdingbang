package com.dingtaifu.dingdingbang.global.request;

import android.text.TextUtils;

import com.dingtaifu.dingdingbang.base.BaseView;

import java.net.SocketTimeoutException;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

/**
 * Created by codeest on 2017/2/23.
 */

public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {
    private BaseView mView;
    private String mErrorMsg;
    private boolean isShowErrorState = true;

    protected CommonSubscriber(BaseView view) {
        this.mView = view;
    }

    protected CommonSubscriber(BaseView view, String errorMsg) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected CommonSubscriber(BaseView view, boolean isShowErrorState) {
        this.mView = view;
        this.isShowErrorState = isShowErrorState;
    }

    protected CommonSubscriber(BaseView view, String errorMsg, boolean isShowErrorState) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
        this.isShowErrorState = isShowErrorState;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        if (isShowErrorState) {
            if (mView == null) {
                return;
            }
            if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
                mView.showErrorMsg(mErrorMsg);
            } else if (e instanceof HttpException) {
                mView.showErrorMsg("数据加载失败ヽ(≧Д≦)ノ");
            } else if(e instanceof SocketTimeoutException){
                mView.showErrorMsg("链接超时，请重试");
            } else {
                mView.showErrorMsg("未知错误ヽ(≧Д≦)ノ");
            }
        }
    }
}
