package com.example.sunnyweather.model;

/*Province实体类，用于对Province进行操作
* 用于获取和设置相应的字段*/

public class Province {
    private int id;
    private String provinceName;//省名
    private int provinceCode;//省代号

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
