package com.felixpc.down.downloader.View.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.felixpc.down.downloader.Control.mInterface.TaskControlOnClistenter;

/**
 * Created by Felix on 2016/4/14.
 * {@link TaskControlButton}
 */
public class TaskControlButton extends ImageView implements View.OnLongClickListener, View.OnClickListener {
    private String TAG = TaskControlButton.class.getName();
    /**
     * {@link #STATU_PLAY} 正在下载
     */
    public static final int STATU_PLAY = 1;
    /**
     * {@link #STATU_PAUSE} 暂停下载
     */
    public static final int STATU_PAUSE = 2;
    /**
     * {@link #STATU_CANCLE} 取消下载或者初始化
     */
    public static final int STATU_CANCLE = 0;
    private int statu = STATU_CANCLE;

    private TaskControlOnClistenter listen;

//    OnLongClickListener longClickListener = null;

    public TaskControlButton(Context context) {
        super(context);
    }

    public TaskControlButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public void setOnLongClickListener(OnLongClickListener l) {
//        setStatu(STATU_STOP);
//        super.setOnLongClickListener(l);
//    }

    public void addTaskControlListener(TaskControlOnClistenter listen) {
        if (listen == null) {
            this.listen = listen;
            this.setOnClickListener(this);
            this.setOnLongClickListener(this);
        }
    }


    public int getStatu() {
        return statu;
    }

    /**
     * @param statu 状态<br/>
     *              {@link TaskControlButton#STATU_PLAY}<br/>
     *              {@link TaskControlButton#STATU_CANCLE}<br/>
     *              {@link TaskControlButton#STATU_PAUSE}<br/>
     */
    public void setStatu(int statu) {
        Log.i(TAG, "按钮状态改变为：" + statu);
        this.statu = statu;
    }

    @Override
    public boolean onLongClick(View v) {
        setStatu(STATU_CANCLE);
        if (listen != null) {
            listen.mOnclickListenter(getStatu());
        }
        //截断事件
        return true;
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "测试判断：" + (v == this ? "是一个控件" : "不是一个控件"));
        switch (getStatu()) {
            case STATU_PLAY:
                setStatu(STATU_PAUSE);
                break;
            case STATU_PAUSE:
                setStatu(STATU_PLAY);
                break;
            case STATU_CANCLE:
                setStatu(STATU_PLAY);
                break;
        }
        if (listen != null) {
            listen.mOnclickListenter(getStatu());
        }
    }
}
