package com.makedream;

import android.app.Application;
import android.os.Handler;

import com.makedream.jawasi.Global;
import com.makedream.jawasi.model.DaoMaster;
import com.makedream.jawasi.model.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Administrator on 2016/7/1.
 */

public class JaWaSiApplication extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        Global.handler = new Handler();
        Global.mContext = this;

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"exercise-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
