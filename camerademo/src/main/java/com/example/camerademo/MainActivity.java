package com.example.camerademo;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private Dialog dialog;
    private Uri contentUri;
    private Uri uritempFile;
    private Button button;

    private static final int PHOTO_TK = 0;
    private static final int PHOTO_PZ = 1;
    private static final int PHOTO_CLIP = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkSelfPermission();
    }

    private void initView() {
        imageView = findViewById(R.id.iv);
        imageView.setOnClickListener(this);
        button = findViewById(R.id.btn);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv:
                showBottomDialog();
                break;
            case R.id.update_dialog_TK:
                Intent intent = new Intent(Intent.ACTION_PICK,null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent,PHOTO_TK);
                dialog.dismiss();
                break;
            case R.id.update_dialog_PZ:
                if (Build.VERSION.SDK_INT >= 23){
                    int checkPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
                    if (checkPermission != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{android.Manifest.permission.CAMERA},1);
                        return;
                    }else {
                        runCamera();
                    }
                }else {
                    runCamera();
                }
                break;
            case R.id.update_dialog_cancel:
                dialog.dismiss();
                break;
            case R.id.btn:
                Intent intent1 = new Intent(MainActivity.this,LuZhiActivity.class);
                startActivity(intent1);
                break;
                default:
                    break;
        }
    }

    private void runCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri mImageCaptureUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            contentUri = FileProvider.getUriForFile(MainActivity.this,"com.example.camerademo.fileProvider",new File(Environment.getExternalStorageDirectory(),"temp.jpg"));
            intent.putExtra(MediaStore.EXTRA_OUTPUT,contentUri);
        }else {
            mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"temp.jpg"));
            intent.putExtra(MediaStore.EXTRA_OUTPUT,mImageCaptureUri);
        }
        startActivityForResult(intent,PHOTO_PZ);
        dialog.dismiss();
    }

    public void checkSelfPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE},0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    runCamera();
                }else {
                    Toast.makeText(MainActivity.this, "请手动打开相机权限", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showBottomDialog() {
        TextView update_photo,update_PZ,cancel;
        View view = getLayoutInflater().inflate(R.layout.show_head_dialog,null);
        dialog = new Dialog(this,R.style.dialog_photo);

        Display display = getWindowManager().getDefaultDisplay();
        dialog.setContentView(view,new ViewGroup.LayoutParams(display.getWidth(),display.getHeight()));
        dialog.show();

        update_photo = view.findViewById(R.id.update_dialog_TK);
        update_PZ = view.findViewById(R.id.update_dialog_PZ);
        cancel = view.findViewById(R.id.update_dialog_cancel);

        update_photo.setOnClickListener(this);
        update_PZ.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case PHOTO_PZ:
                    Uri uri;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                        uri = contentUri;
                    }else {
                        uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/temp.jpg"));
                    }
                    setPhoto(uri);
                    break;
                case PHOTO_TK:
                    setPhoto(data.getData());
                    break;
                case PHOTO_CLIP:
                    Picasso.with(MainActivity.this)
                            .load(uritempFile)
                            .into(imageView);
                    break;
                    default:
                        break;
            }
        }
    }

    private void setPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");

        intent.setDataAndType(uri,"image/*");
        intent.putExtra("crop","true");
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        intent.putExtra("outputX",60);
        intent.putExtra("outputY",60);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.setClipData(ClipData.newRawUri(MediaStore.EXTRA_OUTPUT,uri));
            uritempFile = uri;
        }else {
            uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/small.jpg");
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uritempFile);
        intent.putExtra("return-data",false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection",true);
        startActivityForResult(intent,PHOTO_CLIP);
    }
}
