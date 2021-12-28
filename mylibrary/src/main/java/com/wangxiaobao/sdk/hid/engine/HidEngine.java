package com.wangxiaobao.sdk.hid.engine;

import android.content.Context;
import android.hardware.usb.*;
import android.os.Handler;
import com.wangxiaobao.sdk.hid.engine.request.RequestData;
import com.wangxiaobao.sdk.hid.engine.response.ResponseData;
import com.wangxiaobao.sdk.hid.engine.task.Task;
import com.wangxiaobao.sdk.hid.engine.task.TaskManager;
import com.wangxiaobao.sdk.hid.utils.LogUtil;

public class HidEngine {
    private UsbDevice usbDevice;
    private UsbManager usbManager;
    private TaskManager taskManager;
    private Handler handler=new Handler();

    public HidEngine(Context mContext, UsbDevice usbDevice) {
        this.usbDevice = usbDevice;
        this.usbManager = (UsbManager) mContext.getSystemService(Context.USB_SERVICE);
        this.taskManager=new TaskManager(this);
    }

    public <T> void send(RequestData requestData, CallBackHandler<T> callBackHandler){
        this.taskManager.addTask(new Task(requestData,callBackHandler));
    }
    public void clear(){
        this.taskManager.clear();
    }

    public <T> void sendCmd(RequestData requestData, CallBackHandler<T> callBackHandler){
        UsbInterface usbInterface = usbDevice.getInterface(0);
        UsbEndpoint inEndpoint = usbInterface.getEndpoint(0);
        UsbEndpoint outEndpoint = usbInterface.getEndpoint(1);
        UsbDeviceConnection connection = usbManager.openDevice(usbDevice);
        if(connection==null)return;
        connection.claimInterface(usbInterface, true);

        byte[] requestBytes= requestData.getByteData();

        StringBuilder sb1 = new StringBuilder();
        for (int i = 0; i < requestBytes.length; i++) {
            sb1.append(String.format("%02x", requestBytes[i]));
        }

        LogUtil.log("发送数据:"+sb1.toString());

        connection.bulkTransfer(outEndpoint, requestBytes, requestBytes.length, 10000);
        byte[] receivedBytes = new byte[64];
        connection.bulkTransfer(inEndpoint, receivedBytes, receivedBytes.length, 10000);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < receivedBytes.length; i++) {
            sb.append(String.format("%02x", receivedBytes[i]));
        }

        LogUtil.log("收到数据:"+sb.toString());
        ResponseData responseData=ResponseData.fromValue(receivedBytes);
        int errorCode=responseData.getErrorCode();
        if(errorCode!=0){
            handler.post(() -> callBackHandler.callback.onFailure(ErrorCode.of(errorCode)));
        }else{
            Object obj=callBackHandler.dataAdapter.parse(responseData.getData());
            handler.post(() -> callBackHandler.callback.onSuccess((T) obj));
        }
        connection.releaseInterface(usbInterface);
        connection.close();
    }
}
