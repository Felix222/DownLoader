package com.felixpc.down.downloader.mode.net.JavaNetConnection;

import android.util.Log;

import com.felixpc.down.downloader.mode.tool.IOUtils;
import com.felixpc.down.downloader.mode.tool.StringUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Felix on 2016/4/15.
 */
public class HttpHelper {

    String TAG = HttpHelper.class.getName();

    /**
     * get请求
     *
     * @param url
     * @return
     */
    public static HttpResult get(String url) {
        HttpGet get = new HttpGet(url);
        return execute(url, get);
    }

    /**
     * post 请求
     *
     * @param url
     * @param bytes
     * @return
     */
    public static HttpResult post(String url, byte[] bytes) {
        HttpPost post = new HttpPost(url);
        ByteArrayEntity byteArrayEntity = new ByteArrayEntity(bytes);
        post.setEntity(byteArrayEntity);
        return execute(url, post);
    }
/*

    */

    /**
     * 下载
     *
     * @param url
     * @return
     *//*

    public static HttpResult download(String url) {
        HttpGet get = new HttpGet(url);
        return execute(url, get);
    }
*/
    private static HttpResult execute(String url, HttpRequestBase requestBase) {

        boolean isHttps = url.startsWith("https://");
        AbstractHttpClient httpClient = HttpClientFactory.CreateClient(isHttps);
        HttpContext httpContext = new SyncBasicHttpContext(new BasicHttpContext());
        HttpRequestRetryHandler retryHandler = httpClient.getHttpRequestRetryHandler();//获取重试机制
        int retryCount = 0;
        boolean retry = true;
        while (retry) {
            try {
                HttpResponse response = httpClient.execute(requestBase, httpContext);//访问网络
                if (response != null) {
                    return new HttpResult(response, httpClient, requestBase);
                }
            } catch (Exception e) {
                IOException ioException = new IOException(e.getMessage());
                retry = retryHandler.retryRequest(ioException, ++retryCount, httpContext);//错误丢给重试机制判断是否需要重试
            }
        }
        return null;
    }

    /**
     * http的返回结果的封装，可以直接从中获取返回的字符串或者流
     */
    public static class HttpResult {
        String TAG = HttpHelper.class.getName();
        private HttpResponse mResponse;
        private InputStream mIn;
        private String mStr;
        private HttpClient mHttpClient;
        private HttpRequestBase mRequestBase;

        public HttpResult(HttpResponse response, HttpClient httpClient
                , HttpRequestBase requestBase) {
            this.mResponse = response;
            this.mHttpClient = httpClient;
            this.mRequestBase = requestBase;

        }

        /**
         * 获取网络返回码
         *
         * @return
         */
        public int getCode() {
            StatusLine status = mResponse.getStatusLine();
            return status.getStatusCode();
        }


        public String getString() {
            if (!StringUtil.isEmpty(mStr)) {
                return mStr;
            }
            InputStream input = getNetworkInputStream();
            ByteArrayOutputStream out = null;
            if (input != null) {
                try {
                    out = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int len = -1;
                    while ((len = input.read(buffer)) != -1) {
                        out.write(buffer, 0, len);
                    }
                    mStr = new String(out.toByteArray(), "utf-8");
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    IOUtils.close(out);
                    close();
                }
            }
            return mStr;
        }

        /**
         * 关闭网络连接
         */
        public void close() {
            if (mRequestBase != null) {
                mRequestBase.abort();
            }
            IOUtils.close(mIn);
            if (mHttpClient != null) {
                mHttpClient.getConnectionManager().closeExpiredConnections();
            }
        }

        /**
         * 文件的大小
         *
         * @return
         */
        public long getFileSize() {
            long size = this.mResponse.getEntity().getContentLength();
            Log.i(TAG, "可能是文件的长度： " + size);
            return size;
        }

        /**
         * 获取网络返回流
         *
         * @return
         */
        public InputStream getNetworkInputStream() {
            if (mIn == null && getCode() < 300) {
                HttpEntity entity = mResponse.getEntity();
                try {
                    mIn = entity.getContent();
                } catch (IOException e) {
                    Log.i(TAG, "getFileInputStream: 异常");
                    e.printStackTrace();
                }
            }
            return mIn;
        }
    }

}
