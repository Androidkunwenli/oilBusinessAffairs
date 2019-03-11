package com.qmy.yzsw.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.TransportMsgActivity;
import com.qmy.yzsw.activity.TransportReleaseActivity;
import com.qmy.yzsw.activity.base.BaseFragment;
import com.qmy.yzsw.adapter.PullTheGoodsAdapter;
import com.qmy.yzsw.bean.CarDetailBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.DialogCallback;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.utils.HttpUtils;
import com.youth.xframe.widget.loadingview.XLoadingView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PullTheGoodsFragment extends BaseFragment {
    @BindView(R.id.rec_list)
    RecyclerView recList;
    @BindView(R.id.x_layout)
    XLoadingView xLayout;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    private PullTheGoodsAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_pull_the_goods;
    }

    private int myself;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        if (arguments != null) {
            myself = arguments.getInt("myself", 0);
        }
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        recList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new PullTheGoodsAdapter(getActivity(), myself, "已接单");
        recList.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.image_state:
                        CarDetailBean carDetailBean = (CarDetailBean) adapter.getData().get(position);
                        HttpUtils.carOperator(getActivity(), carDetailBean.getId(), "1", new DialogCallback<BaseBean>(getActivity()) {
                            @Override
                            public void onSuccess(Response<BaseBean> response) {
                                ToastUtils.showShort(response.body().getMsg());
                                onResume();
                            }
                        });
                        break;
                }
            }
        });
    }

    @Override
    public void initView() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CarDetailBean bean = (CarDetailBean) adapter.getData().get(position);
                if (StringUtils.equals("0", bean.getMyself())) {
                    TransportMsgActivity.start(getActivity(), bean, null);
                } else if (StringUtils.equals("1", bean.getMyself())) {
                    TransportReleaseActivity.start(getActivity(), 1, bean, null);
                }
            }
        });
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onResume();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        HttpUtils.carList(getActivity(), myself, new JsonCallback<BaseBean<List<CarDetailBean>>>() {
            @Override
            public void onSuccess(Response<BaseBean<List<CarDetailBean>>> response) {
                swipeLayout.setRefreshing(false);
                List<CarDetailBean> data = response.body().getData();
                if (data.isEmpty()) {
                    xLayout.showEmpty();
                } else {
                    mAdapter.setNewData(data);
                    xLayout.showContent();
                }
            }
        });
    }


    public PullTheGoodsFragment newInstance(int myself) {
        PullTheGoodsFragment f = new PullTheGoodsFragment();
        Bundle b = new Bundle();
        b.putInt("myself", myself);
        f.setArguments(b);
        return f;
    }
}
