package com.qmy.yzsw.config;

public class Urls {
    private static final String URL = "http://yzsw.qumaiyou.com:8888";
    //    private static final String URL = "http://47.93.62.197:8080";
    //    public static final String URL = "http://192.168.1.120:8180";
    //注册
    public static final String REGISTER = URL + "/app/user/register";
    //获取验证码
    public static final String GETSMSCODE = URL + "/app/user/getSmsCode";
    //忘记密码
    public static final String FORGETPASSWORD = URL + "/app/user/forgetPass";
    //登陆
    public static final String LOGIN = URL + "/app/user/login";
    //首页热点资讯
    public static final String HOTNEWS = URL + "/app/main/hotNews";
    //资讯详情接口
    public static final String NEWDETAIL = URL + "/app/main/newDetail";
    // 今日资讯列表接口
    public static final String NEWLIST = URL + "/app/main/newList";
    //系统首页接口
    public static final String INDEX = URL + "/app/main/index";
    //油站证件图片保存接口
    public static final String CERTIFICATESAVE = URL + "/app/station/certificateSave";
    //油站执照信息获取接口
    public static final String GETCERTIFICATEINFO = URL + "/app/station/getCertificateInfo";
    //油站信息获取接口
    public static final String GETINFO = URL + "/app/station/getInfo";
    //油站场景信息获取接口
    public static final String GETSCENEINFO = URL + "/app/station/getSceneInfo";
    //油站图片上传通用接口
    public static final String IMGUPLOAD = URL + "/app/station/imgUpload";
    //油站信息录入接口
    public static final String SAVEINFO = URL + "/app/station/saveInfo";
    //油站场景图片保存接口
    public static final String SCENESAVE = URL + "/app/station/sceneSave";
    //油站租赁发布接口
    public static final String LEASESAVE = URL + "/app/station/leaseSave";
    //油站转让接口
    public static final String MAKEOVERSAVE = URL + "/app/station/makeOverSave";
    //油站租赁转让列表
    public static final String LEASELIST = URL + "/app/station/leaseList";
    //油站租赁转让详情
    public static final String LEASEDETAIL = URL + "/app/station/leaseDetail";
    //油站位置信息保存接口
    public static final String COORDINATESAVE = URL + "/app/station/coordinateSave";
    //证照到期日信息保存接口
    public static final String EXPIREDATESAVE = URL + "/app/station/expireDateSave";
    //证照到期日信息获取接口
    public static final String EXPIREDATEGET = URL + "/app/station/expireDateGet";
    //油站监督信息发布接口
    public static final String SUPERVISESAVE = URL + "/app/station/superviseSave";
    //油站监督信息列表接口
    public static final String SUPERVISELIST = URL + "/app/station/superviseList";
    //今日油价
    public static final String TODAYPRICELIST = URL + "/app/station/todayPriceList";
    //修改今日油价
    public static final String TODAYPRICEUPDATE = URL + "/app/station/todayPriceUpdate";
    //城市选择
    public static final String CITYSELECT = URL + "/app/main/citySelect";
    //发布拉货信息接口
    public static final String CARSAVE = URL + "/app/transport/carSave";
    //拉货信息详情接口
    public static final String CARDETAIL = URL + "/app/transport/carDetail";
    //拉货信息列表接口
    public static final String CARLIST = URL + "/app/transport/carList";
    //找车详情接口
    public static final String OILSDETAIL = URL + "/app/transport/oilsDetail";
    //找车信息列表接口
    public static final String OILSLIST = URL + "/app/transport/oilsList";
    //发布找车信息接口
    public static final String OILSSAVE = URL + "/app/transport/oilsSave";
    //修改昵称
    public static final String NICKNAMESAVE = URL + "/app/mine/nicknameSave";
    //我的首页接口
    public static final String MYINDEX = URL + "/app/mine/index";
    //关于我们接口
    public static final String ABOUTUS = URL + "/app/mine/aboutUs";
    //意见反馈接口
    public static final String COMPLAINT = URL + "/app/mine/complaint";
    //修改密码接口
    public static final String MODIFYPASS = URL + "/app/mine/modifyPass";
    //退出登录接口
    public static final String LOGINOUT = URL + "/app/mine/loginOut";
    //我的收藏列表接口
    public static final String MYCOLLETCTNEWSLIST = URL + "/app/main/myColletctNewsList";
    //我的油价列表接口
    public static final String MYPRICELIST = URL + "/app/station/myPriceList";
    //批量修改油价接口
    public static final String SAVEPRICELIST = URL + "/app/station/savePriceList";
    //资讯收藏/分享接口
    public static final String NEWSOPERATE = URL + "/app/main/newsOperate";
    //油站转让/租赁删除/成交接口
    public static final String LEASEUPDATESTATE = URL + "/app/station/leaseUpdateState";
    //修改个人头像接口
    public static final String IMGHEADSAVE = URL + "/app/mine/imgHeadSave";
    //报表列表接口
    public static final String FILELIST = URL + "/app/file/list";
    //报表下载接口
    public static final String DOWNLOAD = URL + "/app/file/downLoad";
    //拉货信息删除/成交接口
    public static final String CAROPERATOR = URL + "/app/transport/carOperator";
    //找车信息删除/成交接口
    public static final String OILSOPERATOR = URL + "/app/transport/oilsOperator";
    //系统首页消息列表接口
    public static final String SMSLIST = URL + "/app/main/smsList";
    //油站详情接口
    public static final String DETAIL = URL + "/app/station/detail";
    //油站监督信息详情接口
    public static final String SUPERVISEDETAIL = URL + "/app/station/superviseDetail";
    //帮助列表接口
    public static final String HELPLIST = URL + "/app/mine/helpList";
    //我的提交列表接口
    public static final String MYLIST = URL + "/app/report/myList";
    //油品进货台账详情接口
    public static final String OILSINCOMEINFO = URL + "/app/report/oilsIncomeInfo";
    //油品进货台账录入接口
    public static final String OILSINCOMESAVE = URL + "/app/report/oilsIncomeSave";
    //油品销售台账详情接口
    public static final String OILSSALEINFO = URL + "/app/report/oilsSaleInfo";
    //油品销售台账录入接口
    public static final String OILSSALESAVE = URL + "/app/report/oilsSaleSave";
    //安全隐患台账详情接口
    public static final String SECURITYINFO = URL + "/app/report/securityInfo";
    //安全隐患台账录入接口
    public static final String SECURITYSAVE = URL + "/app/report/securitySave";
    //车用尿素进货台账详情接口
    public static final String UREAINFO = URL + "/app/report/ureaInfo";
    //车用尿素进货台账录入接口
    public static final String UREASAVE = URL + "/app/report/ureaSave";
    //字典下拉接口
    public static final String SELECTLIST = URL + "/app/dict/selectList";
    //报表删除
    public static final String DEL = URL + "/app/file/del";
}
