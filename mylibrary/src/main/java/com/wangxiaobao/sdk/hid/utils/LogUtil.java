package com.wangxiaobao.sdk.hid.utils;

import android.util.Log;
import com.wangxiaobao.sdk.hid.BuildConfig;

public class LogUtil {
    private static String TAG = "HidSDK";
    public static void log(String msg){
        Log.d(TAG,msg);
    }
}
