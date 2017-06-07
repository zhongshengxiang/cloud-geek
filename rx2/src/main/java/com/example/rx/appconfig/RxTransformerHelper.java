package com.example.rx.appconfig;

import android.app.Activity;

import com.blankj.utilcode.utils.NetworkUtils;
import com.example.mylibrary.exception.NoNetWorkConnectException;
import com.example.mylibrary.exception.NullListException;
import com.example.mylibrary.exception.ResponseErrorException;
import com.example.mylibrary.exception.TokenOverdueException;
import com.example.mylibrary.utils.DialogUtil;
import com.example.rx.model.ResponseBean;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class RxTransformerHelper {

    //转换线程
    public static <T> ObservableTransformer<T, T> applySchedulers(final LifecycleProvider<ActivityEvent> context) {
        return new ObservableTransformer<T, T>() {

            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(RxLifecycle.<T, ActivityEvent>bindUntilEvent(context.lifecycle(), ActivityEvent.DESTROY));
            }
        };
    }

    //转换线程
    public static <T> ObservableTransformer<T, T> applyFragment(final LifecycleProvider<FragmentEvent> context) {
        return new ObservableTransformer<T, T>() {

            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(RxLifecycle.<T, FragmentEvent>bindUntilEvent(context.lifecycle(), FragmentEvent.DESTROY));
            }
        };
    }

    public static <T> FlowableTransformer<T, T> networkCheck() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        if (!NetworkUtils.isAvailableByPing())
                            throw new NoNetWorkConnectException();
                    }
                }).subscribeOn(Schedulers.newThread());
            }
        };
    }

    //加载进度doalog
    public static <T> ObservableTransformer<T, T> showDialog(final Activity context) {
        return new ObservableTransformer<T, T>() {

            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        DialogUtil.getIntance().showProgressDialog(context, null);
                    }
                }).subscribeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 校验返回状态码是否正确
     *
     * @param
     * @return
     */
    //校验返回状态码是否正确
    public static <T> ObservableTransformer<ResponseBean<T>, T> checkResponse() {
        return new ObservableTransformer<ResponseBean<T>, T>() {

            @Override
            public ObservableSource<T> apply(Observable<ResponseBean<T>> upstream) {
                return upstream.map(new Function<ResponseBean<T>, T>() {
                    @Override
                    public T apply(ResponseBean<T> tResponseBean) throws Exception {
                        if (tResponseBean.succeeded)
                            return tResponseBean.getResult();
                        else {
                            //通用错误
                            if (tResponseBean.errcode == 401) {
                                throw new TokenOverdueException("The token have expired");
                            }
                            throw new ResponseErrorException(tResponseBean.errmsg);
                        }
                    }
                });
            }
        };
    }

    /**
     * 处理token过期
     *
     * @param <T>
     * @return
     */
//    public static <T> Observable.Transformer<T, T> handleTokenOverdue() {
//        return new Observable.Transformer<T, T>() {
//            @Override
//            public Observable<T> call(Observable<T> tObservable) {
//                return tObservable.retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
//                    @Override
//                    public Observable<?> call(Observable<? extends Throwable> observable) {
//                        return observable.flatMap(new Func1<Throwable, Observable<?>>() {
//                            @Override
//                            public Observable<?> call(Throwable throwable) {
//                                if (throwable instanceof TokenOverdueException) {
//                                    Log.i("okhttp", "handleTokenOverdue()");
//                                    //重新获取token
//                                    return RetrofitFactory.createApi(WebApiInterface.class).getToken()
//                                            .compose(RxTransformerHelper.<ResponseBean<Token>>applySchedulers())
//                                            .compose(RxTransformerHelper.<Token>checkResponse())
//                                            .doOnNext(new Action1<Token>() {
//                                                @Override
//                                                public void call(Token token) {
//                                                }
//                                            });
//                                }
//                                return Observable.error(throwable);
//                            }
//                        });
//                    }
//                });
//            }
//        };
//    }

    /**
     * 检验列表是否为空
     *
     * @param <T>
     * @return
     */
    //检验列表是否为空
    public static <T> ObservableTransformer<List<T>, List<T>> checkListIsNull() {
        return new ObservableTransformer<List<T>, List<T>>() {
            @Override
            public ObservableSource<List<T>> apply(Observable<List<T>> upstream) {
                return upstream.map(new Function<List<T>, List<T>>() {
                    @Override
                    public List<T> apply(List<T> ts) throws Exception {
                        if (ts == null || ts.size() == 0) throw new NullListException();
                        return ts;
                    }
                });
            }
        };
    }

    /**
     * 检验列表是否为空
     *
     * @param <T>
     * @return
     */
    //检验列表是否为空
//    public static <T> Observable.Transformer<T, T> showLoadingMulti(final MultiStateView multiStateView) {
//        return new Observable.Transformer<T, T>() {
//            @Override
//            public Observable<T> call(Observable<T> tObservable) {
//                return tObservable.doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        multiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
//                    }
//                })
//                        .subscribeOn(AndroidSchedulers.mainThread());
//            }
//        };
//    }
}
