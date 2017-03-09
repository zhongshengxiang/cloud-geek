package com.example.myapplication.appconfig;

import android.content.Context;
import android.preference.PreferenceManager;

import com.liulishuo.filedownloader.FileDownloader;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.litepal.LitePalApplication;

import cn.finalteam.galleryfinal.FunctionConfig;

/**
 * Created by Vinctor on 16/4/18.
 */
public class MyApplication extends LitePalApplication implements PreferenceManager.OnActivityDestroyListener{

    public static Context appContext;
    private static RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        refWatcher = LeakCanary.install(this);
        FileDownloader.init(this);
        init();
    }

    public static Context getIntance() {
        return appContext;
    }

    public static RefWatcher getRefWatcher() {
        return refWatcher;
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
