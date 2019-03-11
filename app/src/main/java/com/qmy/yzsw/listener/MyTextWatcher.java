package com.qmy.yzsw.listener;

import android.text.Editable;
import android.text.TextWatcher;

public class MyTextWatcher implements TextWatcher {
    private TextWatcherListener mListener;

    public MyTextWatcher(TextWatcherListener listener) {
        mListener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (mListener != null) {
            mListener.afterTextChanged(editable);
        }
    }
}
