package com.icbc.index.entity;


import java.io.Serializable;


public class Account implements Serializable {

    private  Integer id;
    private  String accountName;
    private  String userId;

    public Integer getId() {
        return id;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getUserId() {
        return userId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
