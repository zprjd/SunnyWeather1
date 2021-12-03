package com.example.sunnyweather.gson;

import java.io.Serializable;
import java.util.List;

/*"data":[
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
            "win":["东南风","西南风"],
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
                    "win_speed":"<3级"},
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
                    "desc":"辐射弱，涂擦SPF8-12防晒护肤品。"},
                    {
                        "title":"减肥指数",
                        "level":"一颗星",
                        "desc":"春天快来了，雨天坚持室内运动吧。"
                    },
                    {
                        "title":"血糖指数",
                        "level":"易波动",
                        "desc":"气温多变，血糖易波动，请注意监测。"},
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
    ]*/
public class everyData  implements Serializable {
    private String day;
    private String date;
    private String week;
    private String wea;
    private String tem;
    private String tem1;
    private String tem2;
    private String air_level;
    private String air_tips;
    private String wea_img;
    private String humidity;
    private String visibility;
    private String pressure;
    private String win[];
    private String win_speed;
    private String sunrise;
    private String sunset;
    private List<everyIndex> index;
    private List<everyHour> hours;  //改名为List更好理解，但是JSON不允许

    public void setDay(String day) {
        this.day = day;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public void setWea(String wea) {
        this.wea = wea;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public void setTem1(String tem1) {
        this.tem1 = tem1;
    }

    public void setTem2(String tem2) {
        this.tem2 = tem2;
    }

    public void setAir_level(String air_level) {
        this.air_level = air_level;
    }

    public void setAir_tips(String air_tips) {
        this.air_tips = air_tips;
    }

    public void setWea_img(String wea_img) {
        this.wea_img = wea_img;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public int getVisibility() {
        return Integer.parseInt(visibility);
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public void setWin(String[] win) {
        this.win = win;
    }

    public void setWin_speed(String win_speed) {
        this.win_speed = win_speed;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public void setIndex(List<everyIndex> index) {
        this.index = index;
    }

    public void setHours(List<everyHour> hours) {
        this.hours = hours;
    }

    public String getWin_speed() {
        return win_speed;
    }


    public String getHumidity() {
        return humidity;
    }

    public String getVisibility1() {
        return visibility;
    }

    public String getPressure() {
        return pressure;
    }

    public String[] getWin() {
        return win;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }


    public String getTem1() {
        return tem1;
    }

    public String getWea_img() {
        return wea_img;
    }

    public String getTem2() {
        return tem2;
    }

    public String getDay() {
        return day;
    }

    public String getDate() {
        return date;
    }

    public String getWeek() {
        return week;
    }

    public String getWea() {
        return wea;
    }

    public String getTem() {
        return tem;
    }

    public String getAir_level() {
        return air_level;
    }

    public String getAir_tips() {
        return air_tips;
    }


    public List<everyIndex> getIndex() {
        return index;
    }

    public List<everyHour> getHours() {
        return hours;
    }


}
