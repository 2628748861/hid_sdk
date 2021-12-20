package com.wangxiaobao.sdk.hid.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.*;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.github.mjdev.libaums.UsbMassStorageDevice;
import com.wangxiaobao.sdk.hid.engine.Callback;
import com.wangxiaobao.sdk.hid.engine.ErrorCode;
import com.wangxiaobao.sdk.hid.utils.HexUtil;
import com.wangxiaobao.sdk.hid.HidCmd;
import com.wangxiaobao.sdk.hid.utils.LogUtil;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity implements View.OnClickListener {
    private Context mContext;
    private UsbManager mUsbManager;
    private PendingIntent mPermissionIntent;
    private  HidCmd hidCmd;
    private static final String ACTION_USB_PERMISSION = "com.demo.otgusb.USB_PERMISSION";

    private TextView snTv,timeTv,rkTv,espTv,macTv,currentWifiTv,testMicTv,testLightTv;

    @Override
    protected void onDestroy() {
        unregisterReceiver(mUsbReceiver);
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        setContentView(R.layout.activity_main);

        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        //注册广播,监听USB插入和拔出
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        intentFilter.addAction(ACTION_USB_PERMISSION);
        registerReceiver(mUsbReceiver, intentFilter);


        findViewById(R.id.snLayout).setOnClickListener(this);
        findViewById(R.id.timeLayout).setOnClickListener(this);
        findViewById(R.id.lightLayout).setOnClickListener(this);
        findViewById(R.id.rebootLayout).setOnClickListener(this);
        findViewById(R.id.resumeFactoryLayout).setOnClickListener(this);
        findViewById(R.id.wifiLayout).setOnClickListener(this);
        findViewById(R.id.wifiConnectLayout).setOnClickListener(this);
        findViewById(R.id.currentWifiLayout).setOnClickListener(this);
        findViewById(R.id.testMicLayout).setOnClickListener(this);
        findViewById(R.id.testLightLayout).setOnClickListener(this);


        snTv=findViewById(R.id.snTv);
        timeTv=findViewById(R.id.timeTv);
        rkTv=findViewById(R.id.rkTv);
        espTv=findViewById(R.id.espTv);
        macTv=findViewById(R.id.macTv);
        currentWifiTv=findViewById(R.id.currentWifiTv);
        testMicTv=findViewById(R.id.testMicTv);
        testLightTv=findViewById(R.id.testLightTv);
        test();
    }
    private BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null)
                return;
            switch (action) {
                case ACTION_USB_PERMISSION://用户授权广播
                    synchronized (this) {
                        if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) { //允许权限申请
                            test();
                        } else {
                            logMessage("用户未授权，访问USB设备失败");
                        }
                    }
                    break;
                case UsbManager.ACTION_USB_DEVICE_ATTACHED://USB设备插入广播
                    logMessage("USB设备插入");
                    test();
                    break;
                case UsbManager.ACTION_USB_DEVICE_DETACHED://USB设备拔出广播
                    logMessage("USB设备拔出");
                    break;
            }
        }
    };

    public void test(){
        UsbMassStorageDevice[] storageDevices = UsbMassStorageDevice.getMassStorageDevices(MainActivity.this);
        for (UsbMassStorageDevice storageDevice : storageDevices){
            if (!mUsbManager.hasPermission(storageDevice.getUsbDevice())) {
                mUsbManager.requestPermission(storageDevice.getUsbDevice(), mPermissionIntent);
                break;
            }
            UsbDevice usbDevice=storageDevice.getUsbDevice();
            logMessage("usbDevice:"+usbDevice.getSerialNumber());
            initCmd(usbDevice);
        }
    }


    public void logMessage(String value){
        LogUtil.log(value);
    }

    private void initCmd(UsbDevice usbDevice){
        if(usbDevice==null){
            Toast.makeText(mContext,"没有USB设置挂载",Toast.LENGTH_LONG).show();
            return;
        }
        hidCmd=new HidCmd(mContext,usbDevice);

        hidCmd.getSn(new Callback<String>() {
            @Override
            public void onSuccess(@NonNull @NotNull String data) {
                snTv.setText(data);
            }

            @Override
            public void onFailure(ErrorCode errorCode) {
                snTv.setText("失败:"+errorCode.getMessage());
            }
        });
        hidCmd.getTime(new Callback<String>() {
            @Override
            public void onSuccess(@NonNull @NotNull String data) {
                timeTv.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(data)*1000l)));
            }

            @Override
            public void onFailure(ErrorCode errorCode) {
                timeTv.setText("失败:"+errorCode.getMessage());
            }
        });
        hidCmd.getRkVersion(new Callback<String>() {
            @Override
            public void onSuccess(@NonNull @NotNull String data) {
                rkTv.setText(data);
            }

            @Override
            public void onFailure(ErrorCode errorCode) {
                rkTv.setText("失败:"+errorCode.getMessage());
            }
        });
        hidCmd.getEspVersion(new Callback<String>() {
            @Override
            public void onSuccess(@NonNull @NotNull String data) {
                espTv.setText(data);
            }

            @Override
            public void onFailure(ErrorCode errorCode) {
                espTv.setText("失败:"+errorCode.getMessage());
            }
        });
        hidCmd.getMac(new Callback<String>() {
            @Override
            public void onSuccess(@NonNull @NotNull String data) {
                macTv.setText(data);
            }

            @Override
            public void onFailure(ErrorCode errorCode) {
                macTv.setText("失败:"+errorCode.getMessage());
            }
        });
        hidCmd.getCurrenWifi(new Callback<String>() {
            @Override
            public void onSuccess(@NonNull @NotNull String data) {
                currentWifiTv.setText(data);
            }

            @Override
            public void onFailure(ErrorCode errorCode) {
                currentWifiTv.setText("失败:"+errorCode.getMessage());
            }
        });


    }

    @Override
    public void onClick(View v) {
        if(hidCmd==null){
            Toast.makeText(mContext,"没有USB设置挂载",Toast.LENGTH_LONG).show();
            return;
        }
        switch (v.getId()){
            case R.id.snLayout:
                final EditText et = new EditText(this);
                et.setText("WXBY0302112040034");
                new AlertDialog.Builder(mContext)
                        .setTitle("提示")
                        .setView(et)
                        .setMessage("请输入sn号")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                hidCmd.setSn(et.getText().toString(), new Callback<Boolean>() {
                                    @Override
                                    public void onSuccess(@NonNull @NotNull Boolean data) {
                                        hidCmd.getSn(new Callback<String>() {
                                            @Override
                                            public void onSuccess(@NonNull @NotNull String data) {
                                                snTv.setText(data);
                                            }

                                            @Override
                                            public void onFailure(ErrorCode errorCode) {
                                                snTv.setText("失败:"+errorCode.getMessage());
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(ErrorCode errorCode) {
                                        Toast.makeText(mContext,"设置sn号失败:"+errorCode.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        })
                        .show();
                break;
            case R.id.timeLayout:
                new AlertDialog.Builder(mContext)
                        .setTitle("提示")
                        .setMessage("是否同步时间?")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                hidCmd.setTime(new Date().getTime() / 1000l, new Callback<Boolean>() {
                                    @Override
                                    public void onSuccess(@NonNull @NotNull Boolean data) {
                                        hidCmd.getTime(new Callback<String>() {
                                            @Override
                                            public void onSuccess(@NonNull @NotNull String data) {
                                                timeTv.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(data)*1000l)));
                                            }

                                            @Override
                                            public void onFailure(ErrorCode errorCode) {
                                                timeTv.setText("失败:"+errorCode.getMessage());
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(ErrorCode errorCode) {
                                        Toast.makeText(mContext,"同步时间失败:"+errorCode.getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        })
                        .show();
                break;
            case R.id.lightLayout:

                final String[] items = { "打开指示灯","关闭指示灯"};
                AlertDialog.Builder listDialog =
                        new AlertDialog.Builder(MainActivity.this);
                listDialog.setTitle("请选择指示灯操作类型");
                listDialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hidCmd.setLight(which == 0, new Callback<Boolean>() {
                            @Override
                            public void onSuccess(@NonNull @NotNull Boolean data) {
                                Toast.makeText(mContext,"指示灯操作结果:"+data,Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(ErrorCode errorCode) {
                                Toast.makeText(mContext,"指示灯操作异常:"+errorCode.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                listDialog.show();
                break;
            case R.id.rebootLayout:
                hidCmd.reboot(new Callback<Boolean>() {
                    @Override
                    public void onSuccess(@NonNull @NotNull Boolean data) {
                        Toast.makeText(mContext,"重启成功:"+data,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(ErrorCode errorCode) {
                        Toast.makeText(mContext,"重启失败:"+errorCode.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case R.id.resumeFactoryLayout:
                hidCmd.resumeFactory(new Callback<Boolean>() {
                    @Override
                    public void onSuccess(@NonNull @NotNull Boolean data) {
                        Toast.makeText(mContext,"恢复出厂设置:"+data,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(ErrorCode errorCode) {
                        Toast.makeText(mContext,"恢复出厂设置失败:"+errorCode.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
                break;
            case R.id.testMicLayout:
                hidCmd.testMic(new Callback<Boolean>() {
                    @Override
                    public void onSuccess(@NonNull @NotNull Boolean data) {
                        testMicTv.setText("测试结果:"+data);
                    }

                    @Override
                    public void onFailure(ErrorCode errorCode) {
                        testMicTv.setText("失败:"+errorCode.getMessage());
                    }
                });

                break;
            case R.id.testLightLayout:
                hidCmd.testLight(new Callback<Boolean>() {
                    @Override
                    public void onSuccess(@NonNull @NotNull Boolean data) {
                        testLightTv.setText("测试结果:"+data);
                    }

                    @Override
                    public void onFailure(ErrorCode errorCode) {
                        testLightTv.setText("失败:"+errorCode.getMessage());
                    }
                });
                break;
            case R.id.wifiLayout:
                break;
            case R.id.wifiConnectLayout:
                break;
            case R.id.currentWifiLayout:
                break;
        }
    }
}
