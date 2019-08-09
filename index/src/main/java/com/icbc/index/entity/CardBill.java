package com.icbc.index.entity;


import lombok.Data;

import java.util.Date;

@Data
public class CardBill {

    private Integer id;
    private Integer bankId;
    private Date day;
    private Integer num;
}
