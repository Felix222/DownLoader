package com.felixpc.down.downloader.mode.tool;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

/**
 * Created by Felix on 2016/4/15.
 */
public class FileUtils {
    static String TAG = FileUtils.class.getName();

    public static String getSaveUrl() {
        if (checkStorage()) {
            String path = Environment.getDownloadCacheDirectory().getPath() + "/mDownload";
            return path;
        }
        return null;
    }

    public static boolean checkStorage() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    //检查剩余容量
    public static long remainingSize() {
        if (checkStorage()) return 0;
        StatFs sf = new StatFs(Environment.getDownloadCacheDirectory().getPath());
        int blockSize = sf.getBlockSize();//分区大小
        int avCounts = sf.getAvailableBlocks();//可用分区数
        long spaceLeft = blockSize * avCounts;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            long testSize = sf.getAvailableBytes();
            Log.i(TAG, "spaceLeft: " + spaceLeft + "testSize" + testSize);
        }
        return spaceLeft;
    }

    public static String getFileName(String url) {
        if (StringUtil.isEmpty(url)) return "";
        return url.substring(url.lastIndexOf("/") - 1, url.length());
    }
}
