package com.qmy.yzsw.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.HomeConsultationActivity;
import com.qmy.yzsw.activity.LeaseActivity;
import com.qmy.yzsw.activity.LoginActivity;
import com.qmy.yzsw.activity.MessageActivity;
import com.qmy.yzsw.activity.OilActivity;
import com.qmy.yzsw.activity.ShoppingActivity;
import com.qmy.yzsw.activity.SupplyActivity;
import com.qmy.yzsw.activity.TransportActivity;
import com.qmy.yzsw.activity.WebActivity;
import com.qmy.yzsw.activity.base.BaseFragment;
import com.qmy.yzsw.adapter.HomeAdapter;
import com.qmy.yzsw.bean.HotNewsBean;
import com.qmy.yzsw.bean.IndexBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.utils.GlideImageLoader;
import com.qmy.yzsw.utils.HttpUtils;
import com.qmy.yzsw.utils.KUtils;
import com.qmy.yzsw.utils.LocationUtil;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.rec_list)
    RecyclerView recList;
    @BindView(R.id.image_message)
    ImageView mImageMessage;
    @BindView(R.id.tv_location)
    TextView mTvLocation;
    private HomeAdapter mAdapter;
    private Banner mBanner;
    private LocationUtil mInstance;
    private IndexBean.CityVoBean mCityVo;
    private String mCityName;
    private String mCity;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        request();
    }

    private void request() {
        HttpUtils.index(getActivity(), new JsonCallback<BaseBean<IndexBean>>() {


            @Override
            public void onSuccess(Response<BaseBean<IndexBean>> response) {
                BaseBean<IndexBean> body = response.body();
                if (body.getCode() == 1) {
                    mCityVo = body.getData().getCityVo();
                    mCityName = mCityVo.getCityName();
                    mTvLocation.setText(mCityName);
                    ArrayList<String> arrayList = new ArrayList<>();
                    final List<IndexBean.AdverviseVoListBean> adverviseVoList = body.getData().getAdverviseVoList();
                    for (IndexBean.AdverviseVoListBean adverviseVoListBean : adverviseVoList) {
                        arrayList.add(adverviseVoListBean.getImgUrl());
                    }
                    mBanner.setImages(arrayList).setImageLoader(new GlideImageLoader()).start();
                    mBanner.setOnBannerListener(new OnBannerListener() {
                        @Override
                        public void OnBannerClick(int position) {
                            //linkType (integer, optional): 链接类型：1资讯2报表3外部链接100无链接 ,
                            // linkUrl (string, optional): 链接地址：linkType等于1和2的时候为id,等于3的时候为http地址，其它为空串
                            IndexBean.AdverviseVoListBean adverviseVoListBean = adverviseVoList.get(position);
                            switch (adverviseVoListBean.getLinkType()) {
                                case 1://1资讯
                                    break;
                                case 2://2报表
                                    break;
                                case 3://3外部链接
                                    WebActivity.start(getActivity(), adverviseVoListBean.getLinkUrl());
                                    break;
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 分页请求
     */
    private void getPagingRequest() {
        HttpUtils.hotNews(getActivity(), page += 1, new JsonCallback<BaseBean<ArrayList<HotNewsBean>>>() {
            @Override
            public void onSuccess(Response<BaseBean<ArrayList<HotNewsBean>>> response) {
                BaseBean<ArrayList<HotNewsBean>> body = response.body();
                if (body.getCode() == 1) {
                    if (page == 1) {
                        mAdapter.setNewData(body.getData());
                    } else {
                        ArrayList<HotNewsBean> data = body.getData();
                        if (!data.isEmpty()) {
                            mAdapter.addData(data);
                            mAdapter.loadMoreComplete();
                        } else {
                            mAdapter.loadMoreEnd();
                        }
                    }
                }
            }
        });
    }

    private View getInflate() {
        View inflate = View.inflate(getActivity(), R.layout.item_banner, null);
        mBanner = inflate.findViewById(R.id.banner);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mBanner.getLayoutParams();
        layoutParams.height = ScreenUtils.getScreenHeight() / 4;
        mBanner.setLayoutParams(layoutParams);
        return inflate;
    }


    @Override
    public void initView() {
        recList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new HomeAdapter(getActivity(), true);
        mAdapter.addHeaderView(getInflate());
        mAdapter.addHeaderView(getClassification());
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getPagingRequest();

            }
        }, recList);
        recList.setAdapter(mAdapter);
        getPagingRequest();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                WebActivity.start(getActivity(), (HotNewsBean) adapter.getData().get(position));
            }
        });

        mInstance = LocationUtil.getInstance(getActivity());
        mInstance.setBaiduLocatinListener(new LocationUtil.BaiduLocationListener() {
            @Override
            public void onLocationChanged(BDLocation location) {
                mCity = location.getCity();
                if (mCityName != null && !mCityName.isEmpty()) {
                } else {
                    mTvLocation.setText(mCity);
                }
                mInstance.stopLocation();
                isvfLocationi = false;
                citySelect();
            }
        });
        mInstance.startLocation();
    }

    private void citySelect() {
        HttpUtils.citySelect(getActivity(), mTvLocation.getText().toString().trim(), new JsonCallback<BaseBean>() {
            @Override
            public void onSuccess(Response<BaseBean> response) {
                page = 0;
                request();
                getPagingRequest();
            }
        });
    }

    public View getClassification() {
        View inflate = View.inflate(getActivity(), R.layout.item_classification, null);
        inflate.findViewById(R.id.tv_oil).setOnClickListener(this);//今日油价
        inflate.findViewById(R.id.tv_supply).setOnClickListener(this);//现货供应
        inflate.findViewById(R.id.tv_home_consultation).setOnClickListener(this);//今日资讯
        inflate.findViewById(R.id.tv_lease).setOnClickListener(this);//油站租赁
        inflate.findViewById(R.id.tv_shopping).setOnClickListener(this);//现货求购
        inflate.findViewById(R.id.tv_transport).setOnClickListener(this);//危货运输
        return inflate;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_oil:
                OilActivity.start(getActivity());
                break;
            case R.id.tv_supply:
                SupplyActivity.start(getActivity());
                break;
            case R.id.tv_home_consultation:
                HomeConsultationActivity.start(getActivity());
                break;
            case R.id.tv_lease:
                LeaseActivity.start(getActivity());
                break;
            case R.id.tv_shopping:
                ShoppingActivity.start(getActivity());
                break;
            case R.id.tv_transport:
                TransportActivity.start(getActivity(), 0);
                break;
        }
    }

    private boolean isvfLocationi;

    @OnClick({R.id.image_message, R.id.tv_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_message:
                MessageActivity.start(getActivity());
                break;
            case R.id.tv_location:
                isvfLocationi = true;
                CityPicker.getInstance()
                        .setFragmentManager(getActivity().getSupportFragmentManager())
                        .setLocatedCity(new LocatedCity(mCity, "", ""))
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
                                if (data != null) {
                                    mTvLocation.setText(data.getName());
                                    citySelect();
                                }
                            }

                            @Override
                            public void onLocate() {
                                if (mInstance != null) {
                                    mInstance.startLocation();
                                }
                            }
                        })
                        .show();
                break;
        }
    }
}
