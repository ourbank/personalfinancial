package com.icbc.index.model;

import org.springframework.data.annotation.Transient;

import java.io.Serializable;

/*
 * 对应于数据库的user表
 * @author mychen
 * */
public class Manager implements Serializable {

    private static final long serialVersionUID = 1L;
    private String account;
    private String password;
    private String belongBank;
    private int level;

    @Transient
    private String token;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBelongBank() {
        return belongBank;
    }

    public void setBelongBank(String belongBank) {
        this.belongBank = belongBank;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


}
