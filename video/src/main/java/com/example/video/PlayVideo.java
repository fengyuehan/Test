package com.example.video;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;

public class PlayVideo implements View.OnClickListener {
    private Context context;
    private View videoView;
    public MediaPlayer mediaPlayer;
    private boolean isDisplay = true;
    private SurfaceView surfaceView;
    private Button btn_play;
    private SeekBar seekBar1;
    private TextView tv_time;
    private ImageView iv_cover;
    private LinearLayout relaVideo,ll_loading;
    private ProgressBroadCast progressBroadCast;

    private String mediaUrl;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (null != msg){
                iv_cover.setImageBitmap((Bitmap) msg.obj);
            }
        }
    };

    public PlayVideo(Context context, String mediaUrl) {
        this.context = context;
        this.mediaUrl = mediaUrl;
        videoView = LayoutInflater.from(context).inflate(R.layout.activity_main2,null);
        initView(videoView);
        seekBarProgress();
    }

    private void seekBarProgress() {
        //1、onStartTrackingTouch方法
        //该方法拖动进度条开始拖动的时候调用。
        //2、onStopTrackingTouch方法
        //该方法拖动进度条停止拖动的时候调用
        //3、onProgressChanged
        //该方法拖动进度条进度改变的时候调用
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (null != mediaPlayer){
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initView(View videoView) {
        surfaceView = videoView.findViewById(R.id.sv);
        btn_play = videoView.findViewById(R.id.btn_play);
        seekBar1 = videoView.findViewById(R.id.play_seekbar);
        tv_time = videoView.findViewById(R.id.tv_video_time);
        iv_cover = videoView.findViewById(R.id.iv_cover);
        relaVideo = videoView.findViewById(R.id.rela_video);
        ll_loading = videoView.findViewById(R.id.ll_video_loading);
        surfaceView.setOnClickListener(this);
        btn_play.setOnClickListener(this);

        progressBroadCast = new ProgressBroadCast();
        context.registerReceiver(progressBroadCast,new IntentFilter("play"));
        //keepScreenOn保持屏幕常亮，android屏幕常亮
        surfaceView.getHolder().setKeepScreenOn(true);
        //TYPE
        //SURFACE_TYPE_NORMAL：用RAM缓存原生数据的普通Surface
        //SURFACE_TYPE_HARDWARE：适用于DMA(Direct memory access )引擎和硬件加速的Surface
        //SURFACE_TYPE_GPU：适用于GPU加速的Surface
        //SURFACE_TYPE_PUSH_BUFFERS：表明该Surface不包含原生数据，Surface用到的数据由其他对象提供，在Camera图像预览中就使用该类型的Surface，
        // 有Camera负责提供给预览Surface数据，这样图像预览会比较流畅。如果设置这种类型则就不能调用lockCanvas来获取Canvas对象了。
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
        getVideoInfo();
    }

    private void getVideoInfo() {
        new Thread(){
            @Override
            public void run() {
                //解析媒体文件、获取媒体文件中取得帧和元数据（视频/音频包含的标题、格式、艺术家等信息）。
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                if (Build.VERSION.SDK_INT >= 14){
                    mediaMetadataRetriever.setDataSource(mediaUrl,new HashMap<String, String>());
                }else {
                    mediaMetadataRetriever.setDataSource(mediaUrl);
                }
                //OPTION_CLOSEST    在给定的时间，检索最近一个帧,这个帧不一定是关键帧。
                //OPTION_CLOSEST_SYNC    在给定的时间，检索最近一个同步与数据源相关联的的帧（关键帧）。
                //OPTION_NEXT_SYNC  在给定时间之后检索一个同步与数据源相关联的关键帧。
                //OPTION_PREVIOUS_SYNC   顾名思义，同上
                //这里为了提取我们想要的帧，不使用关键帧，所以用 OPTION_CLOSEST .
                Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime(1000,MediaMetadataRetriever.OPTION_CLOSEST);
                Message message = new Message();
                message.obj = bitmap;
                handler.sendMessage(message);
            }
        }.start();
    }

    public View getVideoView() {
        return videoView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_play:
                if (mediaPlayer == null){
                    netWorkState();
                }else {
                    if (mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                        btn_play.setBackgroundResource(R.mipmap.video_btn_pause);
                    }else {
                        mediaPlayer.isPlaying();
                        iv_cover.setVisibility(View.GONE);
                        new ProgressThread().start();
                        btn_play.setBackgroundResource(R.mipmap.video_btn_start);
                    }
                }
                break;
            case R.id.sv:
                if (isDisplay){
                    relaVideo.setVisibility(View.GONE);
                }else {
                    relaVideo.setVisibility(View.VISIBLE);
                }
                isDisplay = !isDisplay;
                break;
            default:
                break;
        }
    }

    private void netWorkState() {
        mediaPlayer = new MediaPlayer();
        //调用 getCurrentPosition(), getDuration(), getVideoHeight(), getVideoWidth(), setAudioStreamType(),
        // setLooping(), setVolume(), pause(), start(), stop(), seekTo(), prepare(), prepareAsync()方法都会报错。
        // 新创建的MediaPlayer对象，调用以上方法，无法接收到注册的OnErrorListener.onError()回调
        //必须在setDataSource()之后才能调用这些方法。
        new PrepareThread().start();
    }

    class PrepareThread extends Thread{
        @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
        @Override
        public void run() {
            super.run();
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(mediaUrl);
                //setDataSource()方法可能会抛出IOException异常。
                //在此时，mediaPlayer属于Initialized状态。可以调用上面得方法。
                mediaPlayer.setDisplay(surfaceView.getHolder());
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        if (null != mediaPlayer){
                            ll_loading.setVisibility(View.GONE);
                            mediaPlayer.start();
                            new ProgressThread().start();
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class ProgressThread extends Thread{
        @Override
        public void run() {
           while (null != mediaPlayer && mediaPlayer.isPlaying()){
               int currentProgress = mediaPlayer.getCurrentPosition();
               int maxLen = mediaPlayer.getDuration();

               Intent progressIntent = new Intent();
               progressIntent.setAction("play");
               progressIntent.putExtra("position",currentProgress);
               progressIntent.putExtra("max",maxLen);
               context.sendBroadcast(progressIntent);
               try {
                   sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        }
    }

    public BroadcastReceiver getReceiver(){
        return progressBroadCast;
    }

    class ProgressBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int currentPosition = intent.getIntExtra("position",0);
            int maxLength = intent.getIntExtra("max",0);
            seekBar1.setProgress(currentPosition);
            seekBar1.setMax(maxLength);
            setTime(currentPosition, maxLength);
        }
    }

    private void setTime(int currentPosition, int maxLength) {
        int cm = currentPosition / 1000 / 60;
        int cs = currentPosition / 1000 % 60;
        int mm = maxLength / 1000 / 60;
        int ms = maxLength / 1000 % 60;
        StringBuilder builder = new StringBuilder();
        builder.append(cm / 10).append(cm % 10).append(":")
                .append(cs / 10).append(cs % 10).append("/")
                .append(mm / 10).append(mm % 10).append(":")
                .append(ms / 10).append(ms % 10);
        tv_time.setText(builder.toString());
    }
}
