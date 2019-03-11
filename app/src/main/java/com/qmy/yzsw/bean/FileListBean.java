package com.qmy.yzsw.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class FileListBean implements Parcelable {

    /**
     * id (string, optional): id ,
     * fileName (string, optional): 文件名称 ,
     * isDirectory (string, optional): 是不是目录：1是；0否 ,
     * fileUrl (string, optional): 文件web地址 ,
     * createTimeStr (string, optional): 发布时间 ,
     * fileExt (string, optional): 文件扩展名
     * filePath 本地路径
     */

    private String id;
    private String fileName;
    private String isDirectory;
    private String fileUrl;
    private String fileHtml;
    private String createTimeStr;
    private String fileExt;
    private String filePath;

    public String getFileHtml() {
        return fileHtml;
    }

    public void setFileHtml(String fileHtml) {
        this.fileHtml = fileHtml;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getIsDirectory() {
        return isDirectory;
    }

    public void setIsDirectory(String isDirectory) {
        this.isDirectory = isDirectory;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public FileListBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.fileName);
        dest.writeString(this.isDirectory);
        dest.writeString(this.fileUrl);
        dest.writeString(this.fileHtml);
        dest.writeString(this.createTimeStr);
        dest.writeString(this.fileExt);
        dest.writeString(this.filePath);
    }

    protected FileListBean(Parcel in) {
        this.id = in.readString();
        this.fileName = in.readString();
        this.isDirectory = in.readString();
        this.fileUrl = in.readString();
        this.fileHtml = in.readString();
        this.createTimeStr = in.readString();
        this.fileExt = in.readString();
        this.filePath = in.readString();
    }

    public static final Creator<FileListBean> CREATOR = new Creator<FileListBean>() {
        @Override
        public FileListBean createFromParcel(Parcel source) {
            return new FileListBean(source);
        }

        @Override
        public FileListBean[] newArray(int size) {
            return new FileListBean[size];
        }
    };
}
