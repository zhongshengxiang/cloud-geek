package com.example.rx.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import com.example.rx.R;
import com.example.rx.appconfig.MyHandlerSubscriber;
import com.example.rx.appconfig.RxTransformerHelper;
import com.example.rx.http.Retrofit.RetrofitFactory;
import com.example.rx.model.HomeBean;
import com.example.rx.model.ResponseBean;
import com.example.rx.utils.Toaster;
import com.jakewharton.rxbinding2.widget.RxSearchView;
import com.jakewharton.rxbinding2.widget.SearchViewQueryTextEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

public class MainActivity extends BaseActivity {

    @BindView(R.id.et) EditText mEt;
    @BindView(R.id.add) Button mAdd;
    @BindView(R.id.search_bar) SearchView mSearchBar;


    @Override
    public int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        init();
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitFactory.getmWebApi().searchSth(mEt.getText().toString().trim())
                        .compose(RxTransformerHelper.<ResponseBean<HomeBean>>applySchedulers(MainActivity.this))
                        .compose(RxTransformerHelper.<HomeBean>checkResponse())
                        .map(new Function<HomeBean, List<HomeBean.ItemHomeBean>>() {
                            @Override
                            public List<HomeBean.ItemHomeBean> apply(HomeBean homeBean) throws Exception {
                                return homeBean.items;
                            }
                        }).compose(RxTransformerHelper.<HomeBean.ItemHomeBean>checkListIsNull())
                        .subscribe(new MyHandlerSubscriber<List<HomeBean.ItemHomeBean>>() {
                            @Override
                            public void onNext(List<HomeBean.ItemHomeBean> value) {
                                Toaster.show(value.get(0).p_name);
                            }
                        });
            }
        });
    }

    private void init() {
        RxSearchView.queryTextChangeEvents(mSearchBar)
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())// 对etKey[EditText]的监听操作 需要在主线程操作
                //对用户输入的关键字进行过滤
                .filter(new Predicate<SearchViewQueryTextEvent>() {
                    @Override
                    public boolean test(SearchViewQueryTextEvent event) throws Exception {
                        boolean b = TextUtils.isEmpty(event.queryText().toString().trim());
                        return !b;
                    }
                }).switchMap(new Function<SearchViewQueryTextEvent, Observable<ResponseBean<HomeBean>>>() {
            @Override
            public Observable<ResponseBean<HomeBean>> apply(SearchViewQueryTextEvent event) throws Exception {

                return RetrofitFactory.getmWebApi().searchSth(event.queryText().toString().trim());
            }
        })
                .compose(RxTransformerHelper.<ResponseBean<HomeBean>>applySchedulers(this))
                .compose(RxTransformerHelper.<HomeBean>checkResponse())
                .map(new Function<HomeBean, List<HomeBean.ItemHomeBean>>() {
                    @Override
                    public List<HomeBean.ItemHomeBean> apply(HomeBean homeBean) throws Exception {
                        return homeBean.items;
                    }
                }).compose(RxTransformerHelper.<HomeBean.ItemHomeBean>checkListIsNull())
                .subscribe(new MyHandlerSubscriber<List<HomeBean.ItemHomeBean>>() {
                    @Override
                    public void onNext(List<HomeBean.ItemHomeBean> value) {
                        Toaster.show(value.get(0).p_name);
                    }

                    @Override
                    public boolean onOtherError(Throwable e) {
//                        init();
                        return false;
                    }
                });
    }


    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                Intent intent1 = new Intent(thisActivity, MainActivity2.class);
                startActivity(intent1);
                break;
            case R.id.btn2:
                Intent intent2 = new Intent(thisActivity, OnePlusNLayoutActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn3:
                Intent intent3 = new Intent(thisActivity, RootActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn4:
                Intent intent4 = new Intent(thisActivity, TestActivity.class);
                startActivity(intent4);
                break;
            case R.id.btn5:
                Intent intent5 = new Intent(thisActivity, VLayoutActivity.class);
                startActivity(intent5);
                break;
        }
    }
}
