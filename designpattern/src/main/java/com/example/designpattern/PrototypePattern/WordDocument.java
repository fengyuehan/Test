package com.example.designpattern.PrototypePattern;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class WordDocument implements Cloneable {
    private String text;

    private List<String> mImages = new ArrayList<>();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getmImages() {
        return mImages;
    }

    public void setmImages(List<String> mImages) {
        this.mImages = mImages;
    }

    public WordDocument(){
        Log.e("zzf","-----------------WordDocument构造函数----------------");
    }

    public void showDocument(){
        Log.e("zzf","---------------内容开始------------------");
        Log.e("zzf","Text" + text);
        Log.e("zzf","ImageList : ");
        for (String image : mImages){
            Log.e("zzf","Image Name" + image);
        }
        Log.e("zzf","-----------------内容结束----------------------");
    }
    @NonNull
    @Override
    protected WordDocument clone(){
        try {
            WordDocument document = (WordDocument) super.clone();
            document.text = this.text;
            document.mImages = (List<String>) super.clone();
            return document;
        }catch (Exception e){

        }
        return null;
    }
}
