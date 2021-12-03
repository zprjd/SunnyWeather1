package com.example.sunnyweather.loadfile;

import android.content.Context;
import android.util.Log;

import com.example.sunnyweather.db.SunnyWeatherDB;
import com.example.sunnyweather.model.City;
import com.example.sunnyweather.model.County;
import com.example.sunnyweather.model.Province;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class loadFile {
    public static boolean loadAllInFile(SunnyWeatherDB sunnyWeatherDB, Context context) throws IOException {
        Log.d("loadAllInFile","11");
        InputStream inputStream = context.getAssets().open("lisy.csv");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
//        Set<String> ProvinceSet = new HashSet<>();
//        Set<String> CitySet = new HashSet<>();
        Set<String> CountySet = new HashSet<>();
        int n = 3;
        while(n-- > 1){
            bufferedReader.readLine();
        }
        Log.d("loadAllInFile","22");
        while((line = bufferedReader.readLine())!=null) {
            String[] strings = line.split(",");
//            ProvinceSet.add(strings[7]);
//            CitySet.add(strings[9]+","+strings[7]);
//            CountySet.add(strings[0]+","+strings[2]+","+strings[9]);        //set中存string比对象快得多
            County county = new County();
            //county.setWeatherId(strings[0]);
            county.setCountyName(strings[2]);
            county.setCityName(strings[9]);
            county.setProvinceName(strings[7]);
            Log.d("loadAllInFile","County"+county.getProvinceName()+county.getCityName()+county.getCountyName());
            sunnyWeatherDB.saveCounty(county);
        }
        Log.d("loadAllInFile","22");
//        for(String provinceName:ProvinceSet){
//            Province province = new Province();
//            province.setProvinceName(provinceName);
//            sunnyWeatherDB.saveProvince(province);
//        }
//        for(String s:CitySet){
//            String[] strings = s.split(",");
//            City city = new City();
//            city.setCityName(strings[0]);
//            city.setProvinceName(strings[1]);
//            sunnyWeatherDB.saveCity(city);
//            Log.d("loadAllInFile","City"+city.getCityName()+city.getProvinceName());
//        }
//        for(String s:CountySet){
//            String[] strings = s.split(",");
//            County county = new County();
//            //county.setWeatherId(strings[0]);
//            county.setCountyName(strings[1]);
//            county.setCityName(strings[2]);
//            Log.d("loadAllInFile","County"+county.getCityName()+county.getCityName()+county.getWeatherId());
//            sunnyWeatherDB.saveCounty(county);
//        }
        return true;
    }
//
    public static boolean loadProvinceInFile(SunnyWeatherDB sunnyWeatherDB, Context context) throws IOException {
        InputStream inputStream = context.getAssets().open("lisy.csv");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        Set<String> set = new HashSet<>();;
        int n = 3;
        while(n-- > 1){
            bufferedReader.readLine();
        }
        while((line = bufferedReader.readLine())!=null) {
            String[] strings = line.split(",");
            set.add(strings[7]);
        }
        for(String provinceName:set){
            Province province = new Province();
            province.setProvinceName(provinceName);
            Log.d("loadProvinceInFile","用sunnyWeatherDB存Pronvince"+province.getProvinceName());
            sunnyWeatherDB.saveProvince(province);
        }
        return true;
    }

    public static boolean loadAllCitiesInFile(SunnyWeatherDB sunnyWeatherDB, Context context) throws IOException  {
        InputStream inputStream = context.getAssets().open("lisy.csv");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        Set<City> set = new HashSet<>();
        int n = 3;
        while(n-- > 1){
            bufferedReader.readLine();
        }
        while((line = bufferedReader.readLine())!=null){
            String[]strings = line.split(",");
            City city = new City();
            city.setCityName(strings[9]);
            city.setProvinceName(strings[7]);
            //city.setCityCode(strings[0]);
            set.add(city);
        }
        for(City city:set){
            sunnyWeatherDB.saveCity(city);
            Log.d("loadCityInFile",city.getCityName()+city.getProvinceName());
        }
        return true;
    }

    public static boolean loadCountyInFile(SunnyWeatherDB sunnyWeatherDB, Context context) throws IOException  {
        InputStream inputStream = context.getAssets().open("lisy.csv");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        Set<County> set = new HashSet<>();
        int n = 3;
        while(n-- > 1){
            bufferedReader.readLine();
        }

        while((line = bufferedReader.readLine())!=null){
            String[]strings = line.split(",");
            County county = new County();
            county.setWeatherId(strings[0]);
            county.setCountyName(strings[2]);
            county.setCityName(strings[9]);
            set.add(county);
        }
        for(County county:set){
            Log.d("loadCountyInFile",county.getCityName()+county.getCityName()+county.getWeatherId());
            sunnyWeatherDB.saveCounty(county);
        }
        return true;
    }
}
