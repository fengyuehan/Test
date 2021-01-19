package com.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.service.bean.BookBean;

import java.util.ArrayList;
import java.util.List;

/**
 * author : zhangzf
 * date   : 2021/1/18
 * desc   :
 */
public class RemoteBinderService extends Service {
    private RemoteBookBinderImpl remoteBookBinder;
    public static final String SERVICE_NAME = "zzf";

    @Override
    public void onCreate() {
        Log.i("zzf"," onCreate()" + currentProgressAndThread());
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        remoteBookBinder = new RemoteBookBinderImpl();
        return remoteBookBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public class RemoteBookBinderImpl extends IRemoteBookBinder.Stub{
        private List<BookBean> bookBeans = new ArrayList<>();
        @Override
        public void addBookIn(BookBean bookBean) throws RemoteException {
            Logger.i("------------------------服务端修改前的数据----------------------");
            Logger.i("" + bookBean.toString() + currentProgressAndThread());
            Logger.i("--------------------------------------------------------------");
            bookBean.bookAuthor = "zzf";
            bookBeans.add(bookBean);
            Logger.i("------------------------服务端修改后的数据----------------------");
            Logger.i("" + bookBean.toString() + currentProgressAndThread());
            Logger.i("--------------------------------------------------------------");
        }

        @Override
        public void addBookOut(BookBean bookBean) throws RemoteException {
            Logger.i("------------------------服务端修改前的数据----------------------");
            Logger.i("" + bookBean.toString() + currentProgressAndThread());
            Logger.i("--------------------------------------------------------------");
            bookBean.bookAuthor = "zzf";
            bookBeans.add(bookBean);
            Logger.i("------------------------服务端修改后的数据----------------------");
            Logger.i("" + bookBean.toString() + currentProgressAndThread());
            Logger.i("--------------------------------------------------------------");
        }

        @Override
        public void addBookInOut(BookBean bookBean) throws RemoteException {
            Logger.i("------------------------服务端修改前的数据----------------------");
            Logger.i("" + bookBean.toString() + currentProgressAndThread());
            Logger.i("--------------------------------------------------------------");
            bookBean.bookAuthor = "zzf";
            bookBeans.add(bookBean);
            Logger.i("------------------------服务端修改后的数据----------------------");
            Logger.i("" + bookBean.toString() + currentProgressAndThread());
            Logger.i("--------------------------------------------------------------");
        }

        @Override
        public List<BookBean> getBookList() throws RemoteException {
            if (ListUtils.notEmpty(bookBeans)) {
                Logger.i("-------- 书本总数: " + bookBeans.size() + " ------------");
                for (BookBean bookBean : bookBeans) {
                    Logger.i("BookList => " + bookBean);
                }
                Logger.i("-----------------------------------");
            }
            return bookBeans;
        }
    }
    private String currentProgressAndThread() {
        return " ,Progress Name: " + ProgressUtils.getProcessName(this) + " ,Thread Name： " + Thread.currentThread().getName();
    }
}
