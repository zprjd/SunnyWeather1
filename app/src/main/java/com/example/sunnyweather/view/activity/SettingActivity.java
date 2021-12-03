package com.example.sunnyweather.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sunnyweather.R;
import com.example.sunnyweather.db.SunnyWeatherDB;
import com.example.sunnyweather.gson.Weather;
import com.example.sunnyweather.view.adapter.SettingAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

    public class SettingActivity extends BaseAcvity implements Serializable {
    private RecyclerView recyclerView;
    public SunnyWeatherDB sunnyWeatherDB;
    public  List<Weather> weatherList;
    public SettingAdapter settingAdapter;
    public String loaction_name;
    public Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Intent intent = getIntent();
        recyclerView = findViewById(R.id.settingsRecycler);
        weatherList = (ArrayList<Weather>) intent.getSerializableExtra("weatherList");
        loaction_name = intent.getStringExtra("location_name");     //当前定位天气不能删除
        if(loaction_name!=null){
            Log.d("settingOnCreate",loaction_name);
        }else Log.d("settingOnCreate","null");
        for(Weather weather:weatherList){
            Log.d("SettingActivity",weather.getCity());
        }
        Log.d("SettingActivity", String.valueOf(weatherList.size()));
        settingAdapter = new SettingAdapter(weatherList,this);
        sunnyWeatherDB = SunnyWeatherDB.getInstance(this);
        if (weatherList != null) {
            recyclerView.setAdapter(settingAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
        }else{
            Log.e("SettingActivity","ee");
        }
        backButton =findViewById(R.id.back_button_insetting);
        backButton.setVisibility(View.GONE);//点击事件在adapter中
        settingAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Intent intent  = new Intent(SettingActivity.this,MYNEWWeatherActivity.class);
//        startActivity(intent);
//        ActivityCollector.finishAll();
    }
}