package com.wangxiaobao.sdk.hid;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.text.TextUtils;
import android.util.Log;

public class HidCmd implements IHidCmd{
    private String TAG="HidSDK";
    private String OP_HEAD="05";
    private UsbManager mUsbManager;
    private UsbDevice usbDevice;
    public HidCmd(Context mContext, UsbDevice usbDevice){
        this.mUsbManager = (UsbManager) mContext.getSystemService(Context.USB_SERVICE);
        this.usbDevice=usbDevice;
    }
    private String execute(UsbDevice usbDevice,String cmd,String data){
        if(mUsbManager==null||usbDevice==null|| TextUtils.isEmpty(cmd)||TextUtils.isEmpty(data)){
            Log.e(TAG,"执行hid命令失败,参数有误.mUsbManager:"+mUsbManager+",usbDevice:"+usbDevice+",cmd:"+cmd+",data:"+data);
            return "";
        }
        UsbInterface usbInterface = usbDevice.getInterface(0);
        UsbEndpoint inEndpoint = usbInterface.getEndpoint(0);  //读数据节点
        UsbEndpoint outEndpoint = usbInterface.getEndpoint(1); //写数据节点
        UsbDeviceConnection connection = mUsbManager.openDevice(usbDevice);
        connection.claimInterface(usbInterface, true);
        byte[] byte2= HexUtil.hexStringToBytes(cmd+ (TextUtils.isEmpty(data)?"": HexUtil.byteArrToHex(data.getBytes())));
        connection.bulkTransfer(outEndpoint, byte2, byte2.length, 5000);
        byte[] receiveytes = new byte[64];
        connection.bulkTransfer(inEndpoint, receiveytes, receiveytes.length, 10000);
        String result= HexUtil.byteArrToHex(receiveytes);
        Log.e(TAG,"["+cmd+"]执行结果:"+result);
        return result;
    }

    @Override
    public boolean setSn(String sn) {
        String result= execute(usbDevice,OP_HEAD+"07",sn);
        return !TextUtils.isEmpty(result)&&result.startsWith(OP_HEAD);
    }

    @Override
    public String getSn() {
        String result= execute(usbDevice,OP_HEAD+"09",null);
        return HexUtil.hexStringToAscill(result.substring(OP_HEAD.length()));
    }

    @Override
    public boolean setTime(long timestamp) {
        String result= execute(usbDevice,OP_HEAD+"0e",Long.toHexString(timestamp/1000l));
        return !TextUtils.isEmpty(result)&&result.startsWith(OP_HEAD);
    }


    @Override
    public String getTime() {
        String result= execute(usbDevice,OP_HEAD+"0f",null);
        return HexUtil.hexStringToAscill(result.substring(OP_HEAD.length()));
    }

    @Override
    public void reboot() {
        execute(usbDevice,OP_HEAD+"10",null);
    }

    @Override
    public void resumeFactory() {
        execute(usbDevice,OP_HEAD+"11",null);
    }

    @Override
    public boolean setLight(boolean open) {
        String result= execute(usbDevice,OP_HEAD+"12",open?"01":"00");
        return !TextUtils.isEmpty(result)&&result.startsWith(OP_HEAD);
    }

    @Override
    public String getRkVersion() {
        String result= execute(usbDevice,OP_HEAD+"15",null);
        return HexUtil.hexStringToAscill(result.substring(OP_HEAD.length()));
    }

    @Override
    public String getEspVersion() {
        String result= execute(usbDevice,OP_HEAD+"16",null);
        return HexUtil.hexStringToAscill(result.substring(OP_HEAD.length()));
    }


}
