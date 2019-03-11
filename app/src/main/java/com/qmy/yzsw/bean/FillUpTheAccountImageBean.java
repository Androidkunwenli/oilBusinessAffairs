package com.qmy.yzsw.bean;

public class FillUpTheAccountImageBean {
    private String imagePath;
    private String imageupload;
    private String tvtitlestr;
    private int type;
    private boolean isAdd = false;

    public FillUpTheAccountImageBean(String imageupload, String tvtitlestr, int type, boolean isAdd) {
        this.imageupload = imageupload;
        this.tvtitlestr = tvtitlestr;
        this.type = type;
        this.isAdd = isAdd;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public FillUpTheAccountImageBean() {
    }

    public FillUpTheAccountImageBean(String imageupload, String tvtitlestr, int type) {
        this.imageupload = imageupload;
        this.tvtitlestr = tvtitlestr;
        this.type = type;
    }

    public String getImageupload() {
        return imageupload;
    }

    public void setImageupload(String imageupload) {
        this.imageupload = imageupload;
    }

    public String getTvtitlestr() {
        return tvtitlestr;
    }

    public void setTvtitlestr(String tvtitlestr) {
        this.tvtitlestr = tvtitlestr;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
