package com.example.webview;

import android.app.AlertDialog;
import android.content.Context;
import android.webkit.JavascriptInterface;

public class JsInteraction {
    private Context  context;
    public JsInteraction(Context context){
        this.context = context;
    }
@JavascriptInterface
    public String back(){
        return "hello world";
    }
@JavascriptInterface
    public void showMsgFromAndroid(String _msg){
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      builder.setTitle("来自Android端的对话框").setMessage(_msg);
      builder.create().show();
    }
@JavascriptInterface
    public String getMsgFromAndroid(String _msg){
        return _msg;
    }
}
