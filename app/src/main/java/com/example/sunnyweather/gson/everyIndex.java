package com.example.sunnyweather.gson;

import java.io.Serializable;

/*
* "index":[
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
    ]*/
public class everyIndex  implements Serializable {
    private String title;
    private String level;
    private String desc;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
