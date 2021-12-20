package com.wangxiaobao.sdk.hid.engine.adapter;

public class StringAdapter implements DataAdapter<String>{
    @Override
    public String parse(byte[] data) {
        return new String(data);
    }
}
