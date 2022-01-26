package com.wangxiaobao.sdk.hid.engine.adapter;

public class StrAdapter implements DataAdapter<String>{
    @Override
    public String parse(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            sb.append(String.format("%02x", data[i]));
        }
        return sb.toString();
    }
}
