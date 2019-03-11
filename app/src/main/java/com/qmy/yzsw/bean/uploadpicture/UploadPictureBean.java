package com.qmy.yzsw.bean.uploadpicture;

public class UploadPictureBean {
    private int opType;
    private String filePath;
    private String oldPicUrl;
    private String downloadFile;

    public UploadPictureBean() {
    }

    public UploadPictureBean(int opType, String filePath, String oldPicUrl) {
        this.opType = opType;
        this.filePath = filePath;
        this.oldPicUrl = oldPicUrl;
    }

    public int getOpType() {
        return opType;
    }

    public void setOpType(int opType) {
        this.opType = opType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getOldPicUrl() {
        return oldPicUrl;
    }

    public void setOldPicUrl(String oldPicUrl) {
        this.oldPicUrl = oldPicUrl;
    }

    public String getDownloadFile() {
        return downloadFile;
    }

    public void setDownloadFile(String downloadFile) {
        this.downloadFile = downloadFile;
    }

    @Override
    public String toString() {
        return "UploadPictureBean{" +
                "opType=" + opType +
                ", filePath='" + filePath + '\'' +
                ", oldPicUrl='" + oldPicUrl + '\'' +
                ", downloadFile='" + downloadFile + '\'' +
                '}';
    }
}
