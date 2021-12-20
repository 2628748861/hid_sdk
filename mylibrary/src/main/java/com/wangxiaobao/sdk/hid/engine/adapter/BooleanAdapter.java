package com.wangxiaobao.sdk.hid.engine.adapter;

public class BooleanAdapter implements DataAdapter<Boolean> {
    @Override
    public Boolean parse(byte[] data) {
        return data[0]==0;
    }
}
