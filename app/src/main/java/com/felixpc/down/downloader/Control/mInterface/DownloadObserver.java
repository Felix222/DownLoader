package com.felixpc.down.downloader.Control.mInterface;

import com.felixpc.down.downloader.mode.Bean.DownloadTaskInfoBean;

/**
 * Created by Felix on 2016/4/15.
 */
public interface DownloadObserver {
    void ObupProgress(DownloadTaskInfoBean task);

    void ObControlTask(DownloadTaskInfoBean task);

    void ObTaskStatuChange(DownloadTaskInfoBean task);
}
