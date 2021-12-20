package com.wangxiaobao.sdk.hid;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import com.wangxiaobao.sdk.hid.engine.adapter.BooleanAdapter;
import com.wangxiaobao.sdk.hid.engine.adapter.StringAdapter;
import com.wangxiaobao.sdk.hid.engine.CallBackHandler;
import com.wangxiaobao.sdk.hid.engine.Callback;
import com.wangxiaobao.sdk.hid.engine.HidEngine;
import com.wangxiaobao.sdk.hid.engine.OpCode;
import com.wangxiaobao.sdk.hid.engine.request.RequestData;

public class HidCmd implements IHidCmd{

    private HidEngine hidEngine;
    public HidCmd(Context mContext, UsbDevice usbDevice){
        this.hidEngine =new HidEngine(mContext,usbDevice);
    }

    @Override
    public void setSn(String sn,Callback<Boolean> callback) {
        //todo 已验证
        this.hidEngine.send(
                new RequestData.Builder()
                        .cmd(OpCode.CMD_SET_SN)
                        .payload(sn.getBytes())
                        .build(),new CallBackHandler(callback,new BooleanAdapter()));
    }

    @Override
    public void getSn(Callback<String> callback) {
        //todo 已验证
        this.hidEngine.send(new RequestData.Builder()
                        .cmd(OpCode.CMD_GET_SN)
                        .build(),new CallBackHandler(callback,new StringAdapter()));
    }

    @Override
    public void setTime(long unixTimestamp,Callback<Boolean> callback){
        //todo 已验证
        this.hidEngine.send(new RequestData.Builder()
                .cmd(OpCode.CMD_SET_TIME)
                .payload((unixTimestamp+"").getBytes())
                .build(),new CallBackHandler(callback,new BooleanAdapter()));

    }


    @Override
    public void getTime(Callback<String> callback){
        //todo 已验证
        this.hidEngine.send(new RequestData.Builder()
                .cmd(OpCode.CMD_GET_TIME)
                .build(),new CallBackHandler(callback,new StringAdapter()));
    }

    @Override
    public void reboot(Callback<Boolean> callback) {
        //todo 已验证
        this.hidEngine.send(new RequestData.Builder()
                .cmd(OpCode.CMD_REBOOT)
                .build(),new CallBackHandler(callback,new BooleanAdapter()));
    }

    @Override
    public void resumeFactory(Callback<Boolean> callback) {
        //todo 已验证
        this.hidEngine.send(new RequestData.Builder()
                .cmd(OpCode.CMD_RESUMEFACTORY)
                .build(),new CallBackHandler(callback,new BooleanAdapter()));
    }

    @Override
    public void setLight(boolean open, Callback<Boolean> callback) {
        //todo 已验证
        this.hidEngine.send(
                new RequestData.Builder()
                        .cmd(OpCode.CMD_SET_LIGHT)
                        .payload(new byte[]{(byte) (open ? 0x31 : 0x30)})
                        .build(),new CallBackHandler(callback,new BooleanAdapter()));
    }

    @Override
    public void getRkVersion(Callback<String> callback) {
        //todo 已验证
        this.hidEngine.send(new RequestData.Builder()
                .cmd(OpCode.CMD_GET_RK_VERSION)
                .build(),new CallBackHandler(callback,new StringAdapter()));
    }

    @Override
    public void getEspVersion(Callback<String> callback){
        //todo 已验证
        this.hidEngine.send(new RequestData.Builder()
                .cmd(OpCode.CMD_GET_ESP_VERSION)
                .build(),new CallBackHandler(callback,new StringAdapter()));
    }

    @Override
    public void testMic(Callback<Boolean> callback){
        //todo 已验证
        this.hidEngine.send(new RequestData.Builder()
                .cmd(OpCode.CMD_MIC_TEST)
                .build(),new CallBackHandler(callback,new BooleanAdapter()));
    }


    @Override
    public void testLight(Callback<Boolean> callback){
        //todo 已验证
        this.hidEngine.send(new RequestData.Builder()
                .cmd(OpCode.CMD_LIGHT_TEST)
                .build(),new CallBackHandler(callback,new BooleanAdapter()));
    }

    @Override
    public void getMac(Callback<String> callback){
        //todo 已验证  (有问题，当没有蓝牙连接时,返回错误码位01)
        this.hidEngine.send(new RequestData.Builder()
                .cmd(OpCode.CMD_GET_BLE_MAC)
                .build(),new CallBackHandler(callback,new StringAdapter()));
    }

    @Override
    public void wifi(boolean open,Callback<Boolean> callback) {
        this.hidEngine.send(new RequestData.Builder()
                .cmd(OpCode.CMD_OPEN_WIFI)
                .payload(new byte[]{(byte) (open ? 0x31 : 0x30)})
                .build(),new CallBackHandler(callback,new BooleanAdapter()));
    }

    @Override
    public void connectWifi(String ssid, String password,Callback<Boolean> callback) {
        this.hidEngine.send(new RequestData.Builder()
                .cmd(OpCode.CMD_CONNECT_WIFI)
                .payload((ssid+","+password).getBytes())
                .build(),new CallBackHandler(callback,new BooleanAdapter()));
    }

    @Override
    public void getCurrenWifi(Callback<String> callback) {
        this.hidEngine.send(new RequestData.Builder()
                .cmd(OpCode.CMD_get_WIFI)
                .build(),new CallBackHandler(callback,new StringAdapter()));
    }



}
