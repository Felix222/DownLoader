package com.felixpc.down.downloader.downLoad;

import android.os.Handler;
import android.util.Log;

import com.felixpc.down.downloader.tool.Constant;
import com.felixpc.down.downloader.tool.Utiles;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;

/**
 * @User: FelixPC
 * @Date: 2016/3/24 0024
 * @Name: DownLoadThread 下载任务线程
 * @TODO: 描述
 */
public class DownLoadThread extends Thread {
    private String fileUrl, filePath, threadId;//下载地址 ,文件路径,线程id
    private boolean isRuning = true;
    private int threadNum = 5;//线程数量
    private int fileSize = 0;

    /**
     * 下载的百分比
     */
    private int downloadPercent = 0;
    /**
     * 下载的 平均速度
     */
    private int downloadSpeed = 0;
    /**
     * 下载用的时间
     */
    private int usedTime = 0;
    /**
     * 当前时间
     */
    private long curTime;
    /**
     * 是否已经下载完成
     */
    private boolean completed = false;
    /**
     * 下载开始时间
     */
    private long startTime = 0;

    private int downLoadSize;//下载了多少

    private Handler handler = null;

    private fileMassagelistener fileMsg;//文件监听

    private String TAG = "DownLoadThread ";
    private String fileName;


    public DownLoadThread(Handler handler, String fileUrl) {
        this.fileUrl = fileUrl;
        this.filePath = Utiles.getNativePath();
        this.handler = handler;
    }

    @Override
    public void run() {
//        FileDownloadThread[] fds = new FileDownloadThread[threadNum];//设置线程数量
        try {
            URL url = new URL(fileUrl);
            URLConnection connection = (URLConnection) url.getContent();
            fileSize = connection.getContentLength();
            Log.i(TAG, "run: 文件一共：" + fileSize);
            fileName = Utiles.getFileName(fileUrl);
            File file = new File(filePath + fileName);//创建文件
            FileDownloadThread downThread = new FileDownloadThread(url, file);
            downThread.setName("mDownloader_thread");
            downThread.start();
            startTime = System.currentTimeMillis();
            boolean finished = false;
            while (!finished) {
                downLoadSize = 0;
                finished = true;
                downLoadSize += downThread.getDownLoadSize();
                if (!downThread.isFinished()) {
                    finished = false;
                }
                downloadPercent = (downLoadSize * 100) / fileSize;//计算已下载百分比
                curTime = System.currentTimeMillis();
                usedTime = (int) ((curTime - startTime) / 1000);
                if (usedTime == 0) {
                    usedTime = 1;//好像没实际意义
                }
                downloadSpeed = (downLoadSize / usedTime) / 1024;
                sleep(1000);//1s刷新一次界面
                // 接口 刷新下载数据
                if (fileMsg != null) {
                    fileMsg.fileMessage(downLoadSize, downloadSpeed, downloadPercent);
                }
            }
            completed = true;
            //通知下载完成
            handler.sendEmptyMessage(Constant.upDownsucceed);
        } catch (Exception e) {
            Log.e(TAG, "multi file error Exception :" + e.getMessage());
            e.printStackTrace();
        }
        super.run();
    }

    public int getFileSize() {
        return fileSize;
    }

    public void stopWorking() {
        isRuning = false;
    }

    public interface fileMassagelistener {
        /**
         * @param downSize           已经下载的大小
         * @param downloadSpeed      下载的速度
         * @param getDownloadPercent 下载的百分比
         */
        void fileMessage(int downSize, int downloadSpeed, int getDownloadPercent);
    }

    /**
     * 添加下载过程监听
     *
     * @param listener
     */
    public void addFileMassageListener(fileMassagelistener listener) {
        if (fileMsg == null) {
            this.fileMsg = listener;
        }
    }
}
