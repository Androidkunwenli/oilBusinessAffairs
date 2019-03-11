package com.qmy.yzsw.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.baidu.location.Address;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.bean.CarDetailBean;
import com.qmy.yzsw.bean.FileListBean;
import com.qmy.yzsw.bean.FillUpTheAccountBean;
import com.qmy.yzsw.bean.HotNewsBean;
import com.qmy.yzsw.bean.ImageUploadBean;
import com.qmy.yzsw.bean.IndexBean;
import com.qmy.yzsw.bean.InformationBean;
import com.qmy.yzsw.bean.MyPriceListBean;
import com.qmy.yzsw.bean.MySubmissionReportFormListBean;
import com.qmy.yzsw.bean.NewDetailBean;
import com.qmy.yzsw.bean.OilsDetailBean;
import com.qmy.yzsw.bean.VehicleUreaBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.bean.request.LeaseSaveBean;
import com.qmy.yzsw.bean.request.SuperviseSaveBean;
import com.qmy.yzsw.callback.DialogCallback;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.config.Urls;

import java.util.ArrayList;
import java.util.List;

public class HttpUtils {
    /**
     * 获取验证码
     */
    public static void getSms(Context context, String mobileNo, int opType, JsonCallback<BaseBean> jsonCallback) {
        String nowString = TimeUtils.getNowString();
        String sign = "yzsw20180802apiCodegetSmsCodeappKeyyzsw2018params" + mobileNo + "timeStamp" + nowString + "tokenyzsw20180802";
        OkGo.<BaseBean>post(Urls.GETSMSCODE)
                .tag(context)
                .params("timeStamp", nowString)
                .params("mobileNo", mobileNo)
                .params("sign", EncryptUtils.encryptMD5ToString(sign).toLowerCase())
                .params("opType", opType)
                .execute(jsonCallback);
    }

    /**
     * 注册
     */
    public static void register(Context context, String smsCode, String mobileNo, String password, JsonCallback<BaseBean> callback) {
        OkGo.<BaseBean>post(Urls.REGISTER)
                .tag(context)
                .params("smsCode", smsCode)
                .params("mobileNo", mobileNo)
                .params("password", password)
                .execute(callback);
    }

    /**
     * 忘记密码
     */
    public static void forgetPassword(Context context, String smsCode, String mobileNo, String password, JsonCallback<BaseBean> callback) {
        OkGo.<BaseBean>post(Urls.FORGETPASSWORD)
                .tag(context)
                .params("smsCode", smsCode)
                .params("mobileNo", mobileNo)
                .params("password", password)
                .execute(callback);
    }

    /**
     * 登陆
     */
    public static void login(Context context, String mobileNo, String password, JsonCallback<BaseBean> callback) {
        OkGo.<BaseBean>post(Urls.LOGIN)
                .tag(context)
                .params("mobileNo", mobileNo)
                .params("password", password)
                .execute(callback);
    }


    /**
     * 系统首页热点资讯接口
     */
    public static void hotNews(Context context, int page, JsonCallback<BaseBean<ArrayList<HotNewsBean>>> callback) {
        OkGo.<BaseBean<ArrayList<HotNewsBean>>>post(Urls.HOTNEWS)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("page", page)
                .params("limit", 10)
                .execute(callback);
    }

    /**
     * 今日资讯列表接口
     */
    public static void newList(Context context, int page, int type, JsonCallback<BaseBean<ArrayList<HotNewsBean>>> callback) {
        OkGo.<BaseBean<ArrayList<HotNewsBean>>>post(Urls.NEWLIST)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("type", type)
                .params("page", page)
                .params("limit", 10)
                .execute(callback);
    }

    /**
     * 资讯详情接口
     */
    public static void newDetail(Context context, String newsId, JsonCallback<BaseBean<NewDetailBean>> callback) {
        OkGo.<BaseBean<NewDetailBean>>post(Urls.NEWDETAIL)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("newsId", newsId)
                .execute(callback);
    }

    /**
     * 系统首页接口
     */
    public static void index(Context context, JsonCallback<BaseBean<IndexBean>> callback) {
        OkGo.<BaseBean<IndexBean>>post(Urls.INDEX)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .execute(callback);
    }

    /**
     * 保存场景
     */
    public static void sceneSave(Context context, String file9, String file10, String file11,
                                 String file12, String file13, String file14, String file15,
                                 JsonCallback<BaseBean> callback) {
        OkGo.<BaseBean>post(Urls.SCENESAVE)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("file9", file9)
                .params("file10", file10)
                .params("file11", file11)
                .params("file12", file12)
                .params("file13", file13)
                .params("file14", file14)
                .params("file15", file15)
                .execute(callback);
    }

    /**
     * 油站证件图片保存接口
     */
    public static void certificateSave(Context context, ImageUploadBean bean,
                                       JsonCallback<BaseBean> callback) {
        OkGo.<BaseBean>post(Urls.CERTIFICATESAVE)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("file1", bean.getFile1())
                .params("file2", bean.getFile2())
                .params("file3", bean.getFile3())
                .params("file4", bean.getFile4())
                .params("file5", bean.getFile5())
                .params("file6", bean.getFile6())
                .params("file7", bean.getFile7())
                .params("file8", bean.getFile8())
                .params("file9", bean.getFile9())
                .params("file10", bean.getFile10())
                .execute(callback);
    }

    /**
     * 获取场景详情接口
     */
    public static void getSceneInfo(Context context, JsonCallback<BaseBean<ImageUploadBean>> callback) {
        OkGo.<BaseBean<ImageUploadBean>>post(Urls.GETSCENEINFO)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .execute(callback);
    }

    /**
     * 获取场景详情接口
     */
    public static void getCertificateInfo(Context context, JsonCallback<BaseBean<ImageUploadBean>> callback) {
        OkGo.<BaseBean<ImageUploadBean>>post(Urls.GETCERTIFICATEINFO)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .execute(callback);
    }

    /**
     * 油站信息录入接口
     */
    public static <T> void saveInfo(Context context, InformationBean informationBean, JsonCallback<T> callback) {
        if (informationBean.getEnterpriseName().isEmpty()) {
            ToastUtils.showShort("请输入企业名称");
            return;
        }
        if (informationBean.getEnterpricePhone().isEmpty()) {
            ToastUtils.showShort("请输入正确的联系电话");
            return;
        }
        if (!KUtils.IsTelephone(informationBean.getEnterpricePhone())) {
            ToastUtils.showShort("请输入正确的企业固定电话");
            return;
        }
        if (informationBean.getJob().isEmpty()) {
            ToastUtils.showShort("请输入职务");
            return;
        }
        if (informationBean.getEnterpriceLeader().isEmpty()) {
            ToastUtils.showShort("请输入企业负责人");
            return;
        }
        if (informationBean.getLeaderPhone().isEmpty()) {
            ToastUtils.showShort("请输入正确的联系电话");
            return;
        }
        if (!RegexUtils.isMobileSimple(informationBean.getLeaderPhone())) {
            ToastUtils.showShort("请输入正确的联系电话");
            return;
        }
        if (informationBean.getGasolineMoney().isEmpty()) {
            ToastUtils.showShort("请输入汽油日交易额");
            return;
        }
        if (informationBean.getGasolineCount().isEmpty()) {
            ToastUtils.showShort("请输入汽油日交易数量");
            return;
        }
        if (informationBean.getDieseloilMoney().isEmpty()) {
            ToastUtils.showShort("请输入柴油日交易额");
            return;
        }
        if (informationBean.getDieseloilCount().isEmpty()) {
            ToastUtils.showShort("请输入柴油日交易数量");
            return;
        }
        if (StringUtils.equals(informationBean.getRetailgasoline(), "0")
                & StringUtils.equals(informationBean.getRetaildieseloil(), "0")
                & StringUtils.equals(informationBean.getWholesalegasoline(), "0")
                & StringUtils.equals(informationBean.getWholesaledieseloil(), "0")) {
            ToastUtils.showShort("至少选择一项经营范围");
            return;
        }
        if (informationBean.getAssociation().isEmpty()) {
            ToastUtils.showShort("请选择是否加入成品油协会");
            return;
        }
        if (informationBean.getNaturalgas().isEmpty()) {
            ToastUtils.showShort("请选择是否有增加天然气产品需求");
            return;
        }
        if (informationBean.getEv().isEmpty()) {
            ToastUtils.showShort("请选择是否有充电桩需求");
            return;
        }
        if (informationBean.getBrand().isEmpty()) {
            ToastUtils.showShort("请输入加油机品牌");
            return;
        }
        if (informationBean.getBrandcount().isEmpty()) {
            ToastUtils.showShort("请输入加油机数量");
            return;
        }
        if (informationBean.getKnock().isEmpty()) {
            ToastUtils.showShort("请输入加油枪数量");
            return;
        }
        if (informationBean.getOiltankGasoline().isEmpty()) {
            ToastUtils.showShort("请输入汽油油罐数");
            return;
        }
        if (informationBean.getOiltankDieseloil().isEmpty()) {
            ToastUtils.showShort("请输入柴油油罐数");
            return;
        }
        if (informationBean.getGasolinestere().isEmpty()) {
            ToastUtils.showShort("请输入汽油总容积");
            return;
        }
        if (informationBean.getDieselstere().isEmpty()) {
            ToastUtils.showShort("请输入柴油总容积");
            return;
        }
        if (informationBean.getNinetytwo().isEmpty()) {
            ToastUtils.showShort("请选择无或者输入价格");
            return;
        }
        if (informationBean.getNinetyfive().isEmpty()) {
            ToastUtils.showShort("请选择无或者输入价格");
            return;
        }
        if (informationBean.getNinetyeight().isEmpty()) {
            ToastUtils.showShort("请选择无或者输入价格");
            return;
        }
        if (informationBean.getZero().isEmpty()) {
            ToastUtils.showShort("请选择无或者输入价格");
            return;
        }
        if (informationBean.getTen().isEmpty()) {
            ToastUtils.showShort("请选择无或者输入价格");
            return;
        }
        if (informationBean.getTwenty().isEmpty()) {
            ToastUtils.showShort("请选择无或者输入价格");
            return;
        }
        if (informationBean.getLng().isEmpty()) {
            ToastUtils.showShort("请选择无或者输入价格");
            return;
        }
        if (informationBean.getCng().isEmpty()) {
            ToastUtils.showShort("请选择无或者输入价格");
            return;
        }
        if (informationBean.getCharging().isEmpty()) {
            ToastUtils.showShort("请选择无或者输入价格");
            return;
        }

        OkGo.<T>post(Urls.SAVEINFO)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("enterpriseName", informationBean.getEnterpriseName())
                .params("enterpricePhone", informationBean.getEnterpricePhone())
                .params("job", informationBean.getJob())
                .params("enterpriceLeader", informationBean.getEnterpriceLeader())
                .params("leaderPhone", informationBean.getLeaderPhone())
                .params("gasolineMoney", informationBean.getGasolineMoney())
                .params("gasolineCount", informationBean.getGasolineCount())
                .params("dieseloilMoney", informationBean.getDieseloilMoney())
                .params("dieseloilCount", informationBean.getDieseloilCount())
                .params("supplierOne", informationBean.getSupplierOne())
                .params("supplierTwo", informationBean.getSupplierTwo())
                .params("supplierThree", informationBean.getSupplierThree())
                .params("car", informationBean.getCar())
                .params("supermarket", informationBean.getSupermarket())
                .params("quickrepair", informationBean.getQuickrepair())
                .params("fastfood", informationBean.getFastfood())
                .params("other", informationBean.getOther())
                .params("retailgasoline", informationBean.getRetailgasoline())
                .params("retaildieseloil", informationBean.getRetaildieseloil())
                .params("wholesalegasoline", informationBean.getWholesalegasoline())
                .params("wholesaledieseloil", informationBean.getWholesaledieseloil())
                .params("association", informationBean.getAssociation())
                .params("naturalgas", informationBean.getNaturalgas())
                .params("hascng", informationBean.getHascng())
                .params("haslcng", informationBean.getHaslcng())
                .params("haslng", informationBean.getHaslng())
                .params("ev", informationBean.getEv())
                .params("brand", informationBean.getBrand())
                .params("brandcount", informationBean.getBrandcount())
                .params("knock", informationBean.getKnock())
                .params("oiltankGasoline", informationBean.getOiltankGasoline())
                .params("oiltankDieseloil", informationBean.getOiltankDieseloil())
                .params("gasolinestere", informationBean.getGasolinestere())
                .params("dieselstere", informationBean.getDieselstere())
                .params("ninetytwo", informationBean.getNinetytwo())
                .params("ninetyfive", informationBean.getNinetyfive())
                .params("ninetyeight", informationBean.getNinetyeight())
                .params("zero", informationBean.getZero())
                .params("ten", informationBean.getTen())
                .params("twenty", informationBean.getTwenty())
                .params("lng", informationBean.getLng())
                .params("cng", informationBean.getCng())
                .params("charging", informationBean.getCharging())
                .execute(callback);
    }

    /**
     * 油站租赁发布接口
     */
    public static <T> void leasesaveaAndmakeoversave(Context context, boolean isLease, LeaseSaveBean saveBean, JsonCallback<T> callback) {
        if (saveBean.getOilname().isEmpty()) {
            ToastUtils.showShort("油站名称不能为空!");
            return;
        }
        if (saveBean.getOiladdress().isEmpty()) {
            ToastUtils.showShort("详细地址不能为空!");
            return;
        }
        if (saveBean.getId() == null && (saveBean.getCityName().isEmpty() || saveBean.getProvinceName().isEmpty() || saveBean.getCityName().isEmpty())) {
            ToastUtils.showShort("所在地区不能为空!");
            return;
        }
        if (saveBean.getLeasetime().isEmpty() && isLease) {
            ToastUtils.showShort("租赁年限不能为空!");
            return;
        }
        if (saveBean.getLeasemoney().isEmpty()) {
            ToastUtils.showShort("租金价格不能为空!");
            return;
        }
        if (saveBean.getContacts().isEmpty()) {
            ToastUtils.showShort("联系人不能为空!");
            return;
        }
        if (saveBean.getContactphone().isEmpty()) {
            ToastUtils.showShort("联系电话不能为空!");
            return;
        }
        if (!RegexUtils.isMobileSimple(saveBean.getContactphone())) {
            ToastUtils.showShort("请输入正确的联系电话");
            return;
        }
        OkGo.<T>post(isLease ? Urls.LEASESAVE : Urls.MAKEOVERSAVE)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("id", saveBean.getId())
                .params("oilname", saveBean.getOilname())
                .params("provinceName", saveBean.getProvinceName())
                .params("cityName", saveBean.getCityName())
                .params("countyName", saveBean.getCountyName())
                .params("oiladdress", saveBean.getOiladdress())
                .params("leasetime", isLease ? saveBean.getLeasetime() : "")
                .params("leasemoney", saveBean.getLeasemoney())
                .params("contacts", saveBean.getContacts())
                .params("contactphone", saveBean.getContactphone())
                .params("imgFile1", saveBean.getImgFile1())
                .params("imgFile2", saveBean.getImgFile2())
                .execute(callback);
    }

    /**
     * 油站租赁列表接口
     *
     * @param myself    是否查询我的列表：1是；0否
     * @param leasetype 类型：1租赁；2转让
     */
    public static <T> void leaselist(Context context, int leasetype, int myself, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.LEASELIST)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("leasetype", leasetype)
                .params("myself", myself)
                .execute(callback);
    }

    /**
     * 油站租赁详情
     */
    public static <T> void leaseDetail(Context context, String id, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.LEASEDETAIL)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("id", id)
                .execute(callback);
    }

    /*
     * 油站位置信息保存接口
     **/
    public static <T> void coordinateSave(Context context, double currentLat, double currentLon,
                                          String province, String city, String district, String address,
                                          JsonCallback<T> callback) {
        OkGo.<T>post(Urls.COORDINATESAVE)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("latitude", currentLat)
                .params("longitude", currentLon)
                .params("provinceName", province)
                .params("cityName", city)
                .params("countyName", district)
                .params("oiladdress", address)
                .execute(callback);
    }

    /**
     * 证照到期日信息获取接口
     */
    public static <T> void expireDateGet(Context context, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.EXPIREDATEGET)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .execute(callback);
    }

    /**
     * 证照到期日信息保存接口
     */
    public static <T> void expireDateSave(Context context, String businesLicense, String legalPersonLicense,
                                          String dangerousChemicalCertificate, String productOilBusinessLicense,
                                          JsonCallback<T> callback) {
        OkGo.<T>post(Urls.EXPIREDATESAVE)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("businesLicense", businesLicense)
                .params("legalPersonLicense", legalPersonLicense)
                .params("dangerousChemicalCertificate", dangerousChemicalCertificate)
                .params("productOilBusinessLicense", productOilBusinessLicense)
                .execute(callback);
    }

    /**
     * 油站监督信息发布接口
     */
    public static <T> void superviseSave(Context context, SuperviseSaveBean bean,
                                         JsonCallback<T> callback) {
        if (bean.getOilName().isEmpty()) {
            ToastUtils.showShort("油站名称不能为空！");
            return;
        }
        if (bean.getOilAddress().isEmpty()) {
            ToastUtils.showShort("油站地址不能为空！");
            return;
        }
        if (bean.getProbledFeedback().isEmpty()) {
            ToastUtils.showShort("问题反馈不能为空！");
            return;
        }
        OkGo.<T>post(Urls.SUPERVISESAVE)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .params(KUtils.objectToMap(bean), true)
                .execute(callback);
    }

    /**
     * 油站监督信息列表接口
     */
    public static <T> void superviseList(Context context, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.SUPERVISELIST)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .execute(callback);
    }

    /**
     * 获取今日油价
     */
    public static <T> void todayPriceList(Context context, String oilType, double latitude, double longitude, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.TODAYPRICELIST)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("oilType", oilType)
                .params("latitude", latitude)
                .params("longitude", longitude)
                .execute(callback);
    }

    /**
     * 修改今日油价
     */
    public static <T> void todayPriceUpdate(Context context, String oilType, String price, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.TODAYPRICEUPDATE)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("oilType", oilType)
                .params("price", price)
                .execute(callback);
    }

    /**
     * 城市选择接口
     */
    public static <T> void citySelect(Context context, String cityName, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.CITYSELECT)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("cityName", cityName)
                .execute(callback);
    }

    /**
     * 油站信息获取
     */
    public static <T> void getOilInfo(Context context, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.GETINFO)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .execute(callback);
    }

    /**
     * 保存修改的昵称
     */
    public static <T> void nickNameSave(Context context, String nickname, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.NICKNAMESAVE)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("nickname", nickname)
                .execute(callback);
    }

    /**
     * 我的主页
     */
    public static <T> void myIndex(Context context, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.MYINDEX)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .execute(callback);
    }

    //拉货信息列表接口
    public static <T> void carList(Context context, int myself, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.CARLIST)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("myself", myself)
                .execute(callback);
    }

    //找车信息列表接口
    public static <T> void oilsList(FragmentActivity activity, int myself, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.OILSLIST)
                .tag(activity)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("myself", myself)
                .execute(callback);
    }

    //发布拉货信息接口
    public static <T> void carSave(Context context, CarDetailBean bean, JsonCallback<T> callback) {

        if (bean.getEnterpriseName().isEmpty()) {
            ToastUtils.showShort("单位名称不能为空!");
            return;
        }
        if (bean.getEnterpriceLeader().isEmpty()) {
            ToastUtils.showShort("负责人姓名不能为空!");
            return;
        }
        if (!RegexUtils.isMobileSimple(bean.getEnterpricePhone())) {
            ToastUtils.showShort("请输入正确的联系电话");
            return;
        }
        if (bean.getCarModel().isEmpty()) {
            ToastUtils.showShort("车型不能为空!");
            return;
        }
        if (bean.getCarLicense().isEmpty()) {
            ToastUtils.showShort("车牌号不能为空!");
            return;
        }
        OkGo.<T>post(Urls.CARSAVE)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("id", bean.getId())
                .params("enterpriseName", bean.getEnterpriseName())
                .params("enterpriceLeader", bean.getEnterpriceLeader())
                .params("enterpricePhone", bean.getEnterpricePhone())
                .params("carModel", bean.getCarModel())
                .params("carLicense", bean.getCarLicense())
                .params("ninetytwo", bean.getNinetytwo())
                .params("ninetyfive", bean.getNinetyfive())
                .params("ninetyeight", bean.getNinetyeight())
                .params("zero", bean.getZero())
                .params("ten", bean.getTen())
                .params("twenty", bean.getTwenty())
                .params("lng", bean.getLng())
                .params("cng", bean.getCng())
                .params("imgFile1", bean.getImg1())//行车证
                .params("imgFile2", bean.getImg2())//危品运输证
                .params("otherRemark", bean.getOtherRemark())
                .execute(callback);
    }

    //发布找车信息接口
    public static <T> void oilsSave(Context context, OilsDetailBean bean, JsonCallback<T> callback) {

        if (bean.getEnterpriseName().isEmpty()) {
            ToastUtils.showShort("单位名称不能为空!");
            return;
        }
        if (bean.getEnterpriceLeader().isEmpty()) {
            ToastUtils.showShort("负责人姓名不能为空!");
            return;
        }
        if (!RegexUtils.isMobileSimple(bean.getEnterpricePhone())) {
            ToastUtils.showShort("请输入正确的联系电话");
            return;
        }
        if (bean.getPickUpAddr().isEmpty()) {
            ToastUtils.showShort("取货地址不能为空!");
            return;
        }
        if (bean.getSendAddr().isEmpty()) {
            ToastUtils.showShort("收货地址不能为空!");
            return;
        }
        OkGo.<T>post(Urls.OILSSAVE)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("id", bean.getId())
                .params("enterpriseName", bean.getEnterpriseName())
                .params("enterpriceLeader", bean.getEnterpriceLeader())
                .params("enterpricePhone", bean.getEnterpricePhone())
                .params("pickUpAddr", bean.getPickUpAddr())
                .params("sendAddr", bean.getSendAddr())
                .params("ninetytwo", bean.getNinetytwo())
                .params("ninetyfive", bean.getNinetyfive())
                .params("ninetyeight", bean.getNinetyeight())
                .params("zero", bean.getZero())
                .params("ten", bean.getTen())
                .params("twenty", bean.getTwenty())
                .params("lng", bean.getLng())
                .params("cng", bean.getCng())
                .params("otherRemark", bean.getOtherRemark())
                .execute(callback);
    }

    //关于我们
    public static <T> void aboutUs(Context context, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.ABOUTUS)
                .tag(context)
                .execute(callback);
    }

    //反馈意见
    public static <T> void complaint(Context context, String feedback, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.COMPLAINT)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("feedback", feedback)
                .execute(callback);
    }

    //更改密码
    public static <T> void modifyPass(Context context, String oldPass, String newPass, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.MODIFYPASS)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("oldPass", oldPass)
                .params("newPass", newPass)
                .execute(callback);
    }

    //退出登录接口
    public static <T> void loginOut(Activity context) {
        OkGo.<T>post(Urls.LOGINOUT)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .execute(new DialogCallback<T>(context) {
                    @Override
                    public void onSuccess(Response<T> response) {

                    }
                });
    }

    //我的收藏列表接口
    public static <T> void myColletctNewsList(Context context, int page, int limit, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.MYCOLLETCTNEWSLIST)
                .tag(context)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("page", page)
                .params("limit", limit)
                .execute(callback);
    }

    //我的油价列表接口
    public static <T> void myPriceList(BaseActivity activity, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.MYPRICELIST)
                .tag(activity)
                .params("token", SPUtils.getInstance().getString("token"))
                .execute(callback);
    }

    // 批量修改油价接口
    public static <T> void savePriceList(BaseActivity activity, MyPriceListBean bean, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.SAVEPRICELIST)
                .tag(activity)
                .params("token", SPUtils.getInstance().getString("token"))
                .params(KUtils.objectToMap(bean), true)
                .execute(callback);
    }

    // 资讯收藏/分享接口
    public static <T> void newsOperate(BaseActivity activity, String newsId, int opType, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.NEWSOPERATE)
                .tag(activity)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("newsId", newsId)//新闻id
                .params("opType", opType)//操作类型：2收藏；3分享
                .execute(callback);
    }

    //拉货信息详情接口
    public static <T> void carDetail(Activity activity, String id, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.CARDETAIL)
                .tag(activity)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("id", id)
                .execute(callback);
    }

    //找车详情接口
    public static <T> void oilsDetail(Activity activity, String id, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.OILSDETAIL)
                .tag(activity)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("id", id)
                .execute(callback);
    }

    //油站转让/租赁删除/成交接口
    public static <T> void leaseUpdateState(Activity activity, String id, int opType, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.LEASEUPDATESTATE)
                .tag(activity)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("id", id)
                .params("opType", opType)
                .execute(callback);
    }

    //头像保存
    public static <T> void imgHeadSave(Activity activity, String imgHead, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.IMGHEADSAVE)
                .tag(activity)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("imgHead", imgHead)
                .execute(callback);
    }

    /**
     * 报表列表接口
     *
     * @param parentId     父级id，顶级不传
     * @param keyWord      检索关键字
     * @param fileResource 文件来源：1商务局；2税务局；3消防局
     * @param sort         排序：1时间排序;2名称排序
     * @param myself       我的报表：1是，0否
     * @param callback
     */
    public static <T> void fileList(Activity activity, int parentId, String keyWord,
                                    String fileResource, String sort, int myself, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.FILELIST)
                .tag(activity)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("parentId", parentId)
                .params("keyWord", keyWord)
                .params("fileResource", fileResource)
                .params("sort", sort)
                .params("myself", myself)
                .execute(callback);
    }

    //下载
    public static <T> void download(BaseActivity activity, String id, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.DOWNLOAD)
                .tag(activity)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("id", id)
                .execute(callback);
    }

    /**
     * 拉货信息删除/成交接口
     *
     * @param opType 操作类型：0为删除；2为成交 1刷新
     */
    public static <T> void carOperator(Activity activity, String id, String opType, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.CAROPERATOR)
                .tag(activity)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("id", id)
                .params("opType", opType)
                .execute(callback);
    }

    /**
     * 找车信息删除/成交接口
     *
     * @param opType 操作类型：0为删除；2为成交
     */
    public static <T> void oilsOperator(Activity activity, String id, String opType, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.OILSOPERATOR)
                .tag(activity)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("id", id)
                .params("opType", opType)
                .execute(callback);
    }

    /**
     * 系统首页消息列表接口
     */
    public static <T> void smsList(Activity activity, int page, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.SMSLIST)
                .tag(activity)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("page", page)
                .params("limit", 10)
                .execute(callback);
    }

    /**
     * 油站详情接口
     */
    public static <T> void detail(Activity activity, String id, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.DETAIL)
                .tag(activity)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("id", id)
                .execute(callback);
    }

    /**
     * 油站监督信息详情接口
     */
    public static <T> void superviseDetail(Activity activity, String id, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.SUPERVISEDETAIL)
                .tag(activity)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("id", id)
                .execute(callback);
    }

    /**
     * 帮助列表接口
     */
    public static <T> void helpList(Activity activity, int page, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.HELPLIST)
                .tag(activity)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("page", page)
                .params("limit", 10)
                .execute(callback);
    }

    /**
     * 油品销售台账录入接口
     */
    public static <T> void oilsSaleSave(Activity activity, String id, String saleDate, String data, String remark, DialogCallback<T> callback) {
        OkGo.<T>post(Urls.OILSSALESAVE)
                .tag(activity)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("id", id)
                .params("saleDate", saleDate)
                .params("data", data)
                .params("remark", remark)
                .execute(callback);
    }

    /**
     * 我的提交列表接口
     */
    public static <T> void myList(Activity activity, String type, int page, String startTime, String endTime, String keyWord, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.MYLIST)
                .tag(activity)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("type", type)
                .params("page", page)
                .params("limit", 10)
                .params("startTime", startTime)
                .params("endTime", endTime)
                .params("keyWord", keyWord)
                .execute(callback);
    }

    /**
     * 油品销售台账详情接口
     */
    public static <T> void oilsSaleInfo(Activity activity, String id, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.OILSSALEINFO)
                .tag(activity)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("id", id)
                .execute(callback);
    }

    /**
     * 安全隐患台账录入接口
     */
    public static <T> void securitySave(BaseActivity mActivity, String id,
                                        String checkDate,
                                        String checkUser,
                                        String checkRemark,
                                        String rectifyDate,
                                        String rectifyRemark,
                                        String reCheckUser,
                                        String reCheckDate,
                                        String remark,
                                        JsonCallback<T> callback) {
        if (checkDate.isEmpty()) {
            ToastUtils.showShort("检查日期不能为空！");
            return;
        }
        if (checkUser.isEmpty()) {
            ToastUtils.showShort("检查人不能为空！");
            return;
        }
        if (checkRemark.isEmpty()) {
            ToastUtils.showShort("检查发现的主要问题（简述）不能为空！");
            return;
        }
        if (rectifyDate.isEmpty()) {
            ToastUtils.showShort("整改日期不能为空！");
            return;
        }
        if (rectifyRemark.isEmpty()) {
            ToastUtils.showShort("整改简况不能为空！");
            return;
        }
        if (reCheckUser.isEmpty()) {
            ToastUtils.showShort("复查人不能为空！");
            return;
        }
        if (reCheckDate.isEmpty()) {
            ToastUtils.showShort("复查日期不能为空！");
            return;
        }
        if (remark.isEmpty()) {
            ToastUtils.showShort("备注不能为空！");
            return;
        }

        OkGo.<T>post(Urls.SECURITYSAVE)
                .tag(mActivity)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("id", id)
                .params("checkDate", checkDate)
                .params("checkUser", checkUser)
                .params("checkRemark", checkRemark)
                .params("rectifyDate", rectifyDate)
                .params("rectifyRemark", rectifyRemark)
                .params("reCheckUser", reCheckUser)
                .params("reCheckDate", reCheckDate)
                .params("remark", remark)
                .execute(callback);
    }

    /**
     * 安全隐患台账详情接口
     */
    public static <T> void securityInfo(Activity activity, String id, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.SECURITYINFO)
                .tag(activity)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("id", id)
                .execute(callback);
    }

    /**
     * 油品进货台账录入接口
     */
    public static <T> void oilsIncomeSave(BaseActivity mActivity, FillUpTheAccountBean bean, JsonCallback<T> callback) {
        if (bean.getImcomeDate().isEmpty()) {
            ToastUtils.showShort("进油日期不能为空！");
            return;
        }
        if (bean.getOilsType().isEmpty()) {
            ToastUtils.showShort("油号不能为空！");
            return;
        }
        if (bean.getProviderLicense().isEmpty()) {
            ToastUtils.showShort("供油单位名称及许可证编号不能为空！");
            return;
        }
        if (bean.getProviderTel().isEmpty()) {
            ToastUtils.showShort("供油单位联系电话不能为空！");
            return;
        }
        if (bean.getProviderSendNo().isEmpty()) {
            ToastUtils.showShort("供货商单位编号不能为空！");
            return;
        }
        if (bean.getDangerousNo().isEmpty()) {
            ToastUtils.showShort("危品运输资格证及编号不能为空！");
            return;
        }
        if (bean.getCarNo().isEmpty()) {
            ToastUtils.showShort("罐车编号不能为空！");
            return;
        }
        if (bean.getDriverNo().isEmpty()) {
            ToastUtils.showShort("司机资格证明及编号不能为空！");
            return;
        }
        if (bean.getIncomeNum().isEmpty()) {
            ToastUtils.showShort("进货吨数不能为空！");
            return;
        }
        if (bean.getDensity().isEmpty()) {
            ToastUtils.showShort("密度不能为空！");
            return;
        }
        if (bean.getLitre().isEmpty()) {
            ToastUtils.showShort("升不能为空！");
            return;
        }
        if (bean.getIntoPotNo().isEmpty()) {
            ToastUtils.showShort("入灌编号不能为空！");
            return;
        }
        if (bean.getSendUser().isEmpty()) {
            ToastUtils.showShort("送货人签名不能为空！");
            return;
        }
        if (bean.getCheckUser().isEmpty()) {
            ToastUtils.showShort("验收人签名不能为空！");
            return;
        }
        if (bean.getStationLeader().isEmpty()) {
            ToastUtils.showShort("站长签名不能为空！");
            return;
        }
        if (bean.getRemark().isEmpty()) {
            ToastUtils.showShort("描述备注不能为空！");
            return;
        }
        if (bean.getInvoiceImg().isEmpty()) {
            ToastUtils.showShort("进货发票不能为空！");
            return;
        }
        if (bean.getQualificationImg().isEmpty()) {
            ToastUtils.showShort("产品质量合格证明材料不能为空！");
            return;
        }
        if (bean.getSendImg().isEmpty()) {
            ToastUtils.showShort("配送单图片地址不能为空！");
            return;
        }
        OkGo.<T>post(Urls.OILSINCOMESAVE)
                .tag(mActivity)
                .params("token", SPUtils.getInstance().getString("token"))
                .params(KUtils.objectToMap(bean), true)
                .execute(callback);
    }

    /**
     * 油品进货台账详情接口
     */
    public static <T> void oilsIncomeInfo(BaseActivity mActivity, String id, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.OILSINCOMEINFO)
                .tag(mActivity)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("id", id)
                .execute(callback);
    }

    /**
     * 车用尿素进货台账录入接口
     */
    public static <T> void ureaSave(BaseActivity mActivity, VehicleUreaBean bean, JsonCallback<T> callback) {
        if (bean.getImcomeDate().isEmpty()) {
            ToastUtils.showShort("进油日期不能为空！");
            return;
        }
        if (bean.getProviderName().isEmpty()) {
            ToastUtils.showShort("生产企业不能为空！");
            return;
        }
        if (bean.getProviderArea().isEmpty()) {
            ToastUtils.showShort("产地不能为空！");
            return;
        }
        if (bean.getBrand().isEmpty()) {
            ToastUtils.showShort("商标不能为空！");
            return;
        }
        if (bean.getSpec().isEmpty()) {
            ToastUtils.showShort("规格不能为空！");
            return;
        }
        if (bean.getIncomeNum().isEmpty()) {
            ToastUtils.showShort("进货数量不能为空！");
            return;
        }
        if (bean.getGrade().isEmpty()) {
            ToastUtils.showShort("等级不能为空！");
            return;
        }
        if (bean.getBatchNo().isEmpty()) {
            ToastUtils.showShort("生产日期或编号不能为空！");
            return;
        }
        if (bean.getQualificationNo().isEmpty()) {
            ToastUtils.showShort("生产质量合格证材料及编号不能为空！");
            return;
        }
        if (bean.getLicenseNo().isEmpty()) {
            ToastUtils.showShort("生产厂家许可证号不能为空！");
            return;
        }
        if (bean.getRemark().isEmpty()) {
            ToastUtils.showShort("验收人情况说明不能为空！");
            return;
        }
        if (bean.getInvoiceImg().isEmpty()) {
            ToastUtils.showShort("进货发票不能为空！");
            return;
        }
        if (bean.getQualificationImg().isEmpty()) {
            ToastUtils.showShort("产品质量合格证明材料不能为空！");
            return;
        }
        OkGo.<T>post(Urls.UREASAVE)
                .tag(mActivity)
                .params("token", SPUtils.getInstance().getString("token"))
                .params(KUtils.objectToMap(bean), true)
                .execute(callback);
    }

    /**
     * 车用尿素进货台账详情接口
     */
    public static <T> void ureaInfo(Activity activity, String id, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.UREAINFO)
                .tag(activity)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("id", id)
                .execute(callback);
    }

    /**
     * 报表删除接口
     */
    public static <T> void delete(Activity activity, String id, JsonCallback<T> callback) {
        OkGo.<T>post(Urls.DEL)
                .tag(activity)
                .params("token", SPUtils.getInstance().getString("token"))
                .params("id", id)
                .execute(callback);
    }
}
