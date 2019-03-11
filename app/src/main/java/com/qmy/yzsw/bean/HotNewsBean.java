package com.qmy.yzsw.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class HotNewsBean implements Parcelable {

    /**
     * ReturnData«List«首页资讯实体»» {
     * code (integer, optional): 状态码 1成功 99登录失效 98服务器内部错误 其他状态均为失败 ,
     * msg (string, optional): 描述 ,
     * data (Array[首页资讯实体], optional): 业务参数
     * }
     * 首页资讯实体 {
     * newsId (string, optional): 新闻id ,
     * title (string, optional): 新闻标题 ,
     * imgsUrl (string, optional): 新闻图片地址列表，英文逗号分隔 ,
     * viewCnt (string, optional): 新闻浏览次数
     * }
     */

    private String newsId;
    private String title;
    private String imgsUrl;
    private String viewCnt;
    private String collectTime;
    private String addtime;
    private String content;
    private String isCollect;

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }

    public String getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

    @Override
    public String toString() {
        return "HotNewsBean{" +
                "newsId='" + newsId + '\'' +
                ", title='" + title + '\'' +
                ", imgsUrl='" + imgsUrl + '\'' +
                ", viewCnt='" + viewCnt + '\'' +
                '}';
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

    public String getImgsUrl() {
        return imgsUrl;
    }

    public void setImgsUrl(String imgsUrl) {
        this.imgsUrl = imgsUrl;
    }

    public String getViewCnt() {
        return viewCnt;
    }

    public void setViewCnt(String viewCnt) {
        this.viewCnt = viewCnt;
    }

    public HotNewsBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.newsId);
        dest.writeString(this.title);
        dest.writeString(this.imgsUrl);
        dest.writeString(this.viewCnt);
        dest.writeString(this.collectTime);
        dest.writeString(this.addtime);
        dest.writeString(this.content);
        dest.writeString(this.isCollect);
    }

    protected HotNewsBean(Parcel in) {
        this.newsId = in.readString();
        this.title = in.readString();
        this.imgsUrl = in.readString();
        this.viewCnt = in.readString();
        this.collectTime = in.readString();
        this.addtime = in.readString();
        this.content = in.readString();
        this.isCollect = in.readString();
    }

    public static final Creator<HotNewsBean> CREATOR = new Creator<HotNewsBean>() {
        @Override
        public HotNewsBean createFromParcel(Parcel source) {
            return new HotNewsBean(source);
        }

        @Override
        public HotNewsBean[] newArray(int size) {
            return new HotNewsBean[size];
        }
    };
}
