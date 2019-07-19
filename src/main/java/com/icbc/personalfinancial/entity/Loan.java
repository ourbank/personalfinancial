package com.icbc.personalfinancial.entity;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class Loan {

    private  Integer id;
    private  Integer accountId;
    private  Double loanMoney;
    private  Integer longType;
    private  Timestamp createTime;
    private  Double serviceCharge;
    private  Integer bankId;
}
