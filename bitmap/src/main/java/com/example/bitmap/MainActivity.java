package com.example.bitmap;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private Button btn_decodeStream,btn_decodeResource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_decodeResource = findViewById(R.id.btn_decodeResource);
        btn_decodeStream = findViewById(R.id.btn_decodeStream);

        btn_decodeStream.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                decodeStream1();
            }
        });
        btn_decodeResource.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                decodeResource1();
            }
        });
    }

    //getByteCount()，获取图片占用内存大小的理论值；
    //getAllocationByteCount()，获取图片占用内存大小的实际值；
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void decodeResource1() {
        //2020-09-28 16:28:41.137 23250-23250/com.example.bitmap E/zzf: 69696
        //2020-09-28 16:29:58.415 23406-23406/com.example.bitmap E/zzf: 17424
        //scale = (float) targetDensity / density
        //targetDensity表示手机的实际密度，density对应res文件夹中的对应的图片资源的密度
        //ldpi :120   mdpi:160  hdpi:240  xhdpi:320  xxhdpi:480
        //占用内存 = 图片宽度/inSampleSize*scale*图片高度/inSampleSize*scale*每个像素所占的内存
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round, options);
        Log.e("zzf",bitmap.getByteCount()+"-----------"+bitmap.getAllocationByteCount());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void decodeStream1()  {
        //占用内存 = 图片宽度/inSampleSize*图片高度/inSampleSize*每个像素所占的内存
        //2020-09-28 16:21:07.310 22762-22762/com.example.bitmap E/zzf: 9216
        //2020-09-28 16:24:24.875 22975-22975/com.example.bitmap E/zzf: 2304
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inSampleSize = 2;
            InputStream is = getAssets().open("ic_launcher_round.png");
            if (is != null){
                bitmap = BitmapFactory.decodeStream(is,null,options);

            }else {
                Log.e("zzf","is为空");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("zzf",bitmap.getByteCount()+"-----------"+bitmap.getAllocationByteCount());
    }
}
