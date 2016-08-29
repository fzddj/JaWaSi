package com.makedream.jawasi;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import com.makedream.util.event.EventBus;


/**
 * Created by dingdj on 2016/5/13.
 */
public class Global {

    public static Handler handler;

    public static Context mContext;

    /** 备份目录 */
    public static String BACKUP_DIR = Environment.getExternalStorageDirectory() + "/JWS/Backup/";

    public static void runInMainThread(Runnable r) {
        if(r == null || handler == null) return;
        handler.post(r);
    }

    public static Context getContext(){
        return mContext;
    }

    //提供线程中发送一个事件通知的方法
    public static void publishEventFromWorkThread(final String eventKey, final Bundle bundle) {
        Global.runInMainThread(new Runnable() {
            @Override
            public void run() {
                EventBus.getInstance().publishEvent(eventKey, bundle);
            }
        });
    }

}
