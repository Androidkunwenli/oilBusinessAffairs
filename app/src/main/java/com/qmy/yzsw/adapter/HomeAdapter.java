package com.qmy.yzsw.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmy.yzsw.R;
import com.qmy.yzsw.bean.HotNewsBean;

public class HomeAdapter extends BaseQuickAdapter<HotNewsBean, BaseViewHolder> {

    private boolean mShow;
    private FragmentActivity mActivity;

    public HomeAdapter(FragmentActivity activity, boolean show) {
        super(R.layout.item_home);
        mActivity = activity;
        mShow = show;
    }

    @Override
    protected void convert(BaseViewHolder helper, HotNewsBean item) {
        String imgsUrl = item.getImgsUrl();
        helper.setGone(R.id.ll_layout, imgsUrl != null && !imgsUrl.isEmpty())
                .setGone(R.id.tv_content_str, !(imgsUrl != null && !imgsUrl.isEmpty()))
                .setText(R.id.tv_content_str, Html.fromHtml(item.getContent()));
        String[] split = imgsUrl.split(",");
        switch (split.length) {
            case 1:
                helper.setGone(R.id.image_1, true)
                        .setGone(R.id.image_2, false)
                        .setGone(R.id.image_3, false)
                        .setGone(R.id.v_division_1, false)
                        .setGone(R.id.v_division_2, false);
                Glide.with(mActivity.getApplicationContext()).load(split[0]).into((ImageView) helper.getView(R.id.image_1));
                break;
            case 2:
                helper.setGone(R.id.image_1, true)
                        .setGone(R.id.image_2, true)
                        .setGone(R.id.image_3, false)
                        .setGone(R.id.v_division_1, true)
                        .setGone(R.id.v_division_2, false);
                Glide.with(mActivity.getApplicationContext()).load(split[0]).into((ImageView) helper.getView(R.id.image_1));
                Glide.with(mActivity.getApplicationContext()).load(split[1]).into((ImageView) helper.getView(R.id.image_2));
                break;
            case 3:
                helper.setGone(R.id.image_1, true)
                        .setGone(R.id.image_2, true)
                        .setGone(R.id.image_3, true)
                        .setGone(R.id.v_division_1, true)
                        .setGone(R.id.v_division_2, true);
                Glide.with(mActivity.getApplicationContext()).load(split[0]).into((ImageView) helper.getView(R.id.image_1));
                Glide.with(mActivity.getApplicationContext()).load(split[1]).into((ImageView) helper.getView(R.id.image_2));
                Glide.with(mActivity.getApplicationContext()).load(split[2]).into((ImageView) helper.getView(R.id.image_3));
                break;
        }
        helper.setText(R.id.tv_content, item.getTitle())
                .setText(R.id.tv_view_cnt, item.getViewCnt())
                .setGone(R.id.fl_title, (helper.getAdapterPosition() - getHeaderLayoutCount()) == 0 && mShow);
    }
}
