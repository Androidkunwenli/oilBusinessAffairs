package com.qmy.yzsw.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class NewDetailBean implements Parcelable {


    /**
     newsId (string, optional): 新闻id ,
     title (string, optional): 新闻标题 ,
     addtime (string, optional): 发布时间 ,
     imgsUrl (string, optional): 新闻图片地址列表，英文逗号分隔 ,
     content (string, optional): 新闻内容 ,
     viewCnt (string, optional): 新闻浏览次数 ,
     isCollect (string, optional): 是否已经收藏：1收藏；0否
     */

    private String newsId;
    private String title;
    private String addtime;
    private String imgsUrl;
    private String content;
    private String viewCnt;
    private String isCollect;
    private String shareUrl;
    private String shareTitle;
    private String sharedescrib;

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getSharedescrib() {
        return sharedescrib;
    }

    public void setSharedescrib(String sharedescrib) {
        this.sharedescrib = sharedescrib;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getImgsUrl() {
        return imgsUrl;
    }

    public void setImgsUrl(String imgsUrl) {
        this.imgsUrl = imgsUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getViewCnt() {
        return viewCnt;
    }

    public void setViewCnt(String viewCnt) {
        this.viewCnt = viewCnt;
    }

    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }

    public NewDetailBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.newsId);
        dest.writeString(this.title);
        dest.writeString(this.addtime);
        dest.writeString(this.imgsUrl);
        dest.writeString(this.content);
        dest.writeString(this.viewCnt);
        dest.writeString(this.isCollect);
        dest.writeString(this.shareUrl);
        dest.writeString(this.shareTitle);
        dest.writeString(this.sharedescrib);
    }

    protected NewDetailBean(Parcel in) {
        this.newsId = in.readString();
        this.title = in.readString();
        this.addtime = in.readString();
        this.imgsUrl = in.readString();
        this.content = in.readString();
        this.viewCnt = in.readString();
        this.isCollect = in.readString();
        this.shareUrl = in.readString();
        this.shareTitle = in.readString();
        this.sharedescrib = in.readString();
    }

    public static final Creator<NewDetailBean> CREATOR = new Creator<NewDetailBean>() {
        @Override
        public NewDetailBean createFromParcel(Parcel source) {
            return new NewDetailBean(source);
        }

        @Override
        public NewDetailBean[] newArray(int size) {
            return new NewDetailBean[size];
        }
    };
}
