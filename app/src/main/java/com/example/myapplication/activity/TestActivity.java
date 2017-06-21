package com.example.myapplication.activity;

import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.example.myapplication.R.id.recyclerView;


/**
 * Created by Administrator on 2017/3/10.
 */

public class TestActivity extends BaseActivity {
    @BindView(recyclerView) RecyclerView mRecyclerView;

    @Override
    public int getLayoutID() {
        return R.layout.activity_test;
    }

    List<String> data;

    @Override
    public void initView() {

//        mBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Geocoder geocoder = new Geocoder(Utils.getContext(), Locale.getDefault());
//                try {
//                    List<Address> addresses = geocoder.getFromLocation(39.994827, 116.457092, 1);
//                    if (addresses.size() > 0) Toaster.show(addresses.get(0).toString());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        data = new ArrayList<>(200);
        for (int i = 0; i < 200; i++) {
            data.add("条目" + i);
        }
        mRecyclerView.setLayoutManager(new VirtualLayoutManager(this));
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 5);
//        mRecyclerView.addItemDecoration(new TitleItemDecoration(this, data));
//        final StickyLayoutHelper helper = new StickyLayoutHelper(true);
        DelegateAdapter.Adapter adapter = new DelegateAdapter.Adapter<MyHolder2>() {
            @Override
            public LayoutHelper onCreateLayoutHelper() {
                return new StickyLayoutHelper(true);
            }

            @Override
            public MyHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
                View inflate = LayoutInflater.from(thisActivity).inflate(R.layout.item, parent, false);
                return new MyHolder2(inflate);
            }

            @Override
            public void onBindViewHolder(MyHolder2 holder, int position) {
                holder.tv.setText(data.get(position));
//                        .addOnClickListener(R.id.tv);
            }

            @Override
            public int getItemCount() {
                return data.size();
            }


            //            @Override
//            protected void convert(BaseViewHolder helper, String item) {
//                helper.setText(R.id.tv, "第" + helper.getLayoutPosition())
//                        .addOnClickListener(R.id.tv);
//            }
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


    }

    class MyHolder2 extends RecyclerView.ViewHolder {
        public TextView tv;

        public MyHolder2(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }

}
