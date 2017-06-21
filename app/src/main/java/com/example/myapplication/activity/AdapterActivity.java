package com.example.myapplication.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myapplication.Model.MySection;
import com.example.myapplication.Model.ResponseBean;
import com.example.myapplication.Model.Student;
import com.example.myapplication.Model.ZhuangbiImage;
import com.example.myapplication.R;
import com.example.myapplication.appconfig.MyHolder;
import com.example.myapplication.appconfig.RxTransformerHelper;
import com.example.myapplication.interfaces.MySafeSubscriber;
import com.example.myapplication.interfaces.OnRetryAction;
import com.example.myapplication.utils.DialogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscription;

import static com.example.myapplication.R.layout.item;

/**
 * Created by Administrator on 2016/12/20.
 */

public class AdapterActivity extends BaseActivity {
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.btn) Button mButton;


    @Override
    public int getLayoutID() {
        return R.layout.activity_forth;
    }

    @Override
    public void initView() {
        List<MySection> list = new ArrayList<>();
        Student student;
        list.add(new MySection(true, "星期一"));
        for (int i = 0; i < 5; i++) {
            student = new Student();
            student.name = "条目" + i;
            list.add(new MySection(student));
        }
        list.add(new MySection(true, "星期二"));
        for (int i = 0; i < 2; i++) {
            student = new Student();
            student.name = "条目" + i;
            list.add(new MySection(student));
        }
        list.add(new MySection(true, "星期五"));
        for (int i = 0; i < 5; i++) {
            student = new Student();
            student.name = "条目" + i;
            list.add(new MySection(student));
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(thisActivity));
        BaseSectionQuickAdapter adapter = new BaseSectionQuickAdapter<MySection, MyHolder>(R.layout.item_student, item, list) {
            @Override
            protected void convert(MyHolder helper, MySection item) {
                helper.setText(R.id.name, item.t.name);
            }

            @Override
            protected void convertHead(BaseViewHolder helper, MySection item) {
                helper.setText(R.id.tv, item.header);
            }
        };
        mRecyclerView.setAdapter(adapter);

    }


    @OnClick({R.id.request, R.id.empty, R.id.loading, R.id.net})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.request:
                break;
            case R.id.empty:
                haha();
                break;
            case R.id.loading:
                WebViewActivity.startActivity(thisActivity, "测试", "http://114.55.27.208:8555/PlatformInfo/Index");
                break;
            case R.id.net:
                break;
        }
    }

    private void haha() {
        Subscription subscribe = service.register("itcasts", "123")
                .compose(RxTransformerHelper.<ResponseBean<ZhuangbiImage>>applySchedulers(this))
                .compose(RxTransformerHelper.<ZhuangbiImage>checkResponse())
                .subscribe(new MySafeSubscriber<ZhuangbiImage>() {
                    @Override
                    public OnRetryAction getRetryAction() {
                        return new OnRetryAction() {
                            @Override
                            public void retry() {
                                haha();
                            }
                        };
                    }

                    @Override
                    public void onStart() {
                        DialogUtil.getIntance().showProgressDialog(thisActivity,null);
                    }

                    @Override
                    public void onNext(ZhuangbiImage zhuangbiImage) {

                    }

                    @Override
                    public boolean onOtherError(Throwable e) {
                        return super.onOtherError(e);
                    }
                });
        addSubscription(subscribe);
    }
}
