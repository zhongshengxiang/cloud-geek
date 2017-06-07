package com.example.myapplication.widgets;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.myapplication.R;
import com.example.myapplication.utils.ImageShowUtil;
import com.example.mylibrary.exception.NoNetWorkConnectException;
import com.example.mylibrary.exception.NullListException;

/**
 * Created by Administrator on 2016/12/21.
 */

public class ViewChangeDevice {
    public static void changeToError(BaseQuickAdapter adapter, Activity activity, Throwable e) {
        if (e instanceof NoNetWorkConnectException) {
            changeToNetError(adapter, activity);
        } else if (e instanceof NullListException) {
            changeToEmpty(adapter, activity);
        } else {
            changeToResponseError(adapter, activity);
        }
    }

    public static void changeToEmpty(BaseQuickAdapter adapter, Activity activity) {
        View emptyView = activity.getLayoutInflater().inflate(R.layout.empty_view, new FrameLayout(activity), false);
        adapter.setEmptyView(emptyView);
    }


    //网络错误
    public static void changeToNetError(BaseQuickAdapter adapter, Activity activity) {
        View errorView = activity.getLayoutInflater().inflate(R.layout.error_net_view, new FrameLayout(activity), false);
        adapter.setEmptyView(errorView);
    }

    //请求错误
    public static void changeToResponseError(BaseQuickAdapter adapter, Activity activity) {
        View errorView = activity.getLayoutInflater().inflate(R.layout.error_request_view, new FrameLayout(activity), false);
        adapter.setEmptyView(errorView);
    }


    public static void changeToLoading(BaseQuickAdapter adapter, Activity activity) {
        View loadingView = activity.getLayoutInflater().inflate(R.layout.loading_view, new FrameLayout(activity), false);
        ImageShowUtil.showGifImage(activity, (ImageView) loadingView.findViewById(R.id.img), R.drawable.loading);
        adapter.setEmptyView(loadingView);
    }

}
