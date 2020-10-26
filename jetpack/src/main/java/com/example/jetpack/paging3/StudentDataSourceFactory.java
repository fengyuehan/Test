package com.example.jetpack.paging3;

import androidx.annotation.NonNull;

public class StudentDataSourceFactory extends DataSource.Factory<Integer,Student> {
    @NonNull
    @Override
    public androidx.paging.DataSource<Integer, Student> create() {
        DataSource dataSource = new DataSource();
        return dataSource;
    }
}
