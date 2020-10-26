package com.example.jetpack.paging3;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import androidx.paging.PositionalDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * PositionalDataSource:主要用于加载数据有限的数据（加载本地数据库）
 * ItemKeyedDataSource:主要用来请求网络数据，它适用于通过当前页面最后一条数据的 id，作为下一页的数据的开始的位置
 * PageKeyedDataSource:也是用来请求网络数据，它适用于通过页码分页来请求数据。
 * 在 Paging3 之后 ItemKeyedDataSource、PageKeyedDataSource、PositionalDataSource 合并为一个 PagingSource，所有旧 API 加载方法被合并到 PagingSource 中的单个 load() 方法中。
 *
 */
public class DataSource extends PositionalDataSource<Student> {
    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<Student> callback) {
        callback.onResult(getStudents(0, 20),0,1000);
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<Student> callback) {
        callback.onResult(getStudents(params.startPosition,params.loadSize));
    }

    private List<Student> getStudents(int startPosition,int pageSize){
        List<Student> list = new ArrayList<>();
        for (int i = startPosition;i<startPosition +pageSize;i++){
            Student student = new Student();
            student.setId("ID：" + i);
            student.setName("名称：" + i);
            student.setGender("性别" + i);
            list.add(student);
        }
        return list;
    }
}
