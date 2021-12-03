package com.example.sunnyweather.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sunnyweather.gson.Weather;
import com.example.sunnyweather.model.City;
import com.example.sunnyweather.model.County;
import com.example.sunnyweather.model.Province;
import com.example.sunnyweather.util.Utility;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*将一些天气预报中常用的数据库操作封装起来，以便后续使用
* 与LitePal的DataSupport相同。*/
public class SunnyWeatherDB {
    private final String TAG = "SunnyWeatherDB";
    public static final String DB_NAME = "sunny_weather";//DatabaseNAME
    public static final int DB_VERSION = 1;//DatabaseVERSION
    private static SunnyWeatherDB sunnyWeatherDB;
    private SQLiteDatabase mSQLiteDatabase;

    /*为什么用Context?*/
    public SunnyWeatherDB(Context context) {
        //第三个参数？
        SWDatabaseHelper swDatabaseHelper = new SWDatabaseHelper(context, DB_NAME, null, DB_VERSION);
        Log.i(TAG, "dbHelper was created successfully");
        mSQLiteDatabase = swDatabaseHelper.getWritableDatabase();
        Log.i(TAG, "mSQLiteDatabase was created successfully");
    }

    /*获取SunnyWeatherDB实例*/
    public static SunnyWeatherDB getInstance(Context context) {
        if (sunnyWeatherDB == null) sunnyWeatherDB = new SunnyWeatherDB(context);
        return sunnyWeatherDB;
    }

    /*像数据库中添加省、市、县实例*/
    public void saveProvince(Province province) {
        if (province != null) {
            ContentValues values = new ContentValues();
            values.put("province_name", province.getProvinceName());
            values.put("province_code", province.getProvinceCode());
            Cursor cursor = mSQLiteDatabase.query("Province", null, "province_name = ? and province_code = ?"
                    , new String[]{province.getProvinceName(), String.valueOf(province.getProvinceCode())}, null, null, null);
//            if(cursor.getCount() == 0) {//总共的数据量
            mSQLiteDatabase.insert("Province", null, values);//表名，在未指定添加数据情况下给某些可为空的列自动赋值NULL，ContentValues对象
            Log.d(TAG, "加入" + province.getProvinceName() + "到数据库中");
//            }else {
//                Log.d(TAG, "数据库中已存在" + province.getProvinceName());
//            }
            if (cursor != null)
                cursor.close();//不关闭Could not allocate CursorWindow '/data/user/0/com.example.sunnyweather/databases/sunny_weather' of size 2097152 due to error -12.
        }
    }

    public void saveCity(City city) {
        if (city != null) {
            ContentValues values = new ContentValues();
            values.put("city_name", city.getCityName());
            //values.put("city_code",city.getCityCode());
            values.put("province_name", city.getProvinceName());
            Cursor cursor = mSQLiteDatabase.query("City", null, "city_name = ?  and province_name = ?"
                    , new String[]{city.getCityName()/*, city.getCityCode()*/, city.getProvinceName()}, null, null, null);
            if (cursor.getCount() == 0) {//总共的数据量
                mSQLiteDatabase.insert("City", null, values);//表名，在未指定添加数据情况下给某些可为空的列自动赋值NULL，ContentValues对象
                Log.d(TAG, "加入" + city.getProvinceName() + city.getCityCode() + city.getCityName() + "市到数据库中");
            } else {
                Log.d(TAG, "数据库中已存在" + city.getProvinceName() + city.getCityName());
            }
            if (cursor != null)
                cursor.close();//不关闭Could not allocate CursorWindow '/data/user/0/com.example.sunnyweather/databases/sunny_weather' of size 2097152 due to error -12.
        }
    }

    public void saveCounty(County county) {
        if (county != null) {
            ContentValues values = new ContentValues();
            values.put("county_name", county.getCountyName());
            values.put("weatherid", county.getWeatherId());
            values.put("city_name", county.getCityName());
            values.put("province_name", county.getProvinceName());
            Cursor cursor = mSQLiteDatabase.query("County", null, "county_name = ? and city_name = ?"
                    , new String[]{county.getCountyName(), county.getCityName()}, null, null, null);
//            if(cursor.getCount() == 0) {//总共的数据量
            mSQLiteDatabase.insert("County", null, values);
            Log.d(TAG, "加入" + county.getProvinceName()+county.getCityName() + county.getCountyName() + "到数据库中");
//            }else {
//                Log.d(TAG, "数据库中已存在" + county.getCityName()+county.getCountyName());
//            }
            if (cursor != null)
                cursor.close();//不关闭Could not allocate CursorWindow '/data/user/0/com.example.sunnyweather/databases/sunny_weather' of size 2097152 due to error -12.
        }
    }

    public void saveWeather(String weatherString) {
        List<Weather> weatherList = null;
        Weather weather = null;
        if (weatherString != null) {
            try {
                weather = Utility.handleWeatherResponse(weatherString);
                weatherList = sunnyWeatherDB.findAllWeathers("");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ContentValues values = new ContentValues();
            values.put("weatherstring", weatherString);
            values.put("weather_city", weather.getCity());
            for (Weather savedweather : weatherList) {
                if (savedweather.getCity() == weather.getCity()) { //如果有同一城市的更新数据库并退出
                    mSQLiteDatabase.update("Weather", values, "weather_city = ?", new String[]{weather.getCity()});
                    Log.d(TAG + "更新", weather.getCity());
                    return;
                }
            }
            //如果list中没有重复的就加入新的weather
            Cursor cursor = mSQLiteDatabase.query("Weather", null, "weather_city = ?", new String[]{weather.getCity()}, null, null, null);
            if (cursor.getCount() == 0) {//总共的数据量
                mSQLiteDatabase.insert("Weather", null, values);
                Log.d(TAG, "加入" + weatherString + "到数据库中");
            } else {
                mSQLiteDatabase.update("Weather", values, "weather_city = ?", new String[]{weather.getCity()});
                Log.d(TAG, "数据库中已更新" + weatherString);
            }
        }
    }

    public void deleteWeather(String weatherName, List<Weather> weatherList) {
        Log.d(TAG + "准备删除", weatherName);
        if (weatherName != null) {
            for (Weather savedweather : weatherList) {
                if (savedweather.getCity() == weatherName) { //如果有同一城市的更新数据库并退出
                    mSQLiteDatabase.delete("Weather", "weather_city = ?", new String[]{weatherName});
                    Log.d(TAG + "删除", weatherName);
                    return;
                }
            }
        }
    }

    /*用一个List存储从数据库中读取全国所有省、选中省中所有市、选中市中所有县实例并返回*/
    @SuppressLint("Range")
    public List<Province> findAllProvinces() {
        List<Province> list = new ArrayList<>();
        //查找Province表中所有数据
        Cursor cursor = mSQLiteDatabase.query("Province", null, null, null, null, null, null);
        //将指针移动到第一行
        if (cursor.moveToFirst()) {
            do {
                /*遍历Cursor对象，从中取出数据存到一个新的Province对象中,getColumnIndex返回的是某一列在表中的位置索引*/
                Province tempProvince = new Province();
                tempProvince.setId(cursor.getInt(cursor.getColumnIndex("id")));/*将索引传入对应的取值方法中*/
                tempProvince.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                tempProvince.setProvinceCode(cursor.getInt(cursor.getColumnIndex("province_code")));
                if (list.contains(tempProvince) == false) list.add(tempProvince);
            } while (cursor.moveToNext());
        }
        if (cursor != null) cursor.close();
        return list;
    }

    @SuppressLint("Range")
    public List<City> findAllCitiesInSlecetedProvince(String provinceName) {
        List<City> list = new ArrayList<>();
        //查找City表中所有数据
        Cursor cursor = mSQLiteDatabase.query("City", null, "province_name = ?", new String[]{provinceName}, null, null, null);
        Log.d("findAllCitiesInSlecetedProvince", "provinceName" + provinceName + Thread.currentThread());
        Set<City> set = new HashSet<>();
        if (cursor.moveToFirst()) {
            do {
                /*遍历Cursor对象，从中取出数据存到一个新的City对象中，getColumnIndex返回的是某一列在表中的位置索引*/
                City tempCity = new City();
                //tempCity.setId(cursor.getInt(cursor.getColumnIndex("id")));
                tempCity.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                //tempCity.setCityCode(cursor.getInt(cursor.getColumnIndex("city_code")));
                tempCity.setProvinceName(provinceName);
                set.add(tempCity);
            } while (cursor.moveToNext());
            for (City city : set) {
                list.add(city);
                Log.d("findAllCitiesInSlecetedProvince", "list 加入了City" + city.getCityName() + ","
                        + city.getCityCode() + ","
                        + city.getProvinceName() + ","
                        + city.getId());
            }
        }
        if (cursor != null) cursor.close();
        return list;
    }

    @SuppressLint("Range")
    public List<County> findAllCountiesInSelectedCity(String cityName) {
        List<County> list = new ArrayList<>();
        Cursor cursor = mSQLiteDatabase.query("County", null, "city_name = ?", new String[]{String.valueOf(cityName)}, null, null, null);
        //Log.d("findAllCountiesInSelectedCity","cityName"+cityName);
        Set<County> set = new HashSet<>();
        if (cursor.moveToFirst()) {
            //Log.d("findAllCountiesInSelectedCity",cursor.getString(cursor.getColumnIndex("county_name")));
            do {
                County tempCounty = new County();
                tempCounty.setId(cursor.getInt(cursor.getColumnIndex("id")));
                tempCounty.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                tempCounty.setWeatherId(cursor.getString(cursor.getColumnIndex("weatherid")));
                tempCounty.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                tempCounty.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                set.add(tempCounty);
            } while (cursor.moveToNext());
            for (County county : set) {
                list.add(county);
                //Log.d("findAllCountiesInSelectedCity","list加入了County"+county.getCountyName()+county.getCountyName()+county.getId()+","+county.getWeatherId());
            }
        }
        if (cursor != null)
            cursor.close();//不关闭Could not allocate CursorWindow '/data/user/0/com.example.sunnyweather/databases/sunny_weather' of size 2097152 due to error -12.
        return list;
    }

    public List<Weather> findAllWeathers(String name) throws JSONException {
        List<Weather> weatherList = new ArrayList<>();
        Cursor cursor = mSQLiteDatabase.query("Weather", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String weaString = cursor.getString(cursor.getColumnIndex("weatherstring"));
                Weather weather = Utility.handleWeatherResponse(weaString);
                Log.d("findAllWeathersADD", weather.getCity());
                weatherList.add(weather);
            } while (cursor.moveToNext());
        }
//                if (name != "") {
//                    for (int i = 0; i < weatherList.size(); i++) {
//                        Weather weather = weatherList.get(i);
//                        weatherList.add(0, weatherList.remove(i));
//                    }
//                }
        if (cursor != null) cursor.close();
        return weatherList;
    }
    public List<County> searchCounty (String searchText) throws JSONException {
        List<County> countyAList = new ArrayList<>();
        Cursor cursor = mSQLiteDatabase.query("County", null, "county_name like ? or city_name like ? or province_name like ?", new String[]{"%" + searchText + "%","%" + searchText + "%","%" + searchText + "%"}, null, null, null);
        Set<County> set = new HashSet<>();
        if (cursor.moveToFirst()) {
            //Log.d("findAllCountiesInSelectedCity",cursor.getString(cursor.getColumnIndex("county_name")));
            do {
                County tempCounty = new County();
                tempCounty.setId(cursor.getInt(cursor.getColumnIndex("id")));
                tempCounty.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                tempCounty.setWeatherId(cursor.getString(cursor.getColumnIndex("weatherid")));
                tempCounty.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                tempCounty.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                set.add(tempCounty);
            } while (cursor.moveToNext());
            for (County county : set) {
                countyAList.add(county);
                Log.d("searchCounty","list加入了County"+county.getProvinceName()+county.getCountyName()+county.getId()+","+county.getWeatherId());
            }
        }
        return countyAList;
    }
    //@Query("SELECT * FROM country_table WHERE countryName  LIKE :countryName)
}