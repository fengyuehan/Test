package com.example.jsandandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cn.ycbjie.ycstatusbarlib.StatusBarUtils;
import cn.ycbjie.ycstatusbarlib.bar.YCAppBar;

public class MainActivity extends AppCompatActivity {

   private TextView tv_showmsg;
    private WebView webview;
    private Button tv_androidcalljs,tv_androidcalljsargs;
    Handler handler = new Handler();

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initWebView();
        initListener();

        //webview.loadUrl("http://baidu.com");
        //webview.loadUrl("file:///android_asset/123.html");
        webview.loadUrl("file:///android_asset/123.html");
        webview.addJavascriptInterface(MainActivity.this,"jsCallAndroid");

    }

    @JavascriptInterface
    public void changeStatusBar(final String str) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                int id;
                if (str == null){
                    id = Color.parseColor("#FFFFFF");
                }else {
                    id = Color.parseColor(str);
                }
                YCAppBar.setStatusBarColor(MainActivity.this, id);
                StatusBarUtils.StatusBarLightMode(MainActivity.this);
            }
        });
    }

    private void initListener() {
       /* tv_androidcalljs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview.loadUrl("javascript:javacalljs()");
            }
        });
        tv_androidcalljsargs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview.loadUrl("javascript:javacalljswith(" + "'Android传过来的参数'" + ")");
            }
        });*/
    }

    private void initWebView() {
        WebSettings webSettings = webview.getSettings();
        webSettings.setDefaultTextEncodingName("utf-8");
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        //若加载的html里有JS在执行动画等操作，会造成资源浪费（CPU、电量）在onStop和onResume
        //里分别把 setJavaScriptEnabled()给设置成false和true即可
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        //webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面二个的前提。
        //webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        //webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //设置图片加载
        webSettings.setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
        //String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
        //webSettings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录
        webSettings.setAppCacheEnabled(false);//开启 Application Caches 功能
        //设置WebView是否使用其内置的变焦机制，该机制结合屏幕缩放控件使用，默认是false，不使用内置变焦机制。
        webSettings.setAllowContentAccess(false);
        //设置WebView是否保存表单数据，默认true，保存数据。
        webSettings.setSaveFormData(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setUseWideViewPort(true);
        webview.requestFocus();
        webSettings.setSupportMultipleWindows(true);
        //webview.setWebChromeClient(mWebChromeClient);
        webview.setWebViewClient(mWebViewClient);
        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        //webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); //关闭webview中缓存

        webSettings.setAppCacheEnabled(true);
        String appCachePath = getApplicationContext().getCacheDir()
                .getAbsolutePath() + "/webcache";
        // 设置 Application Caches 缓存目录
        webSettings.setAppCachePath(appCachePath);
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        //webSettings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

    }

    //辅助WebView处理Javascript的对话框,网站图标,网站标题等
    WebChromeClient mWebChromeClient = new WebChromeClient() {

        private String mTitle;

        //获取Web页中的标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            this.mTitle = title;
        }

        //获得网页的加载进度并显示
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);

        }
    };

    WebViewClient mWebViewClient = new WebViewClient() {

        //复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器，
        //而是在本WebView中显示
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            /*Log.e("zzf",url);
            if (url.contains("antpocket://pay.com")){
                jumpAntPocket(url);
            }*/
            return true;
        }

        //开始载入页面调用的
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        //在页面加载结束时调用
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        //在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            //handler.cancel(); 默认的处理方式，WebView变成空白页
            //接受证书
            sslErrorHandler.proceed();
            //handleMessage(Message msg); 其他处理
        }

        //加载页面的服务器出现错误时（如404）调用
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            webview.setVisibility(View.GONE);
        }
    };
    private void initView() {
        tv_androidcalljs = findViewById(R.id.tv_androidcalljs);
        tv_androidcalljsargs = findViewById(R.id.tv_androidcalljsargs);
        tv_showmsg = findViewById(R.id.tv_showmsg);
        webview = findViewById(R.id.webview);

    }

    @JavascriptInterface
    public void jsCallAndroid(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,PayActivity.class));
            }
        });

    }

    private void jumpAntPocket(String url) {

        Uri data = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, data);
        //保证新启动的APP有单独的堆栈，如果希望新启动的APP和原有APP使用同一个堆栈则去掉该项
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivityForResult(intent, RESULT_OK);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "未安装AntPocket！", Toast.LENGTH_SHORT).show();
        }
    }

   @JavascriptInterface
    public void jsCallAndroidArgs(String args){
        tv_showmsg.setText(args);
    }
}
