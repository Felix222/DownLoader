package com.felixpc.down.downloader.mode.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.felixpc.down.downloader.Control.mInterface.TaskControlInterface;
import com.felixpc.down.downloader.Control.mInterface.TaskControlOnClistenter;
import com.felixpc.down.downloader.R;
import com.felixpc.down.downloader.View.widget.TaskControlButton;
import com.felixpc.down.downloader.mode.Bean.DownloadTaskInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felix on 2016/4/15.
 */
public class MainTaskListAdapter extends RecyclerView.Adapter<MainTaskListAdapter.MainTaskItemHelper> {
    private List<DownloadTaskInfoBean> taskList = new ArrayList<>();
    private Context context;
    private TaskControlInterface taskControl;

    public MainTaskListAdapter(Context context, TaskControlInterface listen) {
        this.context = context;
        this.taskControl = listen;
    }

    public void upData(List<DownloadTaskInfoBean> tasks) {
        this.taskList = tasks;
        notifyDataSetChanged();
    }

    /**
     * 调用此方法前需要数据库查重
     *
     * @param taskInfoBean
     */
    public void addTask(DownloadTaskInfoBean taskInfoBean) {
        if (taskInfoBean == null) return;
        taskList.add(taskInfoBean);
        notifyItemInserted(taskList.indexOf(taskInfoBean));
    }

    /**
     * 移除列表中的项目
     *
     * @param taskInfoBean
     */
    public void removeTask(DownloadTaskInfoBean taskInfoBean) {
        if (taskInfoBean == null) return;
        int index = taskList.indexOf(taskInfoBean);
        if (index > 0) {
            taskList.remove(taskInfoBean);
            notifyItemRemoved(index);
        }
    }

    @Override
    public MainTaskItemHelper onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainTaskItemHelper(View.inflate(context, R.layout.download_list_item_lay, null));
    }

    @Override
    public void onBindViewHolder(MainTaskItemHelper holder, int position) {
        if (holder != null) {
            holder.SetDaa(taskList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    /**
     * viewholder
     */
    public class MainTaskItemHelper extends RecyclerView.ViewHolder implements TaskControlOnClistenter {
        private TextView fileName, downloadUseTime, fileSize;
        private NumberProgressBar itemProGressBar;
        private TaskControlButton controlButton;
        private TaskControlInterface taskControl;
        private DownloadTaskInfoBean VHBean;

        public MainTaskItemHelper(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            fileName = (TextView) itemView.findViewById(R.id.item_fileName);
            downloadUseTime = (TextView) itemView.findViewById(R.id.item_fileUseTime);
            fileSize = (TextView) itemView.findViewById(R.id.item_fileSize);
            itemProGressBar = (NumberProgressBar) itemView.findViewById(R.id.item_progressBar);
            controlButton = (TaskControlButton) itemView.findViewById(R.id.item_task_control_button);
            controlButton.setStatu(TaskControlButton.STATU_CANCLE);
            controlButton.addTaskControlListener(this);
            itemProGressBar.setMax(100);
        }

        public void SetDaa(DownloadTaskInfoBean itemBean) {
            this.VHBean = itemBean;
            this.fileName.setText(itemBean.getFileName());
            this.downloadUseTime.setText(getUseTimeStr(0));
            this.fileSize.setText(getfileSizeStr(0));
            this.itemProGressBar.setProgress(0);
        }


        public void upProgressbar(int lenth, long useTime) {
            itemProGressBar.setProgress(lenth);
        }

        private String getUseTimeStr(long time) {
            StringBuffer useTimeStr = new StringBuffer("已用时间：");
            return useTimeStr.append(time).append(" 秒").toString();
        }

        private String getfileSizeStr(long size) {
            StringBuffer fileSizeStr = new StringBuffer("文件大小：");
            return fileSizeStr.append(size).append("kb").toString();
        }

        @Override
        public void mOnclickListenter(int clikTyep) {
            if (taskControl != null) {
                taskControl.taskControl(this.VHBean, clikTyep);
            }
        }
    }
}
