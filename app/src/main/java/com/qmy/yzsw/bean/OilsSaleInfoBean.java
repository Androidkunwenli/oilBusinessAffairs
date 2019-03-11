package com.qmy.yzsw.bean;

import java.util.List;

public class OilsSaleInfoBean {

    /**
     * id : string
     * saleDate : 2018-10-14T06:16:35.430Z
     * remark : string
     * createUser : string
     * createTime : 2018-10-14T06:16:35.432Z
     * detailList : [{"id":"string","saleId":"string","saleDate":"2018-10-14T06:16:35.432Z","oilsType":"string","saleNum":0,"price":0,"totalPrice":0,"createUser":"string","createTime":"2018-10-14T06:16:35.432Z"}]
     */

    private String id;
    private String saleDate;
    private String remark;
    private String createUser;
    private String createTime;
    private List<DetailListBean> detailList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public List<DetailListBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DetailListBean> detailList) {
        this.detailList = detailList;
    }

    public static class DetailListBean {
        /**
         * id : string
         * saleId : string
         * saleDate : 2018-10-14T06:16:35.432Z
         * oilsType : string
         * saleNum : 0
         * price : 0
         * totalPrice : 0
         * createUser : string
         * createTime : 2018-10-14T06:16:35.432Z
         */

        private String id;
        private String saleId;
        private String saleDate;
        private String oilsType;
        private String saleNum;
        private String price;
        private String totalPrice;
        private String createUser;
        private String createTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSaleId() {
            return saleId;
        }

        public void setSaleId(String saleId) {
            this.saleId = saleId;
        }

        public String getSaleDate() {
            return saleDate;
        }

        public void setSaleDate(String saleDate) {
            this.saleDate = saleDate;
        }

        public String getOilsType() {
            return oilsType;
        }

        public void setOilsType(String oilsType) {
            this.oilsType = oilsType;
        }

        public String getSaleNum() {
            return saleNum;
        }

        public void setSaleNum(String saleNum) {
            this.saleNum = saleNum;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
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
}
