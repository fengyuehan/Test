package com.example.otcchatkeyboard;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SimpleAppsGridView extends LinearLayout implements View.OnClickListener {
    private ImageView mImage;
    private Context mContext;
    private Activity mActivity;


    public SimpleAppsGridView(Context context,Activity mActivity) {
        super(context);
        this.mContext = context;
        this.mActivity = mActivity;
        View view = View.inflate(context,R.layout.view_apps, this);
        mImage = view.findViewById(R.id.iv_photo);

        mImage.setOnClickListener(this);
    }

    public SimpleAppsGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void onClick(View view) {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            jumpToAlbums();
        }
    }

    private void jumpToAlbums() {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT < 19) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        }
        mActivity.startActivityForResult(intent, 1);
    }
}
