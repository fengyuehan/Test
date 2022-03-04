package com.example.otcchatkeyboard.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.File;
import java.util.ArrayList;

public class MsgBean implements MultiItemEntity {
    public final static int CHAT_SENDER_TEXT= 0;
    public final static int CHAT_SENDER_IMG = 1;

    public final static int CHAT_MSGTYPE_TEXT = 11;
    public final static int CHAT_MSGTYPE_IMG = 12;

    private String sender;
    private String recipient;
    private String id;
    private int msgType;
    private int senderType;
    private String time;
    private String image;
    private int imageWidth;
    private int imageHeight;
    private String name;
    private String content;

    private File file;
    private int fileLoadstye;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getSenderType() {
        return senderType;
    }

    public void setSenderType(int senderType) {
        this.senderType = senderType;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getFileLoadstye() {
        return fileLoadstye;
    }

    public void setFileLoadstye(int fileLoadstye) {
        this.fileLoadstye = fileLoadstye;
    }

    @Override
    public int getItemType() {
        return msgType;
    }

    public static class CommentRequestData {
        private int count;

        private int result;

        private ArrayList<MsgBean> data;

        public int getCount() {
            return count;
        }

        public int getResult() {
            return result;
        }

        public ArrayList<MsgBean> getData() {
            return data;
        }
    }
}
