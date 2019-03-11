package com.qmy.yzsw;


import android.support.multidex.MultiDex;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.lzy.okgo.OkGo;
import com.pgyersdk.crash.PgyCrashManager;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.youth.xframe.base.XApplication;

import cn.jpush.android.api.JPushInterface;

public class YawsApp extends XApplication {
    static {
        PlatformConfig.setWeixin("wx592cfcd6a5175230", "806590363ce108627cae7d006f1682b4");
        PlatformConfig.setQQZone("1107738743", "7g5o8pugqXc9MzFd");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        Utils.init(this);
        OkGo.getInstance().init(this);
        PgyCrashManager.register(this);

        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this, "5a12384aa40fa3551f0001d1"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");

        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
    }

}
