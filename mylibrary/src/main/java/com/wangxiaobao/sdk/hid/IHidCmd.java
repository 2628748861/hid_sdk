package com.wangxiaobao.sdk.hid;

import com.wangxiaobao.sdk.hid.engine.Callback;

public interface IHidCmd {


    void clear();

    void invalid(Callback<String> callback);

    /**
     * 设置sn号
     * @param sn 17位sn号码
     * @return true:设置成功 false:设置失败
     */
    void setSn(String sn,Callback<Boolean> callback) ;

    /**
     * 获取sn号
     * @return 返回sn号
     */
    void getSn(Callback<String> callback) ;

    /**
     * 设置工牌时间
     * @return 返回设置结果
     */
    void setTime(long unixTimestamp,Callback<Boolean> callback);

    /**
     * 获取工牌时间
     * @return 返回工牌时间
     */
    void getTime(Callback<String> callback) ;

    /**
     * 设置工牌复位(重启)
     */
    void reboot(Callback<Boolean> callback);

    /**
     * 工牌恢复出厂设置
     */
    void resumeFactory(Callback<Boolean> callback);

    /**
     * 设置工牌指示灯
     * @param open true:打开指示灯  false:关闭指示灯
     * @return 返回设置结果
     */
   void setLight(boolean open, Callback<Boolean> callback);
    /**
     * 获取工牌Rk版本号
     * @return 工牌Rk版本号
     *
     */
    void getRkVersion(Callback<String> callback);

    /**
     * 获取工牌esp版本号
     * @return 工牌esp版本号
     */
    void getEspVersion(Callback<String> callback);

    /**
     * 获取Mac地址
     * @return 工牌mac地址
     */
    void getMac(Callback<String> callback);

    /**
     * 打开/关闭wifi
     * @param open true:打开wifi  false:关闭wifi
     * @return 打开/关闭 成功与否
     */
    void wifi(boolean open,Callback<Boolean> callback);

    /**
     * 连接到指定wifi
     * @param connect 是否连接
     * @param ssid wifi名称
     * @param password wifi密码
     * @return 连接成功/失败
     */
    void connectWifi(boolean connect,String ssid,String password,Callback<Boolean> callback);


    /**
     * 获取当前连接的wifi名称
     * @return 当前已连接的wifi名称
     */
    void getCurrenWifi(Callback<String> callback) ;


    /**测试麦克风
     * 有损坏，共有四种情况：
     * 1. 两个通道均没有麦损坏，返回 MIC OK!
     * 2. 两个通道均有麦损坏，返回 SD0 AND SD1 ALL ERROR!
     * 3. SD0 通道有麦损坏，返回 SD0 MIC ERROR!
     * 4. SD1 通道有麦损坏，返回 SD1 MIC ERROR！
     * @return 麦克风是否有损坏 true:有损坏 false:正常
     */
    void testMic(Callback<Boolean> callback);

    /**测试指示灯
     * 观察 LED 灯开始按照（红、绿、蓝）的顺序开始各亮一次，即 OK
     * @return
     */
    void testLight(Callback<Boolean> callback);
}
