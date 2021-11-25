package com.wangxiaobao.sdk.hid.test;

import android.app.Activity;
import android.os.Bundle;
import com.wangxiaobao.sdk.hid.HidCmd;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(view -> {
            HidCmd hidCmd=new HidCmd(MainActivity.this,null);
            hidCmd.setLight(true);
            hidCmd.getRkVersion();
        });
    }
}
