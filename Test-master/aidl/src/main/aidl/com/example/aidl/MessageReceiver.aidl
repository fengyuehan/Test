// MessageReceiver.aidl
package aidl.com.example.aidl;

// Declare any non-default types here with import statements
import com.example.aidl.data.MessageModel;
interface MessageReceiver {
    void onMessageReceived(in MessageModel receivedMessage);
}
