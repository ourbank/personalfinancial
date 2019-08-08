package com.icbc.index.entity;


import lombok.Data;

import java.util.Date;

@Data
public class Loandetail {

    private Integer id;
    private Integer bankId;
    private Integer loanId;
    private Double tranMoney;
    private Double owe;
    private Date creatTime;
}
