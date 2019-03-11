package com.qmy.yzsw.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 拉货信息详情接口
 */
public class CarDetailBean implements Parcelable {

    /**
     * id (string, optional): id ,
     * enterpriseName (string, optional): 企业名称 ,
     * enterpriceLeader (string, optional): 企业负责人 ,
     * enterpricePhone (string, optional): 联系电话 ,
     * carModel (string, optional): 车型 ,
     * carLicense (string, optional): 车牌号 ,
     * img1 (string, optional): 行车证 ,
     * img2 (string, optional): 危品运输证 ,
     * otherRemark (string, optional): 其它备注 ,
     * ninetytwo (string, optional),
     * ninetyfive (string, optional),
     * ninetyeight (string, optional),
     * zero (string, optional),
     * ten (string, optional),
     * twenty (string, optional),
     * lng (string, optional),
     * cng (string, optional)
     */

    private String id;
    private String enterpriseName;
    private String enterpriceLeader;
    private String enterpricePhone;
    private String carModel;
    private String carLicense;
    private String img1;
    private String img2;
    private String otherRemark;
    private String ninetytwo;
    private String ninetyfive;
    private String ninetyeight;
    private String zero;
    private String ten;
    private String twenty;
    private String lng;
    private String cng;
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

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarLicense() {
        return carLicense;
    }

    public void setCarLicense(String carLicense) {
        this.carLicense = carLicense;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
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

    public CarDetailBean() {
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
        dest.writeString(this.carModel);
        dest.writeString(this.carLicense);
        dest.writeString(this.img1);
        dest.writeString(this.img2);
        dest.writeString(this.otherRemark);
        dest.writeString(this.ninetytwo);
        dest.writeString(this.ninetyfive);
        dest.writeString(this.ninetyeight);
        dest.writeString(this.zero);
        dest.writeString(this.ten);
        dest.writeString(this.twenty);
        dest.writeString(this.lng);
        dest.writeString(this.cng);
        dest.writeString(this.createTime);
        dest.writeString(this.carCapacity);
        dest.writeString(this.myself);
        dest.writeString(this.state);
    }

    protected CarDetailBean(Parcel in) {
        this.id = in.readString();
        this.enterpriseName = in.readString();
        this.enterpriceLeader = in.readString();
        this.enterpricePhone = in.readString();
        this.carModel = in.readString();
        this.carLicense = in.readString();
        this.img1 = in.readString();
        this.img2 = in.readString();
        this.otherRemark = in.readString();
        this.ninetytwo = in.readString();
        this.ninetyfive = in.readString();
        this.ninetyeight = in.readString();
        this.zero = in.readString();
        this.ten = in.readString();
        this.twenty = in.readString();
        this.lng = in.readString();
        this.cng = in.readString();
        this.createTime = in.readString();
        this.carCapacity = in.readString();
        this.myself = in.readString();
        this.state = in.readString();
    }

    public static final Creator<CarDetailBean> CREATOR = new Creator<CarDetailBean>() {
        @Override
        public CarDetailBean createFromParcel(Parcel source) {
            return new CarDetailBean(source);
        }

        @Override
        public CarDetailBean[] newArray(int size) {
            return new CarDetailBean[size];
        }
    };
}
