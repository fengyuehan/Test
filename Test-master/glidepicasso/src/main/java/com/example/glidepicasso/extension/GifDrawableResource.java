package com.example.glidepicasso.extension;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.resource.drawable.DrawableResource;
import com.hash.study.gif.FrameSequenceDrawable;

public class GifDrawableResource extends DrawableResource<FrameSequenceDrawable> {
    public GifDrawableResource(FrameSequenceDrawable drawable) {
        super(drawable);
    }

    @NonNull
    @Override
    public Class<FrameSequenceDrawable> getResourceClass() {
        return FrameSequenceDrawable.class;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public void recycle() {
        drawable.stop();
        drawable.destroy();
    }
}
