package com.qmy.yzsw.adapter;


import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;
import com.qmy.yzsw.bean.FillUpTheAccountImageBean;
import com.qmy.yzsw.utils.GlideUtils;

public class FillUpTheAccountAdapter extends BaseQuickAdapter<FillUpTheAccountImageBean, BaseViewHolder> {

    private BaseActivity mActivity;

    public FillUpTheAccountAdapter(BaseActivity mActivity) {
        super(R.layout.item_image);
        this.mActivity = mActivity;
    }

    @Override
    protected void convert(BaseViewHolder helper, FillUpTheAccountImageBean item) {
        ImageView image_upload = (ImageView) helper.getView(R.id.image_upload);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) image_upload.getLayoutParams();
        layoutParams.height = ScreenUtils.getScreenHeight() / 6;
        image_upload.setLayoutParams(layoutParams);
        helper.setText(R.id.tv_title_str, item.getTvtitlestr());
        image_upload.setImageResource(item.isAdd() ? R.mipmap.ic_add_image : R.mipmap.ic_upload_photo_b);
        GlideUtils.LoadImage(item.getImagePath() != null && !item.getImagePath().isEmpty() ? item.getImagePath() : item.getImageupload(), image_upload);
    }
}
