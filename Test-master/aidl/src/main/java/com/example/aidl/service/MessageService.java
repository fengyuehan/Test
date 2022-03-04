package com.example.aidl.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.aidl.MessageSender;
import com.example.aidl.data.MessageModel;

import java.util.concurrent.atomic.AtomicBoolean;

import aidl.com.example.aidl.MessageReceiver;

public class MessageService extends Service {
   private static final String TAG = "MessageService";
   private AtomicBoolean serviceStop = new AtomicBoolean(false);
   private RemoteCallbackList<MessageReceiver> listener = new RemoteCallbackList<>();

   public MessageService(){

   }

   IBinder messageSender = new MessageSender.Stub() {
       @Override
       public void sendMessage(com.example.aidl.data.MessageModel messageModel) throws RemoteException {
           Log.e(TAG, "messageModel: " + messageModel.toString());
       }

       @Override
       public void registerReceiveListener(MessageReceiver messageReceiver) throws RemoteException {
            listener.register(messageReceiver);
       }

       @Override
       public void unregisterReceiveListener(MessageReceiver messageReceiver) throws RemoteException {
            listener.unregister(messageReceiver);
       }

       @Override
       public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
           String packageName = null;
           String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
           if (packages != null && packages.length > 0) {
               packageName = packages[0];
           }
           if (packageName == null || !packageName.startsWith("com.example.aidl")) {
               Log.d("onTransact", "拒绝调用：" + packageName);
               return false;
           }

           return super.onTransact(code, data, reply, flags);
       }
   };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //自定义permission方式检查权限
        if (checkCallingOrSelfPermission("com.example.aidl.permission.REMOTE_SERVICE_PERMISSION") == PackageManager.PERMISSION_DENIED) {
            return null;
        }
        return messageSender;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new FakeTCPTask()).start();
    }

    @Override
    public void onDestroy() {
        serviceStop.set(true);
        super.onDestroy();
    }

    private class FakeTCPTask implements Runnable{

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {
            while (!serviceStop.get()){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                MessageModel messageModel = new MessageModel();
                messageModel.setFrom("Service");
                messageModel.setTo("Client");
                messageModel.setContent(String.valueOf(System.currentTimeMillis()));
                int size = listener.beginBroadcast();
                Log.d(TAG, "listenerCount == " + listener);
                for (int i = 0;i < size;i++){
                    MessageReceiver messageReceiver = listener.getRegisteredCallbackItem(i);
                    if (messageReceiver != null){
                        try {
                            messageReceiver.onMessageReceived(messageModel);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
                listener.finishBroadcast();

            }
        }
    }
}
