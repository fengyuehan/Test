package com.example.chatdemo;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SelectPictureDialog extends BottomSheetDialog {

    private Context mContext;
    private LinearLayout ll_select_picture;
    private EditText et_chat;
    private ImageView iv;
    private ImageView iv_image_select;

    public SelectPictureDialog(Context context) {
        super(context);
        this.mContext = context;
        initView();
        initLinstener();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_select, null);
        super.setContentView(view);
        ll_select_picture = view.findViewById(R.id.ll_select_picture);
        et_chat = view.findViewById(R.id.et_chat);
        iv = view.findViewById(R.id.iv);
        iv_image_select = findViewById(R.id.iv_image_select);

    }

    private void initLinstener() {
        iv_image_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    jumpToAlbums();
                }
            }
        });
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
        getOwnerActivity().startActivityForResult(intent, 1);
    }


    public void setVisible(){
        ll_select_picture.setVisibility(View.GONE);
    }


}
