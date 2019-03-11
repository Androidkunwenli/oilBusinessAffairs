package com.qmy.yzsw.bean;

public class VehicleUreaBean {

    /**
     * id : string
     * imcomeDate : 2018-10-15T01:07:05.005Z
     * providerName : string
     * providerArea : string
     * brand : string
     * spec : 0
     * specUnit : 0
     * incomeNum : 0
     * incomeUnit : 0
     * grade : string
     * batchNo : string
     * qualificationNo : string
     * licenseNo : string
     * remark : string
     * invoiceImg : string
     * qualificationImg : string
     * otherImgs : string
     * createUser : string
     * createTime : 2018-10-15T01:07:05.005Z
     */

    private String id;
    private String imcomeDate;
    private String providerName;
    private String providerArea;
    private String brand;
    private String spec;
    private String specUnit;
    private String incomeNum;
    private String incomeUnit;
    private String grade;
    private String batchNo;
    private String qualificationNo;
    private String licenseNo;
    private String remark;
    private String invoiceImg = "";
    private String qualificationImg = "";
    private String otherImgs;
    private String createUser;
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImcomeDate() {
        return imcomeDate;
    }

    public void setImcomeDate(String imcomeDate) {
        this.imcomeDate = imcomeDate;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderArea() {
        return providerArea;
    }

    public void setProviderArea(String providerArea) {
        this.providerArea = providerArea;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getSpecUnit() {
        return specUnit;
    }

    public void setSpecUnit(String specUnit) {
        this.specUnit = specUnit;
    }

    public String getIncomeNum() {
        return incomeNum;
    }

    public void setIncomeNum(String incomeNum) {
        this.incomeNum = incomeNum;
    }

    public String getIncomeUnit() {
        return incomeUnit;
    }

    public void setIncomeUnit(String incomeUnit) {
        this.incomeUnit = incomeUnit;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getQualificationNo() {
        return qualificationNo;
    }

    public void setQualificationNo(String qualificationNo) {
        this.qualificationNo = qualificationNo;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getInvoiceImg() {
        return invoiceImg != null && !invoiceImg.isEmpty() ? invoiceImg : "";
    }

    public void setInvoiceImg(String invoiceImg) {
        this.invoiceImg = invoiceImg;
    }

    public String getQualificationImg() {
        return qualificationImg != null && !qualificationImg.isEmpty() ? qualificationImg : "";
    }

    public void setQualificationImg(String qualificationImg) {
        this.qualificationImg = qualificationImg;
    }

    public String getOtherImgs() {
        return otherImgs;
    }

    public void setOtherImgs(String otherImgs) {
        this.otherImgs = otherImgs;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
