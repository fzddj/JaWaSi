package com.makedream.jawasi.util;

import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.ddj.commonkit.FileUtil;
import com.makedream.jawasi.Global;
import com.makedream.jawasi.R;
import com.makedream.util.StringUtil;
import com.makedream.util.TelephoneUtil;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by dingdj on 2016/8/29.
 */

public class BackupUtil {

    public static final String DATA_DIR = "/data/";
    public static final String DATABASE_DIR = "/databases/";
    private static final String SHARED_PREFS_DIR = "/shared_prefs/";
    public static final String DATABASES_DB = "exercise-db";

    Context mContext;

    public BackupUtil(Context context) {
        this.mContext = context;
    }

    public void backUp() {
        new ExportSettingsTask().execute();
    }

    public void restore() {
        new ImportSettingsTask().execute();
    }

    public class ExportSettingsTask extends AsyncTask<Void, Void, String> {
        private final ProgressDialog dialog = new ProgressDialog(mContext);

        // can use UI thread here
        protected void onPreExecute() {
            this.dialog.setMessage(mContext.getResources().getString(R.string.settings_backup_ing));
            this.dialog.show();
        }

        // automatically done on worker thread (separate from UI thread)
        protected String doInBackground(final Void... args) {
            return backupData(mContext, Global.BACKUP_DIR);
        }

        // can use UI thread here
        protected void onPostExecute(final String msg) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    }


    public class ImportSettingsTask extends AsyncTask<Void, Void, String> {
        private final ProgressDialog dialog = new ProgressDialog(mContext);

        protected void onPreExecute() {
            this.dialog.setMessage(mContext.getResources().getString(R.string.settings_backup_restore_ing));
            this.dialog.show();
        }

        // could pass the params used here in AsyncTask<String, Void, String> - but not being re-used
        protected String doInBackground(final Void... args) {
            return restoreData(mContext, Global.BACKUP_DIR);
        }

        protected void onPostExecute(final String msg) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    public static String backupData(Context mContext, String backupPath) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || StringUtil.isAnyEmpty(backupPath)) {
            return mContext.getResources().getString(R.string.settings_backup_sdcard_unavailable);
        }
        FileUtil.delAllFile(backupPath);
        String namespace = mContext.getPackageName();
        String versionName = TelephoneUtil.getVersionName(mContext);
        final String baseDir = Environment.getDataDirectory() + DATA_DIR + namespace;

        try {
            if (!new File(baseDir + SHARED_PREFS_DIR).exists()) {
                FileUtil.copyFolder("/dbdata/databases/" + namespace + SHARED_PREFS_DIR, backupPath);
            } else {
                FileUtil.copyFolder(baseDir + SHARED_PREFS_DIR, backupPath);
            }

            FileUtil.copyFolder(baseDir + DATABASE_DIR, backupPath);

            FileUtil.renameFile(backupPath + "application.xml", backupPath + "application.xml" + "-" + versionName);
            return mContext.getResources().getString(R.string.settings_backup_success);
        } catch (Exception e) {
            e.printStackTrace();
            return mContext.getResources().getString(R.string.settings_backup_failed);
        }
    }


    /**
     * 指定一个还原路径
     *
     * @param mContext
     * @param restorePath
     * @return
     * @author linqiang(866116)
     * @Since 2013-7-23
     * @retrun String
     **/
    public static String restoreData(Context mContext, String restorePath) {
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return mContext.getResources().getString(R.string.settings_backup_sdcard_unavailable);
        }

        String namespace = mContext.getPackageName();
        File backup = new File(restorePath);

        String[] files = backup.list();

        if (files == null || files.length < 2) {
            return mContext.getResources().getString(R.string.settings_backup_not_found);
        }

        String baseDir = Environment.getDataDirectory() + DATA_DIR + namespace;
        String dbDir = Environment.getDataDirectory() + DATA_DIR + namespace + DATABASE_DIR;
        String sharedPrefDir = Environment.getDataDirectory() + DATA_DIR + namespace + SHARED_PREFS_DIR;
        if (!new File(sharedPrefDir).exists()) {
            sharedPrefDir = "/dbdata/databases/" + namespace + SHARED_PREFS_DIR;
        }

        File dbFile = new File(baseDir + "/"+DATABASES_DB);
        if (dbFile.exists()) {
            dbFile.delete();
        }

        try {
            String destPath;
            for (String file : files) {
                final String sorucePath = Global.BACKUP_DIR + file;
                destPath = null;
                if (file.contains(".xml")) {
                    if (file.contains("application.xml")) {
                        destPath = sharedPrefDir + "application.xml";
                    } else {
                        destPath = sharedPrefDir + file;
                    }
                }  else if (file.contains(".jpg")) {
                    //还原壁纸
                    Bitmap bitmap = BitmapFactory.decodeFile(sorucePath);
                    WallpaperManager.getInstance(mContext).setBitmap(bitmap);
                    continue;
                }else  {
                    destPath = dbDir + file;
                }
                if (destPath == null)
                    continue;
                // Log.d(Global.TAG, "from path->"+sorucePath);
                // Log.d(Global.TAG, "to path->"+destPath);
                FileUtil.copy(sorucePath, destPath);
            }

            return mContext.getResources().getString(R.string.settings_backup_restore_success);
        } catch (Exception e) {
            return mContext.getResources().getString(R.string.settings_backup_restore_failed);
        }
    }


    private static FilenameFilter filenameFilter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String filename) {
            if (filename.contains("resetflag")) {
                return false;
            }
            return true;
        }
    };

}
