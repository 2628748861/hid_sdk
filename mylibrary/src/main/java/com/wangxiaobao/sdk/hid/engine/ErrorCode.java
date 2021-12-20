package com.wangxiaobao.sdk.hid.engine;

public enum ErrorCode {
    ERRCODE_ERROR_COMMAND(0xff,"未知指令错误"),
    ERRCODE_ERROR_UNKNOWN(0xee,"未知错误码"),
    ERRCODE_HID_SUCCESS(0x00,"请求正常"),
    ERRCODE_HID_DEV_BUSY(0x01,"设备内部错误"),
    ERRCODE_MAC_SD0_ERROR(0x02,"麦克风D0通道有麦损坏"),
    ERRCODE_MAC_SD1_ERROR(0x03,"麦克风D1通道有麦损坏"),
    ERRCODE_MAC_ALL_ERROR(0x04,"麦克风两个通道都有麦损坏"),
    ERRCODE_WIFI_DISCONNECT_ERROR(0x05,"wifi没有连接"),
    ERRCODE_WIFI_CONNECT_FAIL(0x06,"wifi连接失败"),
    ERRCODE_WIFI_SSID_INVALID(0x07,"ssid无效");



    private int code;
    private String message;
    ErrorCode(int code,String message){
        this.code=code;
        this.message=message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ErrorCode of(int code){
        for (ErrorCode errorCode:ErrorCode.values()){
            if(errorCode.code == code)
                return errorCode;
        }
        return ERRCODE_ERROR_UNKNOWN;
    }

}
