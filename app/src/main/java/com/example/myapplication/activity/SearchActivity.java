package com.example.myapplication.activity;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.example.myapplication.Model.HomeBean;
import com.example.myapplication.Model.ResponseBean;
import com.example.myapplication.R;
import com.example.myapplication.interfaces.MySafeSubscriber;
import com.example.myapplication.interfaces.OnRetryAction;
import com.example.myapplication.utils.Toaster;
import com.jakewharton.rxbinding.widget.RxSearchView;
import com.jakewharton.rxbinding.widget.SearchViewQueryTextEvent;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.ed) SearchView mEd;
    @BindView(R.id.btn) Button mBtn;

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    private void init() {
        Subscription subscribe = RxSearchView.queryTextChangeEvents(mEd)
                .debounce(400, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())// 对etKey[EditText]的监听操作 需要在主线程操作
                //对用户输入的关键字进行过滤
                .filter(new Func1<SearchViewQueryTextEvent, Boolean>() {
                    @Override
                    public Boolean call(SearchViewQueryTextEvent event) {
                        boolean b = TextUtils.isEmpty(event.queryText().toString().trim());
                        return !b;
                    }
                })
                .switchMap(new Func1<SearchViewQueryTextEvent, Observable<ResponseBean<HomeBean>>>() {
                    @Override
                    public Observable<ResponseBean<HomeBean>> call(SearchViewQueryTextEvent event) {
                        return service.searchSth(event.queryText().toString().trim());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySafeSubscriber<ResponseBean<HomeBean>>() {
                    @Override
                    public OnRetryAction getRetryAction() {
                        return null;
                    }

                    @Override
                    public boolean onOtherError(Throwable e) {
                        return false;
                    }

                    @Override
                    public void onNext(ResponseBean<HomeBean> bean) {
                        Toaster.show(bean.getResult().items.get(0).p_name);
                    }
                });
        addSubscription(subscribe);
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_search;
    }


    @Override
    public void initView() {

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                service.login(1)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new MySafeSubscriber<Teacher>() {
//                    @Override
//                    public OnRetryAction getRetryAction() {
//                        return null;
//                    }
//
//                    @Override
//                    public void onNext(Teacher teacher) {
//                        Toaster.show(teacher.toString());
//                    }
//
//                    @Override
//                    public void onOtherError(Throwable e) {
//
//                    }
//                });
                Snackbar.make(mEd, "SnackbarTest", Snackbar.LENGTH_LONG).show();
            }


        });
    }
}