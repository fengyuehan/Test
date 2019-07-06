package com.example.zxingdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ScannerView mScannerView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.btn);
        button.setOnClickListener(this);
        mScannerView = findViewById(R.id.scanner_view);
        mScannerView.setMediaResId(R.raw.beep);//设置描成功的声音
        mScannerView.setLaserFrameBoundColor(getResources().getColor(R.color.zz));
        mScannerView.setLaserColor(getResources().getColor(R.color.zz));

        mScannerView.setOnScannerCompletionListener(new OnScannerCompletionListener() {
            @Override
            public void onScannerCompletion(Result result, ParsedResult parsedResult, Bitmap bitmap) {
                Log.e("zzf",parsedResult.toString());
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this
                            , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }else {
                    jumpToAlbums();
                }
                break;
        }
    }

    private void jumpToAlbums() {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT < 19) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        }
        startActivityForResult(intent,1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case 1:
                    Uri uri = data.getData();
                    String imagePath = UriUtils.getPicturePathFromUri(MainActivity.this,uri);
                    praseImage(imagePath);
                    break;
            }
    }
}

    private void praseImage(final String imagePath) {
        AsyncTask myTask = new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {
                // 解析二维码/条码
                return QRCodeDecoder.syncDecodeQRCode(imagePath);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (null == s) {
                    Log.e("zzf","图片获取失败,请重试");
                } else {
                    // 识别出图片二维码/条码，内容为s
                    //pareCode(s);
                    Log.e("zzf",s);
                }
            }
        }.execute(imagePath);
    }
}
