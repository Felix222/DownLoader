package com.felixpc.down.downloader.mode.downLoad;

import android.util.Log;

import com.felixpc.down.downloader.Control.mInterface.DownLoadRunnableInterface;
import com.felixpc.down.downloader.Control.mInterface.DownloadObserver;
import com.felixpc.down.downloader.mode.Bean.DownloadTaskInfoBean;
import com.felixpc.down.downloader.mode.tool.Constant;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Felix on 2016/4/15.
 */
public class WDownloadManager implements DownLoadRunnableInterface {
    private String TAG = WDownloadManager.class.getName() + " ";
    private static WDownloadManager instance;
    private HashMap<DownloadTaskInfoBean, DownloadObserver> obMap = new HashMap<>();
    //    private final HashMap<Integer, DownloadTaskInfoBean> tasklist = new HashMap<>();//记录下载的任务
    private List<DownloadTaskInfoBean> tasklist = new ArrayList<>();
    private Map<Integer, DownloadRunnable> mTaskRMap = new ConcurrentHashMap<>();


    public static WDownloadManager getInstance() {
        if (null == instance) {
            synchronized (WDownloadManager.class) {
                instance = new WDownloadManager();
            }
        }
        return instance;
    }

    private WDownloadManager() {
    }

    /**
     * 添加观察者
     */
    public void addTaskOb(DownloadTaskInfoBean task, DownloadObserver ob) {
        if (!obMap.containsKey(ob)) {
            synchronized (obMap) {
                obMap.put(task, ob);
            }
        }

    }

    public void removeTaskOb(DownloadObserver ob) {
        if (obMap.containsKey(ob)) {
            synchronized (obMap) {
                obMap.remove(ob);
            }
        } else {
            Log.i(TAG, "removeTaskOb: 没有此观察者 ");
//            throw new Exception("不存在此观察者");
        }
    }

    /**
     * 任务状态更新
     *
     * @param task <br/>{@link com.felixpc.down.downloader.mode.tool.Constant }下的常量
     */
    public void notifyDownStatuChange(DownloadTaskInfoBean task) {
        if (!obMap.containsKey(task)) {
            DownloadObserver ob = obMap.get(task);
            ob.ObTaskStatuChange(task);
        } else {
            Log.i(TAG, "notifyDownStatuChange: 没有此观察者 ");
//            throw new Exception("不存在此观察者");
        }
    }

    private Object readResolve() {
        return getInstance();
    }

    /**
     * 新建下载
     *
     * @param task
     */
    public synchronized void NewDownloadTask(DownloadTaskInfoBean task) {
        //判断是否存在了下载
        if (!tasklist.contains(task)) {
            task.setTaskid(tasklist.size());
            tasklist.add(task);
            if (task.getTaskStatu() == Constant.DOWNLOAD_INFO_NORMAL ||
                    task.getTaskStatu() == Constant.DOWNLOAD_INFO_PAUSE ||
                    task.getTaskStatu() == Constant.DOWNLOAD_INFO_ERROR) {
                task.setTaskStatu(Constant.DOWNLOAD_INFO_WAITING);
                notifyDownStatuChange(task);
                DownloadRunnable taskR = new DownloadRunnable(task, this);
                mTaskRMap.put(task.getTaskid(), taskR);
                ThreadManager.getDownloadPool().execute(taskR);
            }
        }
    }

    /**
     * 暂停任务
     *
     * @param task
     */
    public synchronized void pause(DownloadTaskInfoBean task) {
        stopDownload(task);
        task.setTaskStatu(Constant.DOWNLOAD_INFO_PAUSE);
        notifyDownStatuChange(task);
    }

    /**
     * 取消
     *
     * @param task
     */
    public synchronized void cancle(DownloadTaskInfoBean task) {
        stopDownload(task);
        task.setTaskStatu(Constant.DOWNLOAD_INFO_CANCLE);
        notifyDownStatuChange(task);
        task.setCurrenSize(0);
        File file = new File(task.getSavePath());
        file.delete();
        tasklist.remove(task);
        obMap.remove(task);
    }

    private void stopDownload(DownloadTaskInfoBean taskInfoBean) {
        DownloadRunnable runnable = mTaskRMap.remove(taskInfoBean.getTaskid());
        if (runnable != null) {
            ThreadManager.getDownloadPool().cancle(runnable);
        }
    }


    @Override
    public void upTaskStatu(DownloadTaskInfoBean task) {
        notifyDownStatuChange(task);
    }


    @Override
    public void upTaskProgress(DownloadTaskInfoBean task) {
//        更新进度
    }
    //还需要计算百分比
}
