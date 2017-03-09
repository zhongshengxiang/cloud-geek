package com.example.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.example.myapplication.R;
import com.example.myapplication.interfaces.Constants;
import com.example.myapplication.utils.Toaster;
import com.example.myapplication.widgets.JavaScriptObject;
import com.example.myapplication.widgets.ProgressWebViewX5;

import butterknife.BindView;


public class WebViewActivity extends BaseActivity {

    final static String webUrl_key = "web_url";
    final static String title_key = "web_title_url";
    @BindView(R.id.webview)
    ProgressWebViewX5 webview;
    String url = "";

    public static void startActivity(Context context, String title, String url) {
        Intent starter = new Intent(context, WebViewActivity.class);
        if (Constants.isDebug && !TextUtils.isEmpty(url)) {
            Log.v("web", url);
        }
        starter.putExtra(webUrl_key, url);
        starter.putExtra(title_key, title);
        context.startActivity(starter);
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_web;
    }

    @Override
    public void initView() {
        loadUrl();

        webview.addJavascriptInterface(new JavaScriptObject(thisActivity, webview.getWebview()), "myObj");

        webview.setOnShowTitleListener(new ProgressWebViewX5.OnShowTitleListener() {
            @Override
            public void showTitle(String titleString) {
                Toaster.show(titleString);
            }
        });
    }

    protected void loadUrl() {
        url = getIntent().getStringExtra(webUrl_key);
        if (!TextUtils.isEmpty(url)) {
            webview.loadUrl(url);
        } else {
            Toaster.show("数据请求错误");
            finish();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webview != null) {
            webview.destroyWebView();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (webview != null) {
            webview.pauseWebView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webview != null) {
            webview.resumeWebView();
        }
    }
}
