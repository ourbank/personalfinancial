package com.icbc.index.model;

import java.util.Date;

public class CardData {
    private double cardNum;
    private String bankName;
    private Date time;
    public CardData() {
    }
    public CardData(double cardNum, String bankName, Date time) {
        this.cardNum = cardNum;
        this.bankName = bankName;
        this.time = time;
    }

    public double getCardNum() {
        return cardNum;
    }

    public void setCardNum(double cardNum) {
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
