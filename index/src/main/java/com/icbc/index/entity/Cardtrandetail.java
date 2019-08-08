package com.icbc.index.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Cardtrandetail {
    private Integer id;
    private String cardId;
    private Date createTime;
    private Double tranMoney;
    private Integer tranType;
    private Double serviceCharge;
}
