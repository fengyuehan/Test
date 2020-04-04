// MessageSender.aidl
package com.example.aidl;

// Declare any non-default types here with import statements
import com.example.aidl.data.MessageModel;
import com.example.aidl.MessageReceiver;

interface MessageSender {
    void sendMessage(in MessageModel messageModel);

    void registerReceiveListener(MessageReceiver messageReceiver);

    void unregisterReceiveListener(MessageReceiver messageReceiver);
}
