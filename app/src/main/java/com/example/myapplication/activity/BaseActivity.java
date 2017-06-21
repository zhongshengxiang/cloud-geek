package com.example.myapplication.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.myapplication.R;
import com.example.myapplication.appconfig.SystemBarTintManager;
import com.example.myapplication.http.MyService;
import com.example.myapplication.http.Retrofit.RetrofitFactory;
import com.example.myapplication.utils.DialogUtil;
import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.Subscription;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseActivity extends AppCompatActivity implements LifecycleProvider<ActivityEvent> {
    protected CompositeSubscription compositeSubscription;
    protected Activity thisActivity;
    public static final String TAG = "okhttp";
    public MyService service;
    Unbinder mBind;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(ActivityEvent.CREATE);
        initStatusBar();
        setContentView();
        thisActivity = this;
        mBind = ButterKnife.bind(thisActivity);
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
        service = RetrofitFactory.createApi(MyService.class);
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
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();
        DialogUtil.getIntance().dismiss();
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }
        mBind.unbind();
        thisActivity = null;
    }

//    @Override
//    public View onCreateView(String name, Context context, AttributeSet attrs) {
//        View view = null;
//        if (name.equals("FrameLayout")) {
//            view = new AutoFrameLayout(context, attrs);
//        }
//
//        if (name.equals("LinearLayout")) {
//            view = new AutoLinearLayout(context, attrs);
//        }
//
//        if (name.equals("RelativeLayout")) {
//            view = new AutoRelativeLayout(context, attrs);
//        }
//
//        if (view != null) return view;
//
//        return super.onCreateView(name, context, attrs);
//    }

    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    @Override
    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        lifecycleSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    @CallSuper
    protected void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @Override
    @CallSuper
    protected void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

//    @Override
//    public void onTrimMemory(int level) {
//        if (level == TRIM_MEMORY_UI_HIDDEN) {
//            SDKManager.init().hideFloat();
//        }
//    }
}
