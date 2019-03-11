package com.qmy.yzsw.bean;

public class TransportMsgBean {
    private String oilName;
    private String weight;

    public TransportMsgBean(String oilName, String weight) {
        this.oilName = oilName;
        this.weight = weight;
    }

    public String getOilName() {
        return oilName;
    }

    public void setOilName(String oilName) {
        this.oilName = oilName;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
