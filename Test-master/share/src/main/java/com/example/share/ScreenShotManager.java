package com.example.share;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;


public class ScreenShotManager {
    private Context mContext;
    /**
     * 内部存储器内容观察者
     */
    private ContentObserver mInternalObserver;
    /**
     * 外部存储器内容观察者
     */
    private ContentObserver mExternalObserver;

    //匹配各个手机截屏路径的关键字
    private static final String[] KEYWORDS = {
            "screenshot", "screenshots", "screen_shot", "screen-shot", "screen shot",
            "screencapture", "screen_capture", "screen-capture", "screen capture",
            "screencap", "screen_cap", "screen-cap", "screen cap", "截屏"
    };

    //读取媒体数据库时需要读取的列
    private static final String[] MEDIA_PROJECTIONS = {
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
    };

    public ScreenShotManager(Context context){
        this.mContext = context;
        initManger();
    }

    /**
     * 初始化
     */
    private void initManger() {
        if (mContext == null){
            return;
        }
        Handler handler = new Handler(mContext.getMainLooper());
        mInternalObserver = new MediaContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI, handler);
        mExternalObserver = new MediaContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,handler);
    }

    /**
     * 开始监听
     */
    public void startListener(){
        if (mContext == null){
            return;
        }
        mContext.getContentResolver().registerContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI,false,mInternalObserver);
        mContext.getContentResolver().registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,false,mExternalObserver);
    }

    /**
     * 注销监听
     */
    public void stopListener(){
        if (mContext == null){
            return;
        }
        mContext.getContentResolver().unregisterContentObserver(mInternalObserver);
        mContext.getContentResolver().unregisterContentObserver(mExternalObserver);
    }

    private class MediaContentObserver extends ContentObserver {
        private Uri mContentUri;
        public MediaContentObserver(Uri internalContentUri, Handler handler) {
            super(handler);
            this.mContentUri = internalContentUri;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    handleMediaContentChange(mContentUri);
                }
            }).start();
        }
    }

    /**
     * 处理监听到的事件
     * @param mContentUri
     */
    private void handleMediaContentChange(Uri mContentUri) {
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(
                    mContentUri,
                    MEDIA_PROJECTIONS,
                    null,
                    null,
                    MediaStore.Images.ImageColumns.DATE_ADDED + "desc limit 1"
            );
            if (cursor == null){
                return;
            }
            if (!cursor.moveToFirst()){
                return;
            }
            /**
             * 获取各列的索引
             */
            int dataIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            int dataData = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN);
            /**
             * 获取行数据
             */
            String data = cursor.getString(dataIndex);
            long dataTime = cursor.getLong(dataData);
            /**
             * 处理获取到的第一行数据
             */
            if (checkTime(dataTime)){
                handleMediaRowData(data);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 处理监听到的资源
     * @param data
     */
    private void handleMediaRowData(String data) {
        if (checkScreenShot(data)){
            if (TextUtils.isEmpty(data)){
                return;
            }
            Intent intent = new Intent(mContext,MainActivity.class);
            intent.putExtra("snapshot_path",data);
            mContext.startActivity(intent);
        }else {
            Log.e("zzf","Not screenshot event:" + data);
        }
    }

    /**
     * 判断是否截屏
     * @param data
     * @return
     */
    private boolean checkScreenShot(String data) {
        data = data.toLowerCase();
        for (String keyword:KEYWORDS){
            if (data.contains(keyword)){
                return true;
            }
        }
        return false;
    }

    /**
     * 检查是否大于当前时间五秒(兼容小米)，是则舍弃，反之亦然
     * @param dataTime
     * @return
     */
    private boolean checkTime(long dataTime) {
        return System.currentTimeMillis() - dataTime < 5*1000;
    }
}
