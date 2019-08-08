package com.icbc.index.entity;

import lombok.Data;

import java.util.Date;

@Data
public class metal {
    private Integer id;
    private Enum metalType;
    private Integer money;
    private Integer serviceCharge;
    private Date createTime;
    private Integer bankid;

}
