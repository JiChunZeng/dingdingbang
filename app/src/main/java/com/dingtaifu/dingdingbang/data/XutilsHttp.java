package com.dingtaifu.dingdingbang.data;

import com.dingtaifu.dingdingbang.utils.Logs;

import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.List;

/**
 * Created by qhy on 2017/11/3.
 * 对Xutils3的再次封装
 */
public class XutilsHttp {
    private static XutilsHttp mXUtilsHelper;

    private XutilsHttp() {

    }

    public static XutilsHttp getInstance() {
        if (null == mXUtilsHelper) {
            mXUtilsHelper = new XutilsHttp();
        }
        return mXUtilsHelper;
    }


    public void post(RequestParams params, ViewShowHelper.ClickListenr<?> callback) {
        callback.onStart();
        x.http().post(params, callback);
        Logs.e("post", "请求地址::::" + params.getUri());
//        Logs.e("post", "请求头信息::::" + params.getHeaders().toString());
        List<KeyValue> keyVals = params.getBodyParams();
        for (KeyValue keyValue : keyVals) {
            Logs.e("post", "请求内容::::" + "key:  " + keyValue.key + "   val:  " + keyValue.value);
        }

    }

    public void get(RequestParams requestParams, ViewShowHelper.ClickListenr<?> callback) {
        callback.onStart();
        x.http().get(requestParams, callback);
    }

    public void downLoadFile(RequestParams params, File savePath, boolean autoResume, ViewShowHelper.ClickListenr<?> callback) {
        callback.onStart();
        params.setAutoResume(autoResume);
        params.setAutoRename(false);
        params.setSaveFilePath(savePath.getAbsolutePath());
        x.http().post(params, callback);
    }

    public void downLoadFile(RequestParams params, String savePath, boolean autoResume, ViewShowHelper.ClickListenr<?> callback) {
        callback.onStart();
        params.setAutoResume(autoResume);
        params.setAutoRename(false);
        params.setSaveFilePath(savePath);
        x.http().post(params, callback);
    }

    public void uploadFile(RequestParams params, File savePath, ViewShowHelper.ClickListenr<?> callback) {
        callback.onStart();
        params.setMultipart(true);
        params.addBodyParameter("file", savePath, null); // 如果文件没有扩展名,
        x.http().post(params, callback);
    }

    public void downloadSoundFile(String url, String path, ViewShowHelper.ClickListenr<?> callback) {
        callback.onStart();
        RequestParams params = new RequestParams(url);
        params.setAutoRename(true);//断点下载
        params.setSaveFilePath(path);
        x.http().get(params, callback);
    }


}
