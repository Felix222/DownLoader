package com.felixpc.down.downloader.View;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.felixpc.down.downloader.R;
import com.felixpc.down.downloader.widget.InputDialog;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private InputDialog inputDialog;
    private StringBuffer inputFileUrl;


    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initView() {
        Log.i(TAG, "初始化控件");
        ((TextView) findViewById(R.id.titlebar_title)).setText("任务列表");
        findViewById(R.id.titlebar_add).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titlebar_add:
                if (inputDialog == null) {
                    inputDialog = new InputDialog(this);
                    inputDialog.setCanceledOnTouchOutside(true);
                    inputDialog.addOKButton(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i(TAG, "ok按钮");
                            inputFileUrl = new StringBuffer(inputDialog.getInputUrl());
                            inputDialog.dismiss();
                            inputDialog = null;
                        }
                    }).addCancleButton(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            inputDialog.dismiss();
                            inputDialog = null;

                        }
                    });
                }
                if (!inputDialog.isShowing()) {
                    inputDialog.show();
                }
                break;
        }
    }
}
