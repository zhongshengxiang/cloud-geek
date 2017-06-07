package com.example.myapplication.activity;

import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.utils.ImageShowUtil;
import com.example.myapplication.widgets.ProgressView;
import com.example.myapplication.widgets.RoadView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by user on 2016/9/18.
 */
public class ThirdActivity extends BaseActivity {


    @BindView(R.id.roadView) RoadView mRoadView;
    @BindView(R.id.button) ImageView mButton;
    @BindView(R.id.pv) ProgressView mPv;

    float progress = 0;

    @Override
    public int getLayoutID() {
        return R.layout.activity_third;
    }

    @Override
    public void initView() {
        findViewById(R.id.yidong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPv.reset();
            }
        });
        Subscription subscribe = Observable.interval(1, TimeUnit.SECONDS)
                .take(100)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(BaseActivity.TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        int l = aLong.intValue();

                        mRoadView.setProgress(l);
                        ObjectAnimator.ofFloat(mButton, "translationX", progress, mRoadView.getWidth() * ((float) l / 100)).start();
                        progress = mRoadView.getWidth() * ((float) l / 100);
                    }
                });
        addSubscription(subscribe);
        ImageShowUtil.showGifImage(thisActivity, mButton, R.drawable.loading);

    }

}
