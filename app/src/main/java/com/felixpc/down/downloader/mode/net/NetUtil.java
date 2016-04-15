package com.felixpc.down.downloader.mode.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by Felix on 2016/4/14.
 * 网络工具类
 */
public class NetUtil {
    static String TAG = NetUtil.class.getName();
    private Context context;
    private NetWorkReceiver receiver = null;

    public NetUtil(Context context) {
        this.context = context;
    }

    /**
     * @param context
     * @return -1 为没有网络<br/>
     * 0 表示2G或者3G <br/>
     * wifi是 {@link ConnectivityManager#TYPE_WIFI }<br/>
     * 4G网络是 {@link TelephonyManager#NETWORK_TYPE_LTE}
     */
    public static int checkNetType(Context context) {
        int statu = 0;
        if (!CheckNet(context)) {
            statu = -1;
        }
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo info = cm.getActiveNetworkInfo();
                if (info != null) {
                    switch (info.getType()) {
                        case ConnectivityManager.TYPE_WIFI:
                            statu = ConnectivityManager.TYPE_WIFI;
                            break;
                        case ConnectivityManager.TYPE_MOBILE://移动网络就判断是不是4G
                            if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_LTE) {
                                statu = TelephonyManager.NETWORK_TYPE_LTE;
                            }
                            break;
                    }
                }
            }
        } catch (Exception e) {
        }
        return statu;
    }

    /**
     * 检查网络是否连接
     *
     * @param context
     * @return true为已连接
     */
    public static boolean CheckNet(Context context) {
        //获取手机连接管理对象
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo info = connectivityManager.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "CheckNet:检查网络异常 ");
        }

        return false;
    }

    /**
     * 检查网络是否连接包含正在连接
     *
     * @param context
     * @return true 已连接或者连接中
     */
    public static boolean checkNetworkStatus(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * @return 添加一个网络监听
     */
    public NetUtil addNetWorkReceiver() {
        try {
            IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            receiver = new NetWorkReceiver();
            context.registerReceiver(receiver, filter);
        } catch (Exception e) {
        }
        return this;
    }

    /**
     * @return 移除网络监听
     */
    public void removeNetReceiver() {
        if (receiver == null) return;
        try {
            context.unregisterReceiver(receiver);
        } catch (Exception e) {

        }
    }

    /**
     * 监听网络的广播
     */
    public class NetWorkReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "onReceive: 网络的变化：" + intent.toString());
        }
    }
}
