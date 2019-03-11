package com.qmy.yzsw.bean;

public class LeaseInfoBean {

    /**
     * id (integer, optional): id ,
     * oilname (string, optional): 油站名称 ,
     * provinceName (string, optional): 省名称 ,
     * cityName (string, optional): 市名称 ,
     * countyName (string, optional): 区县名称 ,
     * oiladdress (string, optional): 详细地址 ,
     * leasetime (string, optional): 租赁时间 ,
     * leasemoney (string, optional): 价格 ,
     * contacts (string, optional): 联系人 ,
     * contactphone (string, optional): 联系电话 ,
     * picture1 (string, optional): 图片1 ,
     * picture2 (string, optional): 图片2
     */

    private int id;
    private String oilname;
    private String provinceName;
    private String cityName;
    private String countyName;
    private String oiladdress;
    private String leasetime;
    private String leasemoney;
    private String contacts;
    private String contactphone;
    private String picture1;
    private String picture2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOilname() {
        return oilname;
    }

    public void setOilname(String oilname) {
        this.oilname = oilname;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getOiladdress() {
        return oiladdress;
    }

    public void setOiladdress(String oiladdress) {
        this.oiladdress = oiladdress;
    }

    public String getLeasetime() {
        return leasetime;
    }

    public void setLeasetime(String leasetime) {
        this.leasetime = leasetime;
    }

    public String getLeasemoney() {
        return leasemoney;
    }

    public void setLeasemoney(String leasemoney) {
        this.leasemoney = leasemoney;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactphone() {
        return contactphone;
    }

    public void setContactphone(String contactphone) {
        this.contactphone = contactphone;
    }

    public String getPicture1() {
        return picture1;
    }

    public void setPicture1(String picture1) {
        this.picture1 = picture1;
    }

    public String getPicture2() {
        return picture2;
    }

    public void setPicture2(String picture2) {
        this.picture2 = picture2;
    }
}
