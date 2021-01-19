package com.example.synchronizeddemo.BlockingQueue;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.synchronizeddemo.R;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * author : zhangzf
 * date   : 2020/12/29
 * desc   :
 */
public class BlockingQueueActivity extends AppCompatActivity {

    ArrayBlockingQueue arrayBlockingQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_queue);
    }
}
