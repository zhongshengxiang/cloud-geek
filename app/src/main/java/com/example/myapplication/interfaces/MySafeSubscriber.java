package com.example.myapplication.interfaces;

import android.util.Log;

import com.example.myapplication.exception.NullListException;
import com.example.myapplication.exception.ResponseErrorException;
import com.example.myapplication.utils.DialogUtil;
import com.example.myapplication.utils.Toaster;
import com.kennyc.view.MultiStateView;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;

public class MySafeSubscriber<T> extends Subscriber<T> {
    private MultiStateView stateView;

    public MySafeSubscriber(MultiStateView stateView) {
        this.stateView = stateView;
    }

    public MySafeSubscriber() {
    }

    @Override
    public void onCompleted() {
        if (stateView != null && stateView.getViewState() != MultiStateView.VIEW_STATE_CONTENT)
            stateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        DialogUtil.getIntance().dismiss();
    }

    @Override
    public void onNext(T t) {

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
                DialogUtil.getIntance().changeToWarn("提示", e.getMessage(), "重试", new SweetAlertDialog.OnSweetClickListener() {
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
}
