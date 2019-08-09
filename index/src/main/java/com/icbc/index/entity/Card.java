package com.icbc.index.entity;


import java.io.Serializable;
import java.util.Date;


public class Card implements Serializable {

    private  Integer id;
    private  String cardId;
    private  Integer cardType;
    private  Integer belongAccountId;
    private  Date createTime;
    private  Double serviceCharge;
    private  Integer bankId;
    private  Double cardMoney;
    private  Date lastestTime;

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

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public Integer getBelongAccountId() {
        return belongAccountId;
    }

    public void setBelongAccountId(Integer belongAccountId) {
        this.belongAccountId = belongAccountId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(Double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public Double getCardMoney() {
        return cardMoney;
    }

    public void setCardMoney(Double cardMoney) {
        this.cardMoney = cardMoney;
    }

    public Date getLastestTime() {
        return lastestTime;
    }

    public void setLastestTime(Date lastestTime) {
        this.lastestTime = lastestTime;
    }
}
