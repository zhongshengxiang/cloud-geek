package com.example.myapplication.activity;

import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.myapplication.R;
import com.example.myapplication.utils.Toaster;
import com.example.myapplication.widgets.TitleItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;


/**
 * Created by Administrator on 2017/3/10.
 */

public class TestActivity extends BaseActivity {
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.btn) Button mBtn;

    @Override
    public int getLayoutID() {
        return R.layout.activity_test;
    }

    List<String> data;

    @Override
    public void initView() {

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Geocoder geocoder = new Geocoder(Utils.getContext(), Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(39.994827,116.457092, 1);
                    if (addresses.size() > 0)  Toaster.show(addresses.get(0).toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        data = new ArrayList<>(200);
        for (int i = 0; i < 200; i++) {
            data.add("条目" + i);
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new TitleItemDecoration(this, data));
        BaseQuickAdapter adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item, data) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.tv, "第" + helper.getLayoutPosition())
                        .addOnClickListener(R.id.tv);
            }
        };
        mRecyclerView.setAdapter(adapter);
        registerComponentCallbacks(new ComponentCallbacks2() {
            @Override
            public void onTrimMemory(int level) {
                if (level == TRIM_MEMORY_UI_HIDDEN) Log.i("okhttp", "onTrimMemory");
            }

            @Override
            public void onConfigurationChanged(Configuration newConfig) {
                Log.i("okhttp", "onConfigurationChanged");
            }

            @Override
            public void onLowMemory() {
                Log.i("okhttp", "onLowMemory");
            }
        });
        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv:
                        Toaster.show(((TextView) view).getText().toString());
                        break;
                    case R.id.tv1:
                        Toaster.show(((TextView) view).getText().toString());
                        break;
                    default:

                        break;
                }
            }
        });
    }


}
