package com.example.myapplication.appconfig;

import android.content.Context;
import android.os.Environment;


import java.io.File;

/**
 * 本应用数据清除管理器
 * Created by user on 2016/7/21.
 */
public class DataCleanManager {
    public static DataCleanManager mManager;

    private DataCleanManager() {
    }

    public static DataCleanManager getInstance() {
        if (mManager == null) {
            mManager = new DataCleanManager();
            return mManager;
        }
        return mManager;
    }
    /**
     * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * * @param context
     */
    public void cleanInternalCache(Context context) {
        deleteFolder(context.getCacheDir());
    }

    /**
     * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * * @param context
     */
    public void cleanDatabases(Context context) {
        deleteFolder(new File("/data/data/" + context.getPackageName() + "/databases"));
    }

    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * * @param
     * context
     */
    public void cleanSharedPreference(Context context) {
//        deleteFolder(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));

    }

    /**
     * 按名字清除本应用数据库 * * @param context * @param dbName
     */
    public void cleanDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * 清除/data/data/com.xxx.xxx/files下的内容 * * @param context
     */
    public void cleanFiles(Context context) {
        deleteFolder(context.getFilesDir());
    }

    /**
     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache) * * @param
     * context
     */
    public void cleanExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            deleteFolder(context.getExternalCacheDir());
        }
    }


    /**
     * 清除本应用所有的数据 * * @param context * @param filepath为扩展参数
     */
    public void cleanApplicationData(Context context, String path[]) {
        cleanInternalCache(context);
        cleanExternalCache(context);
//        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanFiles(context);
        if (path != null) {
            for (int i = 0; i < path.length; i++) {
                deleteFolder(new File(path[i]));
            }
        }
    }

    /**
     * 获取SD卡的路径
     *
     * @return
     */
    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }


    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *
     * @param file 要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    public boolean deleteFolder(File file) {
        // 判断目录或文件是否存在
        synchronized (DataCleanManager.class) {
            if (!file.exists()) {  // 不存在返回 false
                return false;
            } else {
                // 判断是否为文件
                if (file.isFile()) {  // 为文件时调用删除文件方法
//                    return FileUtils.deleteFile(file);
                } else {  // 为目录时调用删除目录方法
//                    return FileUtils.deleteDir(file);
                }
            }
            return true;
        }

    }
}
