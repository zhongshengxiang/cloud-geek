package com.example.myapplication.widgets;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.blankj.utilcode.utils.ScreenUtils;
import com.example.myapplication.R;


public class FormSheet {

    public static Dialog showFullScreenDialogBottom(Context contex, View contentView) {
        //对话框
        return showFullScreenDialog(contex, contentView, Gravity.BOTTOM);
    }

    public static Dialog showFullScreenDialogCenter(Context contex, View contentView) {
        //对话框
        return showFullScreenDialog(contex, contentView, Gravity.CENTER);
    }

    public static Dialog showFullScreenDialog(Context contex, View contentView, int gravity) {
        //对话框
        final Dialog dialog = new AlertDialog.Builder(contex).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.dialog_style);
        window.setGravity(gravity);
        android.view.WindowManager.LayoutParams p = window.getAttributes();  //获取对话框当前的参数值
        p.width = ScreenUtils.getScreenWidth();    //宽度设置为全屏
        window.setAttributes(p);
        window.setContentView(contentView);
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.getDecorView().setBackgroundColor(0x00000000);
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//        view.findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
        return dialog;
    }
    /**
     * 在控件正下方显示
     *
     * @param contentView
     * @return
     */
    public static PopupWindow showAsDropDown(View contentView, View parent) {
        PopupWindow mPopupWindow = getPopupWindow(contentView, parent);
        mPopupWindow.showAsDropDown(parent, 0, 0);
        return mPopupWindow;
    }

    public static PopupWindow showAtLocation(View contentView, View parent, int gravity) {
        PopupWindow mPopupWindow = getPopupWindow(contentView, parent);
        mPopupWindow.showAtLocation(parent, gravity, 0, 0);
        return mPopupWindow;
    }

    @NonNull
    private static PopupWindow getPopupWindow(View contentView, View parent) {
        PopupWindow mPopupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(parent.getResources(), (Bitmap) null));

        mPopupWindow.getContentView().setFocusableInTouchMode(true);
        mPopupWindow.getContentView().setFocusable(true);
        return mPopupWindow;
    }
}
