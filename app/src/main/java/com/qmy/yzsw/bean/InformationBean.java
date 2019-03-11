package com.qmy.yzsw.bean;

public class InformationBean {

    /**
     * enterpriseName (string, optional): 企业名称 ,
     * enterpricePhone (string, optional): 联系电话 ,
     * job (string, optional): 职务 ,
     * enterpriceLeader (string, optional): 企业负责人 ,
     * leaderPhone (string, optional): 企业负责人电话 ,
     * gasolineMoney (string, optional): 汽油日交易额 ,
     * gasolineCount (string, optional): 汽油日交易数量 ,
     * dieseloilMoney (string, optional): 柴油日交易额 ,
     * dieseloilCount (string, optional): 柴油日交易数量 ,
     * supplierOne (string, optional): 进货渠道1 ,
     * supplierTwo (string, optional): 进货渠道2 ,
     * supplierThree (string, optional): 进货渠道3 ,
     * car (string, optional): 是否有洗车业务：1是；0否 ,
     * supermarket (string, optional): 是否有超市：1是；0否 ,
     * quickrepair (string, optional): 是否有快修：1是；0否 ,
     * fastfood (string, optional): 是否有快餐：1是；0否 ,
     * other (string, optional): 其它非油业务 ,
     * retailgasoline (string, optional): 汽油零售：1是；0否 ,
     * retaildieseloil (string, optional): 柴油零售：1是；0否 ,
     * wholesalegasoline (string, optional): 汽油批发：1是；0否 ,
     * wholesaledieseloil (string, optional): 柴油批发：1是；0否 ,
     * association (string, optional): 是否要加入成品油写好：1是；0否;2已是会员 ,
     * naturalgas (string, optional): 是否有增加天然气产品需求：1是；0否 ,
     * hascng (string, optional): 已有CNG：1是；0否 ,
     * haslcng (string, optional): 已有L-CNG：1是；0否 ,
     * haslng (string, optional): 已有LNG：1是；0否 ,
     * ev (string, optional): 是否有增加充电桩需求：1是；0否;2已有充电桩 ,
     * brand (string, optional): 加油机品牌 ,
     * brandcount (string, optional): 加油机数量 ,
     * knock (string, optional): 加油枪数量 ,
     * oiltankGasoline (string, optional): 汽油油罐数 ,
     * oiltankDieseloil (string, optional): 柴油油罐数 ,
     * gasolinestere (string, optional): 汽油总容积 ,
     * dieselstere (string, optional): 柴油总容积 ,
     * ninetytwo (string, optional): 92#油价，-1无 ,
     * ninetyfive (string, optional): 95#油价，-1无 ,
     * ninetyeight (string, optional): 98#油价，-1无 ,
     * zero (string, optional): 0#油价，-1无 ,
     * ten (string, optional): -10#油价，-1无 ,
     * twenty (string, optional): -20#油价，-1无 ,
     * lng (string, optional): lng价格，-1无 ,
     * cng (string, optional): cng价格，-1无 ,
     * charging (string, optional): 充电桩价格，-1无
     */

    private String enterpriseName;
    private String enterpricePhone;
    private String job;
    private String enterpriceLeader;
    private String leaderPhone;
    private String gasolineMoney;
    private String gasolineCount;
    private String dieseloilMoney;
    private String dieseloilCount;
    private String supplierOne;
    private String supplierTwo;
    private String supplierThree;
    private String car;
    private String supermarket;
    private String quickrepair;
    private String fastfood;
    private String other;
    private String retailgasoline = "0";
    private String retaildieseloil = "0";
    private String wholesalegasoline = "0";
    private String wholesaledieseloil = "0";
    private String association;
    private String naturalgas;
    private String hascng = "0";
    private String haslcng = "0";
    private String haslng = "0";
    private String ev;
    private String brand;
    private String brandcount;
    private String knock;
    private String oiltankGasoline;
    private String oiltankDieseloil;
    private String gasolinestere;
    private String dieselstere;
    private String ninetytwo;
    private String ninetyfive;
    private String ninetyeight;
    private String zero;
    private String ten;
    private String twenty;
    private String lng;
    private String cng;
    private String charging;
    private String oiladdress;
    private String approveRemark;

    public String getApproveRemark() {
        return approveRemark;
    }

    public void setApproveRemark(String approveRemark) {
        this.approveRemark = approveRemark;
    }

    public String getOiladdress() {
        return oiladdress;
    }

    public void setOiladdress(String oiladdress) {
        this.oiladdress = oiladdress;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpricePhone() {
        return enterpricePhone;
    }

    public void setEnterpricePhone(String enterpricePhone) {
        this.enterpricePhone = enterpricePhone;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getEnterpriceLeader() {
        return enterpriceLeader;
    }

    public void setEnterpriceLeader(String enterpriceLeader) {
        this.enterpriceLeader = enterpriceLeader;
    }

    public String getLeaderPhone() {
        return leaderPhone;
    }

    public void setLeaderPhone(String leaderPhone) {
        this.leaderPhone = leaderPhone;
    }

    public String getGasolineMoney() {
        return gasolineMoney;
    }

    public void setGasolineMoney(String gasolineMoney) {
        this.gasolineMoney = gasolineMoney;
    }

    public String getGasolineCount() {
        return gasolineCount;
    }

    public void setGasolineCount(String gasolineCount) {
        this.gasolineCount = gasolineCount;
    }

    public String getDieseloilMoney() {
        return dieseloilMoney;
    }

    public void setDieseloilMoney(String dieseloilMoney) {
        this.dieseloilMoney = dieseloilMoney;
    }

    public String getDieseloilCount() {
        return dieseloilCount;
    }

    public void setDieseloilCount(String dieseloilCount) {
        this.dieseloilCount = dieseloilCount;
    }

    public String getSupplierOne() {
        return supplierOne;
    }

    public void setSupplierOne(String supplierOne) {
        this.supplierOne = supplierOne;
    }

    public String getSupplierTwo() {
        return supplierTwo;
    }

    public void setSupplierTwo(String supplierTwo) {
        this.supplierTwo = supplierTwo;
    }

    public String getSupplierThree() {
        return supplierThree;
    }

    public void setSupplierThree(String supplierThree) {
        this.supplierThree = supplierThree;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getSupermarket() {
        return supermarket;
    }

    public void setSupermarket(String supermarket) {
        this.supermarket = supermarket;
    }

    public String getQuickrepair() {
        return quickrepair;
    }

    public void setQuickrepair(String quickrepair) {
        this.quickrepair = quickrepair;
    }

    public String getFastfood() {
        return fastfood;
    }

    public void setFastfood(String fastfood) {
        this.fastfood = fastfood;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getRetailgasoline() {
        return retailgasoline;
    }

    public void setRetailgasoline(String retailgasoline) {
        this.retailgasoline = retailgasoline;
    }

    public String getRetaildieseloil() {
        return retaildieseloil;
    }

    public void setRetaildieseloil(String retaildieseloil) {
        this.retaildieseloil = retaildieseloil;
    }

    public String getWholesalegasoline() {
        return wholesalegasoline;
    }

    public void setWholesalegasoline(String wholesalegasoline) {
        this.wholesalegasoline = wholesalegasoline;
    }

    public String getWholesaledieseloil() {
        return wholesaledieseloil;
    }

    public void setWholesaledieseloil(String wholesaledieseloil) {
        this.wholesaledieseloil = wholesaledieseloil;
    }

    public String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
    }

    public String getNaturalgas() {
        return naturalgas;
    }

    public void setNaturalgas(String naturalgas) {
        this.naturalgas = naturalgas;
    }

    public String getHascng() {
        return hascng;
    }

    public void setHascng(String hascng) {
        this.hascng = hascng;
    }

    public String getHaslcng() {
        return haslcng;
    }

    public void setHaslcng(String haslcng) {
        this.haslcng = haslcng;
    }

    public String getHaslng() {
        return haslng;
    }

    public void setHaslng(String haslng) {
        this.haslng = haslng;
    }

    public String getEv() {
        return ev;
    }

    public void setEv(String ev) {
        this.ev = ev;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrandcount() {
        return brandcount;
    }

    public void setBrandcount(String brandcount) {
        this.brandcount = brandcount;
    }

    public String getKnock() {
        return knock;
    }

    public void setKnock(String knock) {
        this.knock = knock;
    }

    public String getOiltankGasoline() {
        return oiltankGasoline;
    }

    public void setOiltankGasoline(String oiltankGasoline) {
        this.oiltankGasoline = oiltankGasoline;
    }

    public String getOiltankDieseloil() {
        return oiltankDieseloil;
    }

    public void setOiltankDieseloil(String oiltankDieseloil) {
        this.oiltankDieseloil = oiltankDieseloil;
    }

    public String getGasolinestere() {
        return gasolinestere;
    }

    public void setGasolinestere(String gasolinestere) {
        this.gasolinestere = gasolinestere;
    }

    public String getDieselstere() {
        return dieselstere;
    }

    public void setDieselstere(String dieselstere) {
        this.dieselstere = dieselstere;
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

    public String getCharging() {
        return charging;
    }

    public void setCharging(String charging) {
        this.charging = charging;
    }
}
