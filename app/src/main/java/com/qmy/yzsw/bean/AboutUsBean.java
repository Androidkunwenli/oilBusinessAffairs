package com.qmy.yzsw.bean;

public class AboutUsBean {

    /**
     * sysName (string, optional): 企业名称 ,
     * sysLink (string, optional): 企业电话 ,
     * sysWchat (string, optional): 企业公众号 ,
     * sysWebUrl (string, optional): 企业网站 ,
     * sysAddress (string, optional): 企业地址
     */
    /**
     * sysRemark : 油中商务APP是一款针对油站管理及服务行业上下游协同发展的智能服务平台。我们以油站为核心，以“智慧油站线上线下融合新生态”的全新理念，致力于企业、人、服务商等整个体系间更快的连接、进化和智能化，从而引领油品行业发展的未来。
     * sysLogoUrl : http://47.93.62.197:8080/yzsw_imgs/logo.png
     */
    private String sysName;
    private String sysLink;
    private String sysWchat;
    private String sysWebUrl;
    private String sysAddress;
    private String sysRemark;
    private String sysLogoUrl;

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getSysLink() {
        return sysLink;
    }

    public void setSysLink(String sysLink) {
        this.sysLink = sysLink;
    }

    public String getSysWchat() {
        return sysWchat;
    }

    public void setSysWchat(String sysWchat) {
        this.sysWchat = sysWchat;
    }

    public String getSysWebUrl() {
        return sysWebUrl;
    }

    public void setSysWebUrl(String sysWebUrl) {
        this.sysWebUrl = sysWebUrl;
    }

    public String getSysAddress() {
        return sysAddress;
    }

    public void setSysAddress(String sysAddress) {
        this.sysAddress = sysAddress;
    }

    public String getSysRemark() {
        return sysRemark;
    }

    public void setSysRemark(String sysRemark) {
        this.sysRemark = sysRemark;
    }

    public String getSysLogoUrl() {
        return sysLogoUrl;
    }

    public void setSysLogoUrl(String sysLogoUrl) {
        this.sysLogoUrl = sysLogoUrl;
    }
}
