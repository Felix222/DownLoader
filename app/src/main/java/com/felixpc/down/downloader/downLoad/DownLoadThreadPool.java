package com.felixpc.down.downloader.downLoad;

import android.os.Handler;
import android.util.Log;

import java.util.HashMap;

/**
 * @User: FelixPC
 * @Date: 2016/3/24 0024
 * @Name: DownLoadThreadPool
 * @TODO: 描述 任务线程池
 */
public class DownLoadThreadPool {
    private static DownLoadThreadPool pool;
    private DownLoadThread workThread[];
    private int work_threadNum = 0;
    private int curentTaskNum = 0;
    private String TAG = "DownLoadThreadPool ";
    private HashMap<String, Integer> url_to_threak = new HashMap<>();

    /**
     * 初始化缓存池
     */
    public DownLoadThreadPool(int workNum) {
        this.work_threadNum = workNum;
        workThread = new DownLoadThread[workNum];
    }

    /**
     * @param workNum    任务数量
     * @param threadSize 任务能开启多少线程
     * @return
     */
    public synchronized static DownLoadThreadPool getDownloadPool(int workNum, int threadSize) {
        if (null == pool) {
            pool = new DownLoadThreadPool(workNum);
        }
        return pool;
    }


    public void addThreadTask(String url, Handler handler) {
        if (curentTaskNum == 4) {
            Log.i(TAG, "addThreadTask: 任务总数已满 ");
        }
        synchronized (workThread) {
            workThread[curentTaskNum] = new DownLoadThread(handler, url);
            workThread[curentTaskNum].start();
            url_to_threak.put(url, curentTaskNum);
            curentTaskNum++;
        }
    }

    public void removeThreadTask(String url) {

        if (curentTaskNum > 0) {
            synchronized (workThread) {
                int index = url_to_threak.get(url);
                DownLoadThread task = workThread[index];
                task.stopWorking();
                workThread[index] = null;
                url_to_threak.remove(url);
                curentTaskNum--;
            }
        } else {
            Log.i(TAG, "removeThreadTask: 所有任务完成");
        }
    }


    public void destroy() {
    }

}
