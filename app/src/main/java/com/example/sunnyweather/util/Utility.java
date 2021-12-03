package com.example.sunnyweather.util;

import android.text.TextUtils;
import android.util.Log;

import com.example.sunnyweather.db.SunnyWeatherDB;
import com.example.sunnyweather.gson.Weather;
import com.example.sunnyweather.model.City;
import com.example.sunnyweather.model.County;
import com.example.sunnyweather.model.Province;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/*province:
[{"id":1,"name":"北京"},{"id":2,"name":"上海"},{"id":3,"name":"天津"},{"id":4,"name":"重庆"}]
city:

county:

*/
/*将通用的发起HTTP请求以及解析服务器返回的数据操作提取到一个公共的类里，并提供操作静态方法。
 * Utility工具类中提供解析和处理服务器返回的JSON数据操作静态方法*/
public class Utility {
    /*解析处理服务器返回的省级、市级和县级数据
      将服务器返回的全部省、市或县都存入SunnyWeatherDB数据库
    * 自增的id不用管？*/
    /*将服务器返回的省份名字存入数据库*/
    /*province:
    [{"id":1,"name":"北京"},{"id":2,"name":"上海"},{"id":3,"name":"天津"},{"id":4,"name":"重庆"}]*/
    public static boolean handleProvinceResponse(SunnyWeatherDB sunnyWeatherDB,String response) throws JSONException {
        if(!TextUtils.isEmpty(response)){
            Log.d("Utility response",response);
            JSONArray allProvinces = new JSONArray(response);
            for(int i = 0 ;i < allProvinces.length();i++){
                JSONObject provinceObject = allProvinces.getJSONObject(i);
                Province province = new Province();
                province.setProvinceName(provinceObject.getString("name"));
                province.setProvinceCode(provinceObject.getInt("id"));
                sunnyWeatherDB.saveProvince(province);
            }
            return  true;
        }
        return false;
    }
    /*将服务器返回provinceId省下属的市名字名字存入数据库*/
    /*city:
    * */
    public static boolean handleCitiesResponse(SunnyWeatherDB sunnyWeatherDB,String response,String provinceName) throws JSONException {
        if(!TextUtils.isEmpty(response)){
            Log.d("Utility response",response);
            JSONArray allCities = new JSONArray(response);
            for(int i = 0 ;i < allCities.length();i++){
                JSONObject cityObject = allCities.getJSONObject(i);
                City city = new City();
                city.setCityName(cityObject.getString("name"));
                city.setCityCode(cityObject.getString("id"));
                city.setProvinceName(provinceName);
                sunnyWeatherDB.saveCity(city);
            }
            return true;
        }
        return false;
    }
    /*处理服务器返回cityId市下属的县名字的JSON数据
    将该市下属所有县名字存入数据库*/
    public static boolean handleCountiesResponse(SunnyWeatherDB sunnyWeatherDB,String response,String cityName) throws JSONException {
        if(!TextUtils.isEmpty(response)){
            Log.d("Utility response",response);
            JSONArray allCounties = new JSONArray(response);
            for(int i = 0 ;i < allCounties.length();i++){
                JSONObject countyObject = allCounties.getJSONObject(i);
                County county = new County();
                county.setCountyName(countyObject.getString("name"));
                county.setWeatherId(countyObject.getString("id"));
                //changeCountiesWeatherId("https://djapi.cn/citylist_json");
                county.setCityName(cityName);
                sunnyWeatherDB.saveCounty(county);
            }
            return true;
        }
        return false;
    }
    /*public static String changeCountiesWeatherId( String targetAddress) throws JSONException {
        if(!TextUtils.isEmpty(targetAddress)){
            Log.d("changeCountiesWeatherId",targetAddress);
            JSONArray allCounties = new JSONArray(targetAddress);
            for(int i = 0 ;i < allCounties.length();i++){
                JSONObject countyObject = allCounties.getJSONObject(i);
                County county = new County();
                county.setWeatherId(countyObject.getString("code"));
                ContentValues values = new ContentValues();
                values.put("weatherid",countyObject.getString("code"));
            }
            return ;
        }
        return null;
    }*/
    /*将返回的JSON数据解析成Weather实体类*/
    public static Weather handleWeatherResponse(String response) throws JSONException {
        //Log.d("handleWeatherResponse",response);
        JSONObject jsonObject = new JSONObject(response);

        Weather weather = new Gson().fromJson(response,Weather.class);
        return weather;
    }
}
