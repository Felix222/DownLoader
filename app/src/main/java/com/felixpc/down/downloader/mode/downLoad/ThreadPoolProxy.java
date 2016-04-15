package com.felixpc.down.downloader.mode.downLoad;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Felix on 2016/4/15.
 * <p>
 * 线程池
 */
public class ThreadPoolProxy {
    private ThreadPoolExecutor mpool;
    private int mCorePoolSize;
    private int mMaximumpoolSize;
    private long mKeepAliveTime;

    public ThreadPoolProxy(int mCorePoolSize, int mMaximumpoolSize, long mKeepAliveTime) {
        this.mCorePoolSize = mCorePoolSize;
        this.mMaximumpoolSize = mMaximumpoolSize;
        this.mKeepAliveTime = mKeepAliveTime;
    }

    /**
     * 执行任务
     *
     * @param runnable
     */
    public synchronized void execute(Runnable runnable) {
        if (null == runnable) return;
        if (mpool == null || mpool.isShutdown()) {
            mpool = new ThreadPoolExecutor(mCorePoolSize, mMaximumpoolSize, mKeepAliveTime
                    , TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(), Executors.defaultThreadFactory());
        }
        mpool.execute(runnable);
    }

    /**
     * 取消任务
     *
     * @param runnable
     */
    public synchronized void cancle(Runnable runnable) {
        if (mpool != null && (!mpool.isShutdown() || mpool.isTerminating())) {
            mpool.getQueue().remove(runnable);
        }
    }

    /**
     * 取消线程池中某个还未执行的任务
     */
    public synchronized boolean contains(Runnable run) {
        if (mpool != null && (!mpool.isShutdown() || mpool.isTerminating())) {
            return mpool.getQueue().contains(run);
        } else {
            return false;
        }
    }

    /**
     * 立刻关闭线程池，并且正在执行的任务也将会被中断
     */
    public void stop() {
        if (mpool != null && (!mpool.isShutdown() || mpool.isTerminating())) {
            mpool.shutdownNow();
        }
    }

    /**
     * 平缓关闭单任务线程池，但是会确保所有已经加入的任务都将会被执行完毕才关闭
     */
    public synchronized void shutdown() {
        if (mpool != null && (!mpool.isShutdown() || mpool.isTerminating())) {
            mpool.shutdownNow();
        }
    }
}
