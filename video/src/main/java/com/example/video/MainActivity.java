package com.example.video;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private final String url = "http://rbv01.ku6.com/7lut5JlEO-v6a8K3X9xBNg.mp4";
    private PlayVideo playVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        frameLayout = findViewById(R.id.fl);
        playVideo = new PlayVideo(this,url);
        frameLayout.addView(playVideo.getVideoView());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (null != playVideo){
                playVideo.mediaPlayer.release();
                playVideo.mediaPlayer = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(playVideo.getReceiver());
    }
}
