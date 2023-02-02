package com.example.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private Button bt;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    private void initView() {
            webView = (WebView) findViewById(R.id.webView);
            webView.loadUrl("file:///android_asset/index.html");
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setLoadsImagesAutomatically(true);
            webSettings.setAllowFileAccess(true);
            webView.evaluateJavascript("show()",null);
            webView.evaluateJavascript("sum(1,2)", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    Log.d(TAG,"js返回的结果为=" + s);
                    Toast.makeText(MainActivity.this,"js返回的结果=" + s,Toast.LENGTH_SHORT).show();
                }
            });
            String content = "123";
            webView.evaluateJavascript("alertMessage(\""+content+"\")",null);
            webView.addJavascriptInterface(new JsInteraction(MainActivity.this),"android");
    }

    public void onClick(View view) {
        Log.e("TAG", "onClick: ");
        //直接访问H5里不带返回值的方法，show()为H5里的方法
        webView.loadUrl("JavaScript:show()");
        //传固定字符串可以直接用单引号括起来
        webView.loadUrl("javascript:alertMessage('哈哈')");//访问H5里带参数的方法，alertMessage(message)为H5里的方法
        //当出入变量名时，需要用转义符隔开
        String content="9880";
        webView.loadUrl("javascript:alertMessage(\"" +content+   "\")" );
        //Android调用有返回值js方法，安卓4.4以上才能用这个方法
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            webView.evaluateJavascript("sum(1,2)", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    Log.d(TAG, "js返回的结果为" + value);
                    Toast.makeText(MainActivity.this,"js返回的结果为=" + value,Toast.LENGTH_LONG).show();
                }
            });
        } else{
            Toast.makeText(this,"Android4.4版本才支持该功能",Toast.LENGTH_SHORT).show();
        }

    }


}
