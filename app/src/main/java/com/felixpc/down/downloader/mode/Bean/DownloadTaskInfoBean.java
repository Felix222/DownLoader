package com.felixpc.down.downloader.mode.Bean;

import com.felixpc.down.downloader.mode.tool.Constant;

/**
 * Created by Felix on 2016/4/14.
 */
public class DownloadTaskInfoBean {
    private int taskid;//任务id
    private int taskStatu = Constant.DOWNLOAD_INFO_NORMAL; //任务状态

    private String fileUrl;//文件地址
    private String savePath;//保存路径

    private long fileSize = 0;//文件大小
    private long userTime = 0;//已用时间

    private String fileName;//文件名
    private int progressbar = 0;//进度
    private boolean hasFinished = false;

    private long currenSize = 0;//当前下载大小

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    public int getTaskStatu() {
        return taskStatu;
    }

    public void setTaskStatu(int taskStatu) {
        this.taskStatu = taskStatu;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public long getUserTime() {
        return userTime;
    }

    public void setUserTime(long userTime) {
        this.userTime = userTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getProgressbar() {
        return progressbar;
    }

    public void setProgressbar(int progressbar) {
        this.progressbar = progressbar;
    }

    public boolean isHasFinished() {
        return hasFinished;
    }

    public void setHasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
    }

    public long getCurrenSize() {
        return currenSize;
    }

    public void setCurrenSize(long currenSize) {
        this.currenSize = currenSize;
    }
}
