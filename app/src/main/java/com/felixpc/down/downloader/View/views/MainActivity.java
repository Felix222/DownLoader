package com.felixpc.down.downloader.View.views;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.felixpc.down.downloader.R;
import com.felixpc.down.downloader.View.widget.InputDialog;
import com.felixpc.down.downloader.mode.Bean.DownloadTaskInfoBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private InputDialog inputDialog;
    private StringBuffer inputFileUrl;
    private RecyclerView recyclerView;
    private List<DownloadTaskInfoBean> tasklist = new ArrayList<>();

    @Override
    public void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initView() {
        Log.i(TAG, "初始化控件");
        ((TextView) findViewById(R.id.titlebar_title)).setText("任务列表");
        findViewById(R.id.titlebar_add).setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.tast_reacyclerView);
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
