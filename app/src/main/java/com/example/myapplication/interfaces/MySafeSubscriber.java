package com.example.myapplication.interfaces;

import android.util.Log;

import com.example.myapplication.exception.ResponseErrorException;
import com.example.myapplication.utils.DialogUtil;
import com.example.myapplication.utils.Toaster;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;

public abstract class MySafeSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {
        DialogUtil.getIntance().dismiss();
    }

    @Override
    public void onNext(T t) {

    }

    public abstract OnRetryAction getRetryAction();

    public boolean onOtherError(Throwable e) {
        DialogUtil.getIntance().dismiss();
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
                DialogUtil.getIntance().changeToWarn("提示",e.getMessage(),"重试", new SweetAlertDialog.OnSweetClickListener() {
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
        if (!onOtherError(e)) {
            Toaster.show("网络连接不可用，请检查网络");
        }
    }
}
