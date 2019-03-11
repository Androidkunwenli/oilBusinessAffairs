package com.qmy.yzsw.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.qmy.yzsw.R;
import com.qmy.yzsw.YawsApp;
import com.qmy.yzsw.bean.ImageUploadBean;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class GlideUtils {
    /**
     * 加载图片
     */
    public static void LoadImage(Object file, ImageView view) {
        if (file != null && view != null)
            Glide.with(YawsApp.getInstance().getApplicationContext()).load(file)
                    .apply(bitmapTransform(new RoundedCornersTransformation(13, 0, RoundedCornersTransformation.CornerType.ALL))
                            .placeholder(R.mipmap.ic_preloading_picture)
                            .error(R.mipmap.ic_preloading_picture))
                    .into(view);

    }

    /**
     * 加载图片
     */
    public static void LoadImage(String file, ImageView view) {
        if (file != null && !file.isEmpty() && view != null)
            Glide.with(YawsApp.getInstance().getApplicationContext()).load(file)
                    .apply(bitmapTransform(new RoundedCornersTransformation(13, 0, RoundedCornersTransformation.CornerType.ALL))
                            .placeholder(R.mipmap.ic_preloading_picture)
                            .error(R.mipmap.ic_preloading_picture))
                    .into(view);

    }

    /**
     * 加载图片
     */
    public static void getLoadImage(String file, ImageView view, ImageUploadBean bean) {
        if (file != null && view != null) {
            Glide.with(YawsApp.getInstance().getApplicationContext()).load(file)
                    .apply(bitmapTransform(new RoundedCornersTransformation(13, 0, RoundedCornersTransformation.CornerType.ALL))
                            .placeholder(R.mipmap.ic_preloading_picture)
                            .error(bean.getPreview_photo()))
                    .into(view);
        } else {
            GlideUtils.LoadImage(R.mipmap.ic_add_image, view);
        }

    }
}
