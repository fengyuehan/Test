package com.example.glidepicasso.extension;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.hash.study.gif.BuildConfig;
import com.hash.study.gif.FrameSequenceDrawable;
import com.hash.study.gif.GifDecoder;

import java.io.IOException;
import java.io.InputStream;

public class GifResourceDecoder implements ResourceDecoder<InputStream, FrameSequenceDrawable> {

    private final FrameSequenceDrawable.BitmapProvider mBitmapProvider;

    public GifResourceDecoder(final BitmapPool bitmapPool) {
        /**
         * getDirty()与get()区别
         * get()：返回一个正好匹配给定宽、高和配置的只包含透明像素的Bitmap。如果BitmapPool中找不到这样的Bitmap，就返回null。
         * 由于该方法会擦除Bitmap中的所有像素，所以要比 getDirty(int, int, Bitmap.Config)方法略慢一点。
         * getDirty()：返回的任何非空Bitmap对象都可能包含未擦除的像素数据或随机数据。
         */
        this.mBitmapProvider = new FrameSequenceDrawable.BitmapProvider() {
            @Override
            public Bitmap acquireBitmap(int minWidth, int minHeight) {
                return bitmapPool.getDirty(minWidth,minHeight,Bitmap.Config.ARGB_8888);
            }

            @Override
            public void releaseBitmap(Bitmap bitmap) {
                bitmapPool.put(bitmap);
            }
        };
    }

    /**
     * 表示是否直接用改解码器处理该任务
     * @param source
     * @param options
     * @return
     * @throws IOException
     */
    @Override
    public boolean handles(@NonNull InputStream source, @NonNull Options options) throws IOException {
        return true;
    }

    /**
     * 将 GIF 的 InputStream 转为 GifDrawableResource
     */
    @Nullable
    @Override
    public Resource<FrameSequenceDrawable> decode(@NonNull InputStream source, int width, int height, @NonNull Options options) throws IOException {
        GifDecoder gifDecoder = GifDecoder.decodeStream(source);
        if (gifDecoder == null){
            return null;
        }

        int inSampleSize = calcSampleSize(gifDecoder.getWidth(),gifDecoder.getHeight(),width,height);
        FrameSequenceDrawable drawable = new FrameSequenceDrawable(gifDecoder,mBitmapProvider,inSampleSize);
        // 圆形Gif显示开关
        drawable.setCircleMaskEnabled(false);
        return new GifDrawableResource(drawable);
    }

    private int calcSampleSize(int sourceWidth, int sourceHeight, int requestedWidth, int requestedHeight) {
        int exactSampleSize = Math.min(sourceWidth / requestedWidth,
                sourceHeight / requestedHeight);
        int powerOfTwoSampleSize = exactSampleSize == 0 ? 0 : Integer.highestOneBit(exactSampleSize);
        // Although functionally equivalent to 0 for BitmapFactory, 1 is a safer default for our code
        // than 0.
        int sampleSize = Math.max(1, powerOfTwoSampleSize);
        if (BuildConfig.DEBUG) {
            Log.i("zzf", "Downsampling GIF"
                    + ", sampleSize: " + sampleSize
                    + ", target dimens: [" + requestedWidth + "x" + requestedHeight + "]"
                    + ", actual dimens: [" + sourceWidth + "x" + sourceHeight + "]"
            );
        }
        return sampleSize;
    }
}
