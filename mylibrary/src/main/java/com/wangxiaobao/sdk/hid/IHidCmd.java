package com.wangxiaobao.sdk.hid;

public interface IHidCmd {

    /**
     * 设置sn号
     * @param sn 17位sn号码
     * @return true:设置成功 false:设置失败
     */
    boolean setSn(String sn);

    /**
     * 获取sn号
     * @return 返回sn号
     */
    String getSn();

    /**设置工牌时间
     * @param timestamp 时间戳
     * @return 返回设置结果
     */
    boolean setTime(long timestamp);
    /**获取工牌时间
     * @return 返回工牌时间
     */
    String getTime();
    /**
     * 设置工牌复位(重启)
     */
    void reboot();
    /**
     * 工牌恢复出厂设置
     */
    void resumeFactory();

    /** 设置工牌指示灯
     * @param open true:打开指示灯  false:关闭指示灯
     * @return 返回设置结果
     */
    boolean setLight(boolean open);
    /**获取工牌Rk版本号
     * @return 返回工牌Rk版本号
     */
    String getRkVersion();
    /**获取工牌esp版本号
     * @return 返回工牌esp版本号
     */
    String getEspVersion();

}
