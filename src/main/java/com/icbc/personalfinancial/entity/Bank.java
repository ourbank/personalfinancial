package com.icbc.personalfinancial.entity;


import lombok.Data;

@Data
public class Bank {

    private  Integer id;
    private  String bankName;
    private  Integer fatherBankId;
    private  String bankAddr;
}
