package com.example.aidl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.aidl.data.MessageModel;
import com.example.aidl.service.MessageService;

import aidl.com.example.aidl.MessageReceiver;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MessageSender messageSender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpService();
    }

    private void setUpService() {
        Intent intent = new Intent(this, MessageService.class);
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
        //startService(intent);
    }

    private MessageReceiver messageReceiver = new MessageReceiver() {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onMessageReceived(MessageModel receivedMessage) throws RemoteException {
            Log.d(TAG, "onMessageReceived: " + receivedMessage.toString());
        }
    };

    //判断是否
    IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (messageSender != null){
                messageSender.asBinder().unlinkToDeath(this,0);
                messageSender = null;
            }
            setUpService();
        }
    };
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messageSender = MessageSender.Stub.asInterface(service);
            MessageModel messageModel = new MessageModel();
            messageModel.setFrom("client user id");
            messageModel.setTo("receiver user id");
            messageModel.setContent("this  is message content");
            try {
                messageSender.asBinder().linkToDeath(deathRecipient,0);
                messageSender.registerReceiveListener(messageReceiver);
                messageSender.sendMessage(messageModel);

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        if (messageSender != null && messageSender.asBinder().isBinderAlive()){
            try {
                messageSender.unregisterReceiveListener(messageReceiver);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(serviceConnection);
        super.onDestroy();
    }
}
