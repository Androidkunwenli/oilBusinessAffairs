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

public class ImageAdapter extends BaseQuickAdapter<ImageUploadBean, BaseViewHolder> {

    private FragmentActivity mActivity;

    public ImageAdapter(FragmentActivity activity) {
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
            case 1:
                String file1 = item.getFile1();
                if (getImage(item, image_upload)) {
                    GlideUtils.getLoadImage(file1, image_upload, item);
                }
                break;
            case 2:
                String file2 = item.getFile2();
                if (getImage(item, image_upload)) {
                    GlideUtils.getLoadImage(file2, image_upload, item);
                }
                break;
            case 3:
                String file3 = item.getFile3();
                if (getImage(item, image_upload)) {
                    GlideUtils.getLoadImage(file3, image_upload, item);
                }
                break;
            case 4:
                String file4 = item.getFile4();
                if (getImage(item, image_upload)) {
                    GlideUtils.getLoadImage(file4, image_upload, item);
                }
                break;
            case 5:
                String file5 = item.getFile5();
                if (getImage(item, image_upload)) {
                    GlideUtils.getLoadImage(file5, image_upload, item);
                }
                break;
            case 6:
                String file6 = item.getFile6();
                if (getImage(item, image_upload)) {
                    GlideUtils.getLoadImage(file6, image_upload, item);
                }
                break;
            case 7:
                String file7 = item.getFile7();
                if (getImage(item, image_upload)) {
                    GlideUtils.getLoadImage(file7, image_upload, item);
                }
                break;
            case 8:
                String file8 = item.getFile8();
                if (getImage(item, image_upload)) {
                    GlideUtils.getLoadImage(file8, image_upload, item);
                }
                break;
            case 9:
                String file9 = item.getFile9();
                if (getImage(item, image_upload)) {
                    GlideUtils.getLoadImage(file9, image_upload, item);
                }
                break;
            case 29:
                String file10 = item.getFile10();
                if (getImage(item, image_upload)) {
                    GlideUtils.getLoadImage(file10, image_upload, item);
                }
                break;
        }
    }

    private boolean getImage(ImageUploadBean item, ImageView image_upload) {
        boolean image = item.getUpload_image() != null && !item.getUpload_image().isEmpty();
        if (image) {
            Glide.with(mActivity.getApplicationContext()).load(item.getUpload_image()).into(image_upload);
        }
        return !image;
    }
}
