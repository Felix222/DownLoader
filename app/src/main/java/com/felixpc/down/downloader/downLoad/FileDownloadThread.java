package com.felixpc.down.downloader.downLoad;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;

/**
 * @User: FelixPC
 * @Date: 2016/3/24 0024
 * @Name: FileDownloadThread
 * @TODO: 描述 具体下载线程
 */
public class FileDownloadThread extends Thread {
    private URL fileUrl;
    private File NativeFile;

    private String TAG = "FileDownloadThread";
    /**
     * 缓冲区
     */
    private static final int BUFF_SIZE = 1024;
    /**
     * 完成
     */
    private boolean finished = false;
    /**
     * 已经下载多少
     */
    private int downloadSize = 0;

    public FileDownloadThread(URL fileUrl, File nativeFile) {
        this.fileUrl = fileUrl;
        NativeFile = nativeFile;
    }

    @Override
    public void run() {
        BufferedInputStream bis = null;
        RandomAccessFile fos = null;
        byte[] buf = new byte[BUFF_SIZE];
        URLConnection connection = null;
        try {
            connection = fileUrl.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setAllowUserInteraction(true);
            fos = new RandomAccessFile(NativeFile, "rwd");//读写
            //设置本地文件的长度和下载文件相同
            fos.setLength(NativeFile.length());
            bis = new BufferedInputStream(connection.getInputStream(), BUFF_SIZE);
            while (true) {
                int len = bis.read(buf, 0, BUFF_SIZE);
                if (len == -1)   //下载完成
                {
                    break;
                }
                fos.write(buf, 0, len);
                int count = downloadSize + len;//这里需要考虑下，怎样处理好
                if (count > NativeFile.length()) {    //如果下载多了，则减去多余部分
                    Log.e(TAG, "当前" + this.getName() + "下载多了!!!");
                    downloadSize = (int) NativeFile.length();
                    this.finished = true;
                } else {
                    downloadSize += len;
                }
            }
            this.finished = true;  //当前阶段下载完成
            Log.e(TAG, "当前" + this.getName() + "下载完成");
            bis.close();  //关闭流
            fos.close();
        } catch (Exception e) {
            Log.e(TAG, "download error Exception " + e.getMessage());
            e.printStackTrace();
        }
        super.run();
    }

    public int getDownLoadSize() {
        return downloadSize;
    }

    public boolean isFinished() {
        return finished;
    }
}
