package com.example.notificaitondemo;

import android.graphics.Bitmap;

/**
 * @ClassName NotificationContentWrapper
 * @Description TODO
 * @Author user
 * @Date 2019/9/16
 * @Version 1.0
 */
public class NotificationContentWrapper {
    public Bitmap bitmap;
    public String title;
    public String summery;

    public NotificationContentWrapper(Bitmap bitmap, String title, String summery) {
        this.bitmap = bitmap;
        this.title = title;
        this.summery = summery;
    }
}
