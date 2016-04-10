package com.felixpc.down.downloader.tool;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

/**
 * @User: FelixPC
 * @Date: 2016/3/24 0024
 * @Name: Utiles
 * @TODO: 描述
 */
public class Utiles {
    public static void Mtoast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 得到文件的名称
     *
     * @return
     */
    public static String getFileName(String url) {
        String name = null;
        try {
            name = url.substring(url.lastIndexOf("/") + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * @return 返回一个本地目录
     */
    public static String getNativePath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = new File(Environment.getExternalStorageDirectory() + "/downLoader/");//获取跟目录
            if (!sdDir.exists()) {
                sdDir.mkdir();
            }
        }
        return sdDir.toString();
    }
}
