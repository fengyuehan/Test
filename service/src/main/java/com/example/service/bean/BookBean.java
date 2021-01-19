package com.example.service.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : zhangzf
 * date   : 2021/1/18
 * desc   :
 */
public class BookBean implements Parcelable {

    public String bookName;
    public String bookAuthor;
    public double bookPrice;

    public BookBean() {
    }

    public BookBean(String bookName, String bookAuthor, double bookPrice) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookPrice = bookPrice;
    }

    @Override
    public String toString() {
        return "BookBean{" +
                "bookName='" + bookName + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookPrice=" + bookPrice +
                '}';
    }

    protected BookBean(Parcel in) {
        bookName = in.readString();
        bookAuthor = in.readString();
        bookPrice = in.readDouble();
    }

    public void readFromParcel(Parcel in) {
        this.bookName = in.readString();
        this.bookAuthor = in.readString();
        this.bookPrice = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookName);
        dest.writeString(bookAuthor);
        dest.writeDouble(bookPrice);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookBean> CREATOR = new Creator<BookBean>() {
        @Override
        public BookBean createFromParcel(Parcel in) {
            return new BookBean(in);
        }

        @Override
        public BookBean[] newArray(int size) {
            return new BookBean[size];
        }
    };
}
