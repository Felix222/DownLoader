package com.felixpc.down.downloader.Control.mInterface;

import com.felixpc.down.downloader.mode.Bean.DownloadTaskInfoBean;

/**
 * Created by Felix on 2016/4/15.
 */
public interface TaskControlInterface {
    void taskControl(DownloadTaskInfoBean bean, int contolType);
}
