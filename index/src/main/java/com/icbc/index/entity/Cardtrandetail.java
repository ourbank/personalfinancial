package com.icbc.index.entity;

import java.util.Date;

public class Cardtrandetail {
    private Integer id;
    private String cardId;
    private Date createTime;
    private Double tranMoney;
    private Integer tranType;
    private Double serviceCharge;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Double getTranMoney() {
        return tranMoney;
    }

    public void setTranMoney(Double tranMoney) {
        this.tranMoney = tranMoney;
    }

    public Integer getTranType() {
        return tranType;
    }

    public void setTranType(Integer tranType) {
        this.tranType = tranType;
    }

    public Double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(Double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }
}
