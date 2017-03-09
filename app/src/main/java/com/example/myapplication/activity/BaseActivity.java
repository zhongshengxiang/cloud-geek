package com.example.myapplication.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.myapplication.http.MyService;
import com.example.myapplication.http.Retrofit.RetrofitFactory;
import com.example.myapplication.interfaces.Constants;
import com.example.myapplication.utils.DialogUtil;
import com.example.myapplication.utils.Toaster;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseActivity extends RxAppCompatActivity {
    protected CompositeSubscription compositeSubscription;
    protected Activity thisActivity;
    public static final String TAG = "zhongshengxiang";
    public MyService service;
    int sysTemBarColor;
    Unbinder mBind;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (Constants.isDebug){
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDialog() ////打印logcat，当然也可以定位到dropbox，通过文件保存相应的log
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll()
                    .penaltyLog()
                    .build());
        }
        super.onCreate(savedInstanceState);
        setContentView();
        thisActivity = this;
        mBind = ButterKnife.bind(this);
//        sysTemBarColor = getSystemBarColor();
        initService();
//        initTitleBackBar();
        initView();
//        initTint();
//        initReloginEvent();
    }

    protected void setContentView() {
        setContentView(getLayoutID());
    }


    public static void handerNormalException(String error) {
        Toaster.show(Constants.netError);
    }

//    int getSystemBarColor() {
//        return getResources().getColor(R.color.color);
//    }

    //    void setTitleRight(String txt, View.OnClickListener listener) {
//        try {
//            TextView right = (TextView) findViewById(R.id.title_right);
//            right.setVisibility(View.VISIBLE);
//            right.setText(txt);
//            if (listener != null)
//                right.setOnClickListener(listener);
//        } catch (Exception e) {
//        }
//    }
    protected void log(String msg) {
        Log.i(TAG, msg);
    }

    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent start = new Intent(thisActivity, cls);
        start.putExtras(bundle);
        startActivity(start);
    }

    ;
//    protected void initTitleBackBar() {
//        ImageView back;
//        try {
//            back = (ImageView) findViewById(R.id.back_prev);
//            back.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    thisActivity.finish();
//                }
//            });
//        } catch (Exception e) {
//        }
//    }

//    protected void initTint() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(true);
//        }
//        // 创建状态栏的管理实例
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        // 激活状态栏设置
//        tintManager.setStatusBarTintEnabled(true);
//        // 激活导航栏设置
//        tintManager.setNavigationBarTintEnabled(true);
//        // 设置一个颜色给系统栏
//        tintManager.setTintColor(sysTemBarColor);
//        // 设置一个样式背景给导航栏
//        //tintManager.setNavigationBarTintResource(R.color.color);
//        // 设置一个状态栏资源
//        //tintManager.setStatusBarTintDrawable(MyDrawable);
//    }

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

    protected void initService() {
        service = RetrofitFactory.createCacheApi(MyService.class);
    }

    public abstract int getLayoutID();

    public abstract void initView();

//    void setTitleRightImg(int ResID, View.OnClickListener listener) {
//        try {
//            final ImageView right = (ImageView) findViewById(R.id.right_img);
//            right.setVisibility(View.VISIBLE);
//            right.setImageResource(ResID);
//            right.setOnClickListener(listener);
//        } catch (Exception e) {
//        }
//    }

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

//    public static void handerNormalException(Throwable e) {
//        if (DialogUtil.getIntance().isShowing()) {
//            DialogUtil.getIntance().dismiss();
//        }
//        Toaster.show(netErrorString);
//    }

    String UPDATETIME = "updatetime";


    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
