package com.icbc.index.entity;

import java.util.Date;

public class metal {
    private Integer id;
    private Enum metalType;
    private Integer money;
    private Integer serviceCharge;
    private Date createTime;
    private Integer bankid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Enum getMetalType() {
        return metalType;
    }

    public void setMetalType(Enum metalType) {
        this.metalType = metalType;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(Integer serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getBankid() {
        return bankid;
    }

    public void setBankid(Integer bankid) {
        this.bankid = bankid;
    }
}
