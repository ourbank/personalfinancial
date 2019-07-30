package com.icbc.personalfinancial.entity;

import lombok.Data;

import java.io.Serializable;


@Data
public class User implements Serializable {

    private  Integer id;
    private  String userName;
    private  String userId;
    private  String addr;
    private  String mobile;

}
