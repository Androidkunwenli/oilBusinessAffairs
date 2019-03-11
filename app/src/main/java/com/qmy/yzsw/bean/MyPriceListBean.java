package com.qmy.yzsw.bean;

public class MyPriceListBean {

    /**
     * ninetytwo (string, optional): 92#油价，-1无 ,
     * ninetyfive (string, optional): 95#油价，-1无 ,
     * ninetyeight (string, optional): 98#油价，-1无 ,
     * zero (string, optional): 0#油价，-1无 ,
     * ten (string, optional): -10#油价，-1无 ,
     * twenty (string, optional): -20#油价，-1无 ,
     * lng (string, optional): LNG价格，-1无 ,
     * cng (string, optional): CNG价格，-1无 ,
     * charging (string, optional): 充电桩价格，-1无
     */

    private String ninetytwo;
    private String ninetyfive;
    private String ninetyeight;
    private String zero;
    private String ten;
    private String twenty;
    private String lng;
    private String cng;
    private String charging;

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

    public String getCharging() {
        return charging;
    }

    public void setCharging(String charging) {
        this.charging = charging;
    }
}
