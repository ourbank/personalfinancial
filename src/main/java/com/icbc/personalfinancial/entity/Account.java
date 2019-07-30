package com.icbc.personalfinancial.entity;


import lombok.Data;

import java.io.Serializable;

@Data
public class Account implements Serializable {

    private  Integer id;
    private  String accountName;
    private  String userId;
}
