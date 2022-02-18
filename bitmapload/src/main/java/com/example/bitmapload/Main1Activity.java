package com.example.bitmapload;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * ### intent通过binder传递bitmap的原理
 * bitmap在native层传递的时候会有两种方案：
 * 1. 直接将图片写入进程的缓冲区。
 * 缓冲区是进程在初始化的时候就已经申请了的，并且大小是一定的。因此如果写入的大小超过了缓冲区的大小，就会报错。
 * 2. 使用共享内存，将共享内存的fd，也就是文件描述符写入缓冲区。
 * 这样的好处就是传递图片的大小不会受限制。
 *
 * intent直接传递bitmap对应方案1，intent通过binder传递bitmap对应方案2。
 *
 * ### 为什么intent传递bitmap不默认使用共享内存？
 * 个人理解，缓冲区的大小是进程创建的时候就申请好的，如果能保证不超出缓冲区大小的情况下使用缓冲区，不需要再另外申请共享内存肯定是最好的。
 * 如果默认就使用共享内存，而缓冲区资源又没人用的话，就造成了资源浪费。
 *
 * 因此如果开发者自己认为需要传递大文件的话，就使用共享内存，默认不使用。
 */

public class Main1Activity extends AppCompatActivity {

  //constants
  public static final String KEY_IMAGE_BINDER = "imageBinder";
  //data
  Bitmap bitmapTest;
  //ui
  private Button btnTest;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_other);
    bitmapTest = getBitmap();
    initViews();
  }

  private void initViews() {
    btnTest = findViewById(R.id.btn_test);
    btnTest.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent(Main1Activity.this, ShowImageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBinder(KEY_IMAGE_BINDER, new ImageInterface.Stub() {

          @Override public Bitmap getBitmap() throws RemoteException {
            return bitmapTest;
          }
        });
        intent.putExtras(bundle);
        startActivity(intent);
      }
    });
  }

  //实际操作中，bitmap一般是从网络拉取
  @SuppressLint("UseCompatLoadingForDrawables")
  private Bitmap getBitmap() {
    return drawableToBitmap(getResources().getDrawable(R.drawable.world));
  }

  public static Bitmap drawableToBitmap(Drawable drawable) {
    if (drawable instanceof BitmapDrawable) {
      BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
      if (bitmapDrawable.getBitmap() != null) {
        return bitmapDrawable.getBitmap();
      }
    }
    return null;
  }
}