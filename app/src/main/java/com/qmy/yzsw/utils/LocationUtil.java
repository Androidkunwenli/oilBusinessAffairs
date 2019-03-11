package com.qmy.yzsw.utils;

import android.content.Context;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * 创建时间：2015年12月1日 上午10:57:55
 * 项目名称：ELife
 *
 * @author Z.G.N
 * @version 1.0
 * @since jdk1.7.0_51
 * 文件名称：LocationUtil.java
 * 类说明：获取地址位置
 */
public class LocationUtil {
    private static LocationUtil locationUtil;
    private BDLocation location;
    private Context context;
    private LocationClient mLocationClient;
    private MyLocationListener mMyLocationListener;
    public BDLocation mlocation; // 定位到的数据
    public boolean isLocated; // 是否定位完成

    private LocationUtil(Context context) {
        // 百度地图
//		SDKInitializer.initialize(context);
        mLocationClient = new LocationClient(context.getApplicationContext());
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        //mGeofenceClient = new GeofenceClient(getApplicationContext());
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置高精度模式
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
        option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
        option.setTimeOut(5000);
        option.setIsNeedAddress(true);  //反地理编码
        mLocationClient.setLocOption(option);
    }

    /**
     * 定义一个单例方法，避免多线程并发的写法
     *
     * @param context
     * @return
     */
    public static LocationUtil getInstance(Context context) {
        if (locationUtil == null) {
            synchronized (LocationUtil.class) {
                if (locationUtil == null) {
                    locationUtil = new LocationUtil(context);
                }
            }
        }
        return locationUtil;
    }

    /**
     * 开始进行百度定位
     */
    public void startLocation() {

        mLocationClient.start();
    }

    /**
     * 结束百度定位
     */
    public void stopLocation() {
        mLocationClient.stop();
    }

    /**
     * 实现实位回调监听
     */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location != null && !TextUtils.isEmpty(location.getCity())) {
                if (baiduLocationListener != null) {
                    baiduLocationListener.onLocationChanged(location);
                }
                stopLocation();
            }
        }
    }

    public BaiduLocationListener baiduLocationListener;

    public void setBaiduLocatinListener(BaiduLocationListener baiduLocationListener) {
        this.baiduLocationListener = baiduLocationListener;
    }

    public interface BaiduLocationListener {
        public void onLocationChanged(BDLocation location);
    }


}

