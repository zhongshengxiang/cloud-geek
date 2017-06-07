package com.example.myapplication.utils;

import android.widget.Toast;

import com.example.myapplication.appconfig.MyApplication;

public class Toaster {
    private static Toast toast;

    public static void show(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    public static void showLong(String msg) {
        showToast(msg, Toast.LENGTH_LONG);
    }

    private static void showToast(String msg, int duration) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.appContext, msg, duration);
        } else {
            toast.setText(msg);
            toast.setDuration(duration);
        }
        toast.show();
    }
}
