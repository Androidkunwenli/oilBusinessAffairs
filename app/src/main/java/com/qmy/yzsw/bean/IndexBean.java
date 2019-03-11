package com.qmy.yzsw.bean;

import java.util.List;

public class IndexBean {

    /**
     * ReturnData«主页实体» {
     * code (integer, optional): 状态码 1成功 99登录失效 98服务器内部错误 其他状态均为失败 ,
     * msg (string, optional): 描述 ,
     * data (主页实体, optional)
     * }
     * 主页实体 {
     * cityVo (主页城市实体, optional),
     * smsNewCnt (string, optional): 新消息数量 ,
     * adverviseVoList (Array[主页轮播实体], optional): 广告轮播
     * }
     * 主页城市实体 {
     * cityId (string, optional): 城市id ,
     * cityName (string, optional): 城市名称
     * }
     * 主页轮播实体 {
     * imgUrl (string, optional): 图片地址 ,
     * linkType (integer, optional): 链接类型：1资讯2报表3外部链接100无链接 ,
     * linkUrl (string, optional): 链接地址：linkType等于1和2的时候为id,等于3的时候为http地址，其它为空串
     * }
     */

    private CityVoBean cityVo;
    private String smsNewCnt;
    private List<AdverviseVoListBean> adverviseVoList;

    public CityVoBean getCityVo() {
        return cityVo;
    }

    public void setCityVo(CityVoBean cityVo) {
        this.cityVo = cityVo;
    }

    public String getSmsNewCnt() {
        return smsNewCnt;
    }

    public void setSmsNewCnt(String smsNewCnt) {
        this.smsNewCnt = smsNewCnt;
    }

    public List<AdverviseVoListBean> getAdverviseVoList() {
        return adverviseVoList;
    }

    public void setAdverviseVoList(List<AdverviseVoListBean> adverviseVoList) {
        this.adverviseVoList = adverviseVoList;
    }

    public static class CityVoBean {
        /**
         * cityId : string
         * cityName : string
         */

        private String cityId;
        private String cityName;

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }
    }

    public static class AdverviseVoListBean {
        /**
         * imgUrl : string
         * linkType : 0
         * linkUrl : string
         */

        private String imgUrl;
        private int linkType;
        private String linkUrl;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getLinkType() {
            return linkType;
        }

        public void setLinkType(int linkType) {
            this.linkType = linkType;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }
    }
}
