package com.felixpc.down.downloader;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.MaterialEditText;
import android.widget.TextView;

import com.felixpc.down.downloader.tool.Constant;
import com.felixpc.down.downloader.tool.Utiles;
import com.panwrona.downloadprogressbar.library.DownloadProgressBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DownloadProgressBar.OnProgressUpdateListener {
    private LinearLayout fatherLay;
    private MaterialEditText pathText;
    private DownloadProgressBar download_progressBar;
    private boolean isDownLoadComplete = false;
    private TextView hint_Text;
    private String nativePath;
    private boolean isSaveDownLoadDir = false;

    private String TAG = "MainActivity ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {

        fatherLay = (LinearLayout) findViewById(R.id.father_lay);
        pathText = (MaterialEditText) findViewById(R.id.path_lay);
        download_progressBar = (DownloadProgressBar) findViewById(R.id.download_progressBar);
        fatherLay.setOnClickListener(this);
        download_progressBar.setOnClickListener(this);
        download_progressBar.setOnProgressUpdateListener(this);
        hint_Text = (TextView) findViewById(R.id.hint_text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.father_lay:
                fatherLay.requestFocus();
                pathText.clearFocus();
                break;
            case R.id.download_progressBar:
                String filePath = pathText.getText().toString().trim();
                Log.i(TAG, "文件网络地址：" + filePath);
                fatherLay.requestFocus();
                pathText.clearFocus();

                if (download_progressBar != null) {
                    if (isDownLoadComplete) {
                        Log.i(TAG, "取消下载……");
                        download_progressBar.clearAnimation();
                        isDownLoadComplete = false;
                    } else {
                        //开始下载
                        Log.i(TAG, "激活下载……");
                        if (null == filePath && "".equals(filePath)) {
                            Utiles.Mtoast(this, "输入下载地址！！");
                            return;
                        }

                        download_progressBar.playManualProgressAnimation();
                        isDownLoadComplete = true;

                    }
                }
                break;
        }
    }

    @Override
    public void onProgressUpdate(float v) {
        hint_Text.setText(v + " %");
    }

    @Override
    public void onAnimationStarted() {
        Log.i(TAG, "onAnimationStarted: ");
        download_progressBar.setEnabled(false);
        pathText.setEnabled(false);
        hint_Text.setText("正在下载……");
    }

    @Override
    public void onAnimationEnded() {
        Log.i(TAG, "onAnimationEnded: ");
        download_progressBar.setEnabled(true);
        pathText.setEnabled(true);
    }

    @Override
    public void onAnimationSuccess() {
        Log.i(TAG, "onAnimationSuccess: ");

    }

    @Override
    public void onAnimationError() {
        Log.i(TAG, "onAnimationError: ");
        hint_Text.setText("下载出错。");
    }

    @Override
    public void onManualProgressStarted() {
        Log.i(TAG, "onManualProgressStarted: ");
    }

    @Override
    public void onManualProgressEnded() {
        Log.i(TAG, "onManualProgressEnded: ");

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.upDownsucceed:


                    break;
                case Constant.upDownUiMsg:

                    break;
                case Constant.PROGRESS_ERROR:
                    if (download_progressBar != null) {
                        download_progressBar.playToError();
                    }
                    break;
            }
        }
    };


}
