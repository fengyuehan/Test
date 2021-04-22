package com.example.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : zhangzf
 * date   : 2021/4/8
 * desc   :
 */
public class NameBean implements Parcelable {
    private String name;

    public NameBean(String name) {
        this.name = name;
    }

    protected NameBean(Parcel in) {
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NameBean> CREATOR = new Creator<NameBean>() {
        @Override
        public NameBean createFromParcel(Parcel in) {
            return new NameBean(in);
        }

        @Override
        public NameBean[] newArray(int size) {
            return new NameBean[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
