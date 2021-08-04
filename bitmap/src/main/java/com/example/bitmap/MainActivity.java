package com.example.bitmap;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private Button btn_decodeStream,btn_decodeResource,btn_jump,btn_jump_app;
    private static final String pkg = "com.example.dell.myapplication";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_decodeResource = findViewById(R.id.btn_decodeResource);
        btn_decodeStream = findViewById(R.id.btn_decodeStream);
        btn_jump = findViewById(R.id.btn_jump);
        btn_jump_app = findViewById(R.id.btn_jump_app);
        btn_jump_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Otheractivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent,1);
            }
        });
        btn_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //第一种  包名+指定的activity的全路径
               /* Intent intent = new Intent();
                ComponentName cn = new ComponentName(pkg, "com.example.dell.myapplication.OtherActivity");
                intent.setComponent(cn);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivityForResult(intent,RESULT_OK);
                startActivityForResult(intent,1);*/
                //第二种，包名拉起
                Intent intent1 = getPackageManager().getLaunchIntentForPackage(pkg);
                //intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent1,1);
                //第三种url拉起
                /*Intent intent = new Intent();
                intent.setData(Uri.parse("csd://pull.csd.demo/cyn"));
                //intent.putExtra("", "");//这里Intent当然也可传递参数,但是一般情况下都会放到上面的URL中进行传递
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent,1);*/
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("zzf","onActivityResult");
        /*if (requestCode == 1){
            if (resultCode == RESULT_OK){
                String data1 = data.getStringExtra("data");
                Toast.makeText(this,data1,Toast.LENGTH_SHORT).show();
            }
        }*/
        if(data == null){
            Log.e("zzf","data为null");
            return;
        }
        String data1 = data.getStringExtra("data");
        Toast.makeText(this,data1,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("zzf","onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("zzf","onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("zzf","onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("zzf","onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("zzf","onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("zzf","onPause");
    }
}
