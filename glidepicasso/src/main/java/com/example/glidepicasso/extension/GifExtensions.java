package com.example.glidepicasso.extension;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideType;
import com.bumptech.glide.request.RequestOptions;
import com.hash.study.gif.FrameSequenceDrawable;

@GlideExtension
public class GifExtensions {
    /**
     * 构造方法一定要写私有的
     */
    private GifExtensions(){

    }
    private final static RequestOptions DECODE_TYPE = RequestOptions
            .decodeTypeOf(FrameSequenceDrawable.class)
            .lock();
    @GlideType(FrameSequenceDrawable.class)
    public static RequestBuilder<FrameSequenceDrawable> asGif2(RequestBuilder<FrameSequenceDrawable> requestBuilder){
        return requestBuilder.apply(DECODE_TYPE);
    }

}
