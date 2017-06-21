package com.example.myapplication.appconfig;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by Administrator on 2017/6/8.
 */

public class MyHolder extends BaseViewHolder {
    public MyHolder(View view) {
        super(view);
        AutoUtils.auto(itemView);
    }
}
