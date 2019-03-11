package com.qmy.yzsw.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmy.yzsw.R;
import com.qmy.yzsw.bean.FileListBean;

public class ReportFormAdmAdapter extends BaseQuickAdapter<FileListBean, BaseViewHolder> {

    private int index;

    public ReportFormAdmAdapter(int index) {
        super(R.layout.item_report_form_adm);
        this.index = index;
    }

    @Override
    protected void convert(BaseViewHolder helper, FileListBean item) {
        if (index == 0) {
            helper.setImageResource(R.id.image_download, R.mipmap.ic_download);
        } else if (index == 1) {
            helper.setImageResource(R.id.image_download, R.mipmap.ic_more);
        }
        helper.setText(R.id.tv_file_name, item.getFileName())
                .setText(R.id.tv_file_time, item.getCreateTimeStr())
                .setGone(R.id.image_download, !item.getFileExt().isEmpty())
                .addOnClickListener(R.id.image_download)
                .addOnClickListener(R.id.ll_layout);
        switch (item.getFileExt()) {
            case "mp4":
                helper.setImageResource(R.id.image_file, R.mipmap.mp4);
                break;
            case "docx":
                helper.setImageResource(R.id.image_file, R.mipmap.word);
                break;
            case "pdf":
                helper.setImageResource(R.id.image_file, R.mipmap.pdf);
                break;
            case "rar":
            case "zip":
                helper.setImageResource(R.id.image_file, R.mipmap.ic_compress);
                break;
            default:
                helper.setImageResource(R.id.image_file, R.mipmap.ic_file);
                break;
        }

    }
}
