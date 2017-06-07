package com.example.rx.appconfig;

import android.util.Log;

import com.example.mylibrary.exception.NullListException;
import com.example.mylibrary.exception.ResponseErrorException;
import com.example.mylibrary.interfaces.Constants;
import com.example.mylibrary.interfaces.OnRetryAction;
import com.example.mylibrary.utils.DialogUtil;
import com.example.rx.utils.Toaster;
import com.kennyc.view.MultiStateView;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class MyHandlerSubscriber<T> implements Observer<T> {
    private MultiStateView stateView;

    public MyHandlerSubscriber(MultiStateView stateView) {
        this.stateView = stateView;
    }

    public MyHandlerSubscriber() {
    }

    @Override
    public void onSubscribe(Disposable d) {
        Log.i("okhttp", "onSubscribe()");
    }

    public OnRetryAction getRetryAction() {
        return null;
    }

    public boolean onOtherError(Throwable e) {
        return false;
    }

    @Override
    public void onError(Throwable e) {

        /**
         * 获取根源 异常
         */

        if (Constants.isDebug) {
            Log.e("ERROR_okhttp", e.toString());
        }

        if (e instanceof ResponseErrorException) {
            if (DialogUtil.getIntance().isShowing()) {
                DialogUtil.getIntance().changeToError(e.getMessage(), new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        OnRetryAction listener = getRetryAction();
                        if (listener != null)
                            listener.retry();
                    }
                });
                return;
            }
        }
        if (stateView != null) {
            if (e instanceof NullListException) {
                stateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
                return;
            }
            stateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
            return;
        }
        if (!onOtherError(e)) {
            DialogUtil.getIntance().dismiss();
            Toaster.show("网络连接不可用，请检查网络");
        }
    }

    @Override
    public void onComplete() {
        if (stateView != null && stateView.getViewState() != MultiStateView.VIEW_STATE_CONTENT)
            stateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        DialogUtil.getIntance().dismiss();
    }
}
