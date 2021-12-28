package com.wangxiaobao.sdk.hid.engine.task;

import android.os.Handler;
import com.wangxiaobao.sdk.hid.engine.CallBackHandler;
import com.wangxiaobao.sdk.hid.engine.HidEngine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskManager {
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private HidEngine engine;
    public TaskManager(HidEngine engine){
        this.engine=engine;
    }

    public void addTask(Task task){
        executor.submit(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            engine.sendCmd(task.requestData, task.callBackHandler);
        });
    }

    public void clear(){
        executor.shutdown();
    }
}
