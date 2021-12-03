package com.example.sunnyweather.model;

/*City实体类，方便对City操作
* 用于获取和设置相应的字段*/
public class City {
    private int id;
    private String cityName;//市名
    private String cityCode;//LocationId
    private String provinceName;//市所属省的名字
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityNanme) {
        this.cityName = cityNanme;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityCode() {
        return cityCode;
    }


    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", cityName='" + cityName + '\'' +
                ", cityCode=" + cityCode +
                ", provinceName='" + provinceName + '\'' +
                '}';
    }
}
