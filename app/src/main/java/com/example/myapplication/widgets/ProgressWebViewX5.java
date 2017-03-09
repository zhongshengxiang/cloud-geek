package com.example.myapplication.widgets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.myapplication.R;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;


/**
 * Created by Vinctor on 2016/6/7.
 */
public class ProgressWebViewX5 extends LinearLayout {

    @BindView(R.id.web_view)
    WebView webview;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private Activity mContext;

    private String url;


    public ProgressWebViewX5(Context context) {
        this(context, null);
    }

    public ProgressWebViewX5(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressWebViewX5(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = (Activity) context;
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.view_web_progress_x5, this);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
    }

    public WebView getWebview() {
        return webview;
    }

    public void loadUrl(String url) {
        testNetWork();
        if (url == null) {
            url = "http://www.baidu.com";
        } else {
            this.url = url;
        }
        initWebview(url);
    }

    Subscription sub;

    void testNetWork() {
        if (onShowTitleListener != null) {
            onShowTitleListener.showTitle("加载中...");
        }
    }

    @SuppressLint("JavascriptInterface")
    public void addJavascriptInterface(Object obj, String name) {
        webview.addJavascriptInterface(obj, name);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebview(String url) {

        webview.addJavascriptInterface(new JavaScriptObject(mContext, webview), "android");

        WebSettings webSettings = webview.getSettings();

        webSettings.setJavaScriptEnabled(true);
        // 设置可以访问文件
        webSettings.setAllowFileAccess(true);
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // 设置默认缩放方式尺寸是far
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDefaultFontSize(16);

        webSettings.setBlockNetworkImage(true);

        webview.loadUrl(url);

        // 设置WebViewClient
        webview.setWebViewClient(new WebViewClient() {
            // url拦截
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 使用自己的WebView组件来响应Url加载事件，而不是使用默认浏览器器加载页面
                view.loadUrl(url);
                // 相应完成返回true
                return true;
                // return super.shouldOverrideUrlLoading(view, url);
            }

            // 页面开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (view == null) {
                    return;
                }
                mProgressBar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);

            }

            // 页面加载完成
            @Override
            public void onPageFinished(WebView view, String url) {
                if (view == null) {
                    return;
                }
                mProgressBar.setVisibility(View.INVISIBLE);
                webview.getSettings().setBlockNetworkImage(false);

                super.onPageFinished(view, url);
                if (onShowTitleListener != null) {
                    onShowTitleListener.showTitle(view.getTitle());
                }
            }

            // WebView加载的所有资源url
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//				view.loadData(errorHtml, "text/html; charset=UTF-8", null);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });

        // 设置WebChromeClient
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            // 处理javascript中的alert
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }


            @Override
            // 处理javascript中的confirm
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }


            @Override
            // 处理javascript中的prompt
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }


            // 设置网页加载的进度条
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mProgressBar.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }

            // 设置程序的Title
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
        webview.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) { // 表示按返回键

                        webview.goBack(); // 后退

                        // webview.goForward();//前进
                        return true; // 已处理
                    }
                }
                return false;
            }
        });
    }

    public void reload() {
        testNetWork();
        webview.stopLoading();
        webview.reload();
    }

    public void cancle() {
        sub.unsubscribe();
        webview.stopLoading();
    }

    public boolean canBack() {
        if (webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return false;
    }

    public interface OnShowTitleListener {
        void showTitle(String title);
    }

    OnShowTitleListener onShowTitleListener;

    public void setOnShowTitleListener(ProgressWebViewX5.OnShowTitleListener onShowTitleListener) {
        this.onShowTitleListener = onShowTitleListener;
    }

    public void destroyWebView() {
        if (sub != null) {
            sub.unsubscribe();
        }
        if (webview != null) {
            webview.removeAllViews();
            webview.destroy();
            webview = null;
        }
    }

    public void pauseWebView() {
        if (webview == null) {
            return;
        }
        webview.onPause();
    }

    public void resumeWebView() {
        if (webview == null) {
            return;
        }
        webview.onResume();
    }
}
