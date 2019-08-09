package com.icbc.index.entity;

import java.util.Date;
import java.util.Set;

public class History {
    private  Integer id;
    private Date creatTime;
    private Set<String >cityList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public Set<String> getCityList() {
        return cityList;
    }

    public void setCityList(Set<String> cityList) {
        this.cityList = cityList;
    }
}
