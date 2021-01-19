package com.example.service;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.service.bean.BookBean;
import com.example.service.callback.CalculateResultActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btRemoteStart;
    private Button btRemoteStop;

    private Button btRemoteBind;
    private Button btAddBook;
    private Button btGetBook;
    private Button btRemoteUnbind;
    private Button btn_callback;


    // 绑定服务连接对象
    private IRemoteBookBinder iRemoteBookBinder;
    private ServiceConnection bindConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iRemoteBookBinder = IRemoteBookBinder.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iRemoteBookBinder = null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remote_service_activity);
        initView();
        initListener();
    }

    private void initListener() {
        btRemoteStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent();
                intent.setAction("com.renj.remote.start");
                intent.setPackage("com.renj.service");
                startService(intent);*/
            }
        });
        btRemoteStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent();
                intent.setAction("com.renj.remote.start");
                intent.setPackage("com.renj.service");
                stopService(intent);*/
            }
        });
        btRemoteBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //Intent intent1 = new Intent(MainActivity.this,RemoteBinderService.class);
                intent.setAction("com.renj.remote.binder");
                intent.setPackage("com.example.service");
                bindService(intent, bindConnection, Service.BIND_AUTO_CREATE);
            }
        });
        btAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iRemoteBookBinder == null) {
                    Toast.makeText(MainActivity.this,"请先绑定服务",Toast.LENGTH_LONG).show();
                    return;
                }

                try {
                    // addBookIn
                    Logger.i("-------- addBookIn() ------------------------");
                    int nextIntIn = RandomUtils.randomInt(10000);
                    BookBean bookBeanIn = new BookBean("书名-" + nextIntIn, "作者-" + nextIntIn,
                            (RandomUtils.randomInt(10000) + 10000) / 100d);
                    Logger.i(RemoteBinderService.SERVICE_NAME + " addBookIn() before info：" + bookBeanIn.toString());
                    iRemoteBookBinder.addBookIn(bookBeanIn);
                    Logger.i(RemoteBinderService.SERVICE_NAME + " addBookIn() after info：" + bookBeanIn.toString());

                    // addBookOut
                    Logger.i("-------- addBookOut() ------------------------");
                    int nextIntOut = RandomUtils.randomInt(10000);
                    BookBean bookBeanOut = new BookBean("书名-" + nextIntOut, "作者-" + nextIntOut,
                            (RandomUtils.randomInt(10000) + 10000) / 100d);
                    Logger.i(RemoteBinderService.SERVICE_NAME + " addBookOut() before info：" + bookBeanOut.toString());
                    iRemoteBookBinder.addBookOut(bookBeanOut);
                    Logger.i(RemoteBinderService.SERVICE_NAME + " addBookOut() after info：" + bookBeanOut.toString());

                    // addBookInOut
                    Logger.i("-------- addBookInOut() ------------------------");
                    int nextIntInOut = RandomUtils.randomInt(10000);
                    BookBean bookBeanInOut = new BookBean("书名-" + nextIntInOut, "作者-" + nextIntInOut,
                            (RandomUtils.randomInt(10000) + 10000) / 100d);
                    Logger.i(RemoteBinderService.SERVICE_NAME + " addBookInOut() before info：" + bookBeanInOut.toString());
                    iRemoteBookBinder.addBookInOut(bookBeanInOut);
                    Logger.i(RemoteBinderService.SERVICE_NAME + " addBookInOut() after info：" + bookBeanInOut.toString());

                    Logger.i("-------------------------------------------------");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        btGetBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iRemoteBookBinder == null) {
                    Toast.makeText(MainActivity.this,"请先绑定服务",Toast.LENGTH_LONG).show();
                    return;
                }

                List<BookBean> bookList = null;
                try {
                    bookList = iRemoteBookBinder.getBookList();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                if (ListUtils.notEmpty(bookList)) {
                    Toast.makeText(MainActivity.this,"书本总数: " + bookList.size(),Toast.LENGTH_LONG).show();
                    Logger.i("-------- 书本总数: " + bookList.size() + " ------------");
                    for (BookBean bookBean : bookList) {
                        Logger.i("BookList => " + bookBean);
                    }
                    Logger.i("-----------------------------------");
                } else {

                    Toast.makeText(MainActivity.this,"没有数据",Toast.LENGTH_LONG).show();
                }
            }
        });
        btRemoteUnbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iRemoteBookBinder == null) {
                    Toast.makeText(MainActivity.this,"服务未绑定或已解绑",Toast.LENGTH_LONG).show();
                    return;
                }
                unbindService(bindConnection);
                iRemoteBookBinder = null;
            }
        });
        btn_callback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CalculateResultActivity.class));
            }
        });
    }

    private void initView() {
        btRemoteStart = findViewById(R.id.bt_remote_start);
        btRemoteStop = findViewById(R.id.bt_remote_stop);

        btRemoteBind = findViewById(R.id.bt_remote_bind);
        btAddBook = findViewById(R.id.bt_add_book);
        btGetBook = findViewById(R.id.bt_get_book);
        btRemoteUnbind = findViewById(R.id.bt_remote_unbind);
        btn_callback = findViewById(R.id.btn_callback);
    }
}