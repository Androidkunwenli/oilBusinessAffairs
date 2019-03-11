package com.qmy.yzsw.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseFragment;
import com.qmy.yzsw.adapter.MyLeaseaAapter;
import com.qmy.yzsw.bean.LeaseBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.DialogCallback;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.utils.HttpUtils;
import com.youth.xframe.widget.loadingview.XLoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyLeaseFragment extends BaseFragment {
    @BindView(R.id.rec_list)
    RecyclerView recList;
    @BindView(R.id.x_layout)
    XLoadingView x_layout;
    private MyLeaseaAapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_lease;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        recList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MyLeaseaAapter(getActivity(), "1");
        recList.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                LeaseBean leaseBean = (LeaseBean) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.image_no_complete:
                        HttpUtils.leaseUpdateState(getActivity(), leaseBean.getId(), 3, new DialogCallback<BaseBean>(getActivity()) {
                            @Override
                            public void onSuccess(Response<BaseBean> response) {
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

    }

    @Override
    public void onResume() {
        super.onResume();
        HttpUtils.leaselist(getActivity(), 1, myself, new JsonCallback<BaseBean<List<LeaseBean>>>() {
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

    private int myself;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        if (arguments != null) {
            myself = arguments.getInt("myself", 0);
        }
    }

    public MyLeaseFragment newInstance(int myself) {
        MyLeaseFragment f = new MyLeaseFragment();
        Bundle b = new Bundle();
        b.putInt("myself", myself);
        f.setArguments(b);
        return f;
    }
}
