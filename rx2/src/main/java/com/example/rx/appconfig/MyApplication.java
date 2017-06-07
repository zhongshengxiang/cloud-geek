package com.example.rx.appconfig;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;

import com.blankj.utilcode.utils.Utils;
import com.liulishuo.filedownloader.FileDownloader;
import com.squareup.leakcanary.LeakCanary;

import cn.finalteam.galleryfinal.FunctionConfig;


public class MyApplication extends Application implements PreferenceManager.OnActivityDestroyListener {

    public static Context appContext;
    int count;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        FileDownloader.init(appContext);
        Utils.init(this);
//        init();
    }

    public static Context getIntance() {
        return appContext;
    }

    private void init() {
//        initMultiPhotoPicker();
    }


//    private void initMultiPhotoPicker() {
//        //配置主题
//        //ThemeConfig.CYAN
//        ThemeConfig theme = new ThemeConfig.Builder()
//                .setTitleBarBgColor(getResources().getColor(R.color.color))
//                .setFabNornalColor(getResources().getColor(R.color.color))
//                .setFabPressedColor(getResources().getColor(R.color.color_press))
//                .build();
//
//        //配置imageloader
//        GlideImageLoader imageloader = new GlideImageLoader();
//        //设置核心配置信息
//        CoreConfig coreConfig = new CoreConfig.Builder(appContext, imageloader, theme)
//                .setFunctionConfig(getGalleryFuncionConfig().build())
//                .build();
//        GalleryFinal.init(coreConfig);
//    }

    public static FunctionConfig.Builder getGalleryFuncionConfig() {
        //配置功能
        FunctionConfig.Builder builder = new FunctionConfig.Builder()
                .setEnableEdit(true)//编辑
                .setEnableCrop(true)//裁剪
                .setEnableRotate(true)//旋转
                .setCropSquare(true)//正方形
                .setEnablePreview(true)//预览
                .setForceCrop(true)//强制裁剪
                .setCropReplaceSource(false);// 替换
        return builder;
    }

    @Override
    public void onActivityDestroy() {
    }

}
