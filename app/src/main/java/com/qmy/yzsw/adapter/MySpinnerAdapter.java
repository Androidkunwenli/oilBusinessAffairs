package com.qmy.yzsw.adapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmy.yzsw.R;

public class MySpinnerAdapter extends BaseAdapter {

    private String[] data;
    private Activity mActivity;

    public MySpinnerAdapter(Activity mActivity, String[] data) {
        this.data = data;
        this.mActivity = mActivity;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mActivity).inflate(R.layout.spinner_item, parent, false);
            holder = new ViewHolder();
            holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content_str);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvContent.setText(data[position]);
        return convertView;
    }

    static class ViewHolder {
        TextView tvContent;
    }
}

