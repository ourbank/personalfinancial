package com.icbc.index.entity;

import java.io.Serializable;


public class Bank implements Serializable {

    private  Integer id;
    private  String bankName;
    private  Integer fatherBankId;
    private  String bankAddr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getFatherBankId() {
        return fatherBankId;
    }

    public void setFatherBankId(Integer fatherBankId) {
        this.fatherBankId = fatherBankId;
    }

    public String getBankAddr() {
        return bankAddr;
    }

    public void setBankAddr(String bankAddr) {
        this.bankAddr = bankAddr;
    }
}
