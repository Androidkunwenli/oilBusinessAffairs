package com.qmy.yzsw.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MySubmissionReportFormListBean implements Parcelable {
    private String id;
    private String name;
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.createTime);
    }

    public MySubmissionReportFormListBean() {
    }

    protected MySubmissionReportFormListBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.createTime = in.readString();
    }

    public static final Parcelable.Creator<MySubmissionReportFormListBean> CREATOR = new Parcelable.Creator<MySubmissionReportFormListBean>() {
        @Override
        public MySubmissionReportFormListBean createFromParcel(Parcel source) {
            return new MySubmissionReportFormListBean(source);
        }

        @Override
        public MySubmissionReportFormListBean[] newArray(int size) {
            return new MySubmissionReportFormListBean[size];
        }
    };
}
