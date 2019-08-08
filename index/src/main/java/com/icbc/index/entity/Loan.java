package com.icbc.index.entity;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Loan implements Serializable {

    private  Integer id;
    private  Integer accountId;
    private  Double loanMoney;
    private  Integer longType;
    private  Date createTime;
    private  Double serviceCharge;
    private  Integer bankId;
    private  Integer flag;
}
