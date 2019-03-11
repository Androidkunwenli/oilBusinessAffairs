package com.qmy.yzsw.bean;

public class HelpBean  {

    /**
     * newsId (string, optional): 帮助id ,
     * title (string, optional): 标题 ,
     * content (string, optional): 帮助内容
     */

    private String newsId;
    private String title;
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
