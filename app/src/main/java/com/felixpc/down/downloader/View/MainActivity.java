package com.felixpc.down.downloader.View;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.felixpc.down.downloader.R;

public class MainActivity extends BaseActivity {

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initView() {
        Log.i(TAG, "初始化控件");
        ((TextView) findViewById(R.id.testlay)).setText("测试空指针");
    }

}
