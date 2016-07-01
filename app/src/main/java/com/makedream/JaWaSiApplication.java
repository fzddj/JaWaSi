package com.makedream;

import android.app.Application;
import android.os.Handler;

import com.makedream.jawasi.Global;

/**
 * Created by Administrator on 2016/7/1.
 */

public class JaWaSiApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Global.handler = new Handler();
        Global.mContext = this;
    }
}
