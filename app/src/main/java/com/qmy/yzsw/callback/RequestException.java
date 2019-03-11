package com.qmy.yzsw.callback;

import com.google.gson.Gson;
import com.qmy.yzsw.bean.base.BaseBean;

public class RequestException extends IllegalStateException {
    private BaseBean errorBean;

    public RequestException(String s) {
        super(s);
        errorBean = new Gson().fromJson(s, BaseBean.class);
    }

    public BaseBean getErrorBean() {
        return errorBean;
    }
}
