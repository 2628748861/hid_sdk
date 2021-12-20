package com.wangxiaobao.sdk.hid.engine;

import androidx.annotation.NonNull;

public interface Callback<T> {
    /**
     * 执行成功
     * @param data
     */
    void onSuccess(@NonNull T data);

    /**
     * 执行失败
     * @param errorCode 错误码 {@link ErrorCode}
     *
     */
    void onFailure(ErrorCode errorCode);
}
