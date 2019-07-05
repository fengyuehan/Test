package com.example.zxingdemo;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerView;

public class MainActivity extends AppCompatActivity  {

    private ScannerView mScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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




}
