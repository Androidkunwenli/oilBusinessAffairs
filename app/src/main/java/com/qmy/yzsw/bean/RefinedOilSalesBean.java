package com.qmy.yzsw.bean;

public class RefinedOilSalesBean {
    private String oilLabel = "";
    private String oilSalesVolume = "";
    private String oilUnitPrice = "";
    private String oilTotalPrice = "";

    public RefinedOilSalesBean() {
    }

    public RefinedOilSalesBean(String oilLabel, String oilSalesVolume, String oilUnitPrice, String oilTotalPrice) {
        this.oilLabel = oilLabel;
        this.oilSalesVolume = oilSalesVolume;
        this.oilUnitPrice = oilUnitPrice;
        this.oilTotalPrice = oilTotalPrice;
    }

    public String getOilLabel() {
        return oilLabel;
    }

    public void setOilLabel(String oilLabel) {
        this.oilLabel = oilLabel;
    }

    public String getOilSalesVolume() {
        return oilSalesVolume;
    }

    public void setOilSalesVolume(String oilSalesVolume) {
        this.oilSalesVolume = oilSalesVolume;
    }

    public String getOilUnitPrice() {
        return oilUnitPrice;
    }

    public void setOilUnitPrice(String oilUnitPrice) {
        this.oilUnitPrice = oilUnitPrice;
    }

    public String getOilTotalPrice() {
        return oilTotalPrice;
    }

    public void setOilTotalPrice(String oilTotalPrice) {
        this.oilTotalPrice = oilTotalPrice;
    }
}
