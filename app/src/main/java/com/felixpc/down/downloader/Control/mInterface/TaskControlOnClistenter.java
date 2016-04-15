package com.felixpc.down.downloader.Control.mInterface;

/**
 * Created by Felix on 2016/4/15.
 * <p>
 * 任务控制接口
 */
public interface TaskControlOnClistenter {
    /**
     * 三种状态<br/>
     *
     * @param clikTyep 任务的状态<br/>
     *                 {@link com.felixpc.down.downloader.View.widget.TaskControlButton#STATU_PLAY  <tt>STATU_PLAY</tt>}<br/>
     *                 {@link com.felixpc.down.downloader.View.widget.TaskControlButton#STATU_PAUSE  <tt>STATU_PAUSE</tt>}<br/>
     *                 {@link com.felixpc.down.downloader.View.widget.TaskControlButton#STATU_CANCLE  <tt>STATU_CANCLE</tt>}<br/>
     */
    void mOnclickListenter(int clikTyep);

//    void mOnLongClicklistenter(int controlType);
}
