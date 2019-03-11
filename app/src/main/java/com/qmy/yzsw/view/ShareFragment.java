package com.qmy.yzsw.view;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.qmy.yzsw.R;


/**
 * 分享面板
 */

public class ShareFragment extends DialogFragment {
    private View.OnClickListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_share, container, false);

        return v;
    }

    public void setOnItemChildClickListener(String trim, View.OnClickListener listener) {

    }

}
