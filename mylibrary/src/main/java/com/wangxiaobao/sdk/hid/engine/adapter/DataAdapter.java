package com.wangxiaobao.sdk.hid.engine.adapter;

public interface DataAdapter <T>{
    T parse(byte[] data);
}
