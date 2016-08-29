package com.makedream.util;

import android.util.Log;

import com.makedream.jawasi.BuildConfig;

/**
 * Created by dingdj on 2016/7/11.
 */

public class LogUtil {

    void e(String tag, String message) {
        if(BuildConfig.DEBUG)
            Log.e(tag, message);
    }

    void d(String tag, String message) {
        if(BuildConfig.DEBUG)
            Log.d(tag, message);
    }

}
