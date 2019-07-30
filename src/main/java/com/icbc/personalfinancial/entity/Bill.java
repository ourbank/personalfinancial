package com.icbc.personalfinancial.entity;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Bill {
    private Integer id;
    private Integer accountId;
    private BigDecimal loanMoney;
    private  Integer loanType;
    private Date creatTime;
    private BigDecimal  serviceCharge;
    private  Integer bankId;
}
