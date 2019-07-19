package com.icbc.personalfinancial.entity;


import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;


@Data
public class Card {

    private  Integer id;
    private  String cardId;
    private  Integer cardType;
    private  Integer belongAccountId;
    private  Date createTime;
    private  Double serviceCharge;
    private  Integer bankId;

}
