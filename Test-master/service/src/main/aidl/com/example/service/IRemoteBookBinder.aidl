// IRemoteBookBinder.aidl
package com.example.service;

import com.example.service.bean.BookBean;
import java.util.List;


interface IRemoteBookBinder {
    void addBookIn(in BookBean bookBean);

    void addBookOut(out BookBean bookBean);

    void addBookInOut(inout BookBean bookBean);

    List<BookBean> getBookList();
}