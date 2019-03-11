package com.qmy.yzsw.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.qmy.yzsw.R;
import com.qmy.yzsw.activity.ImageActivity;
import com.qmy.yzsw.bean.ImageUploadBean;


/**
 * Created by Administrator on 2017/6/27 0027.
 */

public class SeeImageFramgment extends DialogFragment {

    private TextView mTv_replace;
    private TextView mTv_see_image;
    private ImageUploadBean mImageUploadBean;
    private TextView mTv_delete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View v = inflater.inflate(R.layout.fragment_see_image, container, false);
        mTv_replace = v.findViewById(R.id.tv_replace);
        mTv_replace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector.create(getActivity())
                        .openGallery(PictureMimeType.ofImage())
                        .selectionMode(PictureConfig.SINGLE)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                dismiss();
            }
        });
        mTv_see_image = v.findViewById(R.id.tv_see_image);
        mTv_see_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageActivity.start(getActivity(), mImageUploadBean.getSeeImage(), mImageUploadBean.getTitle());
                dismiss();
            }
        });

        mTv_delete = v.findViewById(R.id.tv_delete);
        mTv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onDelete(position);
                }
            }
        });
        if (mImageUploadBean.getSeeImage() == null || mImageUploadBean.getSeeImage().isEmpty()) {
            PictureSelector.create(getActivity())
                    .openGallery(PictureMimeType.ofImage())
                    .selectionMode(PictureConfig.SINGLE)
                    .forResult(PictureConfig.CHOOSE_REQUEST);
            dismiss();
        }
        return v;
    }

    //    @Override
//    public void onStart() {
//        super.onStart();
//        Dialog dialog = getDialog();
//        if (dialog != null) {
//            DisplayMetrics dm = new DisplayMetrics();
//            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.8), ViewGroup.LayoutParams.WRAP_CONTENT);
//        }
//    }
    private int position;
    public OnDeleteClickListener mClickListener;

    public void setClickListener(OnDeleteClickListener clickListener) {
        mClickListener = clickListener;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public interface OnDeleteClickListener {
        void onDelete(int position);
    }

    public void setUpload(ImageUploadBean bean) {
        mImageUploadBean = bean;
    }
}
