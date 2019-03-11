package com.qmy.yzsw.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseFragment;
import com.qmy.yzsw.adapter.LeaseAdapter;
import com.qmy.yzsw.bean.LeaseBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.utils.HttpUtils;
import com.youth.xframe.widget.loadingview.XLoadingView;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;

import butterknife.BindView;

public class LeaseFragment extends BaseFragment {
    @BindView(R.id.rec_list)
    RecyclerView recList;
    @BindView(R.id.x_layout)
    XLoadingView x_layout;
    private LeaseAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_lease;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        recList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new LeaseAdapter(getActivity(), "1");
        recList.setAdapter(mAdapter);
    }

    @Override
    public void initView() {

    }

    @Override
    public void onResume() {
        super.onResume();
        HttpUtils.leaselist(getActivity(), 1, 0, new JsonCallback<BaseBean<List<LeaseBean>>>() {
            @Override
            public void onSuccess(Response<BaseBean<List<LeaseBean>>> response) {
                List<LeaseBean> data = response.body().getData();
                if (data.isEmpty()) {
                    x_layout.showEmpty();
                } else {
                    mAdapter.setNewData(data);
                    x_layout.showContent();
                }
            }
        });
    }
}
