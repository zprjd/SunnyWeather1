package com.example.sunnyweather.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;

/*将通用的发起HTTP请求以及解析服务器返回的数据操作提取到一个公共的类里，并提供操作静态方法。
* HttpUtil工具类中提供发起HTTP请求操作静态方法*/
public class HttpUtil {
    public static void sendRequestWithHttpURLConnection(final String address,final HttpCallbackListener listener){
        //开启线程来发起网络请求
        new Thread(() -> {
            HttpURLConnection httpURLConnection = null;
            try {
                //用address 新newURL对象
                URL url = new URL(address);
                httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(8000);
                httpURLConnection.setReadTimeout(8000);
                InputStream in = httpURLConnection.getInputStream();
                //下面对获取到的输入流进行读取
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder responnse = new StringBuilder();
                String line;
                while((line = reader.readLine())!=null){
                    responnse.append(line);
                }
                //Log.d("HttpUtil", String.valueOf(responnse));//
                // 打印结果
                if(listener != null){
                    listener.onFinsh(responnse.toString());//接收服务器响应的数据
                }
            } catch (Exception e) {
                Log.d("httputil",e.toString());
                e.printStackTrace();
                if (listener != null) {
                    listener.onError(e);//接收出现异常的原因,错误的详细信息。
                }
            } finally {
                if (httpURLConnection != null){
                    httpURLConnection.disconnect();
                }
            }
        }).start();
    }
}