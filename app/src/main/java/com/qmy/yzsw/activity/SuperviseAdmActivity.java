package com.qmy.yzsw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.adapter.SuperviseAdmAdapter;
import com.qmy.yzsw.bean.SuperviseListBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.utils.HttpUtils;
import com.youth.xframe.widget.loadingview.XLoadingView;

import java.util.List;

import butterknife.BindView;

public class SuperviseAdmActivity extends BaseActivity {
    @BindView(R.id.rec_list)
    RecyclerView mRecList;
    @BindView(R.id.x_layout)
    XLoadingView x_layout;
    private SuperviseAdmAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_supervise_adm;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mRecList.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new SuperviseAdmAdapter(mActivity);
        mRecList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SuperviseListBean listBean = (SuperviseListBean) adapter.getData().get(position);
                SuperviseReleaseActivity.start(mActivity,listBean);
            }
        });
    }

    @Override
    public void initView() {
        setFindViewById(true);
        setTitleStr("监督管理");
        setTvRightText("发布");
        mRightKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuperviseReleaseActivity.start(mActivity, null);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpUtils.superviseList(mActivity, new JsonCallback<BaseBean<List<SuperviseListBean>>>() {
            @Override
            public void onSuccess(Response<BaseBean<List<SuperviseListBean>>> response) {
                List<SuperviseListBean> data = response.body().getData();
                if (data.isEmpty()) {
                    x_layout.showEmpty();
                } else {
                    mAdapter.setNewData(data);
                    x_layout.showContent();
                }
            }
        });
    }

    public static void start(FragmentActivity activity) {
        activity.startActivity(new Intent(activity, SuperviseAdmActivity.class));
    }
}
