package com.dingtaifu.dingdingbang.data;

import android.content.Context;

import com.dingtaifu.dingdingbang.global.MyApplication;
import com.dingtaifu.dingdingbang.utils.Logs;
import com.dingtaifu.dingdingbang.utils.ToastUtil;
import com.google.gson.Gson;

import org.xutils.common.Callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * Created by qhy on 2017/11/3.
 * JSON数据解析
 */

public class ViewShowHelper {

    private Context mContext;

    public ViewShowHelper(Context context) {
        this.mContext = context;
    }

    public abstract static class ClickListenr<T> implements Callback.CommonCallback<String> {
        private Class<T> clazz;

        public ClickListenr() {
            Class clz = this.getClass();
            ParameterizedType type = (ParameterizedType) clz.getGenericSuperclass();
            Type[] types = type.getActualTypeArguments();
            try {
                clazz = (Class<T>) types[0];
            } catch (Exception e) {
                Logs.e(e.getMessage());
            }
        }

        public abstract void onXUtilsSuccess(T t);

        public abstract void onXUtilsFaild(Throwable ex, boolean isOnCallback);

        public void onStart() {

        }

        public void onSuccess(String result) {
            Logs.d(getClass().getName(), result);
            Gson gson = new Gson();
            T baseBean = gson.fromJson(result, clazz);
            onXUtilsSuccess(baseBean);
        }

        public void onError(Throwable ex, boolean isOnCallback) {
            ToastUtil.show(MyApplication.mContext, "网络好像有点问题，稍后重试");
            onXUtilsFaild(ex, isOnCallback);
        }

        public void onCancelled(CancelledException cex) {
            Logs.e("onCancelled", "用户取消操作");
        }

        public void onFinished() {

        }
    }



}
