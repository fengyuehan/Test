package com.example.changeskin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import java.util.concurrent.CopyOnWriteArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
       try {
           if ("FrameLayout".equals(name)){
               int count = attrs.getAttributeCount();
               for (int i = 0;i < count; i++){
                   String attributeName = attrs.getAttributeName(i);
                   String attributeValue = attrs.getAttributeValue(i);
                   if (attributeName.equals("id")){
                       int id = Integer.parseInt(attributeValue.substring(1));
                       String idVal = getResources().getResourceName(id);
                       if ("android:id/content".equals(idVal)){
                           GrayFrameLayout grayFrameLayout = new GrayFrameLayout(context,attrs);
                           //如果设置了背景
                           grayFrameLayout.setBackgroundDrawable(getWindow().getDecorView().getBackground());
                           return grayFrameLayout;
                       }
                   }
               }
           }
       }catch (Exception e){
           e.printStackTrace();
       }

        return super.onCreateView(name, context, attrs);
    }
}
