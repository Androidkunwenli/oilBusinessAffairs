package com.qmy.yzsw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.adapter.MessageAdapter;
import com.qmy.yzsw.bean.HelpBean;
import com.qmy.yzsw.bean.SmsBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MessageActivity extends BaseActivity {
    @BindView(R.id.rec_list)
    RecyclerView recList;
    private MessageAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setFindViewById(true);
        setTitleStr("消息");
    }

    @Override
    public void initView() {
        recList.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new MessageAdapter(mActivity);
        recList.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                paging();
            }
        }, recList);
        paging();
    }

    private void paging() {
        HttpUtils.smsList(mActivity, page += 1, new JsonCallback<BaseBean<List<SmsBean>>>() {
            @Override
            public void onSuccess(Response<BaseBean<List<SmsBean>>> response) {
                BaseBean<List<SmsBean>> body = response.body();
                if (body.getCode() == 1) {
                    if (page == 1) {
                        mAdapter.setNewData(body.getData());
                    } else {
                        if (!body.getData().isEmpty()) {
                            mAdapter.addData(body.getData());
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
        activity.startActivity(new Intent(activity, MessageActivity.class));
    }
}
