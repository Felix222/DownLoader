package com.felixpc.down.downloader.mode.tool;

/**
 * @User: FelixPC
 * @Date: 2016/3/25 0025
 * @Name: Constant
 * @TODO: 描述 常量类
 */
public class Constant {
    /**
     * {@link #DOWNLOAD_INFO_ERROR}  下载错误
     */
    public static final int DOWNLOAD_INFO_ERROR = -1;
    /**
     * {@link #DOWNLOAD_INFO_DOWNLOADING}  开始下载
     */
    public static final int DOWNLOAD_INFO_DOWNLOADING = 1;
    /**
     * {@link #DOWNLOAD_INFO_PAUSE}  暂停
     */
    public static final int DOWNLOAD_INFO_PAUSE = 2;
    /**
     * {@link #DOWNLOAD_INFO_PAUSE}  取消
     */
    public static final int DOWNLOAD_INFO_CANCLE = 3;
    /**
     * {@link #DOWNLOAD_INFO_PAUSE}  完成
     */
    public static final int DOWNLOAD_INFO_COMPLETE = 4;
    /**
     * {@link #DOWNLOAD_INFO_PAUSE} 普通状态
     */
    public static final int DOWNLOAD_INFO_NORMAL = 5;
    /**
     * {@link #DOWNLOAD_INFO_PAUSE} 等待下载
     */
    public static final int DOWNLOAD_INFO_WAITING = 6;
    /**
     * {@link #DOWNLOAD_INFO_PAUSE}  更新进度条
     */
    public static final int DOWNLOAD_INFO_UPDATA_PROGRESS = 101;//更新进度条


}
