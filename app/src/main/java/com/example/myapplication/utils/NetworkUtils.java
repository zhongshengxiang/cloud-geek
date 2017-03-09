package com.example.myapplication.utils;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.myapplication.appconfig.MyApplication;

public class NetworkUtils {
    public static boolean isOnline() {
        ConnectivityManager manager = (ConnectivityManager) MyApplication.getIntance().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }
}
