package com.example.sunnyweather.util;

import org.json.JSONException;

/*使用Java的回调机制，
要在HttpUtil的sendHttpRequest()方法中开启一个线程发起HTTP请求来接收服务器响应的数据，
因为网络请求通常属于耗时操作且子线程中无法使用return语句返回数据，
因此用HttpCallbackListener接收服务器响应后返回的数据，也接收出现异常的原因,错误的详细信息。*/
public interface HttpCallbackListener {
    /*onFinsh(String response)方法在服务器成功响应我们请求时调用，接收服务器响应的数据
    * onEror(Exception e)方法中的参数记录着错误的详细信息,接收出现异常的原因,错误的详细信息。*/
    void onFinsh(String response) throws JSONException;
    void onError(Exception e);
}
