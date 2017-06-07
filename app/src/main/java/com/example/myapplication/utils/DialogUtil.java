package com.example.myapplication.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.TextUtils;

import com.example.myapplication.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Vinctor on 2016/5/12.
 */
public class DialogUtil {

    private boolean isHasCancle = true;

    private static DialogUtil dialogUtil = null;
    private SweetAlertDialog dialog = null;

    public static DialogUtil getIntance() {
        if (dialogUtil == null) {
            synchronized (DialogUtil.class) {
                if (dialogUtil == null) {
                    dialogUtil = new DialogUtil();
                }
            }
        }
        return dialogUtil;
    }

    public SweetAlertDialog getProgressDialog(Context context, String msg, DialogInterface.OnCancelListener listener) {
        dialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialog.setTitleText(msg);
        dialog.setCancelable(false);
        if (listener != null)
            dialog.setOnCancelListener(listener);
        return dialog;
    }

    public void showProgressDialog(Context context, String msg, DialogInterface.OnCancelListener listener) {
        dismiss();
        getProgressDialog(context, msg, listener).show();
    }

    public void showProgressDialog(Context context, DialogInterface.OnCancelListener listener) {
        dismiss();
        getProgressDialog(context, "加载中...", listener).show();
    }

    public void showErrorDialog(Context context, String titile, String content, String confirmText) {
        dismiss();
        dialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(TextUtils.isEmpty(titile) ? "异常" : titile)
                .setContentText(content)
                .setConfirmText(TextUtils.isEmpty(confirmText) ? "确定" : confirmText);

        dialog.show();
    }

    public void showErrorDialogWithCancle(Context context, String titile, String content, String confirmText
            , final SweetAlertDialog.OnSweetClickListener listener) {
        dismiss();
        dialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                .setTitleText(TextUtils.isEmpty(titile) ? "异常" : titile)
                .setContentText(content)
                .setCancelText("取消")
                .showCancelButton(true)
                .setConfirmText(TextUtils.isEmpty(confirmText) ? "确定" : confirmText)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        dismiss();
                        if (listener != null) listener.onClick(sweetAlertDialog);

                    }
                });
        dialog.show();
    }

    public void showCustomDialog(Context context, String titile, String content, String confirmText) {
        dismiss();
        dialog = new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText(TextUtils.isEmpty(titile) ? "异常" : titile)
                .setCustomImage(R.drawable.yx)
                .setContentText(content)
                .setConfirmText(TextUtils.isEmpty(confirmText) ? "确定" : confirmText);

        dialog.show();
    }

    public void showWarnDialog(Context context, String title, String content, String btnString, final SweetAlertDialog.OnSweetClickListener listener) {
        dismiss();
        dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(TextUtils.isEmpty(title) ? "警告" : title)
                .setContentText(content)
                .showCancelButton(true)
                .setCancelText("取消")
                .setConfirmText(TextUtils.isEmpty(btnString) ? "确定" : btnString)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (listener != null) {
                            listener.onClick(sweetAlertDialog);
                        }
                        dialog.dismiss();
                    }
                });
        dialog.setCancelable(false);
        dialog.show();
    }

    public void showWarnDialogWithCancle(Context context, String title, String content, String conFirmString
            , final SweetAlertDialog.OnSweetClickListener listener) {
        dismiss();
        dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(TextUtils.isEmpty(title) ? "警告" : title)
                .setContentText(content)
                .setConfirmText(TextUtils.isEmpty(conFirmString) ? "确定" : conFirmString)
                .setCancelText("取消")
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        dismiss();
                        if (listener != null) listener.onClick(sweetAlertDialog);
                    }
                });
        dialog.show();
    }

    public void showSuccessDialog(Context context, String titile, String content, final SweetAlertDialog.OnSweetClickListener listener) {
        dismiss();
        dialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(TextUtils.isEmpty(titile) ? "成功" : titile)
                .setContentText(content)
                .setConfirmText("确定")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        dismiss();
                        if (listener != null) listener.onClick(sweetAlertDialog);
                    }
                });
        dialog.show();
    }

    public void changeToError(String errorMsg, SweetAlertDialog.OnSweetClickListener listener) {
        changeToError("", errorMsg, "", listener);
    }

    public void changeToError(String titile, String content, String confirmText, final SweetAlertDialog.OnSweetClickListener listener) {
        if (dialog != null) {
            dialog.setTitleText(TextUtils.isEmpty(titile) ? "异常" : titile)
                    .setContentText(content)
                    .setConfirmText(TextUtils.isEmpty(confirmText) ? "重试" : confirmText)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            dismiss();
                            if (listener != null) listener.onClick(sweetAlertDialog);
                        }
                    })
                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
            dialog.setCancelable(true);
            if (isHasCancle) {
                dialog.setCancelText("取消")
                        .showCancelButton(true);
            }
        }
    }

    public void changeToSuccess(String content, final SweetAlertDialog.OnSweetClickListener listener) {
        changeToSuccess("", content, "", listener);
    }

    public void changeToSuccess(String titile, String content, String confirmText, final SweetAlertDialog.OnSweetClickListener listener) {
        if (dialog != null) {
            dialog.setTitleText(TextUtils.isEmpty(titile) ? "成功" : titile)
                    .setContentText(content)
                    .setConfirmText(TextUtils.isEmpty(confirmText) ? "确定" : confirmText)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            if (listener != null) {
                                dismiss();
                                listener.onClick(sweetAlertDialog);
                            }
                        }
                    })
                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            dialog.setCancelable(false);
            dialog.showCancelButton(false);
        }
    }

    public void changeToWarn(String titile, String content, String confirmText, final SweetAlertDialog.OnSweetClickListener listener) {
        if (dialog != null) {
            dialog.setTitleText(TextUtils.isEmpty(titile) ? "警告" : titile)
                    .setContentText(content)
                    .setConfirmText(TextUtils.isEmpty(confirmText) ? "确定" : confirmText)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            dismiss();
                            if (listener != null) listener.onClick(sweetAlertDialog);
                        }
                    })
                    .changeAlertType(SweetAlertDialog.WARNING_TYPE);
            dialog.setCancelable(true);
            if (isHasCancle) {
                dialog.setCancelText("取消")
                        .showCancelButton(true);
            }
        }
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = null;
    }

    public void dismissWhitoutAni() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = null;
    }

    public boolean isShowing() {
        if (dialog == null)
            return false;
        return dialog.isShowing();
    }
}
