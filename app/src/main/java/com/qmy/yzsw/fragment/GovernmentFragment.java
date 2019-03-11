package com.qmy.yzsw.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.WebActivity;
import com.qmy.yzsw.activity.base.BaseFragment;
import com.qmy.yzsw.adapter.HomeAdapter;
import com.qmy.yzsw.bean.HotNewsBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.utils.HttpUtils;
import com.youth.xframe.widget.loadingview.XLoadingView;

import java.util.ArrayList;

import butterknife.BindView;

public class GovernmentFragment extends BaseFragment {
    @BindView(R.id.rec_list)
    RecyclerView recList;
    @BindView(R.id.x_layout)
    XLoadingView x_layout;
    private HomeAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_government;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        recList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new HomeAdapter(getActivity(), false);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getPagingRequest();
            }
        }, recList);
        recList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                WebActivity.start(getActivity(), (HotNewsBean) adapter.getData().get(position));
            }
        });
    }

    /**
     * 分页请求
     */
    private void getPagingRequest() {
        HttpUtils.newList(getActivity(), page += 1, 1, new JsonCallback<BaseBean<ArrayList<HotNewsBean>>>() {
            @Override
            public void onSuccess(Response<BaseBean<ArrayList<HotNewsBean>>> response) {
                BaseBean<ArrayList<HotNewsBean>> body = response.body();
                if (body.getCode() == 1) {
                    if (page == 1) {
                        ArrayList<HotNewsBean> data = body.getData();
                        if (data.isEmpty()) {
                            x_layout.showEmpty();
                        } else {
                            mAdapter.setNewData(data);
                            x_layout.showContent();
                        }
                    } else {
                        ArrayList<HotNewsBean> data = body.getData();
                        if (!data.isEmpty()) {
                            mAdapter.setNewData(data);
                            mAdapter.loadMoreComplete();
                        } else {
                            mAdapter.loadMoreEnd();
                        }
                    }
                }
            }
        });
    }

    @Override
    public void initView() {
        getPagingRequest();
    }
}
