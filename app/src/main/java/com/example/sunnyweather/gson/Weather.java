package com.example.sunnyweather.gson;

import java.io.Serializable;
import java.util.List;

public class Weather implements Serializable {
    private String cityid;
    private String city; //本来想写county，可是JSON中是city。。。。
    private String update_time;
    private List<everyData>  data;  //本来想写dataList，可是JSON中是data

    @Override
    public String toString() {
        return "Weather{" +
                "cityid='" + cityid + '\'' +
                ", county='" + city + '\'' +
                ", update_time='" + update_time + '\'' +
                ", data=" + data +
                '}';
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public List<everyData> getData() {
        return data;
    }

    public void setData(List<everyData> data) {
        this.data = data;
    }
}

/*{
    "cityid":"101110101",
    "city":"西安",
    "cityEn":"xian",
    "country":"中国",
    "countryEn":"China",
    "update_time":"2021-08-31 20:33:35",
    "data":[
        {
            "day":"31日（星期二）",
            "date":"2021-08-31",
            "week":"星期二",
            "wea":"雾转大雨",
            "wea_img":"yu",
            "wea_day":"雾",
            "wea_day_img":"wu",
            "wea_night":"大雨",
            "wea_night_img":"yu",
            "tem":"20℃",
            "tem1":"21℃",
            "tem2":"19℃",
            "humidity":"93%",
            "visibility":"5km",
            "pressure":"969",
            "win":[
                "东南风",
                "西南风"
                ],
            "win_speed":"<3级",
            "win_meter":"1km\/h",
            "sunrise":"06:16",
            "sunset":"19:12",
            "air":"14",
            "air_level":"优",
            "air_tips":"空气很好，可以外出活动，呼吸新鲜空气，拥抱大自然！",
            "alarm":
            {
                "alarm_type":"",
                "alarm_level":"",
                "alarm_content":""
             },
             "hours":[
                {
                    "hours":"20时",
                    "wea":"小雨",
                    "wea_img":"yu",
                    "tem":"20",
                    "win":"东南风",
                    "win_speed":"<3级"
                },
                {
                    "hours":"21时",
                    "wea":"小雨",
                    "wea_img":"yu",
                    "tem":"20",
                    "win":"东北风",
                    "win_speed":"<3级"
                },
                {
                    "hours":"22时",
                    "wea":"小雨",
                    "wea_img":"yu",
                    "tem":"20",
                    "win":"东风",
                    "win_speed":"<3级"
                },
                {
                    "hours":"23时",
                    "wea":"小雨",
                    "wea_img":"yu",
                    "tem":"20",
                    "win":"东风",
                    "win_speed":"<3级"
                },
                {
                    "hours":"00时",
                    "wea":"中雨",
                    "wea_img":"yu",
                    "tem":"20",
                    "win":"东风",
                    "win_speed":"<3级"
                },
                {
                    "hours":"01时",
                    "wea":"中雨",
                    "wea_img":"yu",
                    "tem":"20",
                    "win":"东南风",
                    "win_speed":"<3级"
                },{
                    "hours":"02时",
                    "wea":"中雨",
                    "wea_img":"yu",
                    "tem":"20",
                    "win":"北风",
                    "win_speed":"<3级"
                },
                {
                    "hours":"03时",
                    "wea":"中雨",
                    "wea_img":"yu",
                    "tem":"20",
                    "win":"南风",
                    "win_speed":"<3级"
                },
                {
                    "hours":"04时",
                    "wea":"中雨",
                    "wea_img":"yu",
                    "tem":"20",
                    "win":"南风",
                    "win_speed":"<3级"
                },
                {
                    "hours":"05时",
                    "wea":"中雨",
                    "wea_img":"yu",
                    "tem":"19",
                    "win":"西北风",
                    "win_speed":"<3级"
                },
                {
                    "hours":"06时",
                    "wea":"中雨",
                    "wea_img":"yu",
                    "tem":"20",
                    "win":"南风",
                    "win_speed":"<3级"
                },
                {
                    "hours":"07时",
                    "wea":"中雨",
                    "wea_img":"yu",
                    "tem":"20",
                    "win":"南风",
                    "win_speed":"<3级"
                }
             ],
            "index":[
                {
                    "title":"紫外线指数",
                    "level":"最弱",
                    "desc":"辐射弱，涂擦SPF8-12防晒护肤品。"
                 },
                {
                    "title":"减肥指数",
                    "level":"一颗星",
                    "desc":"春天快来了，雨天坚持室内运动吧。"
                },
                {
                    "title":"血糖指数",
                    "level":"易波动",
                    "desc":"气温多变，血糖易波动，请注意监测。"
                 },
                {
                    "title":"穿衣指数",
                    "level":"较舒适",
                    "desc":"建议穿薄外套或牛仔裤等服装。"
                },
                {
                    "title":"洗车指数",
                    "level":"不宜",
                    "desc":"有雨，雨水和泥水会弄脏爱车。"
                },
                {
                    "title":"空气污染扩散指数",
                    "level":"优",
                    "desc":"气象条件非常有利于空气污染物扩散。"
                }
            ]
     },
     {
        "day":"01日（星期三）",
        "date":"2021-09-01",
        "week":"星期三",
        .....
    ]
 */
/*
 * {
 *   "code":"200",
 *   "updateTime":"2021-08-31T21:02+08:00",
 *   "fxLink":"http://hfx.link/2ax1",
 *   "now":{
 *       "obsTime":"2021-08-31T20:53+08:00",
 *       "temp":"22","feelsLike":"23",
 *       "icon":"150","text":"晴",
 *       "wind360":"225",
 *       "windDir":"西南风",
 *       "windScale":"1",
 *       "windSpeed":"4",
 *       "humidity":"76",
 *       "precip":"0.0",
 *       "pressure":"1011",
 *       "vis":"25",
 *       "cloud":"3",
 *       "dew":"20"
 *   },
 *   "refer":{
 *       "sources":[
 *           "Weather China"
 *       ],
 *       "license":[
 *           "no commercial use"
 *       ]
 *   }
 * }*/

