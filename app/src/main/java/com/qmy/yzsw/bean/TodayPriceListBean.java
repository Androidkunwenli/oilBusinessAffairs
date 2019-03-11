package com.qmy.yzsw.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class TodayPriceListBean implements Parcelable {

    /**
     * id (string, optional): id ,
     * oilName (string, optional): 有站名称 ,
     * oilAddress (string, optional): 地址 ,
     * oilPrice (string, optional): 单价 ,
     * distance (string, optional): 距离（米） ,
     * myself (string, optional): 是不是自己的价格：1是；0否 ,
     * longitude (string, optional): 经度 ,
     * latitude (string, optional): 纬度
     */

    private String id;
    private String oilName;
    private String oilAddress;
    private String oilPrice;
    private int distance;
    private String myself;
    private Double longitude;
    private Double latitude;


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

    public String getOilPrice() {
        return oilPrice;
    }

    public void setOilPrice(String oilPrice) {
        this.oilPrice = oilPrice;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getMyself() {
        return myself;
    }

    public void setMyself(String myself) {
        this.myself = myself;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public TodayPriceListBean() {
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
        dest.writeString(this.oilPrice);
        dest.writeInt(this.distance);
        dest.writeString(this.myself);
        dest.writeValue(this.longitude);
        dest.writeValue(this.latitude);
    }

    protected TodayPriceListBean(Parcel in) {
        this.id = in.readString();
        this.oilName = in.readString();
        this.oilAddress = in.readString();
        this.oilPrice = in.readString();
        this.distance = in.readInt();
        this.myself = in.readString();
        this.longitude = (Double) in.readValue(Double.class.getClassLoader());
        this.latitude = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Creator<TodayPriceListBean> CREATOR = new Creator<TodayPriceListBean>() {
        @Override
        public TodayPriceListBean createFromParcel(Parcel source) {
            return new TodayPriceListBean(source);
        }

        @Override
        public TodayPriceListBean[] newArray(int size) {
            return new TodayPriceListBean[size];
        }
    };
}
