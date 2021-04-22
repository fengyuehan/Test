package com.example.pictureoptimization;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File imageFile = new File("C:/Users/Yu/Desktop/Test/pictureoptimization/src/main/res/drawable/delete.png");
        long size = getFileSize(imageFile);
        Log.e("zzf",size + "");

        File imageFile1 = new File("C:\\Users\\Yu\\Desktop\\Test\\pictureoptimization\\src\\main\\res\\drawable\\delete1.webp");
        long size1 = getFileSize(imageFile1);
        Log.e("zzf",size1 + "");
    }

    public static long getFileSize(File file) {
        if (!file.exists() || !file.isFile()) {
            System.out.println("文件不存在");
            return -1;
        }
        return file.length();
    }
}