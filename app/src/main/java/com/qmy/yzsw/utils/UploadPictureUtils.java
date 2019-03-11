package com.qmy.yzsw.utils;

import android.app.Activity;
import android.content.Context;

import com.blankj.utilcode.util.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.YawsApp;
import com.qmy.yzsw.bean.base.BaseBean;
import com.qmy.yzsw.bean.uploadpicture.UploadPictureBean;
import com.qmy.yzsw.callback.DialogCallback;
import com.qmy.yzsw.callback.JsonCallback;
import com.qmy.yzsw.config.Urls;

import java.io.File;
import java.util.ArrayList;

public class UploadPictureUtils {
    private static int mInt = 0;

    /**
     * @param activity 上下文
     * @param list     要上传的集合
     * @param success  上传成功的监听
     */
    public static void uploadPicture(final Activity activity,
                                     final ArrayList<UploadPictureBean> list,
                                     final ConversionSuccess success) {
        final ArrayList<UploadPictureBean> arrayList = new ArrayList<>();
        if (list.isEmpty()) {
            if (success != null) {
                success.FileDownloadSuccess(arrayList);
            }
            return;
        }
        KUtils.uploadFile(activity, list.get(mInt).getFilePath(),
                list.get(mInt).getOpType(), list.get(mInt).getOldPicUrl(),
                new DialogCallback<BaseBean<String>>(activity) {
                    @Override
                    public void onSuccess(Response<BaseBean<String>> response) {
                        UploadPictureBean bean = list.get(mInt);
                        bean.setDownloadFile(response.body().getData());
                        arrayList.add(bean);
                        mInt++;
                        if (mInt == list.size()) {
                            mInt = 0;
                            if (success != null) {
                                success.FileDownloadSuccess(arrayList);
                            }
                        } else {
                            KUtils.uploadFile(activity, list.get(mInt).getFilePath(),
                                    list.get(mInt).getOpType(), list.get(mInt).getOldPicUrl(),
                                    this);
                        }
                    }
                });
    }

    public interface ConversionSuccess {
        void FileDownloadSuccess(ArrayList<UploadPictureBean> list);
    }
}
