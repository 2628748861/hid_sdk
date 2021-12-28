package com.wangxiaobao.sdk.hid.engine.task;

import com.wangxiaobao.sdk.hid.engine.CallBackHandler;
import com.wangxiaobao.sdk.hid.engine.request.RequestData;

public class Task {
    public RequestData requestData;
    public CallBackHandler callBackHandler;

    public Task(RequestData requestData, CallBackHandler callBackHandler) {
        this.requestData = requestData;
        this.callBackHandler = callBackHandler;
    }
}
