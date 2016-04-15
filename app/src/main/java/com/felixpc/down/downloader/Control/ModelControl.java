package com.felixpc.down.downloader.Control;

import android.content.Context;

import com.felixpc.down.downloader.mode.Bean.DownloadTaskInfoBean;
import com.felixpc.down.downloader.mode.downLoad.WDownloadManager;

/**
 * Created by Felix on 2016/4/14.
 * <p>
 * 添加数据等业务处理
 */
public class ModelControl {
    private Context context;
    private WDownloadManager downloadManager;

    public ModelControl(Context context) {
        this.context = context;
        downloadManager = WDownloadManager.getInstance();
    }

    public void createTask(String url) {
        DownloadTaskInfoBean bean = new DownloadTaskInfoBean();
        bean.setFileUrl(url);
        downloadManager.NewDownloadTask(bean);

    }
}
