package com.qmy.yzsw.bean;

public class SmsBean {

    /**
     * smsId (string, optional): 消息id ,
     * smsType (string, optional): 消息类型 ,
     * smsContent (string, optional): 消息内容
     */

    private String smsId;
    private String smsType;
    private String smsContent;
    private String sendTime;

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSmsId() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }
}
