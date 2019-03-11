package com.qmy.yzsw.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmy.yzsw.R;
import com.qmy.yzsw.bean.ImageUploadBean;
import com.qmy.yzsw.utils.GlideUtils;

public class ImageSceneAdapter extends BaseQuickAdapter<ImageUploadBean, BaseViewHolder> {

    private FragmentActivity mActivity;

    public ImageSceneAdapter(FragmentActivity activity) {
        super(R.layout.item_image);
        mActivity = activity;
    }

    /**
     * 用来处理 如果是Footer 是 GridView 的情况下 最后占一个 gridview item
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();

        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果当前是footer的位置，那么该item占据2个单元格，正常情况下占据1个单元格
                    return 1;
                }
            });
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, ImageUploadBean item) {
        helper.setText(R.id.tv_title_str, item.getTitle());
        ImageView image_upload = (ImageView) helper.getView(R.id.image_upload);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) image_upload.getLayoutParams();
        layoutParams.height = ScreenUtils.getScreenHeight() / 6;
        image_upload.setLayoutParams(layoutParams);

        switch (item.getOpType()) {
            case 10:
                String file9 = item.getFile9();
                if (setImage(item, image_upload)) {
                    GlideUtils.getLoadImage(file9, image_upload, item);
                }
                break;
            case 11:
                String file10 = item.getFile10();
                if (setImage(item, image_upload)) {
                    GlideUtils.getLoadImage(file10, image_upload, item);
                }
                break;
            case 12:
                String file11 = item.getFile11();
                if (setImage(item, image_upload)) {
                    GlideUtils.getLoadImage(file11, image_upload, item);
                }
                break;
            case 13:
                String file12 = item.getFile12();
                if (setImage(item, image_upload)) {
                    GlideUtils.getLoadImage(file12, image_upload, item);
                }
                break;
            case 14:
                String file13 = item.getFile13();
                if (setImage(item, image_upload)) {
                    GlideUtils.getLoadImage(file13, image_upload, item);
                }
                break;
            case 15:
                String file14 = item.getFile14();
                if (setImage(item, image_upload)) {
                    GlideUtils.getLoadImage(file14, image_upload, item);
                }
                break;
            case 16:
                String file15 = item.getFile15();
                if (setImage(item, image_upload)) {
                    GlideUtils.getLoadImage(file15, image_upload, item);
                }
                break;
        }
    }

    private boolean setImage(ImageUploadBean item, ImageView image_upload) {
        boolean image = item.getUpload_image() != null && !item.getUpload_image().isEmpty();
        if (image) {
            Glide.with(mActivity.getApplicationContext()).load(item.getUpload_image()).into(image_upload);
        }
        return !image;
    }
}
