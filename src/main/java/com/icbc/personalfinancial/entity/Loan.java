package com.icbc.personalfinancial.entity;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Loan implements Serializable {

    private  Integer id;
    private  Integer accountId;
    private  BigDecimal loanMoney;
    private  Integer longType;
    private  Date createTime;
    private  BigDecimal serviceCharge;
    private  Integer bankId;
}
