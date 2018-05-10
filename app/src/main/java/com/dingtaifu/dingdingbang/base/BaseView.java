package com.dingtaifu.dingdingbang.base;

/**
 * BaseView
 */
public interface BaseView {

    /**
     * 开始加载dialog
     */
    void showLoading(String content);

    /**
     * 停止加载dialog
     */
    void stopLoading();

    /**
     * 请求失败
     * @param msg
     *         请求异常信息
     * 
     */
    void showErrorMsg(String msg);
}
