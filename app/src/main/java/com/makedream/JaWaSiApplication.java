package com.makedream;

import android.app.Application;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.makedream.jawasi.Global;
import com.makedream.jawasi.model.DaoMaster;
import com.makedream.jawasi.model.DaoSession;
import com.makedream.jawasi.util.BackupUtil;

import org.greenrobot.greendao.database.Database;

import java.io.File;

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

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, BackupUtil.DATABASES_DB);
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        File sdcardFile = Environment.getExternalStorageDirectory();
        System.out.println("sdcardFile-->" + sdcardFile);
        System.out.println("sd read-->" + sdcardFile.canRead());
        System.out.println("sd write-->" + sdcardFile.canWrite());
        File userFile = new File(sdcardFile.toString() + "/Snote/ddj");
        System.out.println("userFile-->" + userFile.toString());
        if (!userFile.exists()) {
            userFile.mkdirs();
        }
        System.out.println("userFile exists-->" + userFile.exists());

        File dir  = new File(Global.BACKUP_DIR);
        if(!dir.exists()) {
            Log.e("ddj", "dir is not exists");
            dir.mkdirs();
        } else {
            Log.e("ddj", "dir is exist");
        }
        Stetho.initializeWithDefaults(this);
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
