package com.qmy.yzsw.bean;

public class MyIndexBean {

    /**
     * headPortrait (string, optional): 用户头像 ,
     * nickname (string, optional): 昵称 ,
     * mobileNo (string, optional): 手机号码
     */

    private String headPortrait;
    private String nickname;
    private String mobileNo;

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
