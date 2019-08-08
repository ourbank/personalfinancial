package com.icbc.index.entity;


import lombok.Data;

import java.io.Serializable;

@Data
public class Bank implements Serializable {

    private  Integer id;
    private  String bankName;
    private  Integer fatherBankId;
    private  String bankAddr;
}
