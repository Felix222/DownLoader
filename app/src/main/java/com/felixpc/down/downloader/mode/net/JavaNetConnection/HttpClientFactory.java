package com.felixpc.down.downloader.mode.net.JavaNetConnection;

import org.apache.http.HttpVersion;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

/**
 * Created by Felix on 2016/4/14.
 * http连接工厂 老的方法
 */
public class HttpClientFactory {
    private static final int MAX_CONNECTIONS = 8;//最大连接数
    private static final int TIMEOUT = 10 * 1000;//超时时间
    private static final int SOCKET_BUFFER_SIZE = 8 * 1024;//kb为单位


    public static DefaultHttpClient CreateClient(boolean isHttps) {
        DefaultHttpClient httpClient = null;
        HttpParams params = CreateHttpParams();
        if (isHttps) {
            SchemeRegistry schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
            httpClient = new DefaultHttpClient(cm, params);
        } else {
            httpClient = new DefaultHttpClient(params);
        }
        return httpClient;
    }

    private static HttpParams CreateHttpParams() {
        final HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setStaleCheckingEnabled(params, false);//关闭旧连接检查，默认是开启的，关闭提高一点性能
        HttpConnectionParams.setConnectionTimeout(params, TIMEOUT);//超时时间;
        HttpConnectionParams.setSocketBufferSize(params, SOCKET_BUFFER_SIZE);//缓存大小
        HttpConnectionParams.setTcpNoDelay(params, true);//true为不使用延迟发送
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);//http协议版本
        HttpProtocolParams.setUseExpectContinue(params, true);//使用异常处理机
        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

        HttpClientParams.setRedirecting(params, false);//是否采用重定向
        ConnManagerParams.setTimeout(params, TIMEOUT);
        ConnManagerParams.setMaxConnectionsPerRoute(params,
                new ConnPerRouteBean(MAX_CONNECTIONS));//最大连接数
        ConnManagerParams.setMaxTotalConnections(params, 10);//多线程数量
        return params;
    }
}
