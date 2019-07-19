package com.icbc.personalfinancial.entity;


import lombok.Data;

import java.util.SplittableRandom;

@Data
public class Manger {

    private Integer id;
    private String account;
    private String password;
    private String level;
    private Integer belongbank;
}
