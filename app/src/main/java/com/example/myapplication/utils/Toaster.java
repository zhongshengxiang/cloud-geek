package com.example.myapplication.utils;

import android.widget.Toast;

import com.example.myapplication.appconfig.MyApplication;

public class Toaster {
    private static Toast toast;

    public static void show(String msg) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.appContext, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public static void showLong(String msg) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.appContext, msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }
}
