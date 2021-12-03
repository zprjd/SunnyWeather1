package com.example.sunnyweather.view.activity;

import android.os.Bundle;

import com.example.sunnyweather.R;

public class SearchActivity extends BaseAcvity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Intent intent = getIntent();
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        if(prefs.getString("weatherString",null)!=null){
//            Intent intent1 = new Intent(this, MYNEWWeatherActivity.class);
//            startActivity(intent1);
//            Log.d("MainActivityonCreate","finish");
//            finish();
//        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Intent intent  = new Intent(SearchActivity.this,MYNEWWeatherActivity.class);
//        startActivity(intent);
        //ActivityCollector.finishAll();
    }
}