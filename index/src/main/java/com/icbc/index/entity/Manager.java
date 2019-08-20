package com.icbc.index.entity;


import org.springframework.data.annotation.Transient;

import java.io.Serializable;


public class Manager implements Serializable {


    private String account;
    private String password;
    private String level;
    private int belongBank;
    private int id;
    @Transient
    private String token;
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

    public int getBelongBank() {
        return belongBank;
    }

    public void setBelongBank(int belongBank) {
        this.belongBank = belongBank;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
