package com.qmy.yzsw.view;

import android.text.Editable;
import android.text.TextWatcher;

public class MyTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mListener != null && !s.toString().isEmpty()) {
            mListener.afterTextChanged(s, index);
        }
    }

    private int index;

    public MyTextWatcher(int index, TextWatcherListener listener) {
        mListener = listener;
        this.index = index;
    }

    public TextWatcherListener mListener;

    public interface TextWatcherListener {
        void afterTextChanged(Editable s, int index);
    }
}
