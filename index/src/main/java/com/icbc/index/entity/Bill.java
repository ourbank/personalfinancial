package com.icbc.index.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Bill {
    private Integer id;
    private Integer bankId;
    private Date day;
    private BigDecimal num;
    private Enum business;
}
