package com.qmy.yzsw.activity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.navisdk.adapter.BaiduNaviManagerFactory;
import com.baidu.navisdk.adapter.IBNTTSManager;
import com.baidu.navisdk.adapter.IBaiduNaviManager;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.adapter.TodayPriceListAdapter;
import com.qmy.yzsw.bean.TodayPriceListBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.utils.HttpUtils;
import com.qmy.yzsw.utils.KUtils;
import com.qmy.yzsw.utils.LocationUtil;
import com.qmy.yzsw.utils.NormalUtils;
import com.qmy.yzsw.view.ModifyPriceFramgment;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class OilActivity extends BaseActivity implements SensorEventListener, LocationUtil.BaiduLocationListener {
    @BindView(R.id.rec_list)
    RecyclerView reclist;
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.ninetytwo)
    TextView ninetytwo;
    @BindView(R.id.ninetyfive)
    TextView ninetyfive;
    @BindView(R.id.ninetyeight)
    TextView ninetyeight;
    @BindView(R.id.zero)
    TextView zero;
    @BindView(R.id.ten)
    TextView ten;
    @BindView(R.id.twenty)
    TextView twenty;
    @BindView(R.id.lng)
    TextView lng;
    @BindView(R.id.cng)
    TextView cng;
    @BindView(R.id.charging)
    TextView charging;
    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    @BindView(R.id.tv_msg)
    TextView mTv_msg;
    @BindView(R.id.ll_layout_height)
    LinearLayout llLayoutHeight;
    @BindView(R.id.ll_layout_height_setting)
    LinearLayout llLayoutHeightSetting;
    private BaiduMap mBaiduMap;
    private LocationUtil mLocationUtil;
    private TodayPriceListAdapter mAdapter;
    private Marker mMarker;
    private int measuredHeight;

    @Override
    public int getLayoutId() {
        return R.layout.activity_oil;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mBaiduMap = mapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.FOLLOWING, true, null));
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.overlook(0);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务

        mLocationUtil = LocationUtil.getInstance(mActivity);
        mLocationUtil.setBaiduLocatinListener(this);
        mLocationUtil.startLocation();
        initDirs();
        initNavi();
    }


    private void initNavi() {
        BaiduNaviManagerFactory.getBaiduNaviManager().init(this,
                mSDCardPath, APP_FOLDER_NAME, new IBaiduNaviManager.INaviInitListener() {

                    @Override
                    public void onAuthResult(int status, String msg) {
                        String result;
                        if (0 != status) {
                            result = "key校验失败, " + msg;
                            Toast.makeText(OilActivity.this, result, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void initStart() {
                    }

                    @Override
                    public void initSuccess() {
                        hasInitSuccess = true;
                        // 初始化tts
                        initTTS();
                    }

                    @Override
                    public void initFailed() {
                        Toast.makeText(OilActivity.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void initTTS() {
        // 使用内置TTS
        BaiduNaviManagerFactory.getTTSManager().initTTS(getApplicationContext(),
                getSdcardDir(), APP_FOLDER_NAME, NormalUtils.getTTSAppID());
        // 注册同步内置tts状态回调
        BaiduNaviManagerFactory.getTTSManager().setOnTTSStateChangedListener(
                new IBNTTSManager.IOnTTSPlayStateChangedListener() {
                    @Override
                    public void onPlayStart() {
                    }

                    @Override
                    public void onPlayEnd(String speechId) {
                    }

                    @Override
                    public void onPlayError(int code, String message) {
                    }
                }
        );

        // 注册内置tts 异步状态消息
        BaiduNaviManagerFactory.getTTSManager().setOnTTSStateChangedHandler(
                new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                    }
                }
        );
    }

    private static final String APP_FOLDER_NAME = "OilActivitySimpleDemo";
    private String mSDCardPath = null;
    private boolean hasInitSuccess = false;

    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    private String oilType = "ninetytwo";

    @Override
    public void initView() {
        setFindViewById(true);
        setTitleStr("今日油价");
        setTvRightText("92#");
        mRightKey.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_oil_dropdown_box, 0);
        mRightKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llLayout.getVisibility() == View.GONE) {
                    llLayout.setVisibility(View.VISIBLE);
                } else {
                    llLayout.setVisibility(View.GONE);
                }
            }
        });

        reclist.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new TodayPriceListAdapter(mActivity);
        reclist.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TodayPriceListBean todayPriceListBean = (TodayPriceListBean) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.image_modify_oil_price:
                        final ModifyPriceFramgment framgment = new ModifyPriceFramgment();
                        framgment.setOnItemChildClickListener(mRightKey.getText().toString().trim(), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                switch (view.getId()) {
                                    case R.id.btn_cancle:
                                        framgment.dismiss();
                                        break;
                                    case R.id.btn_ok:
                                        String price = framgment.et_price.getText().toString().trim();
                                        if (price.isEmpty()) {
                                            ToastUtils.showShort("请输入要修改的油价格");
                                            return;
                                        }
                                        HttpUtils.todayPriceUpdate(mActivity, oilType, price, new JsonCallback<BaseBean>() {
                                            @Override
                                            public void onSuccess(Response<BaseBean> response) {
                                                framgment.dismiss();
                                                show();
                                            }
                                        });
                                        break;
                                }
                            }
                        });
                        framgment.show(getSupportFragmentManager(), "ModifyPriceFramgment");
                        break;
                    case R.id.ll_layout:
                        OilMsgActivity.start(mActivity, todayPriceListBean, mCurrentLat, mCurrentLon);
                        break;
                    case R.id.image_navigation:
                        if (mCurrentLon != 0.0 && mCurrentLat != 0.0) {
                            KUtils.startNavi(mActivity,
                                    new LatLng(mCurrentLat, mCurrentLon),
                                    "",
                                    new LatLng(todayPriceListBean.getLatitude(),
                                            todayPriceListBean.getLongitude()),
                                    "");
                        }
                        break;
                }
            }
        });
    }

    public static void start(FragmentActivity activity) {
        activity.startActivity(new Intent(activity, OilActivity.class));
    }

    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    private MyLocationData locData;
    boolean isFirstLoc = true; // 是否首次定位
    private SensorManager mSensorManager;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onLocationChanged(BDLocation location) {
        // map view 销毁后不在处理新接收的位置
        if (location == null || mapView == null) {
            return;
        }
        mCurrentLat = location.getLatitude();
        mCurrentLon = location.getLongitude();
        mCurrentAccracy = location.getRadius();
        locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(mCurrentDirection).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);
        if (isFirstLoc) {
            isFirstLoc = false;
            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(18.0f);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

            show();
        }
    }

    /**
     * 获取今日油价
     */
    private void show() {
        HttpUtils.todayPriceList(mActivity, oilType, mCurrentLat, mCurrentLon, new JsonCallback<BaseBean<List<TodayPriceListBean>>>() {
            @Override
            public void onSuccess(Response<BaseBean<List<TodayPriceListBean>>> response) {
                List<TodayPriceListBean> data = response.body().getData();
                mAdapter.setNewData(data);
                if (mTv_msg != null) {
                    mTv_msg.setText("附近" + data.size() + "个加油站");
                }
                mBaiduMap.clear();
                for (TodayPriceListBean listBean : data) {
                    View view = View.inflate(mActivity, R.layout.item_mak_oil, null);
                    ((TextView) view.findViewById(R.id.tv_oil_price)).setText("¥ " + listBean.getOilPrice());
                    ((TextView) view.findViewById(R.id.tv_oil_name)).setText(listBean.getOilName());
                    try {
                        LatLng latLng = new LatLng(listBean.getLatitude(), listBean.getLongitude());
                        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(view);
                        // 构建markerOption，用于在地图上添加marker
                        OverlayOptions options = new MarkerOptions()//
                                .position(latLng)// 设置marker的位置
                                .icon(bitmapDescriptor)// 设置marker的图标
                                .draggable(false);// 设置手势拖拽
                        //   .zIndex(9)// 設置marker的所在層級
                        // 在地图上添加marker，并显示
                        mMarker = (Marker) mBaiduMap.addOverlay(options);
                    } catch (NullPointerException e) {
                        e.getMessage();
                    }
                }
            }
        });
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onStop() {
        //取消注册传感器监听
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocationUtil.stopLocation();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        mapView = null;
        super.onDestroy();
    }

    @OnClick({R.id.ninetytwo, R.id.ninetyfive, R.id.ninetyeight, R.id.zero, R.id.ten, R.id.twenty, R.id.lng, R.id.cng, R.id.charging, R.id.ll_layout_height_setting})
    public void onViewClicked(View view) {
        llLayout.setVisibility(View.GONE);
        switch (view.getId()) {
            case R.id.ninetytwo:
                oilType = "ninetytwo";
                show();
                setTvRightText(ninetytwo.getText().toString().trim());
                break;
            case R.id.ninetyfive:
                oilType = "ninetyfive";
                show();
                setTvRightText(ninetyfive.getText().toString().trim());
                break;
            case R.id.ninetyeight:
                oilType = "ninetyeight";
                show();
                setTvRightText(ninetyeight.getText().toString().trim());
                break;
            case R.id.zero:
                oilType = "zero";
                show();
                setTvRightText(zero.getText().toString().trim());
                break;
            case R.id.ten:
                oilType = "ten";
                show();
                setTvRightText(ten.getText().toString().trim());
                break;
            case R.id.twenty:
                oilType = "twenty";
                show();
                setTvRightText(twenty.getText().toString().trim());
                break;
            case R.id.lng:
                oilType = "lng";
                show();
                setTvRightText(lng.getText().toString().trim());
                break;
            case R.id.cng:
                oilType = "cng";
                show();
                setTvRightText(cng.getText().toString().trim());
                break;
            case R.id.charging:
                oilType = "charging";
                show();
                setTvRightText(charging.getText().toString().trim());
                break;
            case R.id.ll_layout_height_setting:
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) llLayoutHeight.getLayoutParams();
                layoutParams.height = isHeight ? ScreenUtils.getScreenHeight() - SizeUtils.dp2px(92) : SizeUtils.dp2px(280);
                llLayoutHeight.setLayoutParams(layoutParams);
                isHeight = !isHeight;
                break;
        }
    }

    private boolean isHeight = true;
}
