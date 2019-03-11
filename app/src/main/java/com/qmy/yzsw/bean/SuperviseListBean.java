package com.qmy.yzsw.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class SuperviseListBean implements Parcelable {

    /**
     * id (string, optional): id ,
     * oilName (string, optional): 油站名称 ,
     * oilAddress (string, optional): 油站地址 ,
     * myfiles1 (string, optional): 图片地址
     * }
     */

    private String id;
    private String oilName;
    private String oilAddress;
    private String myfiles1;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOilName() {
        return oilName;
    }

    public void setOilName(String oilName) {
        this.oilName = oilName;
    }

    public String getOilAddress() {
        return oilAddress;
    }

    public void setOilAddress(String oilAddress) {
        this.oilAddress = oilAddress;
    }

    public String getMyfiles1() {
        return myfiles1;
    }

    public void setMyfiles1(String myfiles1) {
        this.myfiles1 = myfiles1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.oilName);
        dest.writeString(this.oilAddress);
        dest.writeString(this.myfiles1);
    }

    public SuperviseListBean() {
    }

    protected SuperviseListBean(Parcel in) {
        this.id = in.readString();
        this.oilName = in.readString();
        this.oilAddress = in.readString();
        this.myfiles1 = in.readString();
    }

    public static final Parcelable.Creator<SuperviseListBean> CREATOR = new Parcelable.Creator<SuperviseListBean>() {
        @Override
        public SuperviseListBean createFromParcel(Parcel source) {
            return new SuperviseListBean(source);
        }

        @Override
        public SuperviseListBean[] newArray(int size) {
            return new SuperviseListBean[size];
        }
    };
}
