package com.example.share;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.transition.Transition;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ImageView imageView;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        String path = getIntent().getStringExtra("snapshot_path");
        init(path);
    }

    private void init(String path) {
        //状态栏的高度
        int statusHeight = Utils.getStatusBarHeight(this);
        //虚拟导航栏的高度
        int navHeight = Utils.getNavigationBarHeight(this);
        float width = Utils.getScreenWidth(this) - Utils.dp2px(this, 116);
        float ratio = (float) Utils.div(Utils.getScreenWidth(this), Utils.getScreenHeight(this) - statusHeight - navHeight, 2);
        float height = width / ratio;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.height = (int) height;
        imageView.setLayoutParams(layoutParams);
        //魅族手机生成的截图文件带有 "-" 的命名，会导致获取bitmap为null。利用Glide生成的bitmap
        loadImage(path, statusHeight, navHeight);
    }

    private void loadImage(final String path, final int statusHeight, final int navHeight) {
        Glide.with(this).asBitmap().load(path).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable com.bumptech.glide.request.transition.Transition<? super Bitmap> transition) {
                //源文件生成的bitmap
                //裁剪bitmap,去掉状态栏和底部的菜单栏(x+width must be < bitmap.width())
                try {
                    Bitmap resultBitmap = Bitmap.createBitmap(resource, 0, statusHeight, resource.getWidth(), resource.getHeight() - statusHeight - navHeight);
                    imageView.setImageBitmap(resultBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                if (count <= 5) {
                    count = count + 1;
                    loadImage(path, statusHeight, navHeight);
                }
            }
        });

    }

    private void initView() {
        button = findViewById(R.id.btn);
        imageView = findViewById(R.id.iv);
    }
}
