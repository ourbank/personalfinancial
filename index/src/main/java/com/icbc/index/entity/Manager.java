package com.icbc.index.entity;


import lombok.Data;

import java.io.Serializable;

@Data
public class Manager implements Serializable {

    private Integer id;
    private String account;
    private String password;
    private String level;
    private Integer belongbank;
}
