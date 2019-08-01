package com.icbc.index.model;

import java.util.Date;

public class CardData {
    private int cardNum;
    private String bankName;
    private Date time;
    public CardData() {
    }
    public CardData(int cardNum, String bankName, Date time) {
        this.cardNum = cardNum;
        this.bankName = bankName;
        this.time = time;
    }

    public int getCardNum() {
        return cardNum;
    }

    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "CardData{" +
                "cardNum=" + cardNum +
                ", bankName='" + bankName + '\'' +
                ", time=" + time +
                '}';
    }
}
