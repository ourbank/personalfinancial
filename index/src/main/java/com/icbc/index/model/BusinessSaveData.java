package com.icbc.index.model;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class BusinessSaveData {

    private String day;
    private BigDecimal num;
    private String tableCol;
}
