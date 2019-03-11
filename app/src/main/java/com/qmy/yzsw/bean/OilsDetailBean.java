package com.qmy.yzsw.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class OilsDetailBean implements Parcelable {

    /**
     * id (string, optional): id ,
     * enterpriseName (string, optional): 企业名称 ,
     * enterpriceLeader (string, optional): 企业负责人 ,
     * enterpricePhone (string, optional): 联系电话 ,
     * otherRemark (string, optional): 其它备注 ,
     * ninetytwo (string, optional),
     * ninetyfive (string, optional),
     * ninetyeight (string, optional),
     * zero (string, optional),
     * ten (string, optional),
     * twenty (string, optional),
     * lng (string, optional),
     * cng (string, optional),
     * pickUpAddr (string, optional): 取货地址 ,
     * sendAddr (string, optional): 取货地址
     */

    private String id;
    private String enterpriseName;
    private String enterpriceLeader;
    private String enterpricePhone;
    private String otherRemark;
    private String ninetytwo;
    private String ninetyfive;
    private String ninetyeight;
    private String zero;
    private String ten;
    private String twenty;
    private String lng;
    private String cng;
    private String pickUpAddr;
    private String sendAddr;
    private String createTime;
    private String carCapacity;
    private String myself;
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMyself() {
        return myself;
    }

    public void setMyself(String myself) {
        this.myself = myself;
    }

    public String getCarCapacity() {
        return carCapacity;
    }

    public void setCarCapacity(String carCapacity) {
        this.carCapacity = carCapacity;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpriceLeader() {
        return enterpriceLeader;
    }

    public void setEnterpriceLeader(String enterpriceLeader) {
        this.enterpriceLeader = enterpriceLeader;
    }

    public String getEnterpricePhone() {
        return enterpricePhone;
    }

    public void setEnterpricePhone(String enterpricePhone) {
        this.enterpricePhone = enterpricePhone;
    }

    public String getOtherRemark() {
        return otherRemark;
    }

    public void setOtherRemark(String otherRemark) {
        this.otherRemark = otherRemark;
    }

    public String getNinetytwo() {
        return ninetytwo;
    }

    public void setNinetytwo(String ninetytwo) {
        this.ninetytwo = ninetytwo;
    }

    public String getNinetyfive() {
        return ninetyfive;
    }

    public void setNinetyfive(String ninetyfive) {
        this.ninetyfive = ninetyfive;
    }

    public String getNinetyeight() {
        return ninetyeight;
    }

    public void setNinetyeight(String ninetyeight) {
        this.ninetyeight = ninetyeight;
    }

    public String getZero() {
        return zero;
    }

    public void setZero(String zero) {
        this.zero = zero;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getTwenty() {
        return twenty;
    }

    public void setTwenty(String twenty) {
        this.twenty = twenty;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getCng() {
        return cng;
    }

    public void setCng(String cng) {
        this.cng = cng;
    }

    public String getPickUpAddr() {
        return pickUpAddr;
    }

    public void setPickUpAddr(String pickUpAddr) {
        this.pickUpAddr = pickUpAddr;
    }

    public String getSendAddr() {
        return sendAddr;
    }

    public void setSendAddr(String sendAddr) {
        this.sendAddr = sendAddr;
    }

    public OilsDetailBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.enterpriseName);
        dest.writeString(this.enterpriceLeader);
        dest.writeString(this.enterpricePhone);
        dest.writeString(this.otherRemark);
        dest.writeString(this.ninetytwo);
        dest.writeString(this.ninetyfive);
        dest.writeString(this.ninetyeight);
        dest.writeString(this.zero);
        dest.writeString(this.ten);
        dest.writeString(this.twenty);
        dest.writeString(this.lng);
        dest.writeString(this.cng);
        dest.writeString(this.pickUpAddr);
        dest.writeString(this.sendAddr);
        dest.writeString(this.createTime);
        dest.writeString(this.carCapacity);
        dest.writeString(this.myself);
        dest.writeString(this.state);
    }

    protected OilsDetailBean(Parcel in) {
        this.id = in.readString();
        this.enterpriseName = in.readString();
        this.enterpriceLeader = in.readString();
        this.enterpricePhone = in.readString();
        this.otherRemark = in.readString();
        this.ninetytwo = in.readString();
        this.ninetyfive = in.readString();
        this.ninetyeight = in.readString();
        this.zero = in.readString();
        this.ten = in.readString();
        this.twenty = in.readString();
        this.lng = in.readString();
        this.cng = in.readString();
        this.pickUpAddr = in.readString();
        this.sendAddr = in.readString();
        this.createTime = in.readString();
        this.carCapacity = in.readString();
        this.myself = in.readString();
        this.state = in.readString();
    }

    public static final Creator<OilsDetailBean> CREATOR = new Creator<OilsDetailBean>() {
        @Override
        public OilsDetailBean createFromParcel(Parcel source) {
            return new OilsDetailBean(source);
        }

        @Override
        public OilsDetailBean[] newArray(int size) {
            return new OilsDetailBean[size];
        }
    };
}
