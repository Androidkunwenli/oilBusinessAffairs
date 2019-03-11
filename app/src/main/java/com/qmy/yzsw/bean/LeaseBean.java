package com.qmy.yzsw.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class LeaseBean implements Parcelable {

    /**
     * id (string, optional): id ,
     * imgUrl (string, optional): 图片地址 ,
     * name (string, optional): 名称 ,
     * address (string, optional): 地址 ,
     * publishTime (string, optional): 发布时间 ,
     * approveStatus (integer, optional): 审核状态：1审核通过；0待审核;-1拒绝通过 ,
     * myself (string, optional): 是否自己：1是；0否
     */

    private String id;
    private String imgUrl;
    private String name;
    private String address;
    private String publishTime;
    private String approveStatus;
    private String leasetype1;
    private String myself;

    public String getLeasetype1() {
        return leasetype1;
    }

    public void setLeasetype1(String leasetype1) {
        this.leasetype1 = leasetype1;
    }

    public String getMyself() {
        return myself;
    }

    public void setMyself(String myself) {
        this.myself = myself;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public LeaseBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.imgUrl);
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeString(this.publishTime);
        dest.writeString(this.approveStatus);
        dest.writeString(this.leasetype1);
        dest.writeString(this.myself);
    }

    protected LeaseBean(Parcel in) {
        this.id = in.readString();
        this.imgUrl = in.readString();
        this.name = in.readString();
        this.address = in.readString();
        this.publishTime = in.readString();
        this.approveStatus = in.readString();
        this.leasetype1 = in.readString();
        this.myself = in.readString();
    }

    public static final Creator<LeaseBean> CREATOR = new Creator<LeaseBean>() {
        @Override
        public LeaseBean createFromParcel(Parcel source) {
            return new LeaseBean(source);
        }

        @Override
        public LeaseBean[] newArray(int size) {
            return new LeaseBean[size];
        }
    };
}
