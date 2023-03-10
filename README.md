# Android开发

## WebView与JS交互

> Android手机App如果想要和web之间进行消息传递,就必须利用Android原生与Web的JS代码之间通信。具体通信交互方式可分为四类：
>
> 1. App通知Web执行动作；
> 2. App主动从Web获取信息；
> 3. Web通知App执行动作；
> 4. Web从App获取信息。

### App通知Web执行动作

```java
  webView.loadUrl("JavaScript:show()");
```

### App主动从Web获取信息

```JAVA
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
```

### Web通知App执行某项动作

App需要注册一个类，并且在该类中添加在JS中使用的方法，属性更改为@JavascriptInterface。

```java
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

}
```

接着在调用webView对象的addJavascriptInterface方法，给这个类注册一个实例，JS才可以通过实例名称调用App的方法。

```java
webView.addJavascriptInterface(new JsInteraction(MainActivity.this),"android");
```

### Web从App获取信息

将第三类中的返回值void改为String即可。

```java
@JavascriptInterface
    public String getMsgFromAndroid(String _msg){
        return _msg;
    }
```

### H5通过WebView上传照片

#   A n d r o i d P r o j e c t  
 