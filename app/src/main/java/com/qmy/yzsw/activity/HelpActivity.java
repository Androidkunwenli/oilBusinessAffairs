package com.qmy.yzsw.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.bean.HelpBean;
import com.qmy.yzsw.bean.HotNewsBean;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HelpActivity extends BaseActivity {
    @BindView(R.id.rec_list)
    RecyclerView recList;
    private BaseQuickAdapter<HelpBean, BaseViewHolder> mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setFindViewById(true);
        setTitleStr("帮助中心");
        recList.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new BaseQuickAdapter<HelpBean, BaseViewHolder>(R.layout.item_help) {
            @Override
            protected void convert(BaseViewHolder helper, HelpBean item) {
                helper.setText(R.id.tv_title_str, item.getTitle())
                        .setText(R.id.tv_content_str, Html.fromHtml(item.getContent()));
            }
        };
        recList.setAdapter(mAdapter);
    }

    @Override
    public void initView() {
        HttpUtils.helpList(mActivity, page += 1, new JsonCallback<BaseBean<List<HelpBean>>>() {
            @Override
            public void onSuccess(Response<BaseBean<List<HelpBean>>> response) {
                BaseBean<List<HelpBean>> body = response.body();
                if (body.getCode() == 1) {
                    if (page == 1) {
                        mAdapter.setNewData(body.getData());
                    } else {
                        List<HelpBean> data = body.getData();
                        if (!data.isEmpty()) {
                            mAdapter.addData(data);
                        } else {
                            mAdapter.loadMoreEnd();
                        }
                    }
                }
            }
        });
    }

    public static void start(BaseActivity activity) {
        activity.startActivity(new Intent(activity, HelpActivity.class));
    }
}
