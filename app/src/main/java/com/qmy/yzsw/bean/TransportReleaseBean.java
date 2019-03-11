package com.qmy.yzsw.bean;

public class TransportReleaseBean {
    private String oil;
    private String oilStr;
    private String weight;
    private String[] data = new String[]{"92#", "95#", "98#", "0#", "-10#", "-20#", "LNG", "CNG"};
    private String[] dataStr = new String[]{"ninetytwo", "ninetyfive", "ninetyeight", "zero", "ten", "twenty", "lng", "cng"};

    public TransportReleaseBean() {
    }

    public TransportReleaseBean(String oil, String oilStr, String weight) {
        this.oil = oil;
        this.oilStr = oilStr;
        this.weight = weight;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public String[] getDataStr() {
        return dataStr;
    }

    public void setDataStr(String[] dataStr) {
        this.dataStr = dataStr;
    }


    public String getOil() {
        return oil;
    }

    public void setOil(String oil) {
        this.oil = oil;
    }

    public String getOilStr() {
        return oilStr;
    }

    public void setOilStr(String oilStr) {
        this.oilStr = oilStr;
    }

    public String getWeight() {
        return weight != null && !weight.isEmpty() ? weight : "";
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
