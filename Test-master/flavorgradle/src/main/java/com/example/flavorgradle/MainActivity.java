package com.example.flavorgradle;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends BaseMVPActivity {

    private LinearLayout linearLayout;
    private TextView textView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = findViewById(R.id.mLinearLayout);
        textView = findViewById(R.id.mTvTitle);
        try {
            textView.setText("包名 =" + getPackageManager().getPackageInfo(getPackageName(),0).packageName+"\n"+
                   "应用名 =" +  getResources().getString(getPackageManager().getPackageInfo(getPackageName(),0).applicationInfo.labelRes)+ "\n"+
                    "talex.zsw.flavorsdemo.Constant.info = " + BuildConfig.FLAVOR +"\n"+
                            "渠道名称 = "+ BuildConfig.CHANNEL + "\n"+
                            "app build.gradle CHANNEL = " + BuildConfig.CHANNEL
                    );
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        linearLayout.addView(getView());
    }
}