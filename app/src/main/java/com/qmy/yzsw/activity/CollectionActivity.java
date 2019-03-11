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
import com.qmy.yzsw.adapter.CollectionAdapter;
import com.qmy.yzsw.adapter.HomeAdapter;
import com.qmy.yzsw.bean.HotNewsBean;
import com.qmy.yzsw.bean.NewDetailBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.utils.HttpUtils;
import com.youth.xframe.widget.loadingview.XLoadingView;

import java.util.ArrayList;

import butterknife.BindView;

public class CollectionActivity extends BaseActivity {
    @BindView(R.id.rec_list)
    RecyclerView recList;
    @BindView(R.id.x_layout)
    XLoadingView x_layout;
    private CollectionAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setFindViewById(true);
        setTitleStr("我的收藏");
    }

    @Override
    public void initView() {
        recList.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new CollectionAdapter(mActivity, false);
//        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                getPagingRequest();
//            }
//        }, recList);
        recList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                WebActivity.start(mActivity, (HotNewsBean) adapter.getData().get(position));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        page = 0;
        getPagingRequest();
    }

    /**
     * 分页请求
     */
    private void getPagingRequest() {
        HttpUtils.myColletctNewsList(mActivity, page += 1, 10, new JsonCallback<BaseBean<ArrayList<HotNewsBean>>>() {
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

    public static void start(FragmentActivity activity) {
        activity.startActivity(new Intent(activity, CollectionActivity.class));
    }
}
