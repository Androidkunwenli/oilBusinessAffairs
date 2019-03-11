package com.qmy.yzsw.db;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.qmy.yzsw.R;
import com.qmy.yzsw.bean.FileListBean;

import java.io.File;
import java.text.NumberFormat;

/**
 */
public class FileDaUtils {
    private static FileDaUtils fileDaUtils;
    private static Context mContext;

    /**
     * 获取对象
     *
     * @return YJAgent
     */
    public static FileDaUtils getInstance(Context context) {
        if (fileDaUtils == null) {
            synchronized (FileDaUtils.class) {
                if (fileDaUtils == null) {
                    fileDaUtils = new FileDaUtils();
                    mContext = context;
                }
            }
        }
        return fileDaUtils;
    }

    private FileListBean getFileIdBean(FileListBean fileIdBean) {
        SQLiteDatabase database = new DbOpenHelper(mContext).getReadableDatabase();
        Cursor cursor = database.query("file_db", null, " fileName = ? and fileType = ?",
                new String[]{fileIdBean.getFileName(), fileIdBean.getFileExt()}, null, null, null);
        while (cursor.moveToNext()) {
            String filePath = cursor.getString(2);
            if (filePath != null && !filePath.isEmpty() && new File(filePath).exists()) {
                fileIdBean.setFilePath(filePath);
                return fileIdBean;
            }
        }
        return fileIdBean;
    }

    /**
     * 最新消息添加一条数据
     * 作者：赵亚坤
     * 时间：2018/7/7-10:46
     */
    private void insert(FileListBean bean) {
        SQLiteDatabase db = new DbOpenHelper(mContext).getWritableDatabase();
        if (db.isOpen()) {  //如果数据库打开
            ContentValues values = new ContentValues();
            values.put("fileName", bean.getFileName());
            values.put("filePath", bean.getFilePath());
            values.put("fileType", bean.getFileExt());
            values.put("fileDownload", bean.getFileUrl());
            db.insert("file_db", null, values);
            db.close(); //数据库关闭
        }
    }

    /**
     * @param fileListBean 文件bean
     */
    public void getFilePath(final FileListBean fileListBean, final FileIdListener fileIdListener) {
        final FileListBean fileIdBean = getFileIdBean(fileListBean);
        if (fileIdBean != null && fileIdBean.getFilePath() != null
                && !fileIdBean.getFilePath().isEmpty()
                && new File(fileIdBean.getFilePath()).exists()) {
            if (fileIdListener != null) {
                fileIdListener.onSuccess(fileIdBean.getFilePath(),fileListBean);
            }
        } else {
            OkGo.<File>get(fileListBean.getFileUrl())
                    .tag(mContext)
                    .execute(new FileCallback(fileListBean.getFileName()) {
                        @Override
                        public void onSuccess(Response<File> response) {
                            if (fileIdListener != null) {
                                String absolutePath = response.body().getAbsolutePath();
                                fileIdListener.onSuccess(absolutePath,fileListBean);
                                fileListBean.setFilePath(absolutePath);
                                insert(fileListBean);
                            }
                        }

                        @Override
                        public void downloadProgress(Progress progress) {
                            super.downloadProgress(progress);
                            Log.i(TAG, "下载进度 ： downloadProgress: " + progress.fraction);
                            if (fileIdListener != null) {
                                fileIdListener.downloadProgress(progress);
                            }
                        }

                        @Override
                        public void onError(Response<File> response) {
                            super.onError(response);
                            if (fileIdListener != null) {
                                fileIdListener.onError();
                            }
                            ToastUtils.showShort("下载失败！");
                        }
                    });
        }
    }

    private static NumberFormat numberFormat;

    public static String getProgress(float v) {
        if (numberFormat == null) {
            numberFormat = NumberFormat.getIntegerInstance();
            numberFormat.setMinimumFractionDigits(2);
        }
        return numberFormat.format(v);
    }

    public interface FileIdListener {
        void onSuccess(String path, FileListBean fileListBean);

        void downloadProgress(Progress progress);

        void onError();
    }

    private static final String TAG = "FileDaUtils";

    /**
     * 用户中心确认是否更新提示框
     */
    public static ProgressDialog showUpdateAskDialog(Activity activity, String description) {
        // 创建ProgressDialog对象
        ProgressDialog xh_pDialog = new ProgressDialog(activity);
        // 设置进度条风格，风格为圆形，旋转的
        xh_pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // 设置ProgressDialog 标题
        xh_pDialog.setTitle(description);
        // 设置ProgressDialog标题图标
        xh_pDialog.setIcon(R.mipmap.ic_yzsw_logo);
        // 设置ProgressDialog 的进度条是否不明确 false 就是不设置为不明确
        xh_pDialog.setIndeterminate(false);
        // 设置ProgressDialog 进度条进度
        xh_pDialog.setProgress(0);
        xh_pDialog.setMax(10000);
        // 设置ProgressDialog 是否可以按退回键取消
        xh_pDialog.setCancelable(false);
        // 让ProgressDialog显示
        xh_pDialog.show();
        return xh_pDialog;
    }
}
