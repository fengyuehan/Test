package com.example.customview.doodle;

import android.graphics.Path;

/**
 * 钢笔实体
 */
public class Pen extends IMGPath {

    /**
     * event的身份证
     */
    private int identity = Integer.MIN_VALUE;

    public void reset() {
        this.path.reset();
        this.identity = Integer.MIN_VALUE;
    }

    public void reset(float x, float y) {
        this.path.reset();
        this.path.moveTo(x, y);
        this.identity = Integer.MIN_VALUE;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public boolean isIdentity(int identity) {
        return this.identity == identity;
    }

    public void lineTo(float x, float y) {
        this.path.lineTo(x, y);
    }

    public boolean isEmpty() {
        return this.path.isEmpty();
    }

    public IMGPath toPath() {
        return new IMGPath(new Path(this.path), getMode(), getColor(), getWidth());
    }

}
