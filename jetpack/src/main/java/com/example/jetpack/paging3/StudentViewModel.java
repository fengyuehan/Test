package com.example.jetpack.paging3;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class StudentViewModel extends ViewModel {

    private LiveData<PagedList<Student>> listLiveData;

    public StudentViewModel(){
        StudentDataSourceFactory studentDataSourceFactory = new StudentDataSourceFactory();
        this.listLiveData = new LivePagedListBuilder<Integer,Student>(studentDataSourceFactory,20).build();
    }

    public LiveData<PagedList<Student>> getListLiveData(){
        return listLiveData;
    }
}
