package com.example.myapplication.widgets;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import com.tencent.smtt.sdk.WebView;


public class JavaScriptObject {
    Activity activity;
    WebView webView;


    public JavaScriptObject(Activity ac, WebView webView) {
        this.activity = ac;
        this.webView = webView;
    }

    @JavascriptInterface
    public void openDetail(String proid) {

    }
}
