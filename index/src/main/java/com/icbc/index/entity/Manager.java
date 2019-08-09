package com.icbc.index.entity;


import java.io.Serializable;


public class Manager implements Serializable {


    private String account;
    private String password;
    private String level;
    private Integer belongBank;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getBelongBank() {
        return belongBank;
    }

    public void setBelongBank(Integer belongBank) {
        this.belongBank = belongBank;
    }
}
