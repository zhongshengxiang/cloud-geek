package com.example.myapplication.appconfig;

import com.example.myapplication.widgets.MyFloatView;

/**
 * Created by Administrator on 2017/3/23.
 */

public class SDKManager {

    private MyFloatView mFloatView;
    private static SDKManager mSDKManager;

    private SDKManager() {
    }

    public static SDKManager init() {
        if (mSDKManager == null) {
            mSDKManager = new SDKManager();
        }
        return mSDKManager;
    }


    public void showFloat() {
        if (mFloatView == null) {
            mFloatView = new MyFloatView(MyApplication.appContext);
        } else {
            mFloatView.showFloat();
        }
    }

    public void hideFloat() {
        if (mFloatView != null) mFloatView.hidFloat();
    }

}
