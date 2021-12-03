package com.example.sunnyweather.gson;

import java.io.Serializable;

/*
* "hours":[
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
     ],*/
public class everyHour  implements Serializable {
    private String hours;   //本来是time
    private String tem;
    private String win;
    private String win_speed;
    private String wea;
    private String wea_img;

    public String getWea_img() {
        return wea_img;
    }

    public void setWea_img(String wea_img) {
        this.wea_img = wea_img;
    }



    public String getWin_speed() {
        return win_speed;
    }

    public void setWin_speed(String win_speed) {
        this.win_speed = win_speed;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getWea() {
        return wea;
    }

    public void setWea(String wea) {
        this.wea = wea;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }
}
