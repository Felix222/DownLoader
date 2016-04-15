package com.felixpc.down.downloader.mode.downLoad;

import android.util.Log;

import com.felixpc.down.downloader.Control.mInterface.DownLoadRunnableInterface;
import com.felixpc.down.downloader.mode.Bean.DownloadTaskInfoBean;
import com.felixpc.down.downloader.mode.net.JavaNetConnection.HttpHelper;
import com.felixpc.down.downloader.mode.tool.Constant;
import com.felixpc.down.downloader.mode.tool.FileUtils;
import com.felixpc.down.downloader.mode.tool.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Felix on 2016/4/11.
 */
public class DownloadRunnable implements Runnable {

    private String TAG = DownloadRunnable.class.getName();
    private DownloadTaskInfoBean task;
    private DownLoadRunnableInterface ob;

    public DownloadRunnable(DownloadTaskInfoBean task, DownLoadRunnableInterface ob) {
        this.task = task;
        this.ob = ob;
    }

    @Override
    public void run() {
        task.setTaskStatu(Constant.DOWNLOAD_INFO_DOWNLOADING);
        ob.upTaskStatu(task);
        //检查有没有下载过
        HttpHelper.HttpResult result = null;
        InputStream inStream = null;
        File file = null;
        if (null != task.getSavePath() && task.getSavePath().equals("")) {
            file = new File(task.getSavePath());
        } else {
            String filename = FileUtils.getFileName(task.getFileUrl());
            file = new File(FileUtils.getSaveUrl() + filename);

            Log.i(TAG, "文件路径 " + file.getPath());
            if (file == null) {
                Log.i(TAG, "file创建失败");
            }
            task.setSavePath(file.getPath());
            task.setFileName(filename);

            result = HttpHelper.get(task.getFileUrl());

            task.setFileSize(result.getFileSize());

            if (null == result || ((inStream = result.getNetworkInputStream()) == null)) {
                task.setTaskStatu(Constant.DOWNLOAD_INFO_ERROR);
                ob.upTaskStatu(task);
            } else {
                if (task.getCurrenSize() > 0) {
                    skipBytesFromStream(inStream, task.getCurrenSize());
                }
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file, true);
                    int count = -1;
                    byte[] buffer = new byte[1024];
                    while (((count = inStream.read(buffer)) != -1)
                            && task.getTaskStatu() == Constant.DOWNLOAD_INFO_DOWNLOADING) {
                        // 每次读取到数据后，都需要判断是否为下载状态，如果不是，下载需要终止，如果是，则刷新进度
                        fos.write(buffer, 0, count);
                        fos.flush();
                        task.setCurrenSize(task.getCurrenSize() + count);
                        ob.upTaskProgress(task);
                    }
                } catch (Exception e) {
                    task.setTaskStatu(Constant.DOWNLOAD_INFO_ERROR);
                    ob.upTaskStatu(task);
                    task.setCurrenSize(0);//后根据情况更改
                    e.printStackTrace();
                } finally {
                    IOUtils.close(fos);
                    if (result != null) {
                        result.close();
                    }
                }
                //检查文件大小。确定是否下载完毕
                if (task.getCurrenSize() == task.getFileSize()) {
                    task.setTaskStatu(Constant.DOWNLOAD_INFO_COMPLETE);
                    ob.upTaskStatu(task);
                } else if (task.getTaskStatu() == Constant.DOWNLOAD_INFO_PAUSE) {
                    ob.upTaskStatu(task);
                } else {
                    task.setTaskStatu(Constant.DOWNLOAD_INFO_ERROR);
                    ob.upTaskStatu(task);
//                    task.setCurrenSize(0); //错误了可能要删除文件
//                    file.delete();
                }
            }
        }
    }


    /**
     * 重写了Inpustream 中的skip(long n) 方法，将数据流中起始的n 个字节跳过
     */

    private long skipBytesFromStream(InputStream inputStream, long n) {
        long remaining = n;
        // SKIP_BUFFER_SIZE is used to determine the size of skipBuffer
        int SKIP_BUFFER_SIZE = 10000;
        // skipBuffer is initialized in skip(long), if needed.
        byte[] skipBuffer = null;
        int nr = 0;
        if (skipBuffer == null) {
            skipBuffer = new byte[SKIP_BUFFER_SIZE];
        }
        byte[] localSkipBuffer = skipBuffer;
        if (n <= 0) {
            return 0;
        }
        while (remaining > 0) {
            try {
                long skip = inputStream.skip(10000);
                nr = inputStream.read(localSkipBuffer, 0,
                        (int) Math.min(SKIP_BUFFER_SIZE, remaining));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (nr < 0) {
                break;
            }
            remaining -= nr;
        }
        return n - remaining;
    }

}
