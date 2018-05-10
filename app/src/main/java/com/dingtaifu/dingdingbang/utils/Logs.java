package com.dingtaifu.dingdingbang.utils;

import android.text.TextUtils;

import java.util.concurrent.RejectedExecutionException;

public class Logs {


    private static String TAG = "ddjk";
    /** 日志开关 上线时关闭*/
    private static boolean DEBUG = true;

    public static String getTag(Class<?> cls) {
        return cls == null ? "" : cls.getSimpleName();
    }

    public static void i(Class<?> cls, String msg) {
        if (DEBUG) {
            android.util.Log.i(getTag(cls), msg);
        }
    }

    public static void d(Class<?> cls, String msg) {
        if (DEBUG) {
            android.util.Log.d(getTag(cls), msg);
        }
    }

    public static void e(Class<?> cls, String msg) {
        if (DEBUG) {
            android.util.Log.e(getTag(cls), msg);
        }
    }

    public static void v(Class<?> cls, String msg) {
        if (DEBUG) {
            android.util.Log.v(getTag(cls), msg);
        }
    }

    public static void i(String TAG, String msg) {
        if (DEBUG) {
            if (TextUtils.isEmpty(msg))
                return;
            android.util.Log.i(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (DEBUG) {
            android.util.Log.i(TAG, msg);
        }
    }

    public static void d(String TAG, String msg) {
        if (DEBUG) {
            android.util.Log.d(TAG, msg);
        }
    }

    public static void v(String TAG, String msg) {
        if (DEBUG) {
            android.util.Log.v(TAG, msg);
        }
    }

    public static void e(String TAG, String msg) {
        if (DEBUG && !TextUtils.isEmpty(msg)) {
            android.util.Log.e(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (DEBUG && !TextUtils.isEmpty(msg)) {
            android.util.Log.e(TAG, msg);
        }
    }

    public static void s(String msg) {
        if (DEBUG && !TextUtils.isEmpty(msg)) {
            System.out.println(msg);
        }
    }

    public static void s(Object msg) {
        if (DEBUG) {
            System.out.println(msg);
        }
    }

    public static void e(String TAG, RejectedExecutionException e) {
        if (DEBUG) {
            android.util.Log.e(TAG, e.toString());
        }
    }
}
