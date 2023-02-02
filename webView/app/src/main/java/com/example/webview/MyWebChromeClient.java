package com.example.webview;



import static android.content.ContentValues.TAG;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;


public class MyWebChromeClient extends WebChromeClient {
    private static ValueCallback<Uri> mUploadMessage;
    private static ValueCallback<Uri[]> mUploadMessageLollipop;
    private String myCameraPhotoPath = null;
    private Context context;
    private Activity activity;
    public MyWebChromeClient(Context context,Activity activity){
        this.context = context;
        this.activity = activity;
    }
    public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                String acceptType, String capture) {
        Log.d(TAG, "openFileChooser 4.*");
        mUploadMessage = uploadMsg;
        openSelectDialog();
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallBack,FileChooserParams fileChooserParams){
        Log.d(TAG, "openFileChooser 5.0+ ");
        mUploadMessageLollipop = filePathCallBack;
        openSelectDialog();
        return true;
    }

    private void openSelectDialog() {
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(photoIntent.resolveActivity(context.getPackageManager()) !=null)
        {
            myCameraPhotoPath = "file:" + context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    .toString() + "/" + DateUtil.getNowDateTime() + ".jpg";
            Log.d(TAG, "photoFile=" + myCameraPhotoPath);
            photoIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.parse(myCameraPhotoPath));
        }
        Intent[] intentArray = new Intent[]{photoIntent};
        Intent selectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        selectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        selectionIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        selectionIntent.setType("image/*");
        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT,selectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE,"请拍照或选择照片");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,intentArray);


    }



}


