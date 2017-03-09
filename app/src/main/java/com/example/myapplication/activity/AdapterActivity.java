package com.example.myapplication.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myapplication.Model.ResponseBean;
import com.example.myapplication.Model.Student;
import com.example.myapplication.Model.ZhuangbiImage;
import com.example.myapplication.R;
import com.example.myapplication.appconfig.RxTransformerHelper;
import com.example.myapplication.interfaces.MySafeSubscriber;
import com.example.myapplication.interfaces.OnRetryAction;
import com.example.myapplication.utils.Toaster;

import butterknife.BindView;
import butterknife.OnClick;
import cn.vinctor.loadingviewfinal.RecyclerViewFinal;
import rx.Subscription;

/**
 * Created by Administrator on 2016/12/20.
 */

public class AdapterActivity extends BaseActivity {
    @BindView(R.id.recyclerView) RecyclerViewFinal mRecyclerView;
    @BindView(R.id.btn) Button mButton;
    private BaseQuickAdapter<Student> adapter;
    int mCurrentCounter;

    @Override
    public int getLayoutID() {
        return R.layout.activity_forth;
    }

    @Override
    public void initView() {

        adapter = new BaseQuickAdapter<Student>(R.layout.item_student, null) {
            View.OnClickListener ocl;

            @Override
            protected void convert(BaseViewHolder holder, final Student student) {
                ocl = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.name:
                                Toaster.show(student.name);
                                break;
                            case R.id.age:
                                Toaster.show(student.age);
                                break;
                        }
                    }
                };
                holder.setText(R.id.name, student.name)
                        .setText(R.id.age, student.age)
                        .setOnClickListener(R.id.name, ocl)
                        .setOnClickListener(R.id.age, ocl);
            }
        };

        mRecyclerView.setLayoutManager(new LinearLayoutManager(thisActivity));
        mRecyclerView.setAdapter(adapter);

    }

    @OnClick({R.id.request, R.id.empty, R.id.loading, R.id.net})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.request:
                break;
            case R.id.empty:
                Subscription subscribe = service.register("itcasts","123").compose(RxTransformerHelper.<ResponseBean<ZhuangbiImage>>applySchedulers())
                        .compose(RxTransformerHelper.<ZhuangbiImage>checkResponse())
                        .compose(RxTransformerHelper.<ZhuangbiImage>handleTokenOverdue())
                        .subscribe(new MySafeSubscriber<ZhuangbiImage>() {
                            @Override
                            public OnRetryAction getRetryAction() {
                                return null;
                            }

                            @Override
                            public boolean onOtherError(Throwable e) {
                                return super.onOtherError(e);
                            }
                        });
                addSubscription(subscribe);
                break;
            case R.id.loading:
                WebViewActivity.startActivity(thisActivity,"测试","http://www.baidu.com");
                break;
            case R.id.net:
                break;
        }
    }
}
