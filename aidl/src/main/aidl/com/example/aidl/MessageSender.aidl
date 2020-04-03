// MessageSender.aidl
package com.example.aidl;

// Declare any non-default types here with import statements

interface MessageSender {
    void sendMessage(in MessageModel messageModel);

        void registerReceiveListener(MessageReceiver messageReceiver);

        void unregisterReceiveListener(MessageReceiver messageReceiver);
}
