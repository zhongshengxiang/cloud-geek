package com.example.myapplication.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.myapplication.R;
import com.example.myapplication.appconfig.SystemBarTintManager;
import com.example.myapplication.http.MyService;
import com.example.myapplication.http.Retrofit.RetrofitFactory;
import com.example.myapplication.utils.DialogUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseActivity extends RxAppCompatActivity {
    protected CompositeSubscription compositeSubscription;
    protected Activity thisActivity;
    public static final String TAG = "okhttp";
    public MyService service;
    Unbinder mBind;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStatusBar();
        setContentView();
        thisActivity = this;
        mBind = ButterKnife.bind(this);
        initService();
        initView();
    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        // 创建状态栏的管理实例
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // 激活状态栏设置
        tintManager.setStatusBarTintEnabled(true);
        // 激活导航栏设置
        tintManager.setNavigationBarTintEnabled(true);
        // 设置一个颜色给系统栏
        tintManager.setTintColor(ContextCompat.getColor(this, R.color.color));
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    protected void setContentView() {
        setContentView(getLayoutID());
    }


    protected void log(String msg) {
        Log.i(TAG, msg);
    }

    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent start = new Intent(thisActivity, cls);
        if (bundle != null) start.putExtras(bundle);
        startActivity(start);
    }


    protected void initService() {
        service = RetrofitFactory.createCacheApi(MyService.class);
    }

    public abstract int getLayoutID();

    public abstract void initView();


    public void addSubscription(Subscription subscription) {
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(subscription);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        DialogUtil.getIntance().dismiss();
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }
        mBind.unbind();
        thisActivity = null;
    }

//    @Override
//    public void onTrimMemory(int level) {
//        if (level == TRIM_MEMORY_UI_HIDDEN) {
//            SDKManager.init().hideFloat();
//        }
//    }
}
