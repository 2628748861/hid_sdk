package com.wangxiaobao.sdk.hid.engine;


public interface Callback<T> {
    /**
     * 执行成功
     * @param data
     */
    void onSuccess( T data);

    /**
     * 执行失败
     * @param errorCode 错误码 {@link ErrorCode}
     *
     */
    void onFailure(ErrorCode errorCode);
}
