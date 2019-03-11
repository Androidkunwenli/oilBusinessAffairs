package com.qmy.yzsw.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.photoview.PhotoView;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.base.BaseActivity;


import butterknife.BindView;

public class ImageActivity extends BaseActivity {
    @BindView(R.id.image)
    PhotoView image;
    public final static String IMAGE_PATH = "IMAGE_PATH";

    @Override
    public int getLayoutId() {
        return R.layout.activity_image;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        Glide.with(mActivity.getApplicationContext()).load(getIntent().getStringExtra(IMAGE_PATH)).into(image);
    }

    @Override
    public void initView() {
        setFindViewById(true);
        setTitleStr(getIntent().getStringExtra("name"));
    }

    public static void start(Activity activity, String image_path, String name) {
        Intent intent = new Intent(activity, ImageActivity.class);
        intent.putExtra(IMAGE_PATH, image_path);
        intent.putExtra("name", name);
        activity.startActivity(intent);
    }
}
