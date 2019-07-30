package com.icbc.personalfinancial.entity;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;


@Data
public class Card implements Serializable {

    private  Integer id;
    private  String cardId;
    private  Integer cardType;
    private  Integer belongAccountId;
    private  Date createTime;
    private  BigDecimal serviceCharge;
    private  Integer bankId;
    private  BigDecimal cardMoney;
    private  Date lastestTime;
}
