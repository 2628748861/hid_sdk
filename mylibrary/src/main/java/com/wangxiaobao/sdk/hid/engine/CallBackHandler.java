package com.wangxiaobao.sdk.hid.engine;

import com.wangxiaobao.sdk.hid.engine.adapter.DataAdapter;

public class CallBackHandler <T>{
    public Callback<T> callback;
    public DataAdapter<T> dataAdapter;
    public CallBackHandler(Callback<T> callback, DataAdapter<T> dataAdapter) {
        this.callback = callback;
        this.dataAdapter = dataAdapter;
    }

}
