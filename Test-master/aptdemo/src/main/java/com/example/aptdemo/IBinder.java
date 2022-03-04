package com.example.aptdemo;

/**
 *用来给用户绑定activity
 */
public interface IBinder<T> {
    void bind(T target);
}
