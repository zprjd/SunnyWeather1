package com.example.sunnyweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

/*借助SQLiteOpenHelper帮助类对数据库进行创建和升级
建立数据库和表将服务器将获取到的数据存在本地*/

public class SWDatabaseHelper extends SQLiteOpenHelper {
    private final String TAG = "SunnyWeatherOpenHelper";

    /*在数据库中建三张表*/
    /*PROVINCE建表语句*/
    public static final String CREATE_PROVINCE = "create table Province("
            +"id integer primary key autoincrement,"// id是integer整型，主键primary key，autoincrement关键字表示id列自增长
            +"province_name text,"//省名province_name是文本类型
            +"province_code text)";//省代号province_code是文本类型
    /*City建表语句*/
    public static final String CREATE_CITY = "create table City("
            +"id integer primary key autoincrement,"// id是integer整型，主键primary key，autoincrement关键字表示id列自增长
            +"city_name text,"//市名city_name是文本类型
            +"city_code text,"//市代号city_code是文本类型
            +"province_name text)";             //City表关联Province表的外键
    /*County建表语句*/
    public static final String CREATE_COUNTY = "create table County("
            +"id integer primary key autoincrement,"// id是integer整型，主键primary key，autoincrement关键字表示id列自增长
            +"province_name text,"//县名province_name是文本类型
            +"county_name text,"//县名county_name是文本类型
            +"weatherid text,"//县代号weatherid是文本类型
            +"city_name integer)";               //County表关联City表的外键

    public static final String CREATE_WEATHER = "create table Weather("
            +"weather_city text,"
            +"weatherstring text)";
    public SWDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    /*在onCreate()方法中调用了SQLiteDabase的execSQL()方法去执行三条建表语句*/
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_PROVINCE);
        Log.i(TAG,"province created");
        sqLiteDatabase.execSQL(CREATE_CITY);
        Log.i(TAG,"city created");
        sqLiteDatabase.execSQL(CREATE_COUNTY);
        Log.i(TAG,"county created");
        sqLiteDatabase.execSQL(CREATE_WEATHER);
        Log.i(TAG,"weather created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
