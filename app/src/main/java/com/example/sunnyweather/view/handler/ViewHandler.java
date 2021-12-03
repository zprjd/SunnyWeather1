package com.example.sunnyweather.view.handler;


import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.sunnyweather.gson.Weather;
import com.example.sunnyweather.view.activity.MYNEWWeatherActivity;
import com.example.sunnyweather.view.adapter.HorizontalAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONException;

public class ViewHandler extends Handler {
    private MYNEWWeatherActivity mynewWeatherActivity;
    public ViewHandler(MYNEWWeatherActivity mynewWeatherActivity){
        this.mynewWeatherActivity = mynewWeatherActivity;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        String response = (String) msg.obj;
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mynewWeatherActivity).edit();
        editor.putString("weatherString", response);        //myweaher的string
        editor.apply();
        mynewWeatherActivity.sunnyWeatherDB.saveWeather(response);              //覆盖数据库中的
        try {
            mynewWeatherActivity.weatherList=mynewWeatherActivity.sunnyWeatherDB.findAllWeathers("");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String name = mynewWeatherActivity.mweather.getCity();      //请求到的天气的名字
        if(mynewWeatherActivity.weatherList!=null) {
            for (Weather weather : mynewWeatherActivity.weatherList) {          //如果找到就覆盖原来list中的
                if (weather.getCity() == name) {
                    weather = mynewWeatherActivity.mweather;
                    break;
                }
            }
        }

        if (mynewWeatherActivity.mweather != null) {     //请求成功
            mynewWeatherActivity.closeProgressDialog();
            //不能在子线程进行UI操作
            if(mynewWeatherActivity.weatherList!=null) {
                try {
                    mynewWeatherActivity.weatherList = mynewWeatherActivity.sunnyWeatherDB.findAllWeathers(mynewWeatherActivity.mweather.getCity());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("requestweather", String.valueOf(mynewWeatherActivity.weatherList.size()));
                for (Weather weather : mynewWeatherActivity.weatherList) {
                    Log.d("requestWeather", weather.getCity());
                }
            }
            mynewWeatherActivity.horizontalAdapter = new HorizontalAdapter(mynewWeatherActivity, mynewWeatherActivity.weatherList);
            mynewWeatherActivity.viewPager2.setAdapter(mynewWeatherActivity.horizontalAdapter);
            mynewWeatherActivity.viewPager2.setOrientation(mynewWeatherActivity.viewPager2.ORIENTATION_HORIZONTAL);
            mynewWeatherActivity.horizontalAdapter.notifyDataSetChanged();
            Log.d("requestweather", "notify");
            if(msg.what==2) {                       //没有新加入城市，跳转到指定城市
                Log.d("requestWeather1", mynewWeatherActivity.mweather.getCity() + "," + mynewWeatherActivity.mweather.getUpdate_time());
                for(int i = 0;i<mynewWeatherActivity.weatherList.size();i++){
                    if(mynewWeatherActivity.weatherList.get(i).getCity().equals(mynewWeatherActivity.mweather.getCity())){
                        Log.d("requestWeather1",mynewWeatherActivity.weatherList.get(i).getCity()+","+i);
                        mynewWeatherActivity.viewPager2.setCurrentItem(i);
                    }
                }
            }else if(msg.what==1) {                 //新加入城市就更新tablayout
                new TabLayoutMediator(mynewWeatherActivity.tabLayout, mynewWeatherActivity.viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        Weather weather = mynewWeatherActivity.weatherList.get(position);
                        tab.setText(weather.getCity());
                    }
                }).attach();
            }
        } else {
            //必须写MYNEWWeatherActivity.this，因为this默认是httpcallbacklistener
            Toast.makeText(mynewWeatherActivity, "获取天气信息失败", Toast.LENGTH_SHORT).show();
        }
    }
}