package com.example.myapplication.appconfig;

import android.app.Activity;
import android.util.Log;

import com.example.myapplication.Model.ResponseBean;
import com.example.myapplication.Model.Token;
import com.example.myapplication.exception.NoNetWorkConnectException;
import com.example.myapplication.exception.NullListException;
import com.example.myapplication.exception.ResponseErrorException;
import com.example.myapplication.exception.TokenOverdueException;
import com.example.myapplication.http.MyService;
import com.example.myapplication.http.Retrofit.RetrofitFactory;
import com.example.myapplication.utils.DialogUtil;
import com.example.myapplication.utils.NetworkUtils;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxTransformerHelper {
    //转换线程
    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> Observable.Transformer<T, T> networkCheck() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (!NetworkUtils.isOnline()) throw new NoNetWorkConnectException();
                    }
                })
                        .subscribeOn(Schedulers.newThread());
            }
        };
    }

    //加载进度doalog
    public static <T> Observable.Transformer<T, T> showDIalog(final Activity context) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        DialogUtil.getIntance().showProgressDialog(context, null);
                    }
                })
                        .subscribeOn(AndroidSchedulers.mainThread());
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
    public static <T> Observable.Transformer<ResponseBean<T>, T> checkResponse() {
        return new Observable.Transformer<ResponseBean<T>, T>() {
            @Override
            public Observable<T> call(Observable<ResponseBean<T>> responseBeanObservable) {
                return responseBeanObservable.map(new Func1<ResponseBean<T>, T>() {
                    @Override
                    public T call(ResponseBean<T> tResponseBean) {
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
    public static <T> Observable.Transformer<T, T> handleTokenOverdue() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Throwable> observable) {
                        return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                            @Override
                            public Observable<?> call(Throwable throwable) {
                                if (throwable instanceof TokenOverdueException) {
                                    Log.i("okhttp","handleTokenOverdue()");
                                    //重新获取token
                                    return RetrofitFactory.createApi(MyService.class).getToken()
                                            .compose(RxTransformerHelper.<ResponseBean<Token>>applySchedulers())
                                            .compose(RxTransformerHelper.<Token>checkResponse())
                                            .doOnNext(new Action1<Token>() {
                                                @Override
                                                public void call(Token token) {
                                                }
                                            });
                                }
                                return Observable.error(throwable);
                            }
                        });
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
    public static <T> Observable.Transformer<List<T>, List<T>> checkListIsNull() {
        return new Observable.Transformer<List<T>, List<T>>() {
            @Override
            public Observable<List<T>> call(Observable<List<T>> listObservable) {
                return listObservable.map(new Func1<List<T>, List<T>>() {
                    @Override
                    public List<T> call(List<T> ts) {
                        if (ts == null) throw new NullListException();
                        if (ts.size() == 0) throw new NullListException();
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
