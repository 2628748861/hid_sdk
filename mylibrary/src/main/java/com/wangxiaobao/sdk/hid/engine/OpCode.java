package com.wangxiaobao.sdk.hid.engine;

public class OpCode {
    public static final int CMD_INVALID=0xff;
    public static final int CMD_SET_TIME=0x0e;
    public static final int CMD_GET_TIME=0x0f;
    public static final int CMD_SET_SN=0x1f;
    public static final int CMD_GET_SN=0x1e;
    public static final int CMD_REBOOT=0x10;
    public static final int CMD_RESUMEFACTORY=0x11;
    public static final int CMD_SET_LIGHT=0x12;
    public static final int CMD_GET_RK_VERSION=0x20;
    public static final int CMD_GET_ESP_VERSION=0x21;
    public static final int CMD_GET_BLE_MAC=0x22;
    public static final int CMD_OPEN_WIFI=0x1c;
    public static final int CMD_CONNECT_WIFI=0x1d;
    public static final int CMD_get_WIFI=0x23;
    public static final int CMD_MIC_TEST=0x24;
    public static final int CMD_LIGHT_TEST=0x25;
}
