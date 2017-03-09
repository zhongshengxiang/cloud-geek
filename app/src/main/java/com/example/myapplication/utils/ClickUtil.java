package com.example.myapplication.utils;

import android.util.SparseArray;
import android.view.View;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class ClickUtil {
    private static long lastClickTime;
    private static long throwClickTime = 1000;
    public static final int KEY = 2;
    static SparseArray<Long> map = new SparseArray<>();

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (map.indexOfKey(KEY) < 0) {
            map.put(KEY, time);
            return false;
        }

        lastClickTime = map.get(KEY);
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < throwClickTime) {
            return true;
        }
        map.put(KEY, time);
        return false;
    }

    public void throttleFirst(final View view, final View.OnClickListener listener) {
        RxView.clicks(view).throttleFirst(KEY, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (listener != null)
                            listener.onClick(view);
                    }
                });
    }
}
